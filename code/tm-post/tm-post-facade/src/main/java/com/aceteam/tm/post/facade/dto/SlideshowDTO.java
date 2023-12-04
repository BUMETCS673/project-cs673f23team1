package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class SlideshowDTO implements Serializable {
    /**
     * Slideshow id
     */
    private Integer id;

    /**
     * Name
     */
    private String name;

    /**
     * Slideshow
     */
    private String image;

    /**
     * Jump address
     */
    private String jumpAddress;

    /**
     * desc
     */
    private String desc;

    /**
     * State (0 disabled, 1 enabled)
     */
    private Boolean state;

    /**
     * Logical deletion (0 normal, 1 deletion)
     */
    private Boolean isDeleted;

    /**
     * Create user id
     */
    private Long createUser;

    /**
     * Update user id
     */
    private Long updateUser;

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
