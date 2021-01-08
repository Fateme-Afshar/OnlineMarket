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

	public Customer(String lastName, String firstName, String email, String username) {
		this();
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.username = username;
	}

	public String getDateModifiedGmt() {
		return dateModifiedGmt;
	}

	public void setDateModifiedGmt(String dateModifiedGmt) {
		this.dateModifiedGmt = dateModifiedGmt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateCreatedGmt() {
		return dateCreatedGmt;
	}

	public void setDateCreatedGmt(String dateCreatedGmt) {
		this.dateCreatedGmt = dateCreatedGmt;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public List<MetaDataItem> getMetaData() {
		return metaData;
	}

	public void setMetaData(List<MetaDataItem> metaData) {
		this.metaData = metaData;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPayingCustomer() {
		return isPayingCustomer;
	}

	public void setPayingCustomer(boolean payingCustomer) {
		isPayingCustomer = payingCustomer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private int randomNumber(int max, int min){
		return (int) (Math.random() * (max - min + 1) + min);
	}

}