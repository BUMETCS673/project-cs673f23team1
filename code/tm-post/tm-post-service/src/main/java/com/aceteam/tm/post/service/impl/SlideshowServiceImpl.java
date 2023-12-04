package com.aceteam.tm.post.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
@Service
public class SlideshowServiceImpl {

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
