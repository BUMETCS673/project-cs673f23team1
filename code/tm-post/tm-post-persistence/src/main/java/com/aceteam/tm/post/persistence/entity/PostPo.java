package com.aceteam.tm.post.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPo implements Serializable {
/**
     * Post ID
     */
    private Integer id;

    /**
     * Post Title
     */
    private String title;

    /**
     * Post Content
     */
    private String content;

    /**
     * Post Author
     */
    private String author;

    /**
     * Post Label
     */
    private String label;

    /**
     * 0 is normal, 1 is deleted
     */
    private boolean isDeleted;

    /**
     * Create User ID
     */
    private Long createUser;

    /**
     * Update User ID
     */
    private Long updateUser;

    /**
     * Creation Time
     */
    private String createTime;

    /**
     * Update Time
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;
}
