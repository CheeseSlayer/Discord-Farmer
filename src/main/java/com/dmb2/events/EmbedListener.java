package com.dmb2.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import com.dmb2.enums.Enchantment;
import com.dmb2.enums.MinerField;
import com.dmb2.enums.Pickaxe;
import com.dmb2.enums.Resource;
import com.dmb2.events.sub.ChestEvent;
import com.dmb2.events.sub.LevelEvent;
import com.dmb2.events.sub.MineEvent;
import com.dmb2.events.sub.PickaxeBreakEvent;
import com.dmb2.events.sub.PickaxeChangeEvent;
import com.dmb2.events.sub.PickaxeRepairEvent;
import com.dmb2.events.sub.VerificationEvent;
import com.dmb2.log.Logger;
import com.dmb2.main.DiscordBot;
import com.dmb2.main.Util;

import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EmbedListener extends ListenerAdapter {
	
	@Override
	public void onGuildMessageEmbed(GuildMessageEmbedEvent event) {
		for(MessageEmbed embed : event.getMessageEmbeds()) {
			if(embed.getAuthor() == null || !embed.getAuthor().getName().equals(DiscordBot.getInstance().getJda().getSelfUser().getName())) return;
			
			for(Field field : embed.getFields()) {
				if(field.getName().equals(MinerField.INVENTORY_INFORMATION.getName())) {
					for(String line : field.getValue().split("\n")) {
						Scanner lineScanner = new Scanner(line);
						
						String key = lineScanner.next();
						String value = lineScanner.next();
						
						if(key.contains("Pickaxe")) {
							Optional<Pickaxe> pickaxeOpt = Pickaxe.getPickaxeByEmote(value);
							if(pickaxeOpt.isPresent()) {
								DiscordBot.getInstance().getMiningData().setPickaxe(pickaxeOpt.get());
							} else {
								Logger.warn("Unknown pickaxe: " + value);
							}
						}
						
						if(key.contains("Level")) {
							if(Util.isInteger(value)) {
								DiscordBot.getInstance().getMiningData().setLevel(Integer.parseInt(value));
							}
						}
						
						lineScanner.close();
					}
				}
				
				if(field.getName().equals(MinerField.INVENTORY_RESOURCES.getName())) {
					for(String line : field.getValue().split("\n")) {
						Scanner lineScanner = new Scanner(line);
						
						for(int i = 0; i < line.split(" ").length - 2; i++) lineScanner.next();
						long count = lineScanner.nextLong();
						String emote = lineScanner.next();
						
						Optional<Resource> resourceOpt = Resource.getResourceByEmote(emote);
						if(resourceOpt.isPresent()) {
							DiscordBot.getInstance().getMiningData().setResource(resourceOpt.get(), count);
						} else {
							Logger.warn("Unknown resource: " + emote);
						}
						
						lineScanner.close();
					}
				}
				
				if(field.getName().equals(MinerField.ENCHANTMENTS.getName())) {
					for(String line : field.getValue().split("\n")) {
						Scanner lineScanner = new Scanner(line);
						
						if(lineScanner.hasNext()) {
							String firstToken = lineScanner.next().toLowerCase();
							Optional<Enchantment> enchantmentOpt = Enchantment.getEnchantmentByName(firstToken);
							if(enchantmentOpt.isPresent()) {
								short level = lineScanner.nextShort();
								DiscordBot.getInstance().getMiningData().setEnchantment(enchantmentOpt.get(), level);
							}
						}
						
						lineScanner.close();
					}
				}
				
				if(field.getName().equals(MinerField.MINING_MINED.getName())) {
					Pickaxe pickaxe = null;
					Map<Resource, Integer> mined = new HashMap<Resource, Integer>();
					Stream.of(Resource.values()).forEach(resource -> mined.put(resource, 0));
					
					for(String line : field.getValue().split("\n")) {
						Scanner lineScanner = new Scanner(line);
						
						String firstToken = lineScanner.next();
						
						Optional<Resource> resourceOpt = Resource.getResourceByEmote(firstToken);
						if(resourceOpt.isPresent()) {
							lineScanner.next();
							String countToken = lineScanner.next();
							int count = Integer.parseInt(countToken.replaceAll("x", ""));
							
							DiscordBot.getInstance().getMiningData().addResource(resourceOpt.get(), count);
							
							mined.put(resourceOpt.get(), mined.get(resourceOpt.get()) + count);
						} else {
							lineScanner.next();
							String pickaxeToken = lineScanner.next();
							Optional<Pickaxe> pickaxeOpt = Pickaxe.getPickaxeByEmote(pickaxeToken);
							if(pickaxeOpt.isPresent()) {
								pickaxe = pickaxeOpt.get();
								DiscordBot.getInstance().getMiningData().setPickaxe(pickaxeOpt.get());
							} else {
								Logger.warn("Unknown pickaxe: " + pickaxeToken);
							}
						}
						
						lineScanner.close();
					}
					
					DiscordBot.getInstance().eventCaught(new MineEvent(pickaxe, mined));
				}
				
				if(field.getName().equals(MinerField.MINING_CHEST.getName())) {
					for(String line : field.getValue().split("\n")) {
						Optional<Resource> lineOpt = Resource.getResourceByEmote(line);
						
						if(lineOpt.isPresent()) {
							String lineChanged = line.replaceAll("\\/ ", "");
							
							Scanner lineScanner = new Scanner(lineChanged);
							
							while(lineScanner.hasNext()) {
								String countToken = lineScanner.next();
								String resourceToken = lineScanner.next();
								int count = Integer.parseInt(countToken.replaceAll("\\+", ""));
								Optional<Resource> resourceOpt = Resource.getResourceByEmote(resourceToken);
								if(resourceOpt.isPresent()) {
									DiscordBot.getInstance().getMiningData().addResource(resourceOpt.get(), count);
								}
							}
							
							lineScanner.close();
						}
					}
					
					DiscordBot.getInstance().eventCaught(new ChestEvent());
				}
				
				if(field.getName().equals(MinerField.MINING_BROKEN.getName())) {
					DiscordBot.getInstance().eventCaught(new PickaxeBreakEvent());
				}
				
				if(field.getName().equals(MinerField.MINING_REPAIRED.getName())) {
					Map<Resource, Integer> mined = new HashMap<Resource, Integer>();
					
					for(String line : field.getValue().split("\n")) {
						Scanner lineScanner = new Scanner(line);
						
						while(lineScanner.hasNext()) {
							if(lineScanner.hasNextInt()) {
								int count = lineScanner.nextInt();
								String emoteToken = lineScanner.next();
								Optional<Resource> resourceOpt = Resource.getResourceByEmote(emoteToken);
								
								if(resourceOpt.isPresent()) {
									mined.put(resourceOpt.get(), count);
								} else {
									Logger.warn("Unknown resource: " + emoteToken);
								}
							} else {
								lineScanner.next();
							}
						}
						
						lineScanner.close();
					}
					
					DiscordBot.getInstance().eventCaught(new PickaxeRepairEvent(mined));
				}
				
				if(field.getName().equals(MinerField.MINING_LEVELUP.getName())) {
					DiscordBot.getInstance().getMiningData().levelUp();
					
					for(String line : field.getValue().split("\n")) {
						Scanner lineScanner = new Scanner(line);
						
						Optional<Pickaxe> pickaxeOpt = Pickaxe.getPickaxeByEmote(line);
						
						if(line.contains(Resource.EMERALD.getEmoteName())) {
							String countToken = lineScanner.next();
							int count = Integer.parseInt(countToken.replaceAll("\\+", ""));
							DiscordBot.getInstance().getMiningData().addResource(Resource.EMERALD, count);
						} else if(pickaxeOpt.isPresent()) {
							DiscordBot.getInstance().getMiningData().setPickaxe(pickaxeOpt.get());
							DiscordBot.getInstance().eventCaught(new PickaxeChangeEvent());
						}
						
						lineScanner.close();
					}
					
					DiscordBot.getInstance().eventCaught(new LevelEvent());
				}
				
				if(field.getName().equals(MinerField.VERIFICATION.getName())) {
					String imageUrl = embed.getImage().getUrl();
					String[] urlParts = imageUrl.split("=");
					String code = urlParts[1];
					
					DiscordBot.getInstance().eventCaught(new VerificationEvent(code));
				}
			}
		}
	}
	
}
