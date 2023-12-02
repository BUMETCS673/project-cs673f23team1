package com.aceteam.tm.user.service.impl;

import com.aceteam.tm.post.facade.dto.PostDTO;
import com.aceteam.tm.post.facade.server.PostService;
import com.aceteam.tm.user.persistence.entity.LikePo;
import com.aceteam.tm.user.persistence.entity.LikePoExample;
import com.aceteam.tm.user.persistence.mapper.LikePoExMapper;
import com.aceteam.tm.user.persistence.mapper.LikePoMapper;
import com.acteam.tm.user.facade.dto.LikeDTO;
import com.acteam.tm.user.facade.dto.LikeSearchDTO;
import com.acteam.tm.user.facade.server.LikeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikePoMapper likePoMapper;

    @Autowired
    private LikePoExMapper likePoExMapper;

    @Reference
    private PostService postService;

    /**
     * 获取所有点赞的通过审核的文章信息
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<LikeDTO> getPaasAll(LocalDateTime startTime, LocalDateTime endTime) {
        return LikeMS.INSTANCE.toDTO(likePoExMapper.selectAllPost(startTime, endTime));
    }

    /**
     * 通过用户id获取点赞的文章信息
     *
     * @param likeSearchDTO
     * @return
     */
    @Override
    public PageInfo<LikeDTO> getPostByUserId(LikeSearchDTO likeSearchDTO) {
        if (likeSearchDTO.getLikeUser() == null) {
            throw BusinessException.build(ResponseCode.NOT_EXISTS, "参数不合规");
        }

        PageHelper.startPage(likeSearchDTO.getCurrentPage(), likeSearchDTO.getPageSize());
        List<LikePo> likePos = likePoExMapper.selectPostByUserId(likeSearchDTO.getLikeUser());

        return LikeMS.INSTANCE.toPage(new PageInfo<>(likePos));
    }

    /**
     * 通过文章id获取点赞的用户信息
     *
     * @param likeSearchDTO
     * @return
     */
    @Override
    public PageInfo<LikeDTO> getUserByPostId(LikeSearchDTO likeSearchDTO) {
        if (likeSearchDTO.getPostId() == null) {
            throw BusinessException.build(ResponseCode.NOT_EXISTS, "参数不合规");
        }
        LikePoExample example = new LikePoExample();
        LikePoExample.Criteria criteria = example.createCriteria().andStateEqualTo(true);
        criteria.andPostIdEqualTo(likeSearchDTO.getPostId());
        example.setOrderByClause("`id` desc");

        PageHelper.startPage(likeSearchDTO.getCurrentPage(), likeSearchDTO.getPageSize());
        List<LikePo> likePos = likePoMapper.selectByExample(example);

        return LikeMS.INSTANCE.toPage(new PageInfo<>(likePos));
    }

    /**
     * 通过id获取点赞信息
     *
     * @param id
     * @return
     */
    @Override
    public LikeDTO getById(Integer id) {
        return LikeMS.INSTANCE.toDTO(likePoMapper.selectByPrimaryKey(id));
    }

    /**
     * 通过文章id和用户id获取点赞信息
     *
     * @param postId
     * @param userId
     * @return
     */
    @Override
    public LikeDTO getByPostIdUserId(Integer postId, Long userId) {
        LikePoExample example = new LikePoExample();
        example.createCriteria().andPostIdEqualTo(postId).andLikeUserEqualTo(userId);
        List<LikeDTO> likeDTOS = LikeMS.INSTANCE.toDTO(likePoMapper.selectByExample(example));
        if (CollectionUtils.isEmpty(likeDTOS)) {
            return null;
        }
        return likeDTOS.get(0);
    }

    /**
     * 获取文章的点赞数量
     *
     * @param postIds
     * @return
     */
    @Override
    public Long getLikeCountPost(List<Integer> postIds) {
        LikePoExample example = new LikePoExample();
        example.createCriteria().andStateEqualTo(true)
                .andPostIdIn(postIds);
        return likePoMapper.countByExample(example);
    }

    /**
     * 是否点赞
     *
     * @param postId
     * @param userId
     * @return
     */
    @Override
    public Boolean isLike(Integer postId, Long userId) {
        LikePoExample example = new LikePoExample();
        example.createCriteria().andStateEqualTo(true)
                .andPostIdEqualTo(postId)
                .andLikeUserEqualTo(userId);
        return likePoMapper.countByExample(example) > 0;
    }

    /**
     * 更新点赞状态
     *
     * @param postId
     * @param currentUser
     * @return
     */
    @Override
    public Boolean updateLikeState(Integer postId, UserSsoDTO currentUser) {
        LikeDTO likeDTO = getByPostIdUserId(postId, currentUser.getUserId());
        LocalDateTime now = LocalDateTime.now();
        // 没有，新增
        if (likeDTO == null) {
            LikePo likePo = new LikePo();
            likePo.setPostId(postId);
            likePo.setState(true);
            likePo.setLikeUser(currentUser.getUserId());
            likePo.setCreateTime(now);
            likePo.setUpdateTime(now);
            if (likePoMapper.insertSelective(likePo) <= 0) {
                throw BusinessException.build(ResponseCode.OPERATE_FAIL, "添加点赞失败");
            }
        } else {
            // 状态取反
            likeDTO.setState(!likeDTO.getState());
            likeDTO.setUpdateTime(now);
            if (likePoMapper.updateByPrimaryKeySelective(LikeMS.INSTANCE.toPo(likeDTO)) <= 0) {
                throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新点赞状态失败");
            }
        }

        return true;
    }

    /**
     * 用户获取的点赞数量
     *
     * @param userId
     * @return
     */
    @Override
    public Long getUserLikeCount(Long userId) {
        List<PostDTO> postDTOS = postService.getByUserId(userId);
        if (CollectionUtils.isNotEmpty(postDTOS)) {
            List<Integer> postIds = postDTOS.stream().map(PostDTO::getId).collect(Collectors.toList());
            return this.getLikeCountPost(postIds);
        }

        return 0L;
    }

    @Override
    public Long getUserTheLikeCount(Long userId) {
        LikePoExample example = new LikePoExample();
        example.createCriteria().andStateEqualTo(true)
                .andLikeUserEqualTo(userId);
        List<LikePo> likePos = likePoMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(likePos)) {
            List<Integer> postIds = likePos.stream().map(LikePo::getPostId).collect(Collectors.toList());
            List<PostDTO> postDTOS = postService.getBaseByIds(postIds, PostStateEnum.enable);
            return CollectionUtils.isNotEmpty(postDTOS) ? postDTOS.size() : 0L;
        }

        return 0L;
    }
}
