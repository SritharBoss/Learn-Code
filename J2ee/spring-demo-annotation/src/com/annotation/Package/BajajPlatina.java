package com.annotation.Package;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BajajPlatina implements Vehicle {
	
	@Autowired
	private EngineService engineService;
	

	@Override
	public String VehicleName() {
		return "Vehicle Name : Bajaj Platina 110";
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
