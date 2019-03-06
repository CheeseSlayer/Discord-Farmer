package com.dmb2.events;

import java.util.Random;
import java.util.function.Consumer;

import com.dmb2.enums.Command;
import com.dmb2.enums.Pickaxe;
import com.dmb2.events.sub.IEvent;
import com.dmb2.events.sub.MineEvent;
import com.dmb2.events.sub.PickaxeBreakEvent;
import com.dmb2.events.sub.PickaxeChangeEvent;
import com.dmb2.events.sub.PickaxeRepairEvent;
import com.dmb2.events.sub.VerificationEvent;
import com.dmb2.main.DiscordBot;
import com.dmb2.main.MiningData;

public class EventHandler implements Consumer<IEvent> {

	@Override
	public void accept(IEvent event) {
		if(event instanceof MineEvent) {
			MineEvent mine = (MineEvent) event;
			Pickaxe pickaxe = DiscordBot.getInstance().getArguments().getPickaxe();
			
			if(pickaxe != null && mine.getPickaxe() != null) {
				if(!mine.getPickaxe().equals(pickaxe)) {
					DiscordBot.getInstance().sendCommand(String.format(Command.PICKAXE.getCommand(), pickaxe.getName()), false).queue();
				}
			}
		}
		
		if(event instanceof PickaxeBreakEvent) {
			DiscordBot.getInstance().sendCommand(Command.REPAIR.getCommand(), false).queue();
		}
		
		if(event instanceof PickaxeRepairEvent) {
			PickaxeRepairEvent repair = (PickaxeRepairEvent) event;
			MiningData data = DiscordBot.getInstance().getMiningData();
			
			repair.getCost().keySet().forEach(res -> {
				data.remResource(res, repair.getCost().get(res));
			});
		}
		
		if(event instanceof PickaxeChangeEvent) {
			DiscordBot.getInstance().sync();
		}
		
		if(event instanceof VerificationEvent) {
			DiscordBot.getInstance().setVerification(true);
			
			VerificationEvent verif = (VerificationEvent) event;
			
			try {
				Random random = new Random();
				long sleepTime = Math.max(0, DiscordBot.getInstance().getArguments().getVerificationDelay() + random.nextInt(1500) - 750);
				Thread.sleep(sleepTime);
			} catch (Exception e) { }
			
			DiscordBot.getInstance().sendCommand(String.format(Command.VERIFY.getCommand(), verif.getCode()), false).queue(message -> { DiscordBot.getInstance().setVerification(false); });
		}
	}

}
