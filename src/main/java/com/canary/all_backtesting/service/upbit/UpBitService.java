package com.canary.all_backtesting.service.upbit;

import com.canary.all_backtesting.service.upbit.exception.UpBitServiceErrorCode;
import com.canary.all_backtesting.service.upbit.exception.UpBitServiceException;
import com.canary.all_backtesting.service.upbit.response.SupportedCoin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpBitService {

    private final WebClient upBitClient;

    public List<SupportedCoin> getAllSupportedCoins() {
        log.info("upBitService.getAllSupportedCoins: start fetch supported coins from upBit server.");

        try {
            return upBitClient.get()
                    .uri("/v1/market/all")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<SupportedCoin>>() {
                    })
                    .block();
        } catch (WebClientResponseException e) {
            log.error("upBitService.getAllSupportedCoins: {}", e.getMessage());
            if (e.getStatusCode().is4xxClientError())
                throw new UpBitServiceException(UpBitServiceErrorCode.UPBIT_CLIENT_ERROR);
            throw new UpBitServiceException(UpBitServiceErrorCode.UPBIT_SERVER_ERROR);
        } catch (Exception e) {
            log.error("upBitService.getAllSupportedCoins: {}", e.getMessage());
            throw new UpBitServiceException(UpBitServiceErrorCode.UNEXPECTED_ERROR);
        }

    }
}
