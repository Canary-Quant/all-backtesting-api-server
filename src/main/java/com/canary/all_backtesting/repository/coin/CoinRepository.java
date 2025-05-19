package com.canary.all_backtesting.repository.coin;

import com.canary.all_backtesting.domain.coin.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    @Query("select c from Coin c where c.supported = true")
    List<Coin> findAllBySupported();
}
