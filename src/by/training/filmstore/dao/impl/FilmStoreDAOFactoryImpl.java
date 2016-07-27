package by.training.filmstore.dao.impl;

import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.UserDAO;

public class FilmStoreDAOFactoryImpl extends FilmStoreDAOFactory {
	private static final UserDAO userDAOImpl = new UserDAOImpl();
	private static final FilmDAO filmDAOImpl = new FilmDAOImpl();
	@Override
 	public UserDAO getUserDao() {
		return userDAOImpl;
	}
	@Override
	public FilmDAO getFilmDAO() {
		return filmDAOImpl;
	}
}
