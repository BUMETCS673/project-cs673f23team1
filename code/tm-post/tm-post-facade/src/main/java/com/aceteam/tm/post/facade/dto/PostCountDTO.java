package com.aceteam.tm.post.facade.dto;

import lombok.Data;

/**
 * @description: some desc
 * @author: haoran
 */

@Data
public class PostCountDTO {

    /**
     * Like or dislike
     */
    private Boolean isLike;

    /**
     * Number of likes
     */
    private Long likeCount;

    /**
     * Number of comments
     */
    private Long commentCount;

    /**
     * Whether to follow
     */
    private Boolean isFollow;

    /**
     * Level (Lv6)
     */
    private String level;

    private static final long serialVersionUID = 1L;
}
