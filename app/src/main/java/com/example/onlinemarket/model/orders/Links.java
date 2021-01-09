package com.example.onlinemarket.model.orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Links{

	@SerializedName("self")
	private List<SelfItem> self;

	@SerializedName("collection")
	private List<CollectionItem> collection;

	@SerializedName("customer")
	private List<CustomerItem> customer;

	public List<SelfItem> getSelf(){
		return self;
	}

	public List<CollectionItem> getCollection(){
		return collection;
	}

	public List<CustomerItem> getCustomer(){
		return customer;
	}
}