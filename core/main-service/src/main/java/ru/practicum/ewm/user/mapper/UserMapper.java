package ru.practicum.ewm.user.mapper;

import core.common.user.dto.NewUserRequest;
import core.common.user.dto.UserDto;
import core.common.user.dto.UserShortDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toFullDto(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(NewUserRequest newDto);

    UserShortDto toShortDto(User user);
}