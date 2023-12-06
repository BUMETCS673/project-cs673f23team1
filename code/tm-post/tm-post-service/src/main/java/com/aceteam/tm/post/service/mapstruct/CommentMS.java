package com.aceteam.tm.post.service.mapstruct;

import com.aceteam.tm.post.facade.dto.CommentDTO;
import com.aceteam.tm.post.persistence.entity.CommentPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface CommentMS extends CommonMS<CommentPo, CommentDTO> {
    CommentMS INSTANCE = Mappers.getMapper(CommentMS.class);
}