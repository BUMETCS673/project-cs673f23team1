package com.acteam.tm.user.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class UserOperateCountDTO implements Serializable {
    /**
     * Number of posts
     */
    private Long postCount;

    /**
     * Number of likes
     */
    private Long likeCount;

    /**
     * Number of followers
     */
    private Long followCount;

    /**
     * Number of fans
     */
    private Long fanCount;

    private static final long serialVersionUID = 1L;

}
