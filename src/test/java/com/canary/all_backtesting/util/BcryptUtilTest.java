package com.canary.all_backtesting.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BcryptUtilTest {

    @DisplayName("평문을 입력하면 암호화된 암호문이 반환된다.")
    @Test
    void hashPassword() {

        //given
        String plainPassword = "1234";
        String hashedPassword = BcryptUtil.hashPassword(plainPassword);

        System.out.println("hashedPassword = " + hashedPassword);

        //when then
        assertThat(hashedPassword).isNotEqualTo(plainPassword);
    }

    @DisplayName("동일한 비밀번호라도 다른 값으로 도출된다.")
    @Test
    void duplicatedPassword() {

        //given
        String passWord = "1234";

        String hashedPassword1 = BcryptUtil.hashPassword(passWord);
        String hashedPassword2 = BcryptUtil.hashPassword(passWord);

        //when then
        assertThat(hashedPassword2).isNotEqualTo(hashedPassword1);
    }

    @DisplayName("평문과 암호문을 이용해 매칭되는치 판단할 수 있어야 한다.")
    @Test
    void match() {
        //given
        String passWord = "1234";

        String hashedPassword1 = BcryptUtil.hashPassword(passWord);

        //when
        boolean result = BcryptUtil.isMatch(passWord, hashedPassword1);

        //then
        assertThat(result).isTrue();
    }

}