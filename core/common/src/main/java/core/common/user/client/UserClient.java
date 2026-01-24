package core.common.user.client;

import core.common.user.dto.NewUserRequest;
import core.common.user.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "main-service")
public interface UserClient {

    String ADMIN_PREFIX = "/admin/users";

    @GetMapping(ADMIN_PREFIX)
    List<UserDto> findAll(@RequestParam(required = false) List<Long> ids,
                          @RequestParam(defaultValue = "0", required = false) Integer from,
                          @RequestParam(defaultValue = "10", required = false) Integer size);

    @PostMapping(ADMIN_PREFIX)
    UserDto add(@RequestBody NewUserRequest newDto);

    @DeleteMapping(ADMIN_PREFIX + "/{userId}")
    void delete(@PathVariable Long userId);
}
