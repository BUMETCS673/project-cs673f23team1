package com.aceteam.tm.user.service.mapstruct;

import com.aceteam.tm.user.persistence.entity.UserLevelPo;
import com.acteam.tm.user.facade.dto.UserLevelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description: some desc
 * @author: haoran
 */
@Mapper(componentModel = "spring")
public interface UserLevelMS extends CommonMS<UserLevelPo, UserLevelDTO> {
    UserLevelMS INSTANCE = Mappers.getMapper(UserLevelMS.class);
}

