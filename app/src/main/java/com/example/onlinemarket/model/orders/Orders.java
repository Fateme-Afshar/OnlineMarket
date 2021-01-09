package com.example.onlinemarket.model.orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Orders{

	@SerializedName("discount_total")
	private String discountTotal;

	@SerializedName("order_key")
	private String orderKey;

	@SerializedName("prices_include_tax")
	private boolean pricesIncludeTax;

	@SerializedName("_links")
	private Links links;

	@SerializedName("customer_note")
	private String customerNote;

	@SerializedName("line_items")
	private List<LineItemsItem> lineItems;

	@SerializedName("coupon_lines")
	private List<Object> couponLines;

	@SerializedName("billing")
	private Billing billing;

	@SerializedName("refunds")
	private List<Object> refunds;

	@SerializedName("number")
	private String number;

	@SerializedName("total")
	private String total;

	@SerializedName("shipping")
	private Shipping shipping;

	@SerializedName("date_paid_gmt")
	private Object datePaidGmt;

	@SerializedName("tax_lines")
	private List<Object> taxLines;

	@SerializedName("date_paid")
	private Object datePaid;

	@SerializedName("customer_user_agent")
	private String customerUserAgent;

	@SerializedName("payment_method_title")
	private String paymentMethodTitle;

	@SerializedName("meta_data")
	private List<Object> metaData;

	@SerializedName("date_completed")
	private Object dateCompleted;

	@SerializedName("currency")
	private String currency;

	@SerializedName("id")
	private int id;

	@SerializedName("date_completed_gmt")
	private Object dateCompletedGmt;

	@SerializedName("payment_method")
	private String paymentMethod;

	@SerializedName("shipping_tax")
	private String shippingTax;

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("cart_hash")
	private String cartHash;

	@SerializedName("shipping_total")
	private String shippingTotal;

	@SerializedName("cart_tax")
	private String cartTax;

	@SerializedName("currency_symbol")
	private String currencySymbol;

	@SerializedName("created_via")
	private String createdVia;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("discount_tax")
	private String discountTax;

	@SerializedName("total_tax")
	private String totalTax;

	@SerializedName("version")
	private String version;

	@SerializedName("customer_ip_address")
	private String customerIpAddress;

	@SerializedName("shipping_lines")
	private List<Object> shippingLines;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("fee_lines")
	private List<Object> feeLines;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("status")
	private String status;

	public Orders(String total,
				  int customerId,List<LineItemsItem> lineItems,String customerUserAgent) {
		this.total = total;
		this.customerId = customerId;
		this.lineItems=lineItems;
		this.customerUserAgent=customerUserAgent;
	}

	public String getDiscountTotal(){
		return discountTotal;
	}

	public String getOrderKey(){
		return orderKey;
	}

	public boolean isPricesIncludeTax(){
		return pricesIncludeTax;
	}

	public Links getLinks(){
		return links;
	}

	public String getCustomerNote(){
		return customerNote;
	}

	public List<LineItemsItem> getLineItems(){
		return lineItems;
	}

	public List<Object> getCouponLines(){
		return couponLines;
	}

	public Billing getBilling(){
		return billing;
	}

	public List<Object> getRefunds(){
		return refunds;
	}

	public String getNumber(){
		return number;
	}

	public String getTotal(){
		return total;
	}

	public Shipping getShipping(){
		return shipping;
	}

	public Object getDatePaidGmt(){
		return datePaidGmt;
	}

	public List<Object> getTaxLines(){
		return taxLines;
	}

	public Object getDatePaid(){
		return datePaid;
	}

	public String getCustomerUserAgent(){
		return customerUserAgent;
	}

	public String getPaymentMethodTitle(){
		return paymentMethodTitle;
	}

	public List<Object> getMetaData(){
		return metaData;
	}

	public Object getDateCompleted(){
		return dateCompleted;
	}

	public String getCurrency(){
		return currency;
	}

	public int getId(){
		return id;
	}

	public Object getDateCompletedGmt(){
		return dateCompletedGmt;
	}

	public String getPaymentMethod(){
		return paymentMethod;
	}

	public String getShippingTax(){
		return shippingTax;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public String getDateModifiedGmt(){
		return dateModifiedGmt;
	}

	public String getCartHash(){
		return cartHash;
	}

	public String getShippingTotal(){
		return shippingTotal;
	}

	public String getCartTax(){
		return cartTax;
	}

	public String getCurrencySymbol(){
		return currencySymbol;
	}

	public String getCreatedVia(){
		return createdVia;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public String getDateCreatedGmt(){
		return dateCreatedGmt;
	}

	public String getDiscountTax(){
		return discountTax;
	}

	public String getTotalTax(){
		return totalTax;
	}

	public String getVersion(){
		return version;
	}

	public String getCustomerIpAddress(){
		return customerIpAddress;
	}

	public List<Object> getShippingLines(){
		return shippingLines;
	}

	public String getDateModified(){
		return dateModified;
	}

	public int getParentId(){
		return parentId;
	}

	public List<Object> getFeeLines(){
		return feeLines;
	}

	public int getCustomerId(){
		return customerId;
	}

	public String getStatus(){
		return status;
	}
}