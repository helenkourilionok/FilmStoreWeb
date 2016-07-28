package by.training.filmstore.service;

import by.training.filmstore.service.impl.FilmStoreServiceFactoryImpl;

public abstract class FilmStoreServiceFactory {
	
	private static final FilmStoreServiceFactory filmStoreServiceFactoryImpl = 
											new FilmStoreServiceFactoryImpl();
	
	public abstract FilmService getFilmService();
	public abstract UserService getUserService();
	
	public static FilmStoreServiceFactory getServiceFactory(){
		return filmStoreServiceFactoryImpl;
	}
}
