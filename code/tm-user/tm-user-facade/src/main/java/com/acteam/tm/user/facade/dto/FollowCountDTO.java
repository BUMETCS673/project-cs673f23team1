package com.acteam.tm.user.facade.dto;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
public class FollowCountDTO implements Serializable {

    /**
     * Number of followings
     */
    private Long followCount;

    /**
     * Number of fans
     */
    private Long fanCount;

    private static final long serialVersionUID = 1L;
}
