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
public class CommentPo implements Serializable {
    /**
     * Comment No.
     */
    private Integer id;

    /**
     * Parent comment id
     */
    private Integer preId;

    /**
     * Comments
     */
    private String content;

    /**
     * Commented post id
     */
    private Integer articleId;

    /**
     * Status (0 Disabled, 1 Enabled)
     */
    private Boolean state;

    /**
     * Logical deletion (0 normal, 1 deleted)
     */
    private Boolean isDeleted;

    /**
     * Comment user id
     */
    private Long commentUser;

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
