package com.aceteam.tm.common.enums;

import lombok.Getter;

/**
 * @description: some desc
 * @author: haoran
 */
@Getter
public class SortRuleRnum {
//    hottest("Hottest"),
//    newest("Newest");

    private String desc;

    SortRuleRnum(String name) {
        this.desc = name;
    }
}
