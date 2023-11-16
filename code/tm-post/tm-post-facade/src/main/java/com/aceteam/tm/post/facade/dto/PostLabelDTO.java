package com.aceteam.tm.post.facade.dto;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @description: some desc
 * @author: haoran
 */
public class PostLabelDTO implements Serializable {
    /**
     * Post label number
     */
    private Integer id;

    /**
     * Post id
     */
    private Integer postId;

    /**
     * Post label
     */
    private Integer labelId;

    /**
     * logic delete flag(0: no delete, 1: deleted)
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
