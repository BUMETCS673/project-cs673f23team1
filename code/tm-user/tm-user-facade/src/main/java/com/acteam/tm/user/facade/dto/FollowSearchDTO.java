package com.acteam.tm.user.facade.dto;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
public class FollowSearchDTO implements Serializable {
    /**
     * get Influencer
     */
    private Long getBigCow;

    /**
     * get fans
     */
    private Long getFan;

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
