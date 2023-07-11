package com.annotation.Package;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("appconfiguration.xml");
		
		Vehicle vehicle=context.getBean("bajajPlatina",Vehicle.class);
		
		System.out.println(vehicle.VehicleName());
		
		System.out.println("Wheel Count : "+vehicle.WheelCount());
		
		System.out.println("Engine Name : "+vehicle.EngineName());
	
		context.close();

	}


}