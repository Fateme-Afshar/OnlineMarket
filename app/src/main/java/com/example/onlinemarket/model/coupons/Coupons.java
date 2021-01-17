package com.example.onlinemarket.model.coupons;

import com.google.gson.annotations.SerializedName;

public class Coupons{

	@SerializedName("amount")
	private String amount;

	@SerializedName("code")
	private String code;

	@SerializedName("minimum_amount")
	private String minimumAmount;

	@SerializedName("description")
	private String mDescription;

	public Coupons() {
	}

	public Coupons(
			String amount,
			String code,
			String minimumAmount,
			String description) {
		this.amount = amount;
		this.code = code;
		this.minimumAmount = minimumAmount;
		mDescription = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(String minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}
}