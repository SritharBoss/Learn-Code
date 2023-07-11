package com.annotation.Package;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BajajDiscover implements Vehicle {
	
	@Autowired
	private EngineService engineService;
	
	public BajajDiscover() {
		System.out.println("Inside Bajaj Constructor");
	}
	


	@Override
	public String VehicleName() {
		return "Vehichle Name : Bajaj Discover 100";
	}

	@Override
	public int WheelCount() {
		return 2;
	}
	
	@Override
	public String EngineName() {
		return engineService.EngineName();
	}
	
	

}
