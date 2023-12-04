package com.aceteam.tm.post.persistence.mapper;

import com.aceteam.tm.post.persistence.entity.ResourceNavigatePo;
import com.aceteam.tm.post.persistence.entity.ResourceNavigatePoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface ReourceNavigatePoMapper {
    /**
     * countByExample
     *
     * @param example
     * @return
     */
    long countByExample(ResourceNavigatePoExample example);

    /**
     * deleteByExample
     *
     * @param example
     * @return
     */
    int deleteByExample(ResourceNavigatePoExample example);

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
    int insert(ResourceNavigatePo record);

    /**
     * insertSelective
     *
     * @param record
     * @return
     */
    int insertSelective(ResourceNavigatePo record);

    /**
     * selectByExample
     *
     * @param example
     * @return
     */
    List<ResourceNavigatePo> selectByExample(ResourceNavigatePoExample example);

    /**
     * selectByPrimaryKey
     *
     * @param id
     * @return
     */
    ResourceNavigatePo selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") ResourceNavigatePo record, @Param("example") ResourceNavigatePoExample example);

    /**
     * updateByExample
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") ResourceNavigatePo record, @Param("example") ResourceNavigatePoExample example);

    /**
     * updateByPrimaryKeySelective
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ResourceNavigatePo record);

    /**
     * updateByPrimaryKey
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(ResourceNavigatePo record);
}
