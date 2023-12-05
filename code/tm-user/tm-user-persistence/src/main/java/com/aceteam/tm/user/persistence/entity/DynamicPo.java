package com.aceteam.tm.user.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: The corresponding data table is: fs_dynamic
 * @author: haoran
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicPo implements Serializable {
    /**
     * User dynamic number
     */
    private Integer id;

    /**
     * Types (writing articles, commenting, liking, following, etc.)
     */
    private String type;

    /**
     * Initiator
     */
    private Long userId;

    /**
     * The object ID of the operation (article id, user id, etc.)
     */
    private String objectId;

    /**
     * Comment id
     */
    private Integer commentId;

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
