package com.aceteam.tm.post.service.impl;

import com.aceteam.tm.common.enums.SortRuleEnum;
import com.aceteam.tm.post.facade.dto.CommentDTO;
import com.aceteam.tm.post.facade.dto.CommentSearchDTO;
import com.aceteam.tm.post.facade.server.CommentService;
import com.aceteam.tm.post.persistence.entity.CommentPo;
import com.aceteam.tm.post.persistence.entity.CommentPoExample;
import com.aceteam.tm.post.persistence.mapper.CommentPoExMapper;
import com.aceteam.tm.post.persistence.mapper.CommentPoMapper;
import com.aceteam.tm.post.service.mapstruct.CommentMS;
import com.aceteam.tm.post.service.utils.CommentTreeUtils;
import com.acteam.tm.user.facade.dto.UserLevelDTO;
import com.acteam.tm.user.facade.server.LikeCommentService;
import com.acteam.tm.user.facade.server.UserLevelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liang.manage.auth.facade.dto.user.UserDTO;
import com.liang.manage.auth.facade.server.UserService;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentPoMapper commentPoMapper;

    @Autowired
    private CommentPoExMapper commentPoExMapper;

    @Autowired
    private CommentService commentService;

    @Reference
    private UserService userService;

    @Reference
    private UserLevelService userLevelService;

    @Reference
    private LikeCommentService likeCommentService;

    /**
     * Getting information about comments on posts
     *
     * @param commentSearchDTO
     * @param currentUser
     * @return
     */
    @Override
    public List<CommentDTO> getCommentByArticleId(CommentSearchDTO commentSearchDTO, UserSsoDTO currentUser) {
        CommentPoExample example = new CommentPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andStateEqualTo(true)
                .andPostIdEqualTo(commentSearchDTO.getPostId());
        List<CommentDTO> commentDTOS = CommentMS.INSTANCE.toDTO(commentPoMapper.selectByExample(example));
        if (CollectionUtils.isNotEmpty(commentDTOS)) {
            // Constructing Review Information
            buildCommentInfo(commentDTOS, currentUser);
        }

        commentDTOS = CommentTreeUtils.toTree(commentDTOS);
        // Sort by number of likes and number of responses
        if (SortRuleEnum.hottest.equals(commentSearchDTO.getSortRule())) {
            commentDTOS = commentDTOS.stream()
                    .sorted(Comparator.comparing(CommentDTO::getLikeCount, Comparator.nullsLast(Comparator.reverseOrder()))
                            .thenComparing(CommentDTO::getRepliesCount, Comparator.nullsLast(Comparator.reverseOrder())))
                    .collect(Collectors.toList());
        } else if (SortRuleEnum.newest.equals(commentSearchDTO.getSortRule())) {
            commentDTOS = commentDTOS.stream()
                    .sorted(Comparator.comparing(CommentDTO::getCreateTime, Comparator.nullsLast(Comparator.reverseOrder())))
                    .collect(Collectors.toList());
        }

        return commentDTOS;
    }

    /**
     * Get information about comments on all moderated posts
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<CommentDTO> getAllPostComment(LocalDateTime startTime, LocalDateTime endTime) {
        return CommentMS.INSTANCE.toDTO(commentPoExMapper.getAllPostComment(startTime, endTime));
    }

    @Override
    public List<CommentDTO> getAllCommentReply(LocalDateTime startTime, LocalDateTime endTime) {
        return CommentMS.INSTANCE.toDTO(commentPoExMapper.getAllCommentReply(startTime, endTime));
    }

    /**
     * Get the latest reviews
     *
     * @param commentSearchDTO
     * @return
     */
    @Override
    public PageInfo<CommentDTO> getLatestComment(CommentSearchDTO commentSearchDTO) {
        PageHelper.startPage(commentSearchDTO.getCurrentPage(), commentSearchDTO.getPageSize());
        List<CommentPo> commentPos = commentPoExMapper.selectLatestComments(commentSearchDTO.getContent(), commentSearchDTO.getCommentUser());
        PageInfo<CommentDTO> pageInfo = CommentMS.INSTANCE.toPage(new PageInfo<>(commentPos));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            // Constructing Review Information
            buildCommentInfo(pageInfo.getList(), null);
        }

        return pageInfo;
    }

    /**
     * Get the number of comments on the post
     *
     * @param postId
     * @return
     */
    @Override
    public Long getCommentCountByPost(Integer postId) {
        CommentPoExample example = new CommentPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andStateEqualTo(true)
                .andPostIdEqualTo(postId);

        return commentPoMapper.countByExample(example);
    }

    /**
     * Getting the number of comments
     *
     * @return
     */
    @Override
    public Long getTotal() {
        CommentPoExample example = new CommentPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andStateEqualTo(true);
        return commentPoMapper.countByExample(example);
    }

    /**
     * Create a comment
     *
     * @param commentDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean create(CommentDTO commentDTO, UserSsoDTO currentUser) {
        LocalDateTime now = LocalDateTime.now();
        commentDTO.setIsDeleted(false);
        commentDTO.setState(true);
        commentDTO.setCommentUser(currentUser.getUserId());
        commentDTO.setCreateTime(now);
        commentDTO.setUpdateTime(now);
        if (commentPoMapper.insertSelective(CommentMS.INSTANCE.toPo(commentDTO)) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Failed to add comment");
        }

        return true;
    }

    /**
     * Delete comment
     *
     * @param commentId
     * @return
     */
    @Override
    public Boolean delete(Integer commentId) {
        List<Integer> commentIds = new ArrayList<>();
        List<CommentDTO> children = new ArrayList<>();
        // Get child comment information by parent ID
        this.getAllChildrenByPreId(children, commentId);
        if (CollectionUtils.isNotEmpty(children)) {
            commentIds.addAll(children.stream().map(CommentDTO::getId).collect(Collectors.toList()));
        }
        commentIds.add(commentId);
        CommentPoExample example = new CommentPoExample();
        example.createCriteria().andIdIn(commentIds);

        CommentPo commentPo = new CommentPo();
        commentPo.setIsDeleted(true);
        commentPo.setUpdateTime(LocalDateTime.now());
        if (commentPoMapper.updateByExampleSelective(commentPo, example) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Failed to delete comment");
        }
        return true;
    }

    /**
     * Get child comment information by parent ID
     *
     * @param result
     * @param preId
     * @return
     */
    @Override
    public void getAllChildrenByPreId(List<CommentDTO> result, Integer preId) {
        CommentPoExample example = new CommentPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andStateEqualTo(true)
                .andPreIdEqualTo(preId);
        List<CommentDTO> commentDTOS = CommentMS.INSTANCE.toDTO(commentPoMapper.selectByExample(example));
        if (CollectionUtils.isNotEmpty(commentDTOS)) {
            result.addAll(commentDTOS);
            commentDTOS.forEach(commentDTO -> {
                this.getAllChildrenByPreId(result, commentDTO.getId());
            });
        }
    }

    @Override
    public Integer getPostIdByCommentId(Integer commentId) {
        CommentPo commentPo = commentPoMapper.selectByPrimaryKey(commentId);
        return commentPo == null ? null : commentPo.getPostId();
    }

    @Override
    public CommentDTO getById(Integer commentId) {
        return CommentMS.INSTANCE.toDTO(commentPoMapper.selectByPrimaryKey(commentId));
    }

    /**
     * Constructing Review Information
     *
     * @param commentDTOS
     * @param currentUser
     */
    private void buildCommentInfo(List<CommentDTO> commentDTOS, UserSsoDTO currentUser) {
        // Get user information from the user id collection
        List<Long> userIds = commentDTOS.stream().map(CommentDTO::getCommentUser).collect(Collectors.toList());
        Map<Long, List<UserDTO>> idUsers = userService.getByIds(userIds).stream().collect(Collectors.groupingBy(UserDTO::getId));
        // For response count extraction
        Map<Integer, List<CommentDTO>> preIdMap = commentDTOS.stream().collect(Collectors.groupingBy(CommentDTO::getPreId));
        commentDTOS.forEach(commentDTO -> {
            if (idUsers.containsKey(commentDTO.getCommentUser())) {
                commentDTO.setCommentUserName(idUsers.get(commentDTO.getCommentUser()).get(0).getName());
                commentDTO.setPicture(idUsers.get(commentDTO.getCommentUser()).get(0).getPicture());
            }
            // Get User Levels
            List<UserLevelDTO> userLevelDTOS = userLevelService.getByUserId(commentDTO.getCommentUser());
            if (CollectionUtils.isNotEmpty(userLevelDTOS)) {
                commentDTO.setLevel(userLevelDTOS.get(0).getLevel());
            }
            // Get the number of post likes
            commentDTO.setLikeCount(likeCommentService.getLikeCountCommentId(commentDTO.getId()));
            // Has it been liked
            if (currentUser != null) {
                commentDTO.setIsLike(likeCommentService.isLike(commentDTO.getId(), currentUser.getUserId()));
            }
            // Number of responses
            if (preIdMap.containsKey(commentDTO.getId())) {
                commentDTO.setRepliesCount(preIdMap.get(commentDTO.getId()).size());
            }
        });
    }
}
