package com.example.ShoppingApp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

/*
 *              This class contain all the basic details required for the order.
 *              
 */


@Entity(name="orders")
public class Orders {

	@Id
	private int orderId;
	private String transactionId="";
	private String status;
	private String description="Transaction yet to complete";
	private int amount;
	private String Coupon;
	private String date;
	private int quantity;
	
	public Orders(int orderId, int amount, String coupon, String date,int quantity) {
		this.orderId = orderId;
		this.amount = amount;
		this.Coupon = coupon;
		this.date = date;
		this.quantity=quantity;
	}


	public Orders() {
		
	}

	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public String getCoupon() {
		return Coupon;
	}


	public void setCoupon(String coupon) {
		Coupon = coupon;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}

    
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", transactionId=" + transactionId + ", status=" + status
				+ ", description=" + description + ", amount=" + amount + ", Coupon=" + Coupon + ", date=" + date + "]";
	}
	
	
	
	
}
