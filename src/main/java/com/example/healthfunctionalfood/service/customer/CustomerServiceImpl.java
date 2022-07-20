package com.example.healthfunctionalfood.service.customer;

import com.example.healthfunctionalfood.advice.exception.ApiRequestException;
import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.review.CustomerLike;
import com.example.healthfunctionalfood.domain.review.CustomerReview;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.request.CustomerReviewRequestDto;
import com.example.healthfunctionalfood.dto.response.CustomerReviewResponseDto;
import com.example.healthfunctionalfood.repository.CustomerLikeRepository;
import com.example.healthfunctionalfood.repository.CustomerReviewRepository;
import com.example.healthfunctionalfood.repository.ProductRepository;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService{

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CustomerReviewRepository customerReviewRepository;

    private final CustomerLikeRepository customerLikeRepository;
    @Override
    @Transactional
    public Long addCustomerReview(Long productId, CustomerReviewRequestDto.CreateReview createReview) {
        // 로그인 미완성으로 임시 하드코딩
        // 카카오 로그인으로 사용자 정보 받는 로직 추가 필요
        User userEntity = userRepository.findById(1L).get();

        Product productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("존재하지 않는 제품입니다."));
        CustomerReview saveCustomerReview = createReview.toEntity(productEntity, userEntity);

        CustomerReview customer = customerReviewRepository.save(saveCustomerReview);
        return customer.getId();
    }

    @Override
    @Transactional
    public void modifyCustomerReview(Long productId, CustomerReviewRequestDto.CreateReview updateReview, Long customerReviewId) {
        // 로그인 미완성으로 임시 하드코딩
        // 본인이 쓴글인지, 존재하는 제품인지, 존재하는 리뷰인지 체크 필요
        User userEntity = userRepository.findById(1L).get();

        Product productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("존재하지 않는 제품입니다."));
        CustomerReview customerReview = customerReviewRepository.findById(customerReviewId).get();
        customerReview.updateReview(updateReview);
    }

    @Override
    @Transactional
    public void removeCustomerReview(Long productId, Long customerReviewId) {
        // 로그인 미완성으로 임시 하드코딩
        // 본인이 쓴글인지, 존재하는 제품인지, 존재하는 리뷰인지 체크 필요
        User userEntity = userRepository.findById(1L).get();

        Product productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("존재하지 않는 제품입니다."));
        customerReviewRepository.deleteById(customerReviewId);
    }

    @Override
    public List<CustomerReviewResponseDto.MyReview> findOneCustomerReview(Long productId) {
        // 로그인 미완성으로 임시 하드코딩
        // 본인이 쓴글인지, 존재하는 제품인지, 존재하는 리뷰인지 체크 필요
        User userEntity = userRepository.findById(1L).get();

        Product productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("존재하지 않는 제품입니다."));

        return customerReviewRepository.findByUserId(userEntity.getId()).stream()
                .map(CustomerReviewResponseDto.MyReview::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addCustomerReviewLike(Long productId, Long customerReviewId) {
        // 로그인 미완성으로 임시 하드코딩
        // 존재하는 제품인지, 존재하는 리뷰인지 체크 필요
        User userEntity = userRepository.findById(1L).get();

        Product productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("존재하지 않는 제품입니다."));

        Optional<CustomerReview> customerReview = Optional.ofNullable(customerReviewRepository.findById(customerReviewId).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 리뷰입니다.")));

        Optional<CustomerLike> customerLikeOptional = customerLikeRepository.CustomerReviewIdAndUserId(customerReviewId, userEntity.getId());
        if(customerLikeOptional.isEmpty()){
            CustomerLike customerLike = CustomerLike.builder()
                    .user(userEntity)
                    .customerReview(customerReview.get())
                    .build();
            customerLikeRepository.save(customerLike);
        }else {
            throw new ApiRequestException("이미 좋아요 한 리뷰입니다.");
        }
    }

    @Override
    @Transactional
    public void removeCustomerReviewLike(Long productId, Long customerReviewId) {
        // 로그인 미완성으로 임시 하드코딩
        // 존재하는 제품인지, 존재하는 리뷰인지 체크 필요
        User userEntity = userRepository.findById(1L).get();

        Product productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("존재하지 않는 제품입니다."));

        Optional<CustomerReview> customerReview = Optional.ofNullable(customerReviewRepository.findById(customerReviewId).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 리뷰입니다.")));

        Optional<CustomerLike> customerLikeOptional = customerLikeRepository.CustomerReviewIdAndUserId(customerReviewId, userEntity.getId());
        if(customerLikeOptional.isPresent()){
            customerLikeRepository.deleteByCustomerReviewIdAndUserId(customerReviewId, userEntity.getId());
        }else {
            throw new ApiRequestException("좋아요 하지 않은 리뷰입니다.");
        }
    }
}
