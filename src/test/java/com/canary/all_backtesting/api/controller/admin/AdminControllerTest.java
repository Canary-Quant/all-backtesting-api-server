package com.canary.all_backtesting.api.controller.admin;

import com.canary.all_backtesting.service.coin.CoinService;
import com.canary.all_backtesting.service.upbit.UpBitService;
import com.canary.all_backtesting.service.upbit.exception.UpBitServiceErrorCode;
import com.canary.all_backtesting.service.upbit.exception.UpBitServiceException;
import com.canary.all_backtesting.service.upbit.response.SupportedCoin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CoinService coinService;

    @MockitoBean
    UpBitService upBitService;

    @DisplayName("upbit 에서 지원하는 코인 목록을 저장할 경우 200 응답을 반환한다.")
    @Test
    void initUpBitSupportedCoin() throws Exception {
        Mockito.when(upBitService.getAllSupportedCoins())
                .thenReturn(List.of(new SupportedCoin("t1", "t1,", "t1")));

        Mockito.doNothing().when(coinService).initializeCoinsOfUpBit(anyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin/coins").contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("upbit 서버에 오류 발생시 500 응답을 반환한다.")
    @Test
    void upBitServerError() throws Exception {
        Mockito.when(upBitService.getAllSupportedCoins())
                .thenThrow(new UpBitServiceException(UpBitServiceErrorCode.UPBIT_SERVER_ERROR));

        Mockito.doNothing().when(coinService).initializeCoinsOfUpBit(anyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin/coins").contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("upbit 서버에 잘못된 요청을 하면 400 응답이 반환된다.")
    @Test
    void upBitClientError() throws Exception {
        Mockito.when(upBitService.getAllSupportedCoins())
                .thenThrow(new UpBitServiceException(UpBitServiceErrorCode.UPBIT_CLIENT_ERROR));

        Mockito.doNothing().when(coinService).initializeCoinsOfUpBit(anyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin/coins").contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("upbit 에 예기치 못한 오류가 발생할 경우 400 응답이 반환된다.")
    @Test
    void upBitUnExpectedError() throws Exception {
        Mockito.when(upBitService.getAllSupportedCoins())
                .thenThrow(new UpBitServiceException(UpBitServiceErrorCode.UNEXPECTED_ERROR));

        Mockito.doNothing().when(coinService).initializeCoinsOfUpBit(anyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin/coins").contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

}