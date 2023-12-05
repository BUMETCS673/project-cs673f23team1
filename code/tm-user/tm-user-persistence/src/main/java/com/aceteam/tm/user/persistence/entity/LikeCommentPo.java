package com.aceteam.tm.user.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeCommentPo {
    /**
     * Comment Likes Number
     */
    private Integer id;

    /**
     * Comment id
     */
    private Integer commentId;

    /**
     * Status (0 Canceled,1 Liked)
     */
    private Boolean state;

    /**
     * Comment Likes user id
     */
    private Long likeUser;

    /**
     * Creation time
     */
    private LocalDateTime createTime;

    /**
     * update time
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}
