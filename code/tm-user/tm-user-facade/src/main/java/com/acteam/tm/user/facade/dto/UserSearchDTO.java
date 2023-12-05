package com.acteam.tm.user.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class UserSearchDTO implements Serializable {

    /**
     * Current page
     */
    private Integer currentPage;

    /**
     * Number of items per page
     */
    private Integer pageSize;

    private static final long serialVersionUID = 1L;

}