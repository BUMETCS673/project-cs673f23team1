package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */

@Data
public class PostReadDTO implements Serializable {

    /**
     * User id
     */
    private Long userId;

    /**
     * Post Read Count
     */
    private Long postReadCount;

    private static final long serialVersionUID = 1L;


}
