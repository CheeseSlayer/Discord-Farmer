package com.dmb2.enums;

import java.util.Optional;
import java.util.stream.Stream;

public enum Pickaxe {
	
	WOODEN(0, "wood_pickaxe"),
	STONE(1, "stone_pickaxe"),
	GOLD(2, "gold_pickaxe"),
	IRON(3, "iron_pickaxe"),
	DIAMOND(4, "diamond_pickaxe"),
	SAPPHIRE(5, "sapphire_pickaxe"),
	EMERALD(6, "emerald_pickaxe"),
	RUBY(7, "ruby_pickaxe"),
	ULTIMATE(8, "ultimate_pickaxe"),
	DONATOR(9, "donator_pickaxe"),
	CRATE(10, "crate_pickaxe");
	
	private int id;
	private String emoteName;
	
	private Pickaxe(int id, String emoteName) {
		this.id = id;
		this.emoteName = emoteName;
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	public int getId() {
		return id;
	}
	
	public String getEmoteName() {
		return ":" + emoteName + ":";
	}
	
	public static Optional<Pickaxe> getPickaxeByEmote(String text) {
		return Stream.of(Pickaxe.values()).filter(pickaxe -> text.contains(pickaxe.getEmoteName())).findFirst();
	}
	
	public static Optional<Pickaxe> getPickaxeByName(String name) {
		return Stream.of(Pickaxe.values()).filter(pickaxe -> pickaxe.getName().equalsIgnoreCase(name)).findFirst();
	}
	
}
