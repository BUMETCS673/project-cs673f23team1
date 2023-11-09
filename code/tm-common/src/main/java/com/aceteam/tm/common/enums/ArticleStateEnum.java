package com.aceteam.tm.common.enums;

import lombok.Getter;

/**
 * @description: some desc
 * @author: haoran
 */


@Getter
public class ArticleStateEnum {
    /**
     * Under Reviewing
     */

//    pendingReview(-1, "Reviewing"),
//    disabled(0, "Disabled"),
//    enable(1, "Enabled");


    private Integer code;

    private String desc;

    ArticleStateEnum(Integer code, String name) {
        this.code = code;
        this.desc = name;
    }
}
