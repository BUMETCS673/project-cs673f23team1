package com.acteam.tm.user.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class DynamicDTO implements Serializable {
    /**
     * User Dynamic ID
     */
    private Integer id;

    /**
     * Type (write post, comment, like, follow, etc.)
     */
    private String type;

    /**
     * Initiator
     */
    private Long userId;

    /**
     * Initiator name
     */
    private String userName;

    /**
     * Initiator (avatar)
     */
    private String picture;

    /**
     * ID of the object operated (post id, user id, etc., not comment id)
     */
    private String objectId;

    /**
     * Comment id
     */
    private Integer commentId;

    /**
     * Name of the object operated (user name, post name, comment content, etc.)
     */
    private String title;

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
