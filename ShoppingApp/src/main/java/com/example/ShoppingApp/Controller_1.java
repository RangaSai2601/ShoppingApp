package com.example.ShoppingApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/*                                      Documentation:-
 *  
 *       In this class we will have 3 methods for mapping of  ->  1.) /inventory ,   2.) /fetchCoupons  3.)/{userId}/order.
 *       
 *       -> 1.) /inventory 
 *       this will return the details of the product in json format.
 *       ex:- no:- of ordered products,  price of products, remaining products;
 *       
 *       -> 2.) /fetchCoupons
 *       this will return the coupon's and their discount percentage in json format.
 *       
 *       -> 3.) /{userId}/order?qty=10&coupon=OFF5 
 *       this will return the details of order and it's id if coupon and qty is valid.
 *       
 *           ->In this at first i have checked qty and coupon , if there are not valid then i have returned a appropriate message in the 
 *             json format.  (please don't use "" while passing the coupon name).
 *             
 *           ->I have checked whether the user exist in the database, If not create the user with respective id or use the existing one's.
 *             
 *           -> If the user exist previously,I have checked whether coupon is valid or not and display the message "Coupon not valid",
 *               If it's already used. 
 *               
 *           -> Then i have calucalated the cost of the product, created a new order based on uniqueId generator (based on time and date).
 *              Saved the new order in the dataBase and user List of orders.
 *            
 *           -> Finally returned the details of order and amount in the form of a json response.          
 */





@RestController
public class Controller_1 {

	@Autowired
	Products prod;
	
	@Autowired
	DAO_User data;
	
	@GetMapping("/inventory")
	public ResponseEntity<Object> inventory(){
		
		Map<String,Object> map = new HashMap<>();
		map.put("ordered", prod.getOrdered());
		map.put("price", prod.getPrice());
		map.put("available", prod.getAvailable());
		
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	@GetMapping("/fetchCoupons")
	public ResponseEntity<Object> coupons(){
		
		Map<String,Object> map = new HashMap<>();
		map.put("OFF5", 5);
		map.put("OFF10", 10);
		
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	
	@PostMapping("/{userId}/order")
	public ResponseEntity<Object> orderRequest(@PathVariable int userId ,@RequestParam int qty,String coupon){
		
		Map<String,Object> map = new HashMap<>();
		
		if(qty<1 || qty>prod.getAvailable()) {
			map.put("description", "Invalid quantity");
			return new ResponseEntity(map,HttpStatus.NOT_FOUND);
		}
		else if(!coupon.equals("OFF5") && !coupon.equals("OFF10")) {
			map.put("description", "Invalid coupon");
			return new ResponseEntity(map,HttpStatus.NOT_FOUND);
		}
		
		
		boolean check=data.existUser(userId);
		User usr;
		if(check) {
			usr=data.getUser(userId);

			if((coupon.equals("OFF5") && usr.isOff5()) || (coupon.equals("OFF10") && usr.isOff10()) ) {
				map.put("description", "Invalid coupon");
				return new ResponseEntity(map,HttpStatus.NOT_FOUND);
			}
			
		}
		else {
			usr= new User(userId);
		}
		
			int orderId=prod.getUniqueId();
			int amount=qty*prod.getPrice();
			
			if(coupon.equals("OFF5"))
				amount=(int) (amount-amount*0.05);
			else
				amount=(int) (amount-amount*0.1);
		  
			    Date dNow = new Date();
		        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yy");
		        String date = ft.format(dNow);
		        
			    Orders odr = new Orders(orderId,amount,coupon,date,qty);
			    data.addNewOrder(odr);
			    usr.addOrder(odr);
			    data.updateUser(usr);
			    
			map.put("orderId", orderId);
			map.put("userId", userId);
			map.put("quantity", qty);
			map.put("amount", amount);
			map.put("coupon", coupon);
			     
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
}
