package com.canary.all_backtesting.service.user;

import com.canary.all_backtesting.domain.user.User;
import com.canary.all_backtesting.repository.user.UserRepository;
import com.canary.all_backtesting.service.request.CreateUserServiceRequest;
import com.canary.all_backtesting.service.user.exception.UserServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @DisplayName("회원가입할 수 있다.")
    @Test
    void join() {

        //given
        userService.join(new CreateUserServiceRequest("test", "test"));

        //when
        Optional<User> optional = userRepository.findByUsername("test");

        //then
        assertThat(optional).isNotEmpty();
        assertThat(optional.get().getUsername()).isEqualTo("test");
        assertThat(optional.get().getPassword()).isNotEqualTo("test");

    }

    @DisplayName("username 은 중복될 수 없다.")
    @Test
    void duplicatedUsername() {
        //given
        userService.join(new CreateUserServiceRequest("test", "test"));

        assertThatThrownBy(() -> userService.join(new CreateUserServiceRequest("test", "test2")))
                .isInstanceOf(UserServiceException.class);
    }

    @DisplayName("password 가 동일해도 다른 값이 저장된다.")
    @Test
    void duplicatedPassword() {
        //given
        userService.join(new CreateUserServiceRequest("test1", "test"));
        userService.join(new CreateUserServiceRequest("test2", "test"));

        //when
        User user1 = userRepository.findByUsername("test1").get();
        User user2 = userRepository.findByUsername("test2").get();

        //then
        assertThat(user1.getPassword()).isNotEqualTo(user2.getPassword());

    }


}