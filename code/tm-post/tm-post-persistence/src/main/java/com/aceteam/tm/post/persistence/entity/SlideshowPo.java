package com.aceteam.tm.post.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlideshowPo implements Serializable {
    /**
     * slideshow number
     */
    private Integer id;

    /**
     * name
     */
    private String name;

    /**
     * rotating chart
     */
    private String image;

    /**
     * jump address
     */
    private String jumpAddress;

    /**
     * description
     */
    private String desc;

    /**
     * Status (0 Disabled, 1 Enabled)
     */
    private Boolean state;

    /**
     * Logical deletion (0 normal, 1 deleted)
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
     * Creation time
     */
    private LocalDateTime createTime;

    /**
     * update time
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}
