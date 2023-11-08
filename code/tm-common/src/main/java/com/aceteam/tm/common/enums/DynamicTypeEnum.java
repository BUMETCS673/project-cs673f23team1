package com.aceteam.tm.common.enums;

import lombok.Getter;

/**
 * @description:
 * @author: haoran
 */

@Getter
public class DynamicTypeEnum {
    // Write a post
//    writeArticle("Write A Post"),
//    likeComment("Like"),
//    commentArticle("Comment"),
//    commentReply("Reply The Comment"),
//    followUser("Follow The User");


    private String desc;

    DynamicTypeEnum(String name) {
        this.desc = name;
    }
    }
