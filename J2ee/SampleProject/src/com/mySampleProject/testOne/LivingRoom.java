package com.mySampleProject.testOne;

public class LivingRoom implements MyRoom {
	
	ElectricityService electricityServiceImpl;
	
	public LivingRoom() {};
	
	public LivingRoom(ElectricityService myElectricityService) {
		electricityServiceImpl=myElectricityService;
	}
	
	
	public String getRoomName() {
		return "You're currently in Living Room";
	}
	
	public String getThings() {
		return "Things in Living Room : TV, Fridge and Some other goodies";
	}

	@Override
	public boolean isTurnedOn() {
		return electricityServiceImpl.isTurnedON();
	}
	
}