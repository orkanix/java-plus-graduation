package ru.practicum.ewm.user.service;

import core.common.user.dto.NewUserRequest;
import core.common.user.dto.UserDto;
import core.common.user.dto.UserShortDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAllBy(List<Long> ids, Integer from, Integer size);

    UserDto add(NewUserRequest newDto);

    void delete(Long userId);

    UserShortDto findUserById(Long userId);

    List<UserShortDto> getUsersByIds(List<Long> usersIds);
}