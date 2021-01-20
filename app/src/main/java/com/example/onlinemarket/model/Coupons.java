package com.example.onlinemarket.model;

import com.google.gson.annotations.SerializedName;

public class Coupons{

	@SerializedName("amount")
	private int amount;

	@SerializedName("code")
	private String code;

	@SerializedName("minimum_amount")
	private int minimumAmount;

	@SerializedName("maximum_amount")
	private int maximumAmount;

	@SerializedName("description")
	private String mDescription;

	public Coupons() {
	}

	public Coupons(
			int amount,
			String code,
			int minimumAmount,
			int maximumAmount,
			String description) {
		this.amount = amount;
		this.code = code;
		this.minimumAmount = minimumAmount;
		mDescription = description;
		this.maximumAmount=maximumAmount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(int minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public int getMaximumAmount() {
		return maximumAmount;
	}

	public void setMaximumAmount(int maximumAmount) {
		this.maximumAmount = maximumAmount;
	}
}