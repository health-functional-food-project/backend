package com.example.healthfunctionalfood.service.home;

import com.example.healthfunctionalfood.dto.HomeResponseDto;
import org.springframework.data.domain.Pageable;

public interface HomeService {

    HomeResponseDto.MainContainer getHomeMain(Pageable pageable);
}
