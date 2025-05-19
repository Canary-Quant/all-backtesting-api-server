package com.canary.all_backtesting.service.coin;

import com.canary.all_backtesting.domain.coin.Coin;
import com.canary.all_backtesting.repository.coin.CoinRepository;
import com.canary.all_backtesting.service.coin.response.CoinInformationResponse;
import com.canary.all_backtesting.service.upbit.response.SupportedCoin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoinService {

    private final CoinRepository coinRepository;

    /**
     * 지원되는 코인 저장 로직
     * @param supportedCoinsOfUpBit : upbit 지원 코인 목록
     */
    @Transactional
    public void initializeCoinsOfUpBit(List<SupportedCoin> supportedCoinsOfUpBit) {
        supportedCoinsOfUpBit.stream()
                .map(dto -> Coin.builder()
                        .market(dto.getMarket())
                        .koreanName(dto.getKoreanName())
                        .englishName(dto.getEnglishName())
                        .build()
                ).forEach(coinRepository::save);
    }

    /**
     * upbit 에서 지원되는 코인 반환
     * @return
     */
    public List<CoinInformationResponse> findAll() {
        return coinRepository.findAll()
                .stream()
                .map(CoinInformationResponse::new)
                .toList();
    }

    /**
     * all backtesting 에서 지원되는 coin 목록 반환
     */

    public List<CoinInformationResponse> findAllBySupported() {
        return coinRepository.findAllBySupported()
                .stream()
                .map(CoinInformationResponse::new)
                .toList();
    }

}
