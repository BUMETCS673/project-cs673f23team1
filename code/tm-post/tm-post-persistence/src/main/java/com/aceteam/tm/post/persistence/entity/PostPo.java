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
public class PostPo implements Serializable {

     /**
     * Post id
     */
    private Integer id;

    /**
     * title map
     */
    private String titleMap;

    /**
     * post title
     */
    private String title;

    /**
     * post content
     */
    private String content;

    /**
     * Status (-1 pending review, 0 disabled, 1 enabled)
     */
    private Integer state;

    /**
     * Post Views
     */
    private Integer pv;

    /**
     * Top (the higher the number, the higher the top)
     */
    private Integer top;

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