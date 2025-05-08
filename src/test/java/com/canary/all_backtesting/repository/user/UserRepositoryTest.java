package com.canary.all_backtesting.repository.user;

import com.canary.all_backtesting.domain.user.Role;
import com.canary.all_backtesting.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @DisplayName("username 으로 사용자를 조회할 수 있다.")
    @Test
    void findByUsername() {

        // given
        User user = createUser("test", "test");
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByUsername("test");

        assertThat(findUser).isNotEmpty();
        assertThat(findUser.get()).isEqualTo(user);

    }

    private User createUser(String username, String password) {
        return User.builder()
                .username(username)
                .password(password)
                .role(Role.BASIC)
                .build();
    }
}