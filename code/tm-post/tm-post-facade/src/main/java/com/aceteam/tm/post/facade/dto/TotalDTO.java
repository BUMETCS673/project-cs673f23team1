package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class TotalDTO implements Serializable {
    /**
     * Post Count
     */
    private Long postCount;

    /**
     *  Comment Count
     */
    private Long commentCount;


    /**
     * Post Read Count
     */
    private Long visitCount;

    private static final long serialVersionUID = 1L;
}
