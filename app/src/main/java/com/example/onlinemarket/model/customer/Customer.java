package com.example.onlinemarket.model.customer;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.gson.annotations.SerializedName;

public class Customer{

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("role")
	private String role;

	@SerializedName("_links")
	private Links links;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("billing")
	private Billing billing;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("shipping")
	private Shipping shipping;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("meta_data")
	private List<MetaDataItem> metaData;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("is_paying_customer")
	private boolean isPayingCustomer;

	@SerializedName("username")
	private String username;

	public Customer(){
		this.id=randomNumber(500,10);
		this.dateCreated = String.valueOf(new Date());
	}

	public Customer(String dateCreated, String lastName, String firstName, String email, String username) {
		this();
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.username = username;
	}

	public String getDateModifiedGmt(){
		return dateModifiedGmt;
	}

	public String getRole(){
		return role;
	}

	public Links getLinks(){
		return links;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public String getLastName(){
		return lastName;
	}

	public String getDateCreatedGmt(){
		return dateCreatedGmt;
	}

	public Billing getBilling(){
		return billing;
	}

	public String getDateModified(){
		return dateModified;
	}

	public Shipping getShipping(){
		return shipping;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public List<MetaDataItem> getMetaData(){
		return metaData;
	}

	public int getId(){
		return id;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}

	public boolean isIsPayingCustomer(){
		return isPayingCustomer;
	}

	public String getUsername(){
		return username;
	}

	private int randomNumber(int max,int min){
		return (int) (Math.random() * (max - min + 1) + min);
	}
}