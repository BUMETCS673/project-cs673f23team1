package com.aceteam.tm.user.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: The corresponding data table is: fs_follow
 * @author: haoran
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowPo {
    /**
     * Follow No.
     */
    private Integer id;

    /**
     * People who initiated the follow
     */
    private Long fromUser;

    /**
     * Status(0 Canceled,1 Followed)
     */
    private Boolean state;

    /**
     * people being followed
     */
    private Long toUser;

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
