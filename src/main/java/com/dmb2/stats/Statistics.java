package com.dmb2.stats;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.dmb2.enums.Resource;
import com.dmb2.events.sub.IEvent;
import com.dmb2.events.sub.LevelEvent;
import com.dmb2.events.sub.MineEvent;
import com.dmb2.main.DiscordBot;

public class Statistics implements Consumer<IEvent>{
	
	private static long startTime;
	private static long mineCount;
	private static long xpGained;
	private static long levelsUp;
	
	public static void restart() {
		startTime = System.currentTimeMillis();
		mineCount = 0;
		xpGained = 0;
		levelsUp = 0;
	}

	public static float getMinesPerHour() {
		return round(getPerHour(mineCount));
	}
	
	public static float getXpGainPerHour() {
		return round(getPerHour(xpGained));
	}
	
	public static float getLevelsPerHour() {
		return round(getPerHour(levelsUp));
	}
	
	private static float round(float num) {
		return (float) (Math.floor(num * 100) / 100);
	}
	
	private static float getPerHour(long stat) {
		if(System.currentTimeMillis() - startTime <= 0 || stat == 0) return 0;
		return (float)stat / ((float)(System.currentTimeMillis() - startTime) / (float)TimeUnit.HOURS.toMillis(1));
	}
	
	@Override
	public void accept(IEvent event) {
		if(event instanceof MineEvent) {
			xpGained += Resource.calculateXp(DiscordBot.getInstance().getMiningData(), ((MineEvent) event).getMined());
			
			mineCount++;
		}
		
		if(event instanceof LevelEvent) {
			levelsUp++;
		}
	}
	
}
