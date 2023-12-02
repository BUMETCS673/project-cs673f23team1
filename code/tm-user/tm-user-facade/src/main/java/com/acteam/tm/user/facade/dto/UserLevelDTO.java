package com.acteam.tm.user.facade.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
public class UserLevelDTO implements Serializable {
    /**
     * user level id
     */
    private Integer id;

    /**
     * user id
     */
    private Long userId;

    /**
     * level
     */
    private String level;

    /**
     * points
     */
    private Integer points;

    /**
     * create time
     */
    private LocalDateTime createTime;

    /**
     * update time
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}
