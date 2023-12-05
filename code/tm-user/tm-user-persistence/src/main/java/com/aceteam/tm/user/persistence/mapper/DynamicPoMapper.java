package com.aceteam.tm.user.persistence.mapper;

import com.aceteam.tm.user.persistence.entity.DynamicPo;
import com.aceteam.tm.user.persistence.entity.DynamicPoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface DynamicPoMapper {
    /**
     * countByExample
     *
     * @param example
     * @return
     */
    long countByExample(DynamicPoExample example);

    /**
     * deleteByExample
     *
     * @param example
     * @return
     */
    int deleteByExample(DynamicPoExample example);

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
    int insert(DynamicPo record);

    /**
     * insertSelective
     *
     * @param record
     * @return
     */
    int insertSelective(DynamicPo record);

    /**
     * selectByExample
     *
     * @param example
     * @return
     */
    List<DynamicPo> selectByExample(DynamicPoExample example);

    /**
     * selectByPrimaryKey
     *
     * @param id
     * @return
     */
    DynamicPo selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") DynamicPo record, @Param("example") DynamicPoExample example);

    /**
     * updateByExample
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") DynamicPo record, @Param("example") DynamicPoExample example);

    /**
     * updateByPrimaryKeySelective
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(DynamicPo record);

    /**
     * updateByPrimaryKey
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(DynamicPo record);
}
