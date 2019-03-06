package com.dmb2.tasks;

import java.util.List;

import com.dmb2.enums.Command;
import com.dmb2.enums.Enchantment;
import com.dmb2.enums.Resource;
import com.dmb2.main.DiscordBot;
import com.dmb2.main.MiningData;

public class EnchantmentTask implements Runnable {

	@Override
	public void run() {
		List<Enchantment> enchantOrder = Enchantment.getPriorityEnchantments();
		MiningData miningData = DiscordBot.getInstance().getMiningData();
		
		for(Enchantment enchant : enchantOrder) {
			if(miningData.getEnchantment(enchant) < enchant.getMaxLevel()) {
				short nextLevel = (short) (miningData.getEnchantment(enchant) + 1);
				int price = enchant.getPrice(nextLevel);
				
				if(miningData.getResource(Resource.LAPIS) >= price) {
					if(!DiscordBot.getInstance().isVerification()) {
						DiscordBot.getInstance().sendCommand(String.format(Command.ENCHANT.getCommand(), enchant.getId() + ""), false).queue();
						miningData.remResource(Resource.LAPIS, price);
						miningData.enchant(enchant);
						return;
					}
				}
			}
		}
	}

}
