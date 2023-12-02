package com.acteam.tm.user.facade.server;

import com.acteam.tm.user.facade.dto.DynamicDTO;
import com.github.pagehelper.PageInfo;

import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
public interface DynamicService {

    /**
     * Getting dynamic information about the user
     *
     * @param userId
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<DynamicDTO> getByUserId(Long userId, Integer currentPage, Integer pageSize);

    /**
     * Creating User Dynamic Messages
     *
     * @param dynamicDTO
     * @return
     */
    Boolean create(DynamicDTO dynamicDTO);

    /**
     * Verify that it already exists
     *
     * @param dynamicDTO
     * @return
     */
    Boolean verifyExist(DynamicDTO dynamicDTO);

    /**
     * Delete user dynamic information
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Boolean delete(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Update dynamic information for all users
     *
     * @return
     */
    void updateAll();

}

