package com.aceteam.tm.user.persistence.mapper;

import com.aceteam.tm.user.persistence.entity.LikePo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface LikePoExMapper {
    /**
     * Get information about liked posts by user ids
     *
     * @param likeUser
     * @return
     */
    List<LikePo> selectPostByUserId(Long likeUser);

    /**
     * Get information about all posts that are liked
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<LikePo> selectAllPost(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}
