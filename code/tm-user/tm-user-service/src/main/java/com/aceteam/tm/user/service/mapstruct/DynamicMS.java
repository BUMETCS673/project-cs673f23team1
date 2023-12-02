package com.aceteam.tm.user.service.mapstruct;

import com.aceteam.tm.user.persistence.entity.DynamicPo;
import com.acteam.tm.user.facade.dto.DynamicDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface DynamicMS extends CommonMS<DynamicPo, DynamicDTO> {
    DynamicMS INSTANCE = Mappers.getMapper(DynamicMS.class);
}
