package com.dmb2.main;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.dmb2.enums.Enchantment;
import com.dmb2.enums.Pickaxe;
import com.dmb2.enums.Resource;

public class MiningData {
	
	private Pickaxe pickaxe;
	private int level;
	private Map<Resource, Long> resources;
	private Map<Enchantment, Short> enchantments;
	
	public MiningData() {
		pickaxe = Pickaxe.WOODEN;
		level = 0;
		resources = new HashMap<Resource, Long>();
		Stream.of(Resource.values()).forEach(resource -> resources.put(resource, (long) 0));
		enchantments = new HashMap<Enchantment, Short>();
		Stream.of(Enchantment.values()).forEach(enchantment -> enchantments.put(enchantment, (short) 0));
	}
	
	public Pickaxe getPickaxe() {
		return pickaxe;
	}

	public void setPickaxe(Pickaxe pickaxe) {
		this.pickaxe = pickaxe;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void levelUp() {
		this.level++;
	}

	public Map<Resource, Long> getResources() {
		return resources;
	}

	public void setResources(Map<Resource, Long> resources) {
		this.resources = resources;
	}
	
	public void setResource(Resource resource, long count) {
		this.resources.put(resource, count);
	}
	
	public void addResource(Resource resource, long count) {
		this.resources.put(resource, resources.get(resource) + count);
	}
	
	public void remResource(Resource resource, long count) {
		this.resources.put(resource, resources.get(resource) - count);
	}
	
	public long getResource(Resource resource) {
		return resources.get(resource);
	}

	public Map<Enchantment, Short> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(Map<Enchantment, Short> enchantments) {
		this.enchantments = enchantments;
	}
	
	public void setEnchantment(Enchantment enchantment, short level) {
		this.enchantments.put(enchantment, level);
	}
	
	public short getEnchantment(Enchantment enchantment) {
		return enchantments.get(enchantment);
	}
	
	public void enchant(Enchantment enchantment) {
		this.enchantments.put(enchantment, (short) (enchantments.get(enchantment) + 1));
	}
	
	public void disenchant(Enchantment enchantment) {
		this.enchantments.put(enchantment, (short) (enchantments.get(enchantment) - 1));
	}
	
}
