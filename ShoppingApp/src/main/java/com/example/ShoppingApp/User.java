package com.example.ShoppingApp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="user")
public class User {
	
	@Id
	private int id;
	private boolean off5;
	private boolean off10;
	
	@OneToMany
	List<Orders> list= new ArrayList<>();

	public User(int id) {
		this.id = id;                          
		off5=false;                          //  I have intialized the off5 and off10 to false,  
		off10=false;                         //   -> if off5 is false then it is not yet used,
	}                                        //   -> if off5 is true then it is used.  same for off10;

    public User() {
		
	}
	
    public void addOrder(Orders order) {
		list.add(order);                        // This method is used to add any new Orders to the List;
	}
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOff5() {
		return off5;
	}

	public void setOff5(boolean off5) {
		this.off5 = off5;
	}

	public boolean isOff10() {
		return off10;
	}

	public void setOff10(boolean off10) {
		this.off10 = off10;
	}

	public List<Orders> getList() {
		return list;
	}

	public void setList(List<Orders> list) {
		this.list = list;
	}

	
	

}
