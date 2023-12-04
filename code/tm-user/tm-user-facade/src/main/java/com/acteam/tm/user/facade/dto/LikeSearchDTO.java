package com.acteam.tm.user.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class LikeSearchDTO implements Serializable {
    /**
     * post id
     */
    private Integer postId;

    /**
     * like user id
     */
    private Long likeUser;

    /**
     * current page
     */
    private Integer currentPage;

    /**
     * number of items per page
     */
    private Integer pageSize;

    private static final long serialVersionUID = 1L;
}
