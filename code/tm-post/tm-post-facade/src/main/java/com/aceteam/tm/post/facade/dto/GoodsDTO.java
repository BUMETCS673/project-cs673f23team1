package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class GoodsDTO implements Serializable {

    private String url;

    private String title;

    private String price;

    private String desc;

    private String image;

    private static final long serialVersionUID = 1L;

}
