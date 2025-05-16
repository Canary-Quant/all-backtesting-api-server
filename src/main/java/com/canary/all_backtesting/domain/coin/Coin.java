package com.canary.all_backtesting.domain.coin;

import com.canary.all_backtesting.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_id")
    private Long id;

    @Column(nullable = false)
    private String market;

    @Column(nullable = false)
    private String koreanName;

    @Column(nullable = false)
    private String englishName;

    @Builder
    private Coin(String market, String koreanName, String englishName) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
    }
}
