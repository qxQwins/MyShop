package qwins.myshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qwins.myshop.user.dto.UserCreateDTO;
import qwins.myshop.user.dto.UserResponseDTO;
import qwins.myshop.user.dto.UserUpdateDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userDTO) {
        User newUser =
                userService.addUser(
                        User.builder()
                                .username(userDTO.getUsername())
                                .password(userDTO.getPassword())
                                .build()
                );
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(newUser));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        Page<User> usersPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(usersPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUserName(
            @PathVariable String username
    ) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO userDTO) {
        User updatedUser = userService.updateUser(id,
                User.builder()
                        .username(userDTO.getUsername())
                        .build()
        );
        return ResponseEntity.ok(new UserResponseDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
