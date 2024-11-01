package tony.spring.loginboilerplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tony.spring.loginboilerplate.domain.User;
import tony.spring.loginboilerplate.domain.dto.request.AddUserRequest;
import tony.spring.loginboilerplate.exception.AuthException;
import tony.spring.loginboilerplate.exception.ErrorCode;
import tony.spring.loginboilerplate.repository.UserRepository;

import static tony.spring.loginboilerplate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build();

        return userRepository.save(user).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new AuthException(NOT_FOUND_USER));
    }
}
