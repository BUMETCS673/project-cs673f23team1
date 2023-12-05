package com.aceteam.tm.common.enums;

import lombok.Getter;

/**
 * @description:
 * @author: haoran
 */
@Getter
public enum PostStateEnum {
    /**
     * Under review
     */
    pendingReview(0, "Under review"),
    disabled(1, "Disabled"),
    enable(2, "Enable");

    /**
     * Points
     */
    private Integer code;

    /**
     * Description
     */
    private String desc;

    PostStateEnum(Integer code, String name) {
        this.code = code;
        this.desc = name;
    }
}
