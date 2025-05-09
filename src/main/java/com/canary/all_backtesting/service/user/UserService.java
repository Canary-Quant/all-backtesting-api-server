package com.canary.all_backtesting.service.user;

import com.canary.all_backtesting.domain.user.Role;
import com.canary.all_backtesting.domain.user.User;
import com.canary.all_backtesting.repository.user.UserRepository;
import com.canary.all_backtesting.service.request.CreateUserServiceRequest;
import com.canary.all_backtesting.service.user.exception.UserServiceException;
import com.canary.all_backtesting.util.BcryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.canary.all_backtesting.service.user.exception.UserServiceErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(CreateUserServiceRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserServiceException(DUPLICATED_USERNAME);
        }

        String hashedPassword = BcryptUtil.hashPassword(password);

        User user = User.builder()
                .username(username)
                .password(hashedPassword)
                .role(Role.BASIC)
                .build();

        userRepository.save(user);
    }
}
