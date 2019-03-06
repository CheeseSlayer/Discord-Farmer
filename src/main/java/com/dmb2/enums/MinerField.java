package com.dmb2.enums;

public enum MinerField {
	
	INVENTORY_INFORMATION("Information"),
	INVENTORY_RESOURCES("Resources"),
	ENCHANTMENTS("Your pickaxe's enchantments"),
	VERIFICATION("Anti-bot verification!"),
	MINING_MINED("You mined"),
	MINING_CHEST("Chest found!"),
	MINING_BROKEN("Pickaxe broken!"),
	MINING_REPAIRED("Pickaxe repaired!"),
	MINING_LEVELUP("You leveled up!");
	
	private String name;
	
	private MinerField(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
