package com.canary.all_backtesting.util.jwt;

import com.canary.all_backtesting.domain.user.Role;
import com.canary.all_backtesting.util.jwt.exception.JwtUtilsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @DisplayName("토근을 발급 받을 경우 payload 에는 username 과 role 이 포함되어 있다.")
    @Test
    void createAccessToken() {
        JwtTokenResponse response = jwtUtil.createJwtToken(new CreateJwtRequest("name", Role.BASIC, System.currentTimeMillis()));

        String accessToken = response.getAccessToken();
        String userName = jwtUtil.getUserName(accessToken);
        Role role = jwtUtil.getUserRole(accessToken);

        assertThat(userName).isEqualTo("name");
        assertThat(role).isEqualTo(Role.BASIC);
    }

    @DisplayName("기간이 만료된 token 을 검증할 때 예외가 발생한다.")
    @Test
    void validateExpiredToken() {
        long issuedTime = System.currentTimeMillis() - 1800000;
        CreateJwtRequest request = new CreateJwtRequest("username", Role.BASIC, issuedTime);

        String expiredToken = jwtUtil.createJwtToken(request).getAccessToken();

        assertThatThrownBy(() -> jwtUtil.validateToken(expiredToken))
                .isInstanceOf(JwtUtilsException.class)
                .hasMessage("만료된 토큰입니다.");
    }

}