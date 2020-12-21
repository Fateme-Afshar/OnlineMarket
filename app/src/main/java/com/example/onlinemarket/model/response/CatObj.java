package com.example.onlinemarket.model.response;

import com.google.gson.annotations.SerializedName;

public class CatObj{

	@SerializedName("parent")
	private int parent;

	@SerializedName("image")
	private Image image;

	@SerializedName("menu_order")
	private int menuOrder;

	@SerializedName("display")
	private String display;

	@SerializedName("name")
	private String name;

	@SerializedName("count")
	private int count;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("slug")
	private String slug;

	public int getParent(){
		return parent;
	}

	public Image getImage(){
		return image;
	}

	public int getMenuOrder(){
		return menuOrder;
	}

	public String getDisplay(){
		return display;
	}

	public String getName(){
		return name;
	}

	public int getCount(){
		return count;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public String getSlug(){
		return slug;
	}
}