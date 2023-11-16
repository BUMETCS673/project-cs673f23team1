package com.aceteam.tm.common.enums;

import lombok.Getter;

/**
 * @description: some desc
 * @author: haoran
 */
@Getter
public enum SortRuleEnum {
    /**
     * Hottest
     */
    hottest("Hottest"),
    newest("Newest");

    /**
     * Description
     */
    private String desc;

    SortRuleEnum(String name) {
        this.desc = name;
    }
}
