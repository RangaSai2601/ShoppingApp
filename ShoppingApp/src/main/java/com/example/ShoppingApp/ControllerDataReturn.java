package com.example.ShoppingApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * 
 *          1.) /{userId}/orders   Displays all orders of a particular userId.
 *              -> If the userId is valid, then it display's all basic details of orders by running a loop through the list.
 *                 And sending the data in the form of a Json Format.
 *                 
 *              -> else send the message "Invalid UserId"
 *              
 *          2.) /{userId}/orders/{orderId}       Displaying the details of a Particular orderId
 *              -> If the orderId and userId is valid then send the data of that order.
 *              
 *              -> Else send the message "Invalid OrderId".
 *           
 *           
 */


@Controller
public class ControllerDataReturn {

	@Autowired
	DAO_User data;
	
	@GetMapping("/{userId}/orders")
	public ResponseEntity allOrders(@PathVariable int userId) {
		
		User user=data.getUser(userId);
		
		if(user==null) {
			Map<String,Object> map = new HashMap<>();
			 map.put("userId",userId);
			 map.put("description", "Invalid User Id");
			return new ResponseEntity(map,HttpStatus.NOT_FOUND);
		}
		else {
			List<Orders> list=user.getList();
			List<Map<String,Object>> lisMap = new ArrayList<>();
			
			for(int i=0;i<list.size();i++) {
				Orders order=list.get(i);
				Map<String,Object> map = new HashMap<>();
				 map.put("orderId",order.getOrderId());
				 map.put("amount", order.getAmount());
				 map.put("date", order.getDate());
				 map.put("coupon", order.getCoupon());
				 lisMap.add(map);
			}
			return new ResponseEntity(lisMap,HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/{userId}/orders/{orderId}")
	public ResponseEntity orderDetails(@PathVariable("userId") int userId, @PathVariable("orderId") int orderId) {
		
		Orders order=data.getOrderDetails(userId,orderId);
		Map<String,Object> map = new HashMap<>();
		
		if(order==null) {
			map.put("OrderId",orderId);
			map.put("description","Order not found");
			return new ResponseEntity(map,HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity(order,HttpStatus.OK);
		}
	}
	
}
