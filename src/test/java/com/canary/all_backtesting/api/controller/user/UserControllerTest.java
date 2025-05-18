package com.canary.all_backtesting.api.controller.user;

import com.canary.all_backtesting.api.controller.user.request.CreateUserRequest;
import com.canary.all_backtesting.service.user.UserService;
import com.canary.all_backtesting.service.user.exception.UserServiceErrorCode;
import com.canary.all_backtesting.service.user.exception.UserServiceException;
import com.canary.all_backtesting.service.user.request.CreateUserServiceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserService userService;

    @DisplayName("회원 가입에 성공하면 200 응답이 반환된다.")
    @Test
    void join() throws Exception {

        CreateUserRequest request = new CreateUserRequest("test", "test");

        String content = objectMapper.writeValueAsString(request);
        System.out.println("content = " + content);

        doNothing().when(userService).join(any());

        mockMvc.perform(
                        post("/users")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("같은 username 을 가진 회원이 있다면 400 응답이 반환된다.")
    @Test
    void duplicatedUsername() throws Exception {

        CreateUserRequest request = new CreateUserRequest("test", "test");

        String content = objectMapper.writeValueAsString(request);
        System.out.println("content = " + content);

        doThrow(new UserServiceException(UserServiceErrorCode.DUPLICATED_USERNAME))
                .when(userService).join(any());

        mockMvc.perform(
                        post("/users")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

}