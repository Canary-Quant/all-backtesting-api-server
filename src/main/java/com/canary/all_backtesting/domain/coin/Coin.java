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

    @Column(nullable = false, unique = true)
    private String market;

    @Column(nullable = false)
    private String koreanName;

    @Column(nullable = false)
    private String englishName;

    @Column(nullable = false)
    private boolean supported;

    @Builder
    private Coin(String market, String koreanName, String englishName) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
    }

    public void support() {
        supported = true;
    }

    public void terminateSupport() {
        supported = false;
    }
}
