package by.training.filmstore.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.training.filmstore.service.impl.PoolConnectionInitializer;


public class FilmStoreContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		PoolConnectionInitializer.destroyPoolConnection();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		PoolConnectionInitializer.initPoolConnection();
	}

}
