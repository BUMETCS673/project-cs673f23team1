package com.acteam.tm.user.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
public class LikeDTO implements Serializable{

    /**
     * like id
     */
    private Integer id;

    /**
     * article id
     */
    private Integer articleId;

    /**
     * state(0 cancel, 1 like)
     */
    private Boolean state;

    /**
     * like user id
     */
    private Long likeUser;

    /**
     * create time
     */
    private LocalDateTime createTime;

    /**
     * update time
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

}
