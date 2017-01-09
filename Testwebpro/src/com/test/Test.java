package com.test;

public final class Test {

	class Inner{
		void test(){
			if(Test.this.flag){
				sample();
			}
		}
	}
	private boolean flag=true;
	public void sample(){
		System.out.println("sample");
	}
	public Test(){
		(new Inner()).test();
	}
	public static void main(String args[]){
		new Test();
	}
}
