package com.canary.all_backtesting.service.user;

import com.canary.all_backtesting.domain.user.Role;
import com.canary.all_backtesting.domain.user.User;
import com.canary.all_backtesting.repository.user.UserRepository;
import com.canary.all_backtesting.service.request.CreateUserServiceRequest;
import com.canary.all_backtesting.service.request.LoginServiceRequest;
import com.canary.all_backtesting.service.user.exception.UserServiceException;
import com.canary.all_backtesting.util.BcryptUtil;
import com.canary.all_backtesting.util.jwt.CreateJwtRequest;
import com.canary.all_backtesting.util.jwt.JwtTokenResponse;
import com.canary.all_backtesting.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.canary.all_backtesting.service.user.exception.UserServiceErrorCode.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

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

    public JwtTokenResponse login(LoginServiceRequest request) {

        String username = request.getUsername();

        User findUser = userRepository
                .findByUsername(username).orElseThrow(() -> new UserServiceException(NOT_FOUND_USER));

        String hashedPassword = findUser.getPassword();
        boolean matchResult = BcryptUtil.isMatch(request.getPassword(), hashedPassword);

        if (!matchResult) {
            throw new UserServiceException(INVALID_PASSWORD);
        }

        return jwtUtil.createJwtToken(new CreateJwtRequest(username, Role.BASIC, request.getNowMs()));
    }
}
