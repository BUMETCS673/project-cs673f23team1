package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class LabelSearchDTO implements Serializable {
    /**
     * Label ID
     */
    private Integer id;

    /**
     * Label name
     */
    private String labelName;

    /**
     * Logical deletion (0 normal, 1 deletion)
     */
    private Boolean isDeleted;

    /**
     * current page
     */
    private Integer currentPage;

    /**
     * item for each page
     */
    private Integer pageSize;

    private static final long serialVersionUID = 1L;
}
