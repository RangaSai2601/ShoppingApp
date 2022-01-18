package com.example.ShoppingApp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 *                                                     Documentation:-
 *        /{userId}/{orderId}/pay    
 *      1.) This has a single method which is used for Payement.  
 *      
 *      -> We will receive the userId and OrderId from the URI path.
 *         Based on userId and orderId we will get the particular Order details.
 *         
 *      -> If the OrderId or userId is wrong then it will produce the message called  "Payment Failed due to invalid order id"
 *      
 *      -> If Order Id is correct then we will check whether the transaction is done previously or not.
 *         If yes then in description we will display along with the message "This Transaction was already done"
 *         Condition used for checking this is (transaction.length() >0) ;  
 *         
 *      -> Then we will compare the amount and actual amount in the order data. If not same then the transaction get's failed
 *         by giving message " Payment Failed as amount is invalid"
 *         
 *      -> If there is no error in the amount or order Id, then transaction will be done based on the last digit of the orderId.
 *         If last digit of OrderId -> <=3      transaction is success.
 *                                  -> 4 or 5   payement failed from bank.
 *                                  -> else     No response from payement server.
 *                                  
 *      -> If transaction is success then we will change the details of quantity of the product and also change the status of Coupon.
 *                                   
 *                                                         
 */


@Controller
public class ControllerPayement {

	@Autowired
	Products prod;
	
	@Autowired
	DAO_User data;
	
	@PostMapping("/{userId}/{orderId}/pay")
	public ResponseEntity payement(@PathVariable("userId") int userId,@PathVariable("orderId") int orderId, @RequestParam int amount) {
		
		Orders order=data.getOrderDetails(userId, orderId);
		
		Map<String,Object> map = new HashMap<>();
		
		if(order==null) {
			map.put("orderId", orderId);
			map.put("userId", userId);
	        map.put("transactionId","tran0#"+orderId);
	        map.put("status", "failed");
	        map.put("description", "Payment Failed due to invalid order id");
	        return new ResponseEntity(map,HttpStatus.BAD_REQUEST);
		}
		
		int i=orderId%10;
		
		if(order.getTransactionId().length()>0) {
			map.put("orderId", orderId);
			map.put("userId", userId);
	        map.put("transactionId",order.getTransactionId());                                    
	        map.put("status", order.getStatus());                                           
	        map.put("description", order.getDescription()+". This Transaction was already done");                    
	        return new ResponseEntity(map,HttpStatus.BAD_REQUEST);
		}
		else if(amount!=order.getAmount() ) {
			map.put("orderId", orderId);
			map.put("userId", userId);
	        map.put("transactionId","tran"+Products.transactionNum+"#"+orderId);   order.setTransactionId("tran"+Products.transactionNum+"#"+orderId);
	        Products.transactionNum++;                                   
	        map.put("status", "failed");                                           order.setStatus("failed");
	        map.put("description", "Payment Failed as amount is invalid");         order.setDescription("Payment Failed as amount is invalid");
	        
	        data.updateOrder(order);
	        return new ResponseEntity(map,HttpStatus.BAD_REQUEST);
		}
		else if(i<=3) {
			
			data.changeCouponStatus(userId, order.getCoupon());
			prod.setOrdered(prod.getOrdered()+order.getQuantity());
			prod.setAvailable(prod.getAvailable()-order.getQuantity());
			
			map.put("orderId", orderId);
			map.put("userId", userId);
	        map.put("transactionId","tran"+Products.transactionNum+"#"+orderId);    order.setTransactionId("tran"+Products.transactionNum+"#"+orderId);
	        Products.transactionNum++;                                              order.setDescription("Payement Success");
	        map.put("status", "successful");                                        order.setStatus("successfull");
	        data.updateOrder(order);
	        return new ResponseEntity(map,HttpStatus.OK);
		}
		else if(i==4 || i==5) {
			map.put("orderId", orderId);
			map.put("userId", userId);
	        map.put("transactionId","tran"+Products.transactionNum+"#"+orderId);   order.setTransactionId("tran"+Products.transactionNum+"#"+orderId);
	        Products.transactionNum++;                                   
	        map.put("status", "failed");                                           order.setStatus("failed");
	        map.put("description", "Payment Failed from bank");                    order.setDescription("Payment Failed from bank");
	        data.updateOrder(order);
	        return new ResponseEntity(map,HttpStatus.BAD_REQUEST);
		}
		else {
			map.put("orderId", orderId);
			map.put("userId", userId);
	        map.put("transactionId","tran"+Products.transactionNum+"#"+orderId);   order.setTransactionId("tran"+Products.transactionNum+"#"+orderId);
	        Products.transactionNum++;                                   
	        map.put("status", "failed");                                           order.setStatus("failed");
	        map.put("description", "No response from payment server");             order.setDescription("No response from payment server");
	        data.updateOrder(order);
	        return new ResponseEntity(map,HttpStatus.GATEWAY_TIMEOUT);
		}
		
	}
	
	
	
}
