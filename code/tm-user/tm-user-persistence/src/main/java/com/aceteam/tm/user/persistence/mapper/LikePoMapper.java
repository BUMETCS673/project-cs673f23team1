package com.aceteam.tm.user.persistence.mapper;

import com.aceteam.tm.user.persistence.entity.LikePo;
import com.aceteam.tm.user.persistence.entity.LikePoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface LikePoMapper {
    /**
     * countByExample
     *
     * @param example
     * @return
     */
    long countByExample(LikePoExample example);

    /**
     * deleteByExample
     *
     * @param example
     * @return
     */
    int deleteByExample(LikePoExample example);

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
    int insert(LikePo record);

    /**
     * insertSelective
     *
     * @param record
     * @return
     */
    int insertSelective(LikePo record);

    /**
     * selectByExample
     *
     * @param example
     * @return
     */
    List<LikePo> selectByExample(LikePoExample example);

    /**
     * selectByPrimaryKey
     *
     * @param id
     * @return
     */
    LikePo selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") LikePo record, @Param("example") LikePoExample example);

    /**
     * updateByExample
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") LikePo record, @Param("example") LikePoExample example);

    /**
     * updateByPrimaryKeySelective
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(LikePo record);

    /**
     * updateByPrimaryKey
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(LikePo record);
}
