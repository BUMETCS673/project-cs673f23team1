package com.aceteam.tm.common.enums;

import lombok.Getter;

/**
 * @description: some desc
 * @author: haoran
 */
@Getter
public enum DynamicTypeEnum {
    /**
     * Write Articles
     */
    writeArticles("Write Articles"),
    likeArticles("Like Articles"),
    likeComments("Like Comments"),
    commentArticles("Comment Articles"),
    commentReply("Comment Reply"),
    followUser("Follow User");

    /**
     * Description
     */
    private String desc;

    DynamicTypeEnum(String name) {
        this.desc = name;
    }

}
