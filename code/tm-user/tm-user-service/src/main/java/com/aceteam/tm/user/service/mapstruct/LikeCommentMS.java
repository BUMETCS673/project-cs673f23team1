package com.aceteam.tm.user.service.mapstruct;

import com.aceteam.tm.user.persistence.entity.LikeCommentPo;
import com.acteam.tm.user.facade.dto.LikeCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface LikeCommentMS extends CommonMS<LikeCommentPo, LikeCommentDTO> {
    LikeCommentMS INSTANCE = Mappers.getMapper(LikeCommentMS.class);
}

