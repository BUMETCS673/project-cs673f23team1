package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class PostDTO implements Serializable {
    /**
     * Post ID
     */
    private Integer id;

    /**
     * Title map
     */
    private String titleMap;

    /**
     * Post Title
     */
    private String title;

    /**
     * Post content
     */
    private String content;

    /**
     * Post content (markdown)
     */
    private String markdown;

    /**
     * Post content (html)
     */
    private String html;

    /**
     * Post label
     */
    private List<LabelDTO> labelDTOS;

    /**
     * Post status (-1 pending review, 0 disabled, 1 enabled)
     */
    private Integer state;

    /**
     * Post views
     */
    private Integer pv;

    /**
     * Top (the higher the number, the higher the top)
     */
    private Integer top;

    /**
     * Logical deletion (0 normal, 1 deleted)
     */
    private Boolean isDeleted;

    /**
     * User ID
     */
    private Long createUser;

    /**
     * User name
     */
    private String createUserName;

    /**
     * User Level
     */
    private String level;

    /**
     * Update User ID
     */
    private Long updateUser;

    /**
     * update User Name
     */
    private String updateUserName;

    /**
     * Statistics
     */
    private PostCountDTO postCountDTO;

    /**
     * User Avatar
     */
    private String picture;

    /**
     * Creation Time
     */
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;



}
