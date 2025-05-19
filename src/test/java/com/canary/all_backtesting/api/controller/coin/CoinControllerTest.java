package com.canary.all_backtesting.api.controller.coin;

import com.canary.all_backtesting.domain.coin.Coin;
import com.canary.all_backtesting.service.coin.CoinService;
import com.canary.all_backtesting.service.coin.response.CoinInformationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CoinController.class)
class CoinControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CoinService coinService;

    @DisplayName("all backtesting 에서 지원하는 모든 코인을 조회할 수 있다.")
    @Test
    void getSupportedCoins() throws Exception {

        Mockito.when(coinService.findAllBySupported())
                .thenReturn(List.of(new CoinInformationResponse(createCoin("t1", "t1", "t1"))));

        mockMvc.perform(
                get("/coins")
        ).andDo(print())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data").isArray());
    }

    private Coin createCoin(String market, String koreanName, String englishName) {
        return Coin.builder()
                .market(market)
                .koreanName(koreanName)
                .englishName(englishName)
                .build();
    }
}