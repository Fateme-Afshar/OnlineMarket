package com.example.onlinemarket.model.customer;

import com.google.gson.annotations.SerializedName;

public class MetaDataItem{

	@SerializedName("id")
	private int id;

	@SerializedName("value")
	private String value;

	@SerializedName("key")
	private String key;

	public int getId(){
		return id;
	}

	public String getValue(){
		return value;
	}

	public String getKey(){
		return key;
	}
}