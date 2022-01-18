package com.example.ShoppingApp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class Products {

	private int ordered;
	private int price;
	private int available;
	
	public static int transactionNum=100;           // Static variable used to count the transaction number (used in transaction id);
	
	public Products() {
		ordered=0;
		price=100;
		available=100;
	}
     
	/* 
	 * -> As if there is a requirement of one Product, I have directly created a class for this.
	 * 
	 * -> If there is a requirement of one or more Products then we can create another class called Item and keep 
	 *    @One to Many relation ship between Products and Item
	 */
	
	/*
	 *   This uniqueId method is used to generate unique id for the Order Id. Such that any 2 orders does not have the same id.
	 */
	
	public  int getUniqueId()
	{   
	        Date dNow = new Date();
	        SimpleDateFormat ft = new SimpleDateFormat("ddHHmmss");
	        String datetime = ft.format(dNow);
	        return (int) Integer.valueOf(datetime);

	}
	
	
	public int getOrdered() {
		return ordered;
	}

	public int getPrice() {
		return price;
	}

	public int getAvailable() {
		return available;
	}

	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}

	public void setAvailable(int available) {
		this.available = available;
	}
	
	
	
	
}
