package com.canary.all_backtesting.service.coin.response;

import com.canary.all_backtesting.domain.coin.Coin;
import lombok.Data;

@Data
public class CoinInformationResponse {

    private String market;
    private String koreanName;
    private String englishName;

    public CoinInformationResponse(Coin coin) {
        this.market = coin.getMarket();
        this.koreanName = coin.getKoreanName();
        this.englishName = coin.getEnglishName();
    }
}
