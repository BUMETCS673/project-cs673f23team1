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
     * Get information about all liked and approved articles
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
    PageInfo<LikeDTO> getArticleByUserId(LikeSearchDTO likeSearchDTO);

    /**
     * Get information about the user who liked the post by the post id
     *
     * @param likeSearchDTO
     * @return
     */
    PageInfo<LikeDTO> getUserByArticleId(LikeSearchDTO likeSearchDTO);

    /**
     * Get likes by id
     *
     * @param id
     * @return
     */
    LikeDTO getById(Integer id);

    /**
     * Get likes by article id and user id
     *
     * @param articleId
     * @param userId
     * @return
     */
    LikeDTO getByArticleIdUserId(Integer articleId, Long userId);

    /**
     * Get the number of likes for an article
     *
     * @param articleIds
     * @return
     */
    Long getLikeCountArticle(List<Integer> articleIds);

    /**
     * Like or not
     *
     * @param articleId
     * @param userId
     * @return
     */
    Boolean isLike(Integer articleId, Long userId);

    /**
     * Updating the status of likes
     *
     * @param articleId
     * @param currentUser
     * @return
     */
    Boolean updateLikeState(Integer articleId, UserSsoDTO currentUser);

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

