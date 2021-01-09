package com.example.onlinemarket.model.customer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Links{

	@SerializedName("self")
	private List<SelfItem> self;

	@SerializedName("collection")
	private List<CollectionItem> collection;

	public Links(List<SelfItem> self, List<CollectionItem> collection) {
		this.self = self;
		this.collection = collection;
	}

	public List<SelfItem> getSelf(){
		return self;
	}

	public List<CollectionItem> getCollection(){
		return collection;
	}
}