package com.canary.all_backtesting.service.coin;

import com.canary.all_backtesting.domain.coin.Coin;
import com.canary.all_backtesting.repository.coin.CoinRepository;
import com.canary.all_backtesting.service.upbit.response.SupportedCoin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoinService {

    private final CoinRepository coinRepository;

    @Transactional
    public void initializeCoinsOfUpBit(List<SupportedCoin> supportedCoinsOfUpBit) {
        supportedCoinsOfUpBit.stream()
                .map(dto -> Coin.builder()
                        .market(dto.getMarket())
                        .koreanName(dto.getKoreanName())
                        .englishName(dto.getEnglishName())
                        .build()
                ).forEach(coinRepository::save);
    }

}
