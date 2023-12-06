package com.aceteam.tm.post.facade.server;

import com.aceteam.tm.post.facade.dto.PostLabelDTO;
import com.liang.nansheng.common.auth.UserSsoDTO;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface PostLabelService {
    /**
     * Create Post Label Connection Information
     * @param labelIds
     * @param articleId
     * @param currentUser
     * @return
     */
    Boolean create(List<Integer> labelIds, Integer articleId, UserSsoDTO currentUser);

    /**
     * Update Post Label Connection Information
     * @param labelIds
     * @param articleId
     * @param currentUser
     * @return
     */
    Boolean update(List<Integer> labelIds, Integer articleId, UserSsoDTO currentUser);


    /**
     * Update Post Label Connection Information
     * @param labelIds
     * @return
     */
    List<PostLabelDTO> getByLabelIds(List<Integer> labelIds);


    /**
     * Get Post Label Information by Post Id
     * @param postIds
     * @return
     */
    List<PostLabelDTO> getByPostIds(List<Integer> postIds);

    /**
     * Get Label Use Count
     * @param labelId
     * @return
     */
    Long getCountByLabelId(Integer labelId);
}
