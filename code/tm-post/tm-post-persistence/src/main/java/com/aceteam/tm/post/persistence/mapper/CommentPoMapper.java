package com.aceteam.tm.post.persistence.mapper;

import com.aceteam.tm.post.persistence.entity.CommentPo;
import com.aceteam.tm.post.persistence.entity.CommentPoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface CommentPoMapper {
    /**
     * countByExample
     *
     * @param example
     * @return
     */
    long countByExample(CommentPoExample example);

    /**
     * deleteByExample
     *
     * @param example
     * @return
     */
    int deleteByExample(CommentPoExample example);

    /**
     * deleteByPrimaryKey
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert
     *
     * @param record
     * @return
     */
    int insert(CommentPo record);

    /**
     * insertSelective
     *
     * @param record
     * @return
     */
    int insertSelective(CommentPo record);

    /**
     * selectByExample
     *
     * @param example
     * @return
     */
    List<CommentPo> selectByExample(CommentPoExample example);

    /**
     * selectByPrimaryKey
     *
     * @param id
     * @return
     */
    CommentPo selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") CommentPo record, @Param("example") CommentPoExample example);

    /**
     * updateByExample
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") CommentPo record, @Param("example") CommentPoExample example);

    /**
     * updateByPrimaryKeySelective
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CommentPo record);

    /**
     * updateByPrimaryKey
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CommentPo record);
}
