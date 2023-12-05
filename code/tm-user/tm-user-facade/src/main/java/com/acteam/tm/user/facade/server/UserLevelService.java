package com.acteam.tm.user.facade.server;

import com.acteam.tm.user.facade.dto.UserForumDTO;
import com.acteam.tm.user.facade.dto.UserLevelDTO;
import com.acteam.tm.user.facade.dto.UserSearchDTO;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.auth.UserSsoDTO;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface UserLevelService {
    /**
     * Create user level information
     *
     * @param userId
     * @return
     */
    Boolean create(Long userId);

    /**
     * Update user level information
     *
     * @param userId User ID
     * @param points Points
     * @return
     */
    Boolean update(Long userId,  Integer points);

    /**
     * Update all user level information
     *
     * @return
     */
    Boolean updatePointsAll();

    /**
     * Synchronize all user level information
     *
     * @return
     */
    Boolean syncAll();

    /**
     * Get a list of popular authors
     *
     * @param userSearchDTO
     * @param currentUser
     * @return
     */
    PageInfo<UserForumDTO> getHotAuthorsList(UserSearchDTO userSearchDTO, UserSsoDTO currentUser);

    /**
     * Get user level information by user id
     *
     * @param userId
     * @return
     */
    List<UserLevelDTO> getByUserId(Long userId);

    /**
     * Get user level information from the user id collection
     *
     * @param userIds
     * @return
     */
    List<UserLevelDTO> getByUserIds(List<Long> userIds);

    /**
     * Get user information
     *
     * @param userId
     * @param currentUser
     * @return
     */
    UserForumDTO getUserInfo(Long userId, UserSsoDTO currentUser);
}
