package qwins.myshop.user.dto;

import lombok.*;
import qwins.myshop.user.User;

public record UserResponseDTO(
        Long id,
        String username
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getUsername()
        );
    }
}
