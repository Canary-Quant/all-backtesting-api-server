package com.canary.all_backtesting.domain.coin;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coin {

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

    @Column(nullable = false)
    private LocalDateTime createdAt;


    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private Coin(String market, String koreanName, String englishName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
