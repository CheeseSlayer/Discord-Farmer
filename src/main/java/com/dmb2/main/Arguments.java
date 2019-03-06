package com.dmb2.main;

import com.dmb2.enums.Pickaxe;

import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class Arguments {
	
	@Parameters(index = "0", paramLabel = "User Token", description = "The user's discord token")
	private String userToken;
	
	@Parameters(index = "1", paramLabel = "Channel ID", description = "The discord channel id")
	private String channelId;
	
	@Option(names = "-c", description = "Enables and sets the command module")
	private String commandId;
	
	@Option(defaultValue = "5500", names = "-md", description = "Sets the mining delay")
	private long mineDelay;
	
	@Option(defaultValue = "2500", names = "-vd", description = "Sets the verification delay")
	private long verificationDelay;
	
	@Option(names = "-p", description = "Locks the pickaxe")
	private Pickaxe pickaxe;
	
	@Option(names = "-e", description = "Does auto enchant")
	private boolean autoEnchant;
	
	@Option(names = "-amd", description = "Does adapt mine delay")
	private boolean adaptiveMineDelay;
	
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public long getMineDelay() {
		return mineDelay;
	}

	public void setMineDelay(long mineDelay) {
		this.mineDelay = mineDelay;
	}
	
	public long getVerificationDelay() {
		return verificationDelay;
	}
	
	public void setVerificationDelay(long verificationDelay) {
		this.verificationDelay = verificationDelay;
	}

	public Pickaxe getPickaxe() {
		return pickaxe;
	}

	public void setPickaxe(Pickaxe pickaxe) {
		this.pickaxe = pickaxe;
	}

	public boolean isAutoEnchant() {
		return autoEnchant;
	}

	public void setAutoEnchant(boolean autoEnchant) {
		this.autoEnchant = autoEnchant;
	}
	
	public boolean isAdaptiveMineDelay() {
		return adaptiveMineDelay;
	}
	
	public void setAdaptiveMineDelay(boolean adaptiveMineDelay) {
		this.adaptiveMineDelay = adaptiveMineDelay;
	}
	
}
