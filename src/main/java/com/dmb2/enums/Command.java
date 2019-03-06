package com.dmb2.enums;

public enum Command {
	
	INVENTORY("m!inv"),
	ENCHANTMENTS("m!enchantments"),
	REPAIR("m!repair"),
	MINE("m!mine"),
	PICKAXE("m!pickaxe %s"),
	ENCHANT("m!shop enchant %s"),
	VERIFY("m!verify %s");
	
	private String command;
	
	private Command(String command) {
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
	
}
