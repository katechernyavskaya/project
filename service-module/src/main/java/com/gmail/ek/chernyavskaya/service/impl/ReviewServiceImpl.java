package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.repository.ReviewRepository;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.repository.model.Review;
import com.gmail.ek.chernyavskaya.repository.model.User;
import com.gmail.ek.chernyavskaya.service.ReviewService;
import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;
import com.gmail.ek.chernyavskaya.service.model.ReviewDTO;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.ek.chernyavskaya.service.impl.PaginationUtilImpl.getStartElement;

@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewDTO add(ReviewDTO reviewDTO) {
        Review review = convertDTOToReview(reviewDTO);
        Review reviewSaved = reviewRepository.add(review);
        return convertReviewToDTO(reviewSaved);
    }

    @Override
    public List<ReviewDTO> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDTO> reviewsDTO = reviews.stream().map(this::convertReviewToDTO).collect(Collectors.toList());
        return reviewsDTO;
    }

    @Override
    public List<ReviewDTO> getAllReviewsWithPagination(PaginationDTO paginationDTO) {
        Pagination pagination = convertDTOToPagination(paginationDTO);
        List<Review> reviews = reviewRepository.findAllWithPagination(pagination);
        return reviews.stream().map(this::convertReviewToDTO).collect(Collectors.toList());
    }

    @Override
    public void changeReviewsDisplaying(List<Long> reviewIds) {
        reviewRepository.changeReviewsDisplaying(reviewIds);
    }

    private ReviewDTO convertReviewToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setText(review.getText());
        reviewDTO.setCreationDate(review.getCreationDate());
        reviewDTO.setDisplayed(review.getDisplayed());
        User user = review.getReviewer();
        UserDTO userDTO = convertUserToDTO(user);
        reviewDTO.setReviewer(userDTO);
        return reviewDTO;
    }

    private Review convertDTOToReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setText(reviewDTO.getText());
        UserDTO userDTO = reviewDTO.getReviewer();
        User user = convertDTOToUser(userDTO);
        review.setDisplayed(reviewDTO.getDisplayed());
        review.setReviewer(user);
        review.setCreationDate(reviewDTO.getCreationDate());
        return review;
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setCouldNotBeDeleted(user.getCouldNotBeDeleted());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    private User convertDTOToUser(UserDTO userDTO) {
        User user = new User();
        if (userDTO.getId() != null) {
            user.setId(userDTO.getId());
        }
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setCouldNotBeDeleted(userDTO.getCouldNotBeDeleted());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(userDTO.getRole());
        return user;
    }

    private Pagination convertDTOToPagination(PaginationDTO paginationDTO) {
        Pagination pagination = new Pagination();
        pagination.setElementsPerPage(paginationDTO.getElementsPerPage());
        pagination.setStartElement(getStartElement(paginationDTO));
        return pagination;
    }

}