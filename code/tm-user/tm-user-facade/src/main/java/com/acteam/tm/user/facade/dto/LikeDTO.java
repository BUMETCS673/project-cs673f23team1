package com.acteam.tm.user.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class LikeDTO implements Serializable{

    /**
     * LIKE id
     */
    private Integer id;

    /**
     * post id
     */
    private Integer postId;

    /**
     * Status (0 Canceled,1 Liked)
     */
    private Boolean state;

    /**
     * Liked user id
     */
    private Long likeUser;

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
