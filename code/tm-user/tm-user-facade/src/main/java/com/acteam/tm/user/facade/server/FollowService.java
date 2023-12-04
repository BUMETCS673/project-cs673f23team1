package com.acteam.tm.user.facade.server;

import com.acteam.tm.user.facade.dto.FollowCountDTO;
import com.acteam.tm.user.facade.dto.FollowDTO;
import com.acteam.tm.user.facade.dto.FollowSearchDTO;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.auth.UserSsoDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface FollowService {

    /**
     *
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<FollowDTO> getPaasAll(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Get information about the users you follow
     *
     * @param followSearchDTO
     * @param currentUser
     * @return
     */
    PageInfo<FollowDTO> getFollowUsers(FollowSearchDTO followSearchDTO, UserSsoDTO currentUser);

    /**
     * Get followers by id
     *
     * @param id
     * @return
     */
    FollowDTO getById(Integer id);

    /**
     * Getting Concerns info with fromUser and toUser
     *
     * @param fromUser
     * @param toUser
     * @param isAll true:Does not distinguish between concern and non-concern，false:Only query followed
     * @return
     */
    FollowDTO getByFromToUser(Long fromUser, Long toUser, Boolean isAll);

    /**
     * Update Follow Status
     *
     * @param fromUser
     * @param toUser
     * @return
     */
    Boolean updateFollowState(Long fromUser, Long toUser);

    /**
     * Get number of followers
     *
     * @param userId
     * @return
     */
    FollowCountDTO getFollowCount(Long userId);

}
