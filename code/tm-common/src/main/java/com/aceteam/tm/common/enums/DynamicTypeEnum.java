package com.aceteam.tm.common.enums;

import lombok.Getter;

/**
 * @description: some desc
 * @author: haoran
 */
@Getter
public enum DynamicTypeEnum {
    /**
     * Write Posts
     */
    writePost("Write Post"),
    likePost("Like Post"),
    likeComment("Like Comment"),
    commentPost("Comment Post"),
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
