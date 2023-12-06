package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */

@Data
public class CommentDTO implements Serializable {
    /**
     * Comment id
     */
    private Integer id;

    /**
     * Post father id
     */
    private Integer preId;

    /**
     * Comment content
     */
    private String content;

    /**
     * commented post id
     */
    private Integer postId;

    /**
     * Status(0 disabled, 1 enabled)
     */
    private Boolean state;

    /**
     * Logic delete(0 normal, 1 deleted)
     */
    private Boolean isDeleted;

    /**
     * Comment user id
     */
    private Long commentUser;

    /**
     * Comment user name
     */
    private String commentUserName;

    /**
     * User avatar
     */
    private String picture;

    /**
     * User level
     */
    private String level;

    /**
     * Whether to like
     */
    private Boolean isLike;

    /**
     * Like count
     */
    private Long likeCount;

    /**
     * Reply count
     */
    private Integer repliesCount;

    /**
     * Comment depth
     */
    private Integer depth;

    /**
     * Create time
     */
    private LocalDateTime createTime;

    /**
     * Update time
     */
    private LocalDateTime updateTime;

    private List<CommentDTO> child;

    private static final long serialVersionUID = 1L;


}
