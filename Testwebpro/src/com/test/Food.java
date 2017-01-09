package com.test;

public class Food {
	private String name;
	
	public Food(){
		
	}
	public Food(String name){
		this.name=name;
		
	}

	public String toString() {
		return name;
	}
	
	public void serveFood(){
		System.out.println("I'm serving "+name);
	}
	
}
