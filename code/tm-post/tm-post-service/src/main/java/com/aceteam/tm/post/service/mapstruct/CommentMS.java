package com.aceteam.tm.post.service.mapstruct;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface CommentMS extends CommonMS<CommentPo, CommentDTO> {
    CommentMS INSTANCE = Mappers.getMapper(CommentMS.class);
}