package com.example.healthfunctionalfood.service.review.expert;

import com.example.healthfunctionalfood.advice.exception.ApiRequestException;
import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.review.ExpertReview;
import com.example.healthfunctionalfood.domain.user.Role;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.request.ExpertReviewRequestDto;
import com.example.healthfunctionalfood.repository.ExpertReviewRepository;
import com.example.healthfunctionalfood.repository.ProductRepository;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ExpertServiceImpl implements ExpertService{

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ExpertReviewRepository expertReviewRepository;

    @Override
    @Transactional
    public void addExpertReview(Long productId, ExpertReviewRequestDto.CreateReview createReview) {
        // 로그인 미완성으로 임시 하드코딩
        // 카카오 로그인으로 사용자 정보 받는 로직 추가 필요
        User user = userRepository.findById(1L).get();
        Optional<Product> productOptional = Optional.of(productRepository.findById(productId).orElseThrow(()
                ->new ApiRequestException("존재하지 않는 상품입니다.")
        ));

        if(user.getRole() == Role.EXPERT){
            ExpertReview saveExpertReview = createReview.toEntity(productOptional.get(), user);
            expertReviewRepository.save(saveExpertReview);
        }else {
            throw new ApiRequestException("전문가가 아닙니다.");
        }
    }

    @Override
    @Transactional
    public void modifyExpertReview(Long productId, Long expertReviewId, ExpertReviewRequestDto.CreateReview createReview) {
        // 로그인 미완성으로 임시 하드코딩
        // 카카오 로그인으로 사용자 정보 받는 로직 추가 필요
        User user = userRepository.findById(1L).get();
        Optional<Product> productOptional = Optional.of(productRepository.findById(productId).orElseThrow(()
                ->new ApiRequestException("존재하지 않는 상품입니다.")
        ));

        Optional<ExpertReview> expertReviewOptional = Optional.ofNullable(expertReviewRepository.findByIdAndUserId(expertReviewId, user.getId()).orElseThrow(
                () -> new ApiRequestException("내가 작성한 리뷰가 아니거나 존재하지 않는 리뷰입니다.")));

        if(user.getRole()==Role.EXPERT){
            expertReviewOptional.get().updateExpertReview(createReview);
        }
    }

    @Override
    @Transactional
    public void removeExpertReview(Long productId, Long expertReviewId) {
        // 로그인 미완성으로 임시 하드코딩
        // 카카오 로그인으로 사용자 정보 받는 로직 추가 필요
        User user = userRepository.findById(1L).get();
        Optional<Product> productOptional = Optional.of(productRepository.findById(productId).orElseThrow(()
                ->new ApiRequestException("존재하지 않는 상품입니다.")
        ));

        Optional<ExpertReview> expertReviewOptional = Optional.ofNullable(expertReviewRepository.findByIdAndUserId(expertReviewId, user.getId()).orElseThrow(
                () -> new ApiRequestException("내가 작성한 리뷰가 아니거나 존재하지 않는 리뷰입니다.")));

        if(user.getRole().equals(Role.EXPERT)){
            expertReviewRepository.deleteById(expertReviewId);
        }
    }
}
