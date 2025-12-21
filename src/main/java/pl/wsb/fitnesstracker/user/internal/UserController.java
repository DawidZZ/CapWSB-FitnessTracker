package pl.wsb.fitnesstracker.user.internal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;

import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserIdEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSummaryDto;

import java.util.List;
import java.util.Optional;

/**
 * UserController is responsible for handling HTTP requests related to user operations.
 * It provides endpoints for retrieving and creating users.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userService.createUser(user));
    }
    @GetMapping("/summary")
    public List<UserSummaryDto> getUsersSummary() {
        return userService.findAllUsers().stream()
                .map(u -> new UserSummaryDto(
                        u.getId(),
                        u.getFirstName() + " " + u.getLastName()
                ))
                .toList();
    }
    @GetMapping("/search-by-email")
    public List<UserIdEmailDto> searchByEmailFragment(@RequestParam String fragment) {
        return userService.findAllUsers().stream()
                .filter(u -> u.getEmail() != null)
                .filter(u -> u.getEmail().toLowerCase().contains(fragment.toLowerCase()))
                .map(u -> new UserIdEmailDto(u.getId(), u.getEmail()))
                .toList();
    }

}
