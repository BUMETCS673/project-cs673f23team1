package com.aceteam.tm.user.service.mapstruct;

import com.aceteam.tm.user.persistence.entity.FollowPo;
import com.acteam.tm.user.facade.dto.FollowDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface FollowMS extends CommonMS<FollowPo, FollowDTO> {
    FollowMS INSTANCE = Mappers.getMapper(FollowMS.class);
}

