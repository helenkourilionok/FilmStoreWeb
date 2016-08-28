package by.training.filmstore.dao;

import by.training.filmstore.dao.impl.FilmStoreDAOFactoryImpl;

public abstract class FilmStoreDAOFactory {
	private static final FilmStoreDAOFactoryImpl filmStoreDAOFactoryImpl 
										= new FilmStoreDAOFactoryImpl();
	public abstract UserDAO getUserDao();
	public abstract FilmDAO getFilmDAO();
	public abstract CommentDAO getCommentDAO();
	public abstract ActorDAO getActorDAO();
	public abstract FilmDirectorDAO getFilmDirectorDAO();
	public abstract OrderDAO getOrderDAO();
	public abstract GoodOfOrderDAO getGoodOfOrderDAO();
	
	public static FilmStoreDAOFactory getDAOFactory(){
		return filmStoreDAOFactoryImpl;
	}
}
