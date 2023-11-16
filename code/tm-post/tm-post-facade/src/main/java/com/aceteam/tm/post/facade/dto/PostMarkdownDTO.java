package com.aceteam.tm.post.facade.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @description: some desc
 * @author: haoran
 */
@Data
@Document("tm_post_markdown_info")
public class PostMarkdownDTO {
    @Id
    private String id;

    /**
     * Post ID
     */
    private Integer postId;

    /**
     * Post content (markdown)
     */
    private String postMarkdown;

    /**
     * Post content (html)
     */
    private String postHtml;

    /**
     * User ID
     */
    private Long userId;

    /**
     * Time (create/update)
     */
    private LocalDateTime time;
}
