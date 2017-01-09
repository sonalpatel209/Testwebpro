package com.test;

public class FoodFactory extends Food{

	public Food getFood(String name){
		Food food= new Food(name);
		return food;
	}
	
	
}
