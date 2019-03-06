package com.dmb2.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	
	public static void debug(String message) {
		System.out.println(String.format("[DEBUG][%s] %s", dateAndTime(), message));
	}
	
	public static void info(String message) {
		System.out.println(String.format("[INFO][%s] %s", dateAndTime(), message));
	}
	
	public static void warn(String message) {
		System.out.println(String.format("[WARN][%s] %s", dateAndTime(), message));
	}
	
	public static void error(String message) {
		System.out.println(String.format("[ERROR][%s] %s", dateAndTime(), message));
	}
	
	private static String dateAndTime() {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");
		return formatter.format(dateTime);
	}
	
}
