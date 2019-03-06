package com.dmb2.enums;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Enchantment {
	
	UNBREAKING((short)1, (short)1, (short)5, 50, 80, 135, 200, 400),
	EFFICIENCY((short)2, (short)2, (short)5, 50, 80, 135, 200, 400),
	FORTUNE((short)3, (short)3, (short)3, 50, 80, 135),
	LUCK((short)4, (short)4, (short)3, 50, 80, 135);
	
	private short id;
	private short priority;
	private short maxLevel;
	private int[] prices;
	
	private Enchantment(short id, short priority, short maxLevel, int... prices) {
		this.id = id;
		this.priority = priority;
		this.maxLevel = maxLevel;
		this.prices = prices;
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	public short getId() {
		return id;
	}
	
	public short getPriority() {
		return priority;
	}
	
	public short getMaxLevel() {
		return maxLevel;
	}

	public int[] getPrices() {
		return prices;
	}
	
	public int getPrice(int level) {
		return prices[level - 1];
	}
	
	public static List<Enchantment> getPriorityEnchantments() {
		return Stream.of(Enchantment.values()).sorted((ench1, ench2) -> ench2.priority - ench1.priority).collect(Collectors.toList());
	}

	public static Optional<Enchantment> getEnchantmentByName(String text) {
		return Stream.of(Enchantment.values()).filter(enchantment -> enchantment.getName().equals(text.toLowerCase())).findFirst();
	}
	
}
