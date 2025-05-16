package com.canary.all_backtesting.api.controller;

import com.canary.all_backtesting.service.upbit.UpBitService;
import com.canary.all_backtesting.service.upbit.response.SupportedCoin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final UpBitService upBitService;

    @GetMapping("/all-coins")
    public void test() {
        List<SupportedCoin> supportedCoins = upBitService.getAllSupportedCoins();


    }
}
