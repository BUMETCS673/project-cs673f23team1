package com.aceteam.tm.user.persistence.mapper;

import com.aceteam.tm.user.persistence.entity.LikeCommentPo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface LikeCommentPoExMapper {
    /**
     * Get the likes information for all comments on moderated articles
     * @param startTime
     * @param endTime
     * @return
     */
    List<LikeCommentPo> selectAllCommentLike(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
