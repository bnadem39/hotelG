package com.hotel.service;

import com.hotel.data.StaticData;
import com.hotel.model.Review;
import com.hotel.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class ReviewService {

    public static ObservableList<Review> getAllReviews() {
        return StaticData.getReviews();
    }

    public static ObservableList<Review> getReviewsByClient(User client) {
        return FXCollections.observableArrayList(
                StaticData.getReviews().stream()
                        .filter(r -> r.getClient().getId() == client.getId())
                        .toList()
        );
    }

    public static ObservableList<Review> getPendingReviews() {
        return FXCollections.observableArrayList(
                StaticData.getReviews().stream()
                        .filter(r -> !r.hasResponse())
                        .toList()
        );
    }

    public static void createReview(User client, String type, String content) {
        int id = StaticData.getNextReviewId();
        Review review = new Review(id, client, type, content, null, LocalDateTime.now());
        StaticData.addReview(review);
    }

    public static void respondToReview(Review review, String response) {
        review.setAdminResponse(response);
    }

    public static long countPendingReviews() {
        return StaticData.getReviews().stream()
                .filter(r -> !r.hasResponse())
                .count();
    }
}

