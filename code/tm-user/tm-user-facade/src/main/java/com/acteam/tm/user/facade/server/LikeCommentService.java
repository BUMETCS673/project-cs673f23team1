package com.acteam.tm.user.facade.server;

import com.acteam.tm.user.facade.dto.LikeCommentDTO;
import com.liang.nansheng.common.auth.UserSsoDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface LikeCommentService {

    /**
     * Get the likes information for all comments on moderated articles
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<LikeCommentDTO> getPaasAll(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Get the number of likes on a comment
     *
     * @param commentId
     * @return
     */
    Long getLikeCountCommentId(Integer commentId);

    /**
     * Like it or not
     *
     * @param commentId
     * @param userId
     * @return
     */
    Boolean isLike(Integer commentId, Long userId);

    /**
     * Updating the status of Likes
     *
     * @param commentId
     * @param currentUser
     * @return
     */
    Boolean updateLikeCommentState(Integer commentId, UserSsoDTO currentUser);

    /**
     * Get likes by comment id and user id
     *
     * @param commentId
     * @param userId
     * @return
     */
    LikeCommentDTO getByCommentIdUserId(Integer commentId, Long userId);

}
