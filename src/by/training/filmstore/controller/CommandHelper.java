package by.training.filmstore.controller;

import java.util.HashMap;
import java.util.Map;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.impl.ChangeLanguageCommand;
import by.training.filmstore.command.impl.LoginationCommand;
import by.training.filmstore.command.impl.LogoutCommand;
import by.training.filmstore.command.impl.ShowFilmCommand;
import by.training.filmstore.command.impl.SignUpCommand;


public final class CommandHelper {
	
	private static final CommandHelper instance = new CommandHelper();

	private Map<CommandName, Command> commands = new HashMap<>();

	private CommandHelper() {
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		commands.put(CommandName.SHOW_LIST_FILM, new ShowFilmCommand());
		commands.put(CommandName.LOGINATION, new LoginationCommand());
		commands.put(CommandName.LOGOUT, new LogoutCommand());
		commands.put(CommandName.SIGN_UP,new SignUpCommand());
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
