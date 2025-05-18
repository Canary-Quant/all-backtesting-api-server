package com.canary.all_backtesting.api.controller.login;

import com.canary.all_backtesting.api.controller.login.request.LoginRequest;
import com.canary.all_backtesting.service.user.UserService;
import com.canary.all_backtesting.service.user.exception.UserServiceErrorCode;
import com.canary.all_backtesting.service.user.exception.UserServiceException;
import com.canary.all_backtesting.service.user.request.LoginServiceRequest;
import com.canary.all_backtesting.util.jwt.JwtTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserService userService;

    @DisplayName("로그인에 성공할 경우 Access token 이 발급된다.")
    @Test
    void login() throws Exception{

        LoginRequest request = new LoginRequest("username", "password");

        Mockito.when(userService.login(Mockito.any(LoginServiceRequest.class)))
                        .thenReturn(new JwtTokenResponse("acesstoken", 1000L));

        mockMvc.perform(
                post("/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("username 을 빈 값으로 로그인을 시도할 수 없다.")
    @Test
    void emptyUsername() throws Exception{
        LoginRequest request = new LoginRequest("", "password");

        mockMvc.perform(
                        post("/login")
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("username 을 null 으로 로그인을 시도할 수 없다.")
    @Test
    void nullUsername() throws Exception{
        LoginRequest request = new LoginRequest(null, "password");

        mockMvc.perform(
                        post("/login")
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("password 를 null 으로 로그인을 시도할 수 없다.")
    @Test
    void nullPassword() throws Exception{
        LoginRequest request = new LoginRequest("username", null);

        mockMvc.perform(
                        post("/login")
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("password 를 빈 값으로 로그인을 시도할 수 없다.")
    @Test
    void emptyPassword() throws Exception{
        LoginRequest request = new LoginRequest("username", "");

        mockMvc.perform(
                        post("/login")
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("없는 사용자가 로그인 시도를 할 경우, 401 로 반환된다.")
    @Test
    void notFoundUser() throws Exception{
        LoginRequest request = new LoginRequest("username", "password");

        Mockito.when(userService.login(Mockito.any(LoginServiceRequest.class)))
                .thenThrow(new UserServiceException(UserServiceErrorCode.NOT_FOUND_USER));

        mockMvc.perform(
                post("/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.data").value("해당 사용자를 찾을 수 없습니다."));

    }

    @DisplayName("비밀번호가 일치하지 않을 경우 401로 반환된다.")
    @Test
    void invalidPassword() throws Exception{
        LoginRequest request = new LoginRequest("username", "password");

        Mockito.when(userService.login(Mockito.any(LoginServiceRequest.class)))
                .thenThrow(new UserServiceException(UserServiceErrorCode.INVALID_PASSWORD));

        mockMvc.perform(
                        post("/login")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.data").value("비밀번호가 일치하지 않습니다."));

    }
}