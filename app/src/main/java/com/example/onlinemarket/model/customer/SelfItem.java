package com.example.onlinemarket.model.customer;

import com.google.gson.annotations.SerializedName;

public class SelfItem{

	@SerializedName("href")
	private String href;

	public SelfItem(String href) {
		this.href = href;
	}

	public String getHref(){
		return href;
	}
}