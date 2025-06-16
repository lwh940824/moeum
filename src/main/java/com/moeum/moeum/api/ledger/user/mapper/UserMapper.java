package com.moeum.moeum.api.ledger.user.mapper;

import com.moeum.moeum.api.ledger.user.dto.UserResponseDto;
import com.moeum.moeum.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponseDto toDto(User user);
}
