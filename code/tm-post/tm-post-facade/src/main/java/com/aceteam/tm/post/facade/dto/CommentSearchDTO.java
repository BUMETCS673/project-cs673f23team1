package com.aceteam.tm.post.facade.dto;
import com.aceteam.tm.common.enums.SortRuleEnum;
import lombok.Data;

import java.io.Serializable;
/**
 * @description: some desc
 * @author: haoran
 */
public class CommentSearchDTO implements Serializable {
    /**
     * Comment ID
     */
    private Integer id;

    /**
     * Comment content
     */
    private String content;

    /**
     * Comment user id
     */
    private Long commentUser;

    /**
     * Post id
     */
    private Integer postId;

    /**
     * Sort rule
     */
    private SortRuleEnum sortRule;

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
