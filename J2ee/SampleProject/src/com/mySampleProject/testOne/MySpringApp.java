package com.mySampleProject.testOne;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MySpringApp {

	public static void main(String[] args) 
	{
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("appConfiguration.xml");
		
		MyRoom myRoom=context.getBean("myRoom",MyRoom.class);
		
		System.out.println(myRoom.getRoomName());
		
		System.out.println(myRoom.getThings());
		
		System.out.println("Electricity Status : "+myRoom.isTurnedOn()+" ");
		
		context.close();

	}

}
