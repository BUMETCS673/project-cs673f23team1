package com.aceteam.tm.post.persistence.mapper;

import com.aceteam.tm.post.persistence.entity.PostLabelPo;
import com.aceteam.tm.post.persistence.entity.PostLabelPoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface PostLabelPoMapper {
    /**
     * countByExample
     *
     * @param example
     * @return
     */
    long countByExample(PostLabelPoExample example);

    /**
     * deleteByExample
     *
     * @param example
     * @return
     */
    int deleteByExample(PostLabelPoExample example);

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
    int insert(PostLabelPo record);

    /**
     * insertSelective
     *
     * @param record
     * @return
     */
    int insertSelective(PostLabelPo record);

    /**
     * selectByExample
     *
     * @param example
     * @return
     */
    List<PostLabelPo> selectByExample(PostLabelPoExample example);

    /**
     * selectByPrimaryKey
     *
     * @param id
     * @return
     */
    PostLabelPo selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") PostLabelPo record, @Param("example") PostLabelPoExample example);

    /**
     * updateByExample
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") PostLabelPo record, @Param("example") PostLabelPoExample example);

    /**
     * updateByPrimaryKeySelective
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PostLabelPo record);

    /**
     * updateByPrimaryKey
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(PostLabelPo record);
}
