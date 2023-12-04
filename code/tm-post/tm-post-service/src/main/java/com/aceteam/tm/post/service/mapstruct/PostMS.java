package com.aceteam.tm.post.service.mapstruct;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface PostMS extends CommonMS<PostPo, PostDTO> {
    PostMS INSTANCE = Mappers.getMapper(PostMS.class);
}

