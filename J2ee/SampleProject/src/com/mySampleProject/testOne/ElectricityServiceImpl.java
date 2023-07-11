package com.mySampleProject.testOne;

public class ElectricityServiceImpl implements ElectricityService {
	
	private boolean status;
	
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public boolean isTurnedON() {
		return status;
	}

}
