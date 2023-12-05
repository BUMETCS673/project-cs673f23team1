package com.acteam.tm.user.facade.server;

import com.acteam.tm.user.facade.dto.LikeDTO;
import com.acteam.tm.user.facade.dto.LikeSearchDTO;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.auth.UserSsoDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface LikeService {

    /**
     * Get information about all liked and approved posts
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<LikeDTO> getPaasAll(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Get information about liked posts by user ids
     *
     * @param likeSearchDTO
     * @return
     */
    PageInfo<LikeDTO> getPostByUserId(LikeSearchDTO likeSearchDTO);

    /**
     * Get information about the user who liked the post by the post id
     *
     * @param likeSearchDTO
     * @return
     */
    PageInfo<LikeDTO> getUserByPostId(LikeSearchDTO likeSearchDTO);

    /**
     * Get likes by id
     *
     * @param id
     * @return
     */
    LikeDTO getById(Integer id);

    /**
     * Get likes by post id and user id
     *
     * @param postId
     * @param userId
     * @return
     */
    LikeDTO getByPostIdUserId(Integer postId, Long userId);

    /**
     * Get the number of likes for an post
     *
     * @param postIds
     * @return
     */
    Long getLikeCountPost(List<Integer> postIds);

    /**
     * Like or not
     *
     * @param postId
     * @param userId
     * @return
     */
    Boolean isLike(Integer postId, Long userId);

    /**
     * Updating the status of likes
     *
     * @param postId
     * @param currentUser
     * @return
     */
    Boolean updateLikeState(Integer postId, UserSsoDTO currentUser);

    /**
     * Number of likes acquired by the user
     *
     * @param userId
     * @return
     */
    Long getUserLikeCount(Long userId);

    /**
     * Number of likes received by users
     *
     * @param userId
     * @return
     */
    Long getUserTheLikeCount(Long userId);

}

