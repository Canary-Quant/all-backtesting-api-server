package com.canary.all_backtesting.service.user;

import com.canary.all_backtesting.repository.user.UserRepository;
import com.canary.all_backtesting.service.request.CreateUserServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void join(CreateUserServiceRequest request) {
        String username = request.getUsername();

    }
}
