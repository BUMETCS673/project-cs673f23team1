package com.aceteam.tm.post.service.mapstruct;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface ResourceNavigateMS extends CommonMS<ResourceNavigatePo, ResourceNavigateDTO> {
    ResourceNavigateMS INSTANCE = Mappers.getMapper(ResourceNavigateMS.class);
}
