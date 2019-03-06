package com.dmb2.events.sub;

import java.util.Map;

import com.dmb2.enums.Pickaxe;
import com.dmb2.enums.Resource;

public class MineEvent implements IEvent { 
	
	private Pickaxe pickaxe;
	private Map<Resource, Integer> mined;
	
	public MineEvent(Pickaxe pickaxe, Map<Resource, Integer> mined) {
		this.pickaxe = pickaxe;
		this.mined = mined;
	}

	public Pickaxe getPickaxe() {
		return pickaxe;
	}

	public Map<Resource, Integer> getMined() {
		return mined;
	}
	
}
