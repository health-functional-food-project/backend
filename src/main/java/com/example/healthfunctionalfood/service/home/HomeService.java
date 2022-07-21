package com.example.healthfunctionalfood.service.home;

import com.example.healthfunctionalfood.dto.HomeResponseDto;
import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HomeService {

    HomeResponseDto.MainContainer getHomeMain(Pageable pageable);

    List<ProductRankingResponseDto.RankingItem> getProductRankingListWithCategory(String category);
}
