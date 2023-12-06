package com.aceteam.tm.post.service.impl;

import com.aceteam.tm.post.facade.dto.PostLabelDTO;
import com.aceteam.tm.post.facade.server.PostLabelService;
import com.aceteam.tm.post.persistence.entity.PostLabelPo;
import com.aceteam.tm.post.persistence.entity.PostLabelPoExample;
import com.aceteam.tm.post.persistence.mapper.PostLabelPoExMapper;
import com.aceteam.tm.post.persistence.mapper.PostLabelPoMapper;
import com.aceteam.tm.post.service.mapstruct.PostLabelMS;
import com.liang.nansheng.common.auth.UserSsoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: some desc
 * @author: haoran
 */

@Slf4j
@Component
@Service
public class PostLabelServiceImpl implements PostLabelService {
    @Autowired
    private PostLabelPoMapper postLabelPoMapper;

    @Autowired
    private PostLabelPoExMapper postLabelPoExMapper;

    /**
     * Added file label relationship information
     *
     * @param labelIds
     * @param postId
     * @param currentUser
     * @return
     */
    @Override
    public Boolean create(List<Integer> labelIds, Integer postId, UserSsoDTO currentUser) {
        LocalDateTime now = LocalDateTime.now();
        labelIds.forEach(labelId -> {
            PostLabelPo postLabelPo = new PostLabelPo();
            postLabelPo.setPostId(postId);
            postLabelPo.setLabelId(labelId);
            postLabelPo.setCreateUser(currentUser.getUserId());
            postLabelPo.setUpdateUser(currentUser.getUserId());
            postLabelPo.setCreateTime(now);
            postLabelPo.setUpdateTime(now);
            postLabelPo.setIsDeleted(false);
            postLabelPoMapper.insertSelective(postLabelPo);
        });
        return true;
    }

    /**
     * Update file tag relationship information
     *
     * @param labelIds
     * @param postId
     * @param currentUser
     * @return
     */
    @Override
    public Boolean update(List<Integer> labelIds, Integer postId, UserSsoDTO currentUser) {
        // Get article tag information based on post id collection
        List<PostLabelDTO> postLabelDTOS = getByPostIds(Collections.singletonList(postId));
        List<Integer> labelIdsOld = postLabelDTOS.stream().distinct().map(PostLabelDTO::getLabelId).collect(Collectors.toList());

        // new
        List<Integer> labelIdsCreate = new ArrayList<>();
        labelIds.forEach(labelId -> {
            if (!labelIdsOld.contains(labelId)) {
                labelIdsCreate.add(labelId);
            }
        });
        create(labelIdsCreate, postId, currentUser);

        // need to be deleted
        labelIdsOld.forEach(labelId -> {
            if (!labelIds.contains(labelId)) {
                PostLabelPoExample example = new PostLabelPoExample();
                example.createCriteria().andPostIdEqualTo(postId)
                        .andLabelIdEqualTo(labelId)
                        .andIsDeletedEqualTo(false);

                PostLabelPo postLabelPo = new PostLabelPo();
                postLabelPo.setUpdateUser(currentUser.getUserId());
                postLabelPo.setUpdateTime(LocalDateTime.now());
                postLabelPo.setIsDeleted(true);
                postLabelPoMapper.updateByExampleSelective(postLabelPo, example);
            }
        });

        return true;
    }

    /**
     * Get post tag information based on tag id
     *
     * @param labelIds
     * @return
     */
    @Override
    public List<PostLabelDTO> getByLabelIds(List<Integer> labelIds) {
        PostLabelPoExample example = new PostLabelPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andLabelIdIn(labelIds);

        return PostLabelMS.INSTANCE.toDTO(postLabelPoMapper.selectByExample(example));
    }

    /**
     * Get post tag information based on article id collection
     *
     * @param postIds
     * @return
     */
    @Override
    public List<PostLabelDTO> getByPostIds(List<Integer> postIds) {
        PostLabelPoExample example = new PostLabelPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andPostIdIn(postIds);

        return PostLabelMS.INSTANCE.toDTO(postLabelPoMapper.selectByExample(example));
    }

    /**
     * Get the number of tags used
     *
     * @param labelId
     * @return
     */
    @Override
    public Long getCountByLabelId(Integer labelId) {
        return postLabelPoExMapper.countByLabelId(labelId);
    }

}
