package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class LabelDTO implements Serializable {
    /**
     * Label id
     */
    private Integer id;

    /**
     * Label name
     */
    private String labelName;

    /**
     * logo
     */
    private String logo;

    /**
     * post use count
     */
    private Long postUseCount;

    /**
     * Status(0 disabled, 1 enabled)
     */
    private Boolean isDeleted;

    /**
     * Create user id
     */
    private Long createUser;

    /**
     * Update user id
     */
    private Long updateUser;

    /**
     * Create time
     */
    private LocalDateTime createTime;

    /**
     * Update time
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

}
