package by.training.filmstore.service.impl;

import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.UserService;

public class FilmStoreServiceFactoryImpl extends FilmStoreServiceFactory{

	private static final FilmService filmServiceImpl = new FilmServiceImpl();
	private static final UserService userServiceImpl = new UserServiceImpl();
	
	
	@Override
	public FilmService getFilmService() {
		return filmServiceImpl;
	}

	@Override
	public UserService getUserService() {
		return userServiceImpl;
	}

}
