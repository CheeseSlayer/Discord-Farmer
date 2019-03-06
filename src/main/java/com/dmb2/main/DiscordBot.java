package com.dmb2.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.security.auth.login.LoginException;

import com.dmb2.enums.Command;
import com.dmb2.enums.Enchantment;
import com.dmb2.events.CommandHandler;
import com.dmb2.events.EmbedListener;
import com.dmb2.events.EventHandler;
import com.dmb2.events.sub.IEvent;
import com.dmb2.events.sub.MineEvent;
import com.dmb2.log.Logger;
import com.dmb2.stats.Statistics;
import com.dmb2.tasks.CommandTask;
import com.dmb2.tasks.EnchantmentTask;
import com.dmb2.tasks.MiningTask;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.requests.RestAction;

public class DiscordBot {
	
	//Instance object
	private static DiscordBot instance;
	
	//Event handlers
	private List<Consumer<IEvent>> eventConsumers;
	
	//Class objects
	private Arguments arguments;
	private JDA jda;
	private MiningData miningData;
	private boolean paused, verification;
	private ScheduledThreadPoolExecutor scheduledExecutor;
	
	public DiscordBot(Arguments arguments) {
		this.arguments = arguments;
		this.paused = false;
		this.verification = false;
	}
	
	public void run() throws LoginException, InterruptedException {
		instance = this;
		
		JDABuilder builder = new JDABuilder(AccountType.CLIENT);
        builder.setToken(arguments.getUserToken());
		builder.addEventListener(new EmbedListener());
		if(arguments.getCommandId() != null) builder.addEventListener(new CommandHandler());
		
		Statistics.restart();
		
		jda = builder.build();
		jda.awaitReady();
		
		if(jda.getTextChannelById(arguments.getChannelId()) == null) {
			Logger.error("Invalid channel id");
			jda.shutdown();
			return;
		}
		
		miningData = new MiningData();
		
		sync();
		
		Logger.info("Bot Started! Use \"help\" for a list of commands.");
		
		eventConsumers = new ArrayList<Consumer<IEvent>>();
		eventConsumers.add(new EventHandler());
		eventConsumers.add(new Statistics());
		
		scheduledExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(arguments.isAutoEnchant() ? 4 : 3);
		
		scheduledExecutor.schedule(new CommandTask(), 0, TimeUnit.MILLISECONDS);
		scheduledExecutor.schedule(new MiningTask(), arguments.getMineDelay(), TimeUnit.MILLISECONDS);
		if(arguments.isAutoEnchant()) scheduledExecutor.scheduleAtFixedRate(new EnchantmentTask(), 10000, 10000, TimeUnit.MILLISECONDS);
		
		DiscordBot.getInstance().addEventConsumer(event -> {
			if(event instanceof MineEvent) {
				miningPointer.mined();
			}
		});
	}
	
	public void sync() {
		sendCommand(Command.INVENTORY.getCommand(), false).queue();
		sendCommand(Command.ENCHANTMENTS.getCommand(), false).queue();
	}
	
	public void shutdown() {
		jda.shutdown();
		scheduledExecutor.shutdown();
	}
	
	//Mining Task
	private MiningTask miningPointer;
	
	public void setMiningPointer(MiningTask task) {
		this.miningPointer = task;
	}
	
	//Various Functions
	public RestAction<?> sendCommand(String command, boolean randomCaps) {
		if(!randomCaps) return getMiningChannel().sendMessage(command);
		
		Random random = new Random();
		String cappedCommand = "";
		
		for(int i = 0; i < command.length(); i++) {
			cappedCommand += random.nextBoolean() ? command.charAt(i) : Character.toUpperCase(command.charAt(i));
		}
		
		return getMiningChannel().sendMessage(cappedCommand);
	}
	
	public long getAdaptiveDelay() {
		long[] timeTable = new long[] {5500, 5000, 4500, 4000, 3500, 3000};
		return timeTable[miningData.getEnchantment(Enchantment.EFFICIENCY)];
	}
	
	//Event handlers
	public void eventCaught(IEvent event) {
		Iterator<Consumer<IEvent>> consumerIter = eventConsumers.iterator();
		
		while(consumerIter.hasNext()) {
			Consumer<IEvent> consumer = consumerIter.next();
			consumer.accept(event);
		}
	}
	
    public List<Consumer<IEvent>> getEventConsumers() {
		return eventConsumers;
	}
    
    public void addEventConsumer(Consumer<IEvent> consumer) {
    	eventConsumers.add(consumer);
    }
	
	//Object getters
	public Arguments getArguments() {
		return arguments;
	}
	
	public JDA getJda() {
		return jda;
	}
	
	public MiningData getMiningData() {
		return miningData;
	}
	
	public ScheduledThreadPoolExecutor getScheduledExecutor() {
		return scheduledExecutor;
	}
	
	public boolean isVerification() {
		return verification;
	}
	
	public void setVerification(boolean verification) {
		this.verification = verification;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	//Derived getters
	public TextChannel getMiningChannel() {
		return jda.getTextChannelById(arguments.getChannelId());
	}
	
	//Instance getter
	public static DiscordBot getInstance() {
		return instance;
	}
	
}
