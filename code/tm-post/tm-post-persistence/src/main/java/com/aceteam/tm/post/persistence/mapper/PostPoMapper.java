package com.aceteam.tm.post.persistence.mapper;

import com.aceteam.tm.post.persistence.entity.PostPo;
import com.aceteam.tm.post.persistence.entity.PostPoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface PostPoMapper {
    /**
     * countByExample
     *
     * @param example
     * @return
     */
    long countByExample(PostPoExample example);

    /**
     * deleteByExample
     *
     * @param example
     * @return
     */
    int deleteByExample(PostPoExample example);

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
    int insert(PostPo record);

    /**
     * insertSelective
     *
     * @param record
     * @return
     */
    int insertSelective(PostPo record);

    /**
     * selectByExample
     *
     * @param example
     * @return
     */
    List<PostPo> selectByExample(PostPoExample example);

    /**
     * selectByPrimaryKey
     *
     * @param id
     * @return
     */
    PostPo selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") PostPo record, @Param("example") PostPoExample example);

    /**
     * updateByExample
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") PostPo record, @Param("example") PostPoExample example);

    /**
     * updateByPrimaryKeySelective
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PostPo record);

    /**
     * updateByPrimaryKey
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(PostPo record);
}
