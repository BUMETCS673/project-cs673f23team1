package com.aceteam.tm.post.persistence.mapper;

import com.aceteam.tm.post.facade.dto.PostReadDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description: some desc
 * @author: haoran
 */
public interface PostPoExMapper {
    /**
     * Get the number of user likes
     *
     * @param userIds
     * @return
     */
    List<PostReadDTO> selectUserReadCount(@Param("userIds") List<Long> userIds);

    /**
     * Post Review Data Volume
     *
     * @param title
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> selectArticleCheckCount(@Param("title") String title);

}