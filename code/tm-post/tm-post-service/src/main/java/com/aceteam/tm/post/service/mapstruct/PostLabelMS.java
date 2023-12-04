package com.aceteam.tm.post.service.mapstruct;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface PostLabelMS extends CommonMS<PostLabelPo, PostLabelDTO> {
    PostLabelMS INSTANCE = Mappers.getMapper(PostLabelMS.class);
}