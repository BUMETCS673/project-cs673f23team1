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
public class UserLevelPo implements Serializable {
    /**
     * User level number
     */
    private Integer id;

    /**
     * User id
     */
    private Long userId;

    /**
     * Level (Lv6)
     */
    private String level;

    /**
     * Points
     */
    private Integer points;

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
