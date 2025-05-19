package com.canary.all_backtesting.service.coin;

import com.canary.all_backtesting.domain.coin.Coin;
import com.canary.all_backtesting.repository.coin.CoinRepository;
import com.canary.all_backtesting.service.coin.response.CoinInformationResponse;
import com.canary.all_backtesting.service.upbit.response.SupportedCoin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CoinServiceTest {

    @Autowired
    CoinService coinService;

    @Autowired
    CoinRepository coinRepository;

    @DisplayName("지원되는 코인 목록을 초기화할 수 있다.")
    @Test
    void initializeCoinsOfUpBit() {
        List<SupportedCoin> supportedCoins = List.of(new SupportedCoin("test1", "test1", "test1"), new SupportedCoin("test2", "test2", "test2"));
        coinService.initializeCoinsOfUpBit(supportedCoins);
        List<Coin> findCoins = coinRepository.findAll();

        assertThat(findCoins).hasSize(2);
    }

    @DisplayName("저장된 코인의 정보를 모두 반환할 수 있다.")
    @Test
    void findAll() {
        coinRepository.save(createCoin("t1", "t1", "t1"));
        coinRepository.save(createCoin("t2", "t2", "t2"));
        coinRepository.save(createCoin("t3", "t3", "t3"));

        List<CoinInformationResponse> findCoins = coinService.findAll();

        assertThat(findCoins).hasSize(3);
    }

    @DisplayName("all backtesting 에서 지원하는 코인의 정보를 모두 반환할 수 있다.")
    @Test
    void findAllBySupported() {
        Coin t1 = createCoin("t1", "t1", "t1");
        Coin t2 = createCoin("t2", "t2", "t2");
        Coin t3 = createCoin("t3", "t3", "t3");

        t1.support();
        t2.support();

        coinRepository.saveAll(List.of(t1, t2, t3));

        List<CoinInformationResponse> supportedCoinInformation = coinService.findAllBySupported();

        assertThat(supportedCoinInformation).hasSize(2);
    }

    private Coin createCoin(String market, String kName, String eName) {
        return Coin.builder()
                .market(market)
                .koreanName(kName)
                .englishName(eName)
                .build();
    }
}