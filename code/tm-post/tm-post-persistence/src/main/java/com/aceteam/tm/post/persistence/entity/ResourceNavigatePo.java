package com.aceteam.tm.post.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceNavigatePo implements Serializable {
    /**
     * resource number
     */
    private Integer id;

    /**
     * resource name
     */
    private String resourceName;

    /**
     * logo
     */
    private String logo;

    /**
     * category
     */
    private String category;

    /**
     * description
     */
    private String desc;

    /**
     * link
     */
    private String link;

    /**
     * Logical deletion (0 normal, 1 deleted)
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
     * Creation time
     */
    private LocalDateTime createTime;

    /**
     * update time
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}
