package com.example.onlinemarket.model.orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LineItemsItem{

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("tax_class")
	private String taxClass;

	@SerializedName("taxes")
	private List<Object> taxes;

	@SerializedName("total_tax")
	private String totalTax;

	@SerializedName("total")
	private String total;

	@SerializedName("variation_id")
	private int variationId;

	@SerializedName("subtotal")
	private String subtotal;

	@SerializedName("price")
	private int price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("name")
	private String name;

	@SerializedName("meta_data")
	private List<Object> metaData;

	@SerializedName("id")
	private int id;

	@SerializedName("subtotal_tax")
	private String subtotalTax;

	@SerializedName("sku")
	private String sku;

	public LineItemsItem(String total,
						 int price,
						 int productId,
						 String name) {
		this.total = total;
		this.price = price;
		this.productId = productId;
		this.name = name;
	}

	public int getQuantity(){
		return quantity;
	}

	public String getTaxClass(){
		return taxClass;
	}

	public List<Object> getTaxes(){
		return taxes;
	}

	public String getTotalTax(){
		return totalTax;
	}

	public String getTotal(){
		return total;
	}

	public int getVariationId(){
		return variationId;
	}

	public String getSubtotal(){
		return subtotal;
	}

	public int getPrice(){
		return price;
	}

	public int getProductId(){
		return productId;
	}

	public String getName(){
		return name;
	}

	public List<Object> getMetaData(){
		return metaData;
	}

	public int getId(){
		return id;
	}

	public String getSubtotalTax(){
		return subtotalTax;
	}

	public String getSku(){
		return sku;
	}
}