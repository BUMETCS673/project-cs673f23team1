package com.aceteam.tm.post.service.impl;

import com.aceteam.tm.post.facade.dto.SlideshowDTO;
import com.aceteam.tm.post.facade.server.SlideshowService;
import com.aceteam.tm.post.persistence.entity.SlideshowPoExample;
import com.aceteam.tm.post.persistence.mapper.SlideshowPoMapper;
import com.aceteam.tm.post.service.mapstruct.SlideshowMS;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
@Service
public class SlideshowServiceImpl implements SlideshowService {

    @Autowired
    private SlideshowPoMapper slideshowPoMapper;

    @Override
    public List<SlideshowDTO> getList() {
        SlideshowPoExample example = new SlideshowPoExample();
        example.createCriteria().andStateEqualTo(true)
                .andIsDeletedEqualTo(false);

        return SlideshowMS.INSTANCE.toDTO(slideshowPoMapper.selectByExample(example));
    }
}
