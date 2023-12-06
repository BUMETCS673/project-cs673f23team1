package com.aceteam.tm.post.facade.server;

import com.aceteam.tm.post.facade.dto.ResourceNavigateDTO;
import com.aceteam.tm.post.facade.dto.ResourceNavigateSearchDTO;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.auth.UserSsoDTO;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface ResourceNavigateService {
    /**
     * Access to resources navigation
     *
     * @param resourceNavigateSearchDTO
     * @return
     */
    PageInfo<ResourceNavigateDTO> getList(ResourceNavigateSearchDTO resourceNavigateSearchDTO);

    /**
     * Added resource navigation
     *
     * @param resourceNavigateDTO
     * @param currentUser
     * @return
     */
    Boolean create(ResourceNavigateDTO resourceNavigateDTO, UserSsoDTO currentUser);

    /**
     * Upload Resources Navigation logo
     *
     * @param bytes
     * @param sourceFileName
     * @return
     */
    String uploadResourceNavigateLogo(byte[] bytes, String sourceFileName);

    /**
     * Updated resource navigation
     *
     * @param resourceNavigateDTO
     * @param currentUser
     * @return
     */
    Boolean update(ResourceNavigateDTO resourceNavigateDTO, UserSsoDTO currentUser);

    /**
     * Delete resource navigation
     *
     * @param id
     * @return
     */
    Boolean delete(Integer id);

    /**
     * Get resource navigation for all categories
     *
     * @return
     */
    List<String> getCategorys();
}
