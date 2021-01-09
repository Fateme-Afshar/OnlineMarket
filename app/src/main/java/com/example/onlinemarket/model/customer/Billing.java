package com.example.onlinemarket.model.customer;

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

	public Billing(String country,
				   String city,
				   String phone,
				   String address1,
				   String address2,
				   String postcode,
				   String lastName,
				   String company,
				   String state,
				   String firstName,
				   String email) {
		this.country = country;
		this.city = city;
		this.phone = phone;
		this.address1 = address1;
		this.address2 = address2;
		this.postcode = postcode;
		this.lastName = lastName;
		this.company = company;
		this.state = state;
		this.firstName = firstName;
		this.email = email;
	}

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