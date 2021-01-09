package com.example.onlinemarket.model.orders;

import com.google.gson.annotations.SerializedName;

public class CustomerItem{

	@SerializedName("href")
	private String href;

	public String getHref(){
		return href;
	}
}