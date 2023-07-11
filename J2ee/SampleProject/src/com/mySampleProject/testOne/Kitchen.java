package com.mySampleProject.testOne;

public class Kitchen implements MyRoom {

	private ElectricityServiceImpl electricityServiceImpl;

	public Kitchen() {}
	
	public Kitchen(ElectricityServiceImpl myelectricityService) {
		electricityServiceImpl=myelectricityService;
	}
	
	public String getRoomName() {
		return "You're in a Kitchen";
	}
	
	public String getThings() {
		return "Things in Kitchen : Gas Stove, Plates and some other Food Items,";
	}

	@Override
	public boolean isTurnedOn() {
		return electricityServiceImpl.isTurnedON();
	}
	
	
	
}