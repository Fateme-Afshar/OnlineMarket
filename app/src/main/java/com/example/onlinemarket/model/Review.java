package com.example.onlinemarket.model;

import com.google.gson.annotations.SerializedName;

public class Review{

	@SerializedName("id")
	private int id;

	@SerializedName("review")
	private String review;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("rating")
	private int rating;

	@SerializedName("reviewer")
	private String reviewer;

	@SerializedName("reviewer_email")
	private String reviewerEmail;

	public Review(int id,String review, int productId, int rating, String reviewer, String reviewerEmail) {
		this.review = review;
		this.productId = productId;
		this.rating = rating;
		this.reviewer = reviewer;
		this.reviewerEmail = reviewerEmail;
		this.id=id;
	}

	public Review() {
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getReviewerEmail() {
		return reviewerEmail;
	}

	public void setReviewerEmail(String reviewerEmail) {
		this.reviewerEmail = reviewerEmail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}