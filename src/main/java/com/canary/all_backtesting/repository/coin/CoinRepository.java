package com.canary.all_backtesting.repository.coin;

import com.canary.all_backtesting.domain.coin.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {
}
