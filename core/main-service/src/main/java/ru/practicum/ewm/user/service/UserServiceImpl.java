package ru.practicum.ewm.user.service;

import core.common.user.dto.NewUserRequest;
import core.common.user.dto.UserDto;
import core.common.user.dto.UserShortDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.common.exception.BadRequestException;
import core.common.exception.ConflictException;
import core.common.exception.NotFoundException;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto add(NewUserRequest newDto) {
        log.debug("Метод add(); userInputDto={}", newDto);

        if (userRepository.existsByEmail(newDto.getEmail())) {
            throw new ConflictException("User с Email={} уже существует", newDto.getEmail());
        }

        String localpart = newDto.getEmail().substring(0, newDto.getEmail().indexOf('@'));
        if (localpart.length() > 64) {
            throw new BadRequestException("Localpart is too long");
        }
        User savedUser = userRepository.save(userMapper.toEntity(newDto));

        log.debug("Метод add(); User создан savedUser={}", newDto);

        return userMapper.toFullDto(savedUser);
    }

    @Override
    public List<UserDto> findAllBy(List<Long> ids, Integer from, Integer size) {
        log.debug("Метод findAll(); ids={}, from={}, size={}", ids, from, size);

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<User> users;

        if (ids == null || ids.isEmpty()) {
            users = userRepository.findAll(pageable).getContent();
        } else {
            users = userRepository.findAllByIdIn(ids, pageable);
        }

        return users.stream()
                .map(userMapper::toFullDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        log.debug("Сервис UserServiceImpl; Метод delete(); userId={}", userId);

        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new NotFoundException("User userId={} не найден", userId);
        }
    }

    @Override
    public UserShortDto findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id::" + userId + " не найден!"));

        return userMapper.toShortDto(user);
    }

    @Override
    public List<UserShortDto> getUsersByIds(List<Long> usersIds) {
        return userRepository.findAllById(usersIds).stream()
                .map(userMapper::toShortDto)
                .toList();
    }
}