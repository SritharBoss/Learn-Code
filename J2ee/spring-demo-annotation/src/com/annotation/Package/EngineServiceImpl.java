package com.annotation.Package;

import org.springframework.stereotype.Component;

@Component
public class EngineServiceImpl implements EngineService {

	@Override
	public String EngineName() {
		return "Rocky Engine v2.3";
	}

}
