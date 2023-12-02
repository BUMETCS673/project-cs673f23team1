package com.acteam.tm.user.facade.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
public class LikeCommentDTO implements Serializable {
    /**
     * comment like id
     */
    private Integer id;

    /**
     * comment id
     */
    private Integer commentId;

    /**
     * state(0 cancel, 1 like)
     */
    private Boolean state;

    /**
     * comment like user id
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
