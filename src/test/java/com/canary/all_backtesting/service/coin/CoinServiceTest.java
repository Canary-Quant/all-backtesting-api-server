package com.canary.all_backtesting.service.coin;

import com.canary.all_backtesting.domain.coin.Coin;
import com.canary.all_backtesting.repository.coin.CoinRepository;
import com.canary.all_backtesting.service.upbit.response.SupportedCoin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CoinServiceTest {

    @Autowired
    CoinService coinService;

    @Autowired
    CoinRepository coinRepository;

    @Test
    void initializeCoinsOfUpBit() {
        List<SupportedCoin> supportedCoins = List.of(new SupportedCoin("test1", "test1", "test1"), new SupportedCoin("test2", "test2", "test2"));
        coinService.initializeCoinsOfUpBit(supportedCoins);
        List<Coin> findCoins = coinRepository.findAll();

        assertThat(findCoins).hasSize(2);
    }

}