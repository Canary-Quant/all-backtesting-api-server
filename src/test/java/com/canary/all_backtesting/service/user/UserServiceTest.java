package com.canary.all_backtesting.service.user;

import com.canary.all_backtesting.domain.user.User;
import com.canary.all_backtesting.repository.user.UserRepository;
import com.canary.all_backtesting.service.user.request.CreateUserServiceRequest;
import com.canary.all_backtesting.service.user.request.LoginServiceRequest;
import com.canary.all_backtesting.service.user.exception.UserServiceException;
import com.canary.all_backtesting.util.jwt.JwtTokenResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @AfterEach
    void cleansing() {
        userRepository.deleteAll();
    }

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

    @DisplayName("동시에 같은 username 을 사용해 회원 가입 요청이 들어올 경우 한 사람만 성공 가능하다.")
    @Transactional(propagation = Propagation.NEVER)
    @Test
    void joinAtTheSameTimeWithSameUsername() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        int threadCount = 2;
        CreateUserServiceRequest request = new CreateUserServiceRequest("same", "pass");
        CountDownLatch latch = new CountDownLatch(threadCount);

        ExecutorService es = Executors.newFixedThreadPool(2);

        es.execute(() -> {
            try {
                userService.join(request);
                successCount.incrementAndGet();
            } catch (Exception e) {
                failCount.incrementAndGet();
            } finally {
                latch.countDown();
            }
        });

        es.execute(() -> {
            try {
                userService.join(request);
                successCount.incrementAndGet();
            } catch (Exception e) {
                failCount.incrementAndGet();
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        System.out.println("success count = " + successCount.get());
        System.out.println("fail count = " + failCount.get());
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(1);
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

    @DisplayName("로그인 성공시 jwt token 이 발급된다.")
    @Test
    void login() {
        //given
        long issuedMs = System.currentTimeMillis();
        userService.join(new CreateUserServiceRequest("test", "test"));

        // when
        JwtTokenResponse response = userService.login(new LoginServiceRequest("test", "test", issuedMs));

        //then
        assertThat(response.getAccessToken()).isNotNull();
        assertThat(response.getAccessToken()).isNotBlank();
        assertThat(response.getExpiredSec()).isNotEqualTo(0);
    }

    @DisplayName("없는 아이디로 조회시 예외가 발생한다.")
    @Test
    void loginWithInvalidUsername() {
        //given
        long issuedMs = System.currentTimeMillis();
        userService.join(new CreateUserServiceRequest("test", "test"));

        //when then
        assertThatThrownBy(() -> userService.login(new LoginServiceRequest("test!", "test", issuedMs)))
                .isInstanceOf(UserServiceException.class)
                .hasMessage("해당 사용자를 찾을 수 없습니다.");
    }

    @DisplayName("올바르지 않은 비밀번호 입력시 예외가 발생한다.")
    @Test
    void loginWithInvalidPassword() {

        //given
        long issuedMs = System.currentTimeMillis();
        userService.join(new CreateUserServiceRequest("test", "test"));

        //when then
        assertThatThrownBy(() -> userService.login(new LoginServiceRequest("test", "test!", issuedMs)))
                .isInstanceOf(UserServiceException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}