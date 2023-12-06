package com.aceteam.tm.post.service.mapstruct;

import com.aceteam.tm.post.facade.dto.ResourceNavigateDTO;
import com.aceteam.tm.post.persistence.entity.ResourceNavigatePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface ResourceNavigateMS extends CommonMS<ResourceNavigatePo, ResourceNavigateDTO> {
    ResourceNavigateMS INSTANCE = Mappers.getMapper(ResourceNavigateMS.class);
}
