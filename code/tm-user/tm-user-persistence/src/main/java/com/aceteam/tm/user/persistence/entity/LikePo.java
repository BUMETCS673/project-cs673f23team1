package com.aceteam.tm.user.persistence.entity;

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
public class LikePo implements Serializable {
    /**
     * Like number
     */
    private Integer id;

    /**
     * Post id
     */
    private Integer postId;

    /**
     * Status (0 Canceled,1 Liked)
     */
    private Boolean state;

    /**
     * Like user id
     */
    private Long likeUser;

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
