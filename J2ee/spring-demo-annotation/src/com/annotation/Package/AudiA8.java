package com.annotation.Package;

import org.springframework.stereotype.Component;

@Component
public class AudiA8 implements Vehicle {
	
	private EngineService engineService;
	
	public AudiA8(EngineService theEngineService) {
		engineService=theEngineService;
	}

	@Override
	public String VehicleName() {
		return "Vehicle Name : Audi A8";
	}

	@Override
	public int WheelCount() {
		return 4;
	}

	@Override
	public String EngineName() {
		return engineService.EngineName();
	}

}