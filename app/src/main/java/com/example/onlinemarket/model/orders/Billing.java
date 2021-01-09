package com.example.onlinemarket.model.orders;

import com.google.gson.annotations.SerializedName;

public class Billing{

	@SerializedName("country")
	private String country;

	@SerializedName("city")
	private String city;

	@SerializedName("phone")
	private String phone;

	@SerializedName("address_1")
	private String address1;

	@SerializedName("address_2")
	private String address2;

	@SerializedName("postcode")
	private String postcode;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("company")
	private String company;

	@SerializedName("state")
	private String state;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	public String getCountry(){
		return country;
	}

	public String getCity(){
		return city;
	}

	public String getPhone(){
		return phone;
	}

	public String getAddress1(){
		return address1;
	}

	public String getAddress2(){
		return address2;
	}

	public String getPostcode(){
		return postcode;
	}

	public String getLastName(){
		return lastName;
	}

	public String getCompany(){
		return company;
	}

	public String getState(){
		return state;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}
}