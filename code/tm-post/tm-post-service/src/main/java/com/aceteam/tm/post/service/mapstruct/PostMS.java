package com.aceteam.tm.post.service.mapstruct;

import com.aceteam.tm.post.facade.dto.PostDTO;
import com.aceteam.tm.post.persistence.entity.PostPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface PostMS extends CommonMS<PostPo, PostDTO> {
    PostMS INSTANCE = Mappers.getMapper(PostMS.class);
}

