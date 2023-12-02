package com.acteam.tm.user.facade.dto;

import com.liang.manage.auth.facade.dto.user.UserListDTO;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
public class UserForumDTO extends UserListDTO implements Serializable {
    /**
     * Like count
     */
    private Long likeCount;

    /**
     * Read count
     */
    private Long readCount;

    /**
     * Points
     */
    private Integer points;

    /**
     * Level（Lv6）
     */
    private String level;

    /**
     * Whether to follow
     */
    private Boolean isFollow;

    private static final long serialVersionUID = 1L;
}
