package com.canary.all_backtesting.api.controller.coin;

import com.canary.all_backtesting.api.controller.ApiResponse;
import com.canary.all_backtesting.service.coin.CoinService;
import com.canary.all_backtesting.service.coin.response.CoinInformationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/coins")
@RequiredArgsConstructor
public class CoinController {

    private final CoinService coinService;

    /**
     * @return : all backtesting 에서 지원하는 코인 목록 반환 api
     */
    @GetMapping
    public ApiResponse<List<CoinInformationResponse>> getSupportedCoins() {
        log.info("coinController.getSupportedCoins: all backtesting 에서 지원되는 coin 목록 조회");
        return ApiResponse.ok(coinService.findAllBySupported());
    }
}
