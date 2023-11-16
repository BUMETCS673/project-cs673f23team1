package com.aceteam.tm.post.facade.dto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * @description: some desc
 * @author: haoran
 */
public class PostSearchDTO implements Serializable{
    /**
     * Post ID
     */
    private Integer id;

    /**
     * Post Title
     */
    private String title;

    /**
     * Post label
     */
    private List<Integer> labelIds;

    /**
     * Create user id
     */
    private Long createUser;

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
