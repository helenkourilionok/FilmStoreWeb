package by.training.filmstore.controller.listener;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import by.training.filmstore.command.Command;
import by.training.filmstore.controller.CommandHelper;
import by.training.filmstore.controller.CommandLoader;


public final class FilmStoreContextListener implements ServletContextListener {
	
	private final static String INIT_POOL_CONNECTION = "INIT_POOL_CONNECTION";
	private final static String DESTROY_POOL_CONNECTION = "DESTROY_POOL_CONNECTION";

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		CommandHelper commandHelper = CommandHelper.getInstance();
		Command command = commandHelper.getCommand(DESTROY_POOL_CONNECTION);
		try {
			command.execute(null, null);
		} catch (ServletException|IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		CommandLoader.loadCommand(context);
		CommandHelper commandHelper = CommandHelper.getInstance();
		Command command = commandHelper.getCommand(INIT_POOL_CONNECTION);
		try {
			command.execute(null, null);
		} catch (ServletException|IOException e) {
			throw new RuntimeException(e);
		} 
	}

}
