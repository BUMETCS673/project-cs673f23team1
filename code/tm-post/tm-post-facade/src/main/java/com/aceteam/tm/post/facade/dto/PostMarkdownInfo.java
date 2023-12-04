package com.aceteam.tm.post.facade.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author maliangnansheng
 * @date 2022-05-16 20:54
 */
@Data
@Document("bbs_post_markdown_info")
public class PostMarkdownInfo {
    @Id
    private String id;

    /**
     * post id
     */
    private Integer postId;

    /**
     * post content markdown
     */
    private String postMarkdown;

    /**
     * post content html
     */
    private String postHtml;

    /**
     * user id
     */
    private Long userId;

    /**
     * Time (creation/update)
     */
    private LocalDateTime time;


}
