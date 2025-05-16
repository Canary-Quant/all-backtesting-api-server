package com.canary.all_backtesting.repository.coin;

import com.canary.all_backtesting.domain.coin.Coin;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CoinRepositoryTest {

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    EntityManager em;

    @DisplayName("지원하는 코인을 등록할 수 있다.")
    @Test
    void save() {

        //given
        Coin coin = createCoin("test", "testKor", "testEng");
        coinRepository.save(coin);

        em.clear();

        //when
        Optional<Coin> optional = coinRepository.findById(coin.getId());

        //then
        assertThat(optional).isNotEmpty();
        assertThat(optional.get().getMarket()).isEqualTo("test");
        assertThat(optional.get().getKoreanName()).isEqualTo("testKor");
        assertThat(optional.get().getEnglishName()).isEqualTo("testEng");

    }

    @DisplayName("지원하는 코인을 삭제할 수 있다.")
    @Test
    void delete() {

        //given
        Coin btc = createCoin("test", "btc-kor", "btc");
        Coin eth = createCoin("test", "eth-kor", "eth");
        Coin xrp = createCoin("test", "xrp-kor", "xrp");

        coinRepository.saveAll(List.of(btc, eth, xrp));
        coinRepository.deleteById(eth.getId());
        coinRepository.deleteById(xrp.getId());

        //when
        List<Coin> coins = coinRepository.findAll();

        //then
        assertThat(coins).hasSize(1);
        assertThat(coins.get(0).getId()).isEqualTo(btc.getId());
    }



    private Coin createCoin(String market, String koreanName, String englishName) {
        return Coin.builder()
                .market(market)
                .koreanName(koreanName)
                .englishName(englishName)
                .build();
    }
}