package qwins.myshop.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User addUser(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "User with id " + id + " not found"
                        )
                );
    }

    public User getUserByUsername(String username) {
        return  userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found")
        );
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "User with id " + id + " not found"
                )
        );

        existingUser.setUsername(userDetails.getUsername());
        existingUser.setPassword(userDetails.getPassword());
        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "User with id " + id + " not found"
            );
        }
        userRepository.deleteById(id);
    }

}
