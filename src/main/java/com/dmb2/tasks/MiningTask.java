package com.dmb2.tasks;

import java.util.concurrent.TimeUnit;

import com.dmb2.enums.Command;
import com.dmb2.main.DiscordBot;

public class MiningTask implements Runnable {

	private boolean hasMined;
	private short mineWait;
	
	public MiningTask() {
		this(true, (short)0);
	}
	
	public MiningTask(boolean hasMined, short mineWait) {
		this.hasMined = hasMined;
		this.mineWait = mineWait;
		
		DiscordBot.getInstance().setMiningPointer(this);
	}
	
	@Override
	public void run() {
		if(!DiscordBot.getInstance().isPaused()) {
			mineWait++;
			if(hasMined || mineWait > 3) {
				mineWait = 0;
		
				if(!DiscordBot.getInstance().isVerification()) {
					DiscordBot.getInstance().sendCommand(Command.MINE.getCommand(), true).queue(msg -> {
						DiscordBot.getInstance().getMiningChannel().sendTyping().queue();
					});
					
					hasMined = false;
				}
			}
		}
		
		if(DiscordBot.getInstance().getArguments().isAdaptiveMineDelay()) {
			DiscordBot.getInstance().getScheduledExecutor().schedule(new MiningTask(hasMined, mineWait), DiscordBot.getInstance().getAdaptiveDelay(), TimeUnit.MILLISECONDS);
		} else {
			DiscordBot.getInstance().getScheduledExecutor().schedule(new MiningTask(hasMined, mineWait), DiscordBot.getInstance().getArguments().getMineDelay(), TimeUnit.MILLISECONDS);
		}
	}
	
	public void mined() {
		this.hasMined = true;
	}

}
