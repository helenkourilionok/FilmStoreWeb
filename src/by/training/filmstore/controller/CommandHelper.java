package by.training.filmstore.controller;

import java.util.HashMap;
import java.util.Map;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.impl.ChangeLanguageCommand;
import by.training.filmstore.command.impl.CommentShowPageCommand;
import by.training.filmstore.command.impl.AdminCreateFilmCommand;
import by.training.filmstore.command.impl.AdminCreateFilmShowPageCommand;
import by.training.filmstore.command.impl.AdminShowListFilmCommand;
import by.training.filmstore.command.impl.AdminUpdateFilmCommand;
import by.training.filmstore.command.impl.AdminUpdateFilmShowPageCommand;
import by.training.filmstore.command.impl.LoginShowPageCommand;
import by.training.filmstore.command.impl.LoginationCommand;
import by.training.filmstore.command.impl.LogoutCommand;
import by.training.filmstore.command.impl.MakeCommentCommand;
import by.training.filmstore.command.impl.PoolConnectionDestroyCommand;
import by.training.filmstore.command.impl.PoolConnectionInitCommand;
import by.training.filmstore.command.impl.PutInBasketCommand;
import by.training.filmstore.command.impl.ShowListFilmCommand;
import by.training.filmstore.command.impl.SignUpCommand;
import by.training.filmstore.command.impl.SignUpShowPageCommand;


public final class CommandHelper {
	
	private static final CommandHelper instance = new CommandHelper();

	private Map<CommandName, Command> commands = new HashMap<>();

	private CommandHelper() {
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		commands.put(CommandName.SHOW_LIST_FILM, new ShowListFilmCommand());
		commands.put(CommandName.LOGINATION, new LoginationCommand());
		commands.put(CommandName.LOGOUT, new LogoutCommand());
		commands.put(CommandName.SIGN_UP,new SignUpCommand());
		commands.put(CommandName.SIGN_UP_SHOW_PAGE, new SignUpShowPageCommand());
		commands.put(CommandName.LOGIN_SHOW_PAGE,new LoginShowPageCommand());
		commands.put(CommandName.INIT_POOL_CONNECTION,new PoolConnectionInitCommand());
		commands.put(CommandName.DESTROY_POOL_CONNECTION, new PoolConnectionDestroyCommand());
		commands.put(CommandName.SHOW_COMMENT_PAGE, new CommentShowPageCommand());
		commands.put(CommandName.MAKE_COMMENT, new MakeCommentCommand());
		commands.put(CommandName.PUT_IN_BASKET,new PutInBasketCommand());
		//создавать отдельно список картинок и по индексу писать в поток ту или иную картинку
		commands.put(CommandName.A_CREATE_FILM, new AdminCreateFilmCommand());
		commands.put(CommandName.A_CREATE_FILM_SHOW_PAGE, new AdminCreateFilmShowPageCommand());
		commands.put(CommandName.A_UPDATE_FILM_SHOW_PAGE, new AdminUpdateFilmShowPageCommand());
		commands.put(CommandName.A_UPDATE_FILM,new AdminUpdateFilmCommand());
		commands.put(CommandName.A_SHOW_LIST_FILM, new AdminShowListFilmCommand());
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
