package com.acteam.tm.user.facade.dto;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
public class LikeSearchDTO implements Serializable {
    /**
     * post id
     */
    private Integer articleId;

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
