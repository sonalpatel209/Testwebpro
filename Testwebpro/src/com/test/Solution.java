package com.test;

import java.util.Scanner;

class Solution {

	public static void main(String[] args) {
		// Write your code here.
		FoodFactory foodFactory= new FoodFactory();
		Food food1= foodFactory.getFood("potato");
	}
	
	public static void main1(String[] args) {
		// Write your code here.
		MyCalculator calc = new MyCalculator();
		Scanner input = new Scanner(System.in);
		while (input.hasNext()) {
			int a = 0;
			int b = 0;
			a = input.nextInt();
			b = input.nextInt();

			try {
				int res=calc.power(a, b);
				System.out.println(res);
			} catch (Exception e) {
				
				System.out.println(e.getClass().getName()+":"+e.getMessage());
			}
			input.nextLine();
		}

		input.close();
	}
}
