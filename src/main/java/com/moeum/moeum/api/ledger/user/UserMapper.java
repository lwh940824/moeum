package com.moeum.moeum.api.ledger.user;

import com.moeum.moeum.api.ledger.user.dto.UserResponseDto;
import com.moeum.moeum.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);
}
