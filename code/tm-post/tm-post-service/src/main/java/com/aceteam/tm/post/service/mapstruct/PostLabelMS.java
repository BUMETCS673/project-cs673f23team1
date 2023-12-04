package com.aceteam.tm.post.service.mapstruct;

import com.aceteam.tm.post.facade.dto.PostLabelDTO;
import com.aceteam.tm.post.persistence.entity.PostLabelPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface PostLabelMS extends CommonMS<PostLabelPo, PostLabelDTO> {
    PostLabelMS INSTANCE = Mappers.getMapper(PostLabelMS.class);
}