package com.dmb2.events.sub;

import java.util.Map;

import com.dmb2.enums.Resource;

public class PickaxeRepairEvent implements IEvent { 
	
	private Map<Resource, Integer> cost;
	
	public PickaxeRepairEvent(Map<Resource, Integer> cost) {
		this.cost = cost;
	}
	
	public Map<Resource, Integer> getCost() {
		return cost;
	}
	
}
