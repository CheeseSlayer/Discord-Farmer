package com.dmb2.events;

import com.dmb2.main.DiscordBot;
import com.dmb2.main.MiningData;
import com.dmb2.main.Util;
import com.dmb2.stats.Statistics;

import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if(event.getAuthor().getId() == event.getJDA().getSelfUser().getId()) return;
		
		if(event.getAuthor().getId().equals(DiscordBot.getInstance().getArguments().getCommandId())) {
			PrivateChannel channel = event.getChannel();
			String content = event.getMessage().getContentStripped();
			String[] segs = content.split(" ");
			
			if(segs[0].equalsIgnoreCase("help")) {
				channel.sendMessage(String.format("```\n%s\n```", help())).queue();
			}
			
			if(segs[0].equalsIgnoreCase("status")) {
				MiningData data = DiscordBot.getInstance().getMiningData();
				
				channel.sendMessage(String.format("```\n%s\n```", status(data))).queue();
			}
			
			if(segs[0].equalsIgnoreCase("sync")) {
				DiscordBot.getInstance().sync();
				
				channel.sendMessage(String.format("```\n%s\n```", sync())).queue();
			}
			
			if(segs[0].equalsIgnoreCase("statreset")) {
				Statistics.restart();
				
				channel.sendMessage(String.format("```\n%s\n```", statreset())).queue();
			}
			
			if(segs[0].equalsIgnoreCase("send")) {
				String command = "";
				for(int i = 1; i < segs.length; i++) command += segs[i] + " ";
				command = command.substring(0, command.length() - 1);
				final String finalCommand = command;
				
				DiscordBot.getInstance().sendCommand(command, false).queue(message -> {
					channel.sendMessage(String.format("```\n%s\n```", send(finalCommand))).queue();
				});
			}
			
			if(segs[0].equalsIgnoreCase("pause")) {
				DiscordBot.getInstance().setPaused(!DiscordBot.getInstance().isPaused());
			
				channel.sendMessage(String.format("```\n%s\n```", pause())).queue();
			}
		}
	}
	
	public static String help() {
		StringBuilder helpMenu = new StringBuilder();
		
		helpMenu.append("status -> Retrieves information about the bot\n");
		helpMenu.append("sync -> Synchronizes the bot session data\n");
		helpMenu.append("statreset -> Resets statistics\n");
		helpMenu.append("send <message> -> Sends the message as the bot\n");
		helpMenu.append("pause -> Pauses/Unpauses the bot\n");
		helpMenu.append("help -> Prints the help menu");
		
		return helpMenu.toString();
	}
	
	public static String status(MiningData data) {
		StringBuilder statusMenu = new StringBuilder();
		
		statusMenu.append("Pickaxe: " + Util.capitalize(data.getPickaxe().getName()) + "\n");
		statusMenu.append("Level: " + data.getLevel() + "\n");
		statusMenu.append("\n");
		data.getResources().keySet().forEach(resource -> statusMenu.append(Util.capitalize(resource.getName()) + ": " + data.getResource(resource) + "\n"));
		statusMenu.append("\n");
		data.getEnchantments().keySet().forEach(enchantment -> statusMenu.append(Util.capitalize(enchantment.getName()) + ": " + data.getEnchantment(enchantment) + "\n"));
		statusMenu.append("\n");
		statusMenu.append("Mines Per Hour: " + Statistics.getMinesPerHour() + "\n");
		statusMenu.append("XP Gain Per Hour: " + Statistics.getXpGainPerHour() + "\n");
		statusMenu.append("Levels Per Hour: " + Statistics.getLevelsPerHour());
		
		return statusMenu.toString();
	}
	
	public static String sync() {
		StringBuilder responseMenu = new StringBuilder();
		
		responseMenu.append("Synchronized session data!");
		
		return responseMenu.toString();
	}
	
	public static String statreset() {
		StringBuilder responseMenu = new StringBuilder();
		
		responseMenu.append("Reset statistics.");
		
		return responseMenu.toString();
	}
	
	public static String send(String finalCommand) {
		StringBuilder responseMenu = new StringBuilder();
		
		responseMenu.append("Sent \"" + finalCommand + "\"!");
		
		return responseMenu.toString();
	}
	
	public static String pause() {
		StringBuilder responseMenu = new StringBuilder();
		
		responseMenu.append("Bot is now " + (DiscordBot.getInstance().isPaused() ? "paused" : "unpaused") + "");
		
		return responseMenu.toString();
	}
	
}
