package com.aceteam.tm.post.facade.server;

import com.aceteam.tm.post.facade.dto.CommentDTO;
import com.aceteam.tm.post.facade.dto.CommentSearchDTO;
import com.aceteam.tm.post.facade.dto.PostLabelDTO;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.auth.UserSsoDTO;

import java.time.LocalDateTime;
import java.util.List;
/**
 * @description: some desc
 * @author: haoran
 */
public interface CommentService {
    /**
     * Get the comment information of the Post
     *
     * @param commentSearchDTO
     * @return
     */
    List<CommentDTO> getCommentByArticleId(CommentSearchDTO commentSearchDTO, UserSsoDTO currentUser);

    /**
     * Get information about comments on all moderated articles
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<CommentDTO> getAllPostComment(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Get information about all comment replies on reviewed articles
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<CommentDTO> getAllCommentReply(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Get the latest reviews
     *
     * @param commentSearchDTO
     * @return
     */
    PageInfo<CommentDTO> getLatestComment(CommentSearchDTO commentSearchDTO);

    /**
     * Get the number of comments on the article
     *
     * @param postId
     * @return
     */
    Long getCommentCountByPost(Integer postId);

    /**
     * Getting the number of comments
     *
     * @return
     */
    Long getTotal();

    /**
     * Create a comment
     *
     * @param commentDTO
     * @param currentUser
     * @return
     */
    Boolean create(CommentDTO commentDTO, UserSsoDTO currentUser);

    /**
     * Delete comment
     *
     * @param commentId
     * @return
     */
    Boolean delete(Integer commentId);

    /**
     * Get child comment information by parent ID
     *
     * @param result store results
     * @param preId
     * @return
     */
    void getAllChildrenByPreId(List<CommentDTO> result, Integer preId);

    /**
     * Get comment id  Get post id
     *
     * @param commentId
     * @return
     */
    Integer getPostIdByCommentId(Integer commentId);

    /**
     * Get comment information by bulk id
     *
     * @param commentId
     * @return
     */
    CommentDTO getById(Integer commentId);
}

