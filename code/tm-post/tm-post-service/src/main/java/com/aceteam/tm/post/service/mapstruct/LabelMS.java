package com.aceteam.tm.post.service.mapstruct;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface LabelMS extends CommonMS<LabelPo, LabelDTO> {
    LabelMS INSTANCE = Mappers.getMapper(LabelMS.class);
}
