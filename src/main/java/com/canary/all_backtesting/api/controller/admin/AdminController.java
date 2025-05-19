package com.canary.all_backtesting.api.controller.admin;

import com.canary.all_backtesting.api.controller.ApiResponse;
import com.canary.all_backtesting.service.coin.CoinService;
import com.canary.all_backtesting.service.upbit.UpBitService;
import com.canary.all_backtesting.service.upbit.response.SupportedCoin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UpBitService upBitService;
    private final CoinService coinService;

    @PostMapping("/coins")
    public ApiResponse<Void> initUpBitSupportedCoin() {

        log.info("adminController.initUpBitSupportedCoin: start initialize upBit supported Coin!");
        List<SupportedCoin> supportedCoins = upBitService.getAllSupportedCoins();

        coinService.initializeCoinsOfUpBit(supportedCoins);
        log.info("adminController.initUpBitSupportedCoin: finish persist");
        return ApiResponse.ok();
    }
}
