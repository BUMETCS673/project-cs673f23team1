package com.aceteam.tm.post.facade.server;

import com.aceteam.tm.post.facade.dto.LabelDTO;
import com.aceteam.tm.post.facade.dto.LabelSearchDTO;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.auth.UserSsoDTO;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface LabelService {
    /**
     * 获取标签
     *
     * @param labelSearchDTO
     * @return
     */
    PageInfo<LabelDTO> getList(LabelSearchDTO labelSearchDTO);

    /**
     * 通过标签id集合获取标签信息
     *
     * @param ids
     * @return
     */
    List<LabelDTO> getByIds(List<Integer> ids);

    /**
     * 新增标签
     *
     * @param labelDTO
     * @param currentUser
     * @return
     */
    Boolean create(LabelDTO labelDTO, UserSsoDTO currentUser);

    /**
     * 上传标签logo
     *
     * @param bytes
     * @param sourceFileName
     * @return
     */
    String uploadLabelLogo(byte[] bytes, String sourceFileName);

    /**
     * 更新标签
     *
     * @param labelDTO
     * @param currentUser
     * @return
     */
    Boolean update(LabelDTO labelDTO, UserSsoDTO currentUser);

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    Boolean delete(Integer id);
}

