package com.dmb2.enums;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.dmb2.main.MiningData;

public enum Resource {
	
	STONE("stone",            1 ),
	COAL("coal",              2 ),
	IRON("iron_ingot",        4 ),
	GOLD("gold_ingot",        5 ),
	REDSTONE("redstone_dust", 0 ),
	LAPIS("lapis_lazuli",     0 ),
	OBSIDIAN("obsidian",      7 ),
	DIAMOND("diamond",        10),
	EMERALD("emerald",        0 );
	
	private String emoteName;
	private double xpValue;
	
	private Resource(String emoteName, double xpValue) {
		this.emoteName = emoteName;
		this.xpValue = xpValue;
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	public String getEmoteName() {
		return ":" + emoteName + ":";
	}
	
	public double getXpValue() {
		return xpValue;
	}
	
	public static long calculateXp(MiningData data, Map<Resource, Integer> resources) {
		int[] fortuneMap = {1, 2, 3, 5};
		
		long xpSum = 0;
		
		for(Resource res : resources.keySet()) {
			xpSum += (long)((float)resources.get(res) * (float)res.getXpValue() / (float)fortuneMap[data.getEnchantment(Enchantment.FORTUNE)]);
		}
		
		return xpSum;
	}
	
	public static Optional<Resource> getResourceByEmote(String text) {
		return Stream.of(Resource.values()).filter(resource -> text.contains(resource.getEmoteName())).findFirst();
	}
	
}
