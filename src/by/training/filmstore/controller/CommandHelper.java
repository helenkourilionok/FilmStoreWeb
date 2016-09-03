package by.training.filmstore.controller;

import java.util.Map;

import by.training.filmstore.command.Command;

public final class CommandHelper {
	
	private static final CommandHelper instance = new CommandHelper();

	private Map<CommandName, Command> commands;

	private CommandHelper() {
		commands = CommandLoader.getCommand();
	}

	public Command getCommand(String name) {
		name = name.replace('-', '_');
		CommandName commandName = CommandName.valueOf(name.toUpperCase());

		Command command = commands.get(commandName);

		return command;
	}

	public static CommandHelper getInstance() {
		return instance;
	}
}
