package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
public class ResourceNavigateSearchDTO implements Serializable {
    /**
     * Category
     */
    private String category;

    /**
     * Current page
     */
    private Integer currentPage;

    /**
     * Page size
     */
    private Integer pageSize;

    private static final long serialVersionUID = 1L;
}
