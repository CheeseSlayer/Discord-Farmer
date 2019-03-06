package com.dmb2;

import java.util.stream.Collectors;

import javax.security.auth.login.LoginException;

import com.dmb2.log.Logger;
import com.dmb2.main.Arguments;
import com.dmb2.main.DiscordBot;

import picocli.CommandLine;
import picocli.CommandLine.MissingParameterException;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.ParameterException;

public class Launcher {
	
	public static void main(String[] args) {
		try{
			Arguments arguments = CommandLine.populateCommand(new Arguments(), args);
			
			DiscordBot discordBot = new DiscordBot(arguments);
			discordBot.run();
		} catch (MissingParameterException e) {
			Logger.error("Missing argument: " + e.getMissing().stream().map(ArgSpec::paramLabel).collect(Collectors.joining(", ")));
		} catch (LoginException e) {
			Logger.error("Invalid user token");
		} catch (InterruptedException e) {
			Logger.error("Login interrupted");
		} catch (ParameterException e) {
			Logger.error(e.getMessage());
		}
	}
	
}