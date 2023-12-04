package com.aceteam.tm.post.persistence.mapper;

/**
 * @description: some desc
 * @author: haoran
 */
public interface PostLabelPoExMapper {
    /**
     * Get the number of tags used
     *
     * @param labelId
     * @return
     */
    long countByLabelId(Integer labelId);
}
