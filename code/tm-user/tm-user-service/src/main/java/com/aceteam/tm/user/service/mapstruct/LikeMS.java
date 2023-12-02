package com.aceteam.tm.user.service.mapstruct;

import com.aceteam.tm.user.persistence.entity.LikePo;
import com.acteam.tm.user.facade.dto.LikeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface LikeMS extends CommonMS<LikePo, LikeDTO> {
    LikeMS INSTANCE = Mappers.getMapper(LikeMS.class);
}

