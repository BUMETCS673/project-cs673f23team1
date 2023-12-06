package com.aceteam.tm.post.service.mapstruct;

import com.aceteam.tm.post.facade.dto.LabelDTO;
import com.aceteam.tm.post.persistence.entity.LabelPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface LabelMS extends CommonMS<LabelPo, LabelDTO> {
    LabelMS INSTANCE = Mappers.getMapper(LabelMS.class);
}
