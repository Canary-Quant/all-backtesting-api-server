package com.canary.all_backtesting.domain.coin;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Coin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
