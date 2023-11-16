package com.aceteam.tm.post.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
public class PostCheckCountDTO implements Serializable {

        /**
        * Number of articles enabled
        */
        private Long enableCount;

        /**
        * Number of articles disabled
        */
        private Long disabledCount;

        /**
        * Number of articles pending review
        */
        private Long pendingReviewCount;

        private static final long serialVersionUID = 1L;
}
