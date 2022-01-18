package com.example.ShoppingApp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;



@Repository
@Transactional
public class DAO_User {

	@PersistenceContext
	EntityManager enty;
	
	public void addNewUser(User user) {                  // To add new User to the DataBase
		enty.persist(user);
	}
	
	public void addNewOrder(Orders order) {
		enty.persist(order);                             // To add new Order to the DataBase
	}
	
	public User getUser(int id) {                        // To get the Details of the Existing User by their user_id
		return enty.find(User.class, id);
	}
	
	public Orders getOrder(int orderId) {                // To get the Details of the Existing Order by their order_id
		return enty.find(Orders.class, orderId);
	}
	
	public void updateUser(User user) {
		enty.merge(user);                                 // To update the details of existing User to the DataBase
	}
	
	public void updateOrder(Orders order) {
		enty.merge(order);                                // To update the details of existing Order to the DataBase  
	}
	
	public boolean existUser(int id) {
		                                                   // To know whether user exist with a particular user_Id  
		User user=enty.find(User.class, id);
		
		if(user==null)
			return false;
		else
			return true;
	}
	
	public Orders getOrderDetails(int userId, int orderId) {
		
		User user=enty.find(User.class, userId);
		
		if(user==null)                                     
			return null;
		else {                                              // To get the details of the Order by the given Orderid and UserID
			
			Orders order=null;
			List<Orders> list=user.getList();
			
			for(int i=0;i<list.size();i++) {
				Orders k=list.get(i);
				
				if(k.getOrderId()==orderId) {
					order=k;
					break;
				}
			}
			
			return order;
		}	
	}
	
	public void changeCouponStatus(int userId,String coupon) {
		User user=enty.find(User.class, userId);
		
		if(coupon.equals("OFF5"))
			user.setOff5(true);                                     // If the order is succesfull then we have to change the coupon Status.
		else                                                        //  if false then coupon is not used
			user.setOff10(true);                                    //  if true then that particular coupon has already used.
		
		enty.merge(user);
	}
	
}
