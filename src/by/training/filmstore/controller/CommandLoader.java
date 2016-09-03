package by.training.filmstore.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;

public final class CommandLoader {
	
	private final static Logger logger = LogManager.getRootLogger();
	
	private static Map<CommandName, Command> commands = new HashMap<>();
	
	public static Map<CommandName, Command> getCommand(){
		return commands;
	}
	
	public static void loadCommand(ServletContext context){
		CommandName[] commandName = CommandName.values();
		String commandClass;
		Command command;
		try {
			for(CommandName commandNameTemp:commandName){
				commandClass = context.getInitParameter(commandNameTemp.name());
				command = (Command)Class.forName(commandClass).newInstance();
				commands.put(commandNameTemp,command);
			}
		} catch (ClassNotFoundException e) {
			logger.error("Can't find command class!",e);
		} catch (InstantiationException e) {
			logger.error("Can't create command instance!",e);
		} catch (IllegalAccessException e) {
			logger.error("Can't create command instance!",e);
		}

	}
}
