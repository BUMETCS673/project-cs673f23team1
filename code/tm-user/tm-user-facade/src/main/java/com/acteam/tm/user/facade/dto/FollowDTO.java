package com.acteam.tm.user.facade.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
public class FollowDTO implements Serializable {

    /**
     * Follow user id
     */
    private Integer id;

    /**
     * Follow From User
     */
    private Long fromUser;

    /**
     * Follow state(0 cancel, 1 follow)
     */
    private Boolean state;

    /**
     * Follow To User
     */
    private Long toUser;

    /**
     * User name
     */
    private String name;

    /**
     * Avatar
     */
    private String picture;

    /**
     * Level (Lv6)
     */
    private String level;

    /**
     * Whether to follow
     */
    private Boolean isFollow;

    /**
     * Introduction
     */
    private String intro;

    /**
     * Number of likes received
     */
    private Long likeCount;

    /**
     * Number of readings received
     */
    private Long readCount;

    /**
     * Create time
     */
    private LocalDateTime createTime;

    /**
     * Update time
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

}
