package com.moeum.moeum.api.ledger.User;

import com.moeum.moeum.api.ledger.User.dto.UserResponseDto;
import com.moeum.moeum.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);
}
