package com.aceteam.tm.post.service.mapstruct;

import com.aceteam.tm.post.facade.dto.SlideshowDTO;
import com.aceteam.tm.post.persistence.entity.SlideshowPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface SlideshowMS extends CommonMS<SlideshowPo, SlideshowDTO> {
    SlideshowMS INSTANCE = Mappers.getMapper(SlideshowMS.class);
}
