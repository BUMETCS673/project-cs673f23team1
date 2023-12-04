package com.aceteam.tm.post.persistence.mapper;

import com.aceteam.tm.post.persistence.entity.CommentPo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface CommentPoExMapper {
    /**
     * Get the latest reviews
     *
     * @param content
     * @param commentUser
     * @return
     */
    List<CommentPo> selectLatestComments(String content, Long commentUser);

    /**
     * Get information about comments on all moderated articles
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<CommentPo> getAllArticleComment(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * Get information about all comment replies on reviewed articles
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<CommentPo> getAllCommentReply(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
