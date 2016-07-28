package by.training.filmstore.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class FilmStoreContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//CommandHelper
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//PoolConnectionServiceInitializer.initPoolConnection();
	}

}
