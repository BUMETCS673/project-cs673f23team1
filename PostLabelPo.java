package com.aceteam.tm.post.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @description: Dataset: fs_article_label
 * @author: haoran
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLabelPo implements Serializable {
    /**
     * Post Label Number
     */
    private Integer id;

    /**
     * Post ID
     */
    private Integer articleId;

    /**
     * Label ID
     */
    private Integer labelId;

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
    private LocalDateTime createTime;

    /**
     * Update Time
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}
