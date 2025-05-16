package com.canary.all_backtesting.service.upbit;

import com.canary.all_backtesting.service.upbit.response.SupportedCoin;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpBitService {

    private final WebClient upBitClient;

    public List<SupportedCoin> getAllSupportedCoins() {
        return upBitClient.get()
                .uri("/v1/market/all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SupportedCoin>>() {
                })
                .block();
    }
}
