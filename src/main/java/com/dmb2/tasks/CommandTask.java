package com.dmb2.tasks;

import java.util.Scanner;

import com.dmb2.events.CommandHandler;
import com.dmb2.main.DiscordBot;
import com.dmb2.main.MiningData;
import com.dmb2.stats.Statistics;

public class CommandTask implements Runnable {

	@SuppressWarnings("resource")
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.print("> ");
			String input = scanner.nextLine();
			String[] segs = input.split(" ");
			
			System.out.println();
			
			if(segs[0].equalsIgnoreCase("help")) {
				System.out.println(CommandHandler.help());
				System.out.println();
			}
			
			if(segs[0].equalsIgnoreCase("status")) {
				MiningData data = DiscordBot.getInstance().getMiningData();
				
				System.out.println(CommandHandler.status(data));
				System.out.println();
			}
			
			if(segs[0].equalsIgnoreCase("sync")) {
				DiscordBot.getInstance().sync();
				
				System.out.println(CommandHandler.sync());
				System.out.println();
			}
			
			if(segs[0].equalsIgnoreCase("statreset")) {
				Statistics.restart();
				
				System.out.println(CommandHandler.statreset());
				System.out.println();
			}
			
			if(segs[0].equalsIgnoreCase("send")) {
				String command = "";
				for(int i = 1; i < segs.length; i++) command += segs[i] + " ";
				command = command.substring(0, command.length() - 1);
				final String finalCommand = command;
				
				DiscordBot.getInstance().sendCommand(command, false).queue(message -> {
					System.out.println(CommandHandler.send(finalCommand));
					System.out.println();
				});
			}
			
			if(segs[0].equalsIgnoreCase("pause")) {
				DiscordBot.getInstance().setPaused(!DiscordBot.getInstance().isPaused());
			
				System.out.println(CommandHandler.pause());
				System.out.println();
			}
		}
	}

}
