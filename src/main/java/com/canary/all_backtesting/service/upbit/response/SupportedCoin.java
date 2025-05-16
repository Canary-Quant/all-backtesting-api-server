package com.canary.all_backtesting.service.upbit.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SupportedCoin {

    private final String market;

    @JsonProperty("korean_name")
    private final String koreanName;

    @JsonProperty("english_name")
    private final String englishName;

    public SupportedCoin(String market, String koreanName, String englishName) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
    }
}
