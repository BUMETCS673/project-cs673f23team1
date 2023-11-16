package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
public class ResourceNavigateDTO implements Serializable{
    /**
     * Resource id
     */
    private Integer id;

    /**
     * Resource name
     */
    private String resourceName;

    /**
     * logo
     */
    private String logo;

    /**
     * category
     */
    private String category;

    /**
     * desc
     */
    private String desc;

    /**
     * link
     */
    private String link;

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
