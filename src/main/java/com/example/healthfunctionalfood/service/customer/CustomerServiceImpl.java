package com.example.healthfunctionalfood.service.customer;

import com.example.healthfunctionalfood.advice.exception.ApiRequestException;
import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.review.CustomerReview;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.CustomerReviewDto;
import com.example.healthfunctionalfood.repository.CustomerReviewRepository;
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
public class CustomerServiceImpl implements CustomerService{

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CustomerReviewRepository customerReviewRepository;
    @Override
    @Transactional
    public Long addCustomerReview(Long productId, CustomerReviewDto.CreateReview createReview) {
        // 로그인 미완성으로 임시 하드코딩
        User userEntity = userRepository.findById(1L).get();

        Product productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("존재하지 않는 제품입니다."));
        CustomerReview saveCustomerReview = createReview.toEntity(productEntity, userEntity);

        CustomerReview customer = customerReviewRepository.save(saveCustomerReview);
        return customer.getId();
    }

    @Override
    @Transactional
    public void modifyCustomerReview(Long productId, CustomerReviewDto.CreateReview updateReview, Long customerReviewId) {
        // 로그인 미완성으로 임시 하드코딩
        User userEntity = userRepository.findById(1L).get();

        Product productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("존재하지 않는 제품입니다."));
        CustomerReview customerReview = customerReviewRepository.findById(customerReviewId).get();
        customerReview.updateReview(updateReview);
    }
}
