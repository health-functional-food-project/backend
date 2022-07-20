package com.example.healthfunctionalfood.repository;

import java.util.List;

public interface CustomExpertReviewRepository {

    List<String> getIngredientsForCategory();

    List<String>getHealthConcernsForCategory();

}
