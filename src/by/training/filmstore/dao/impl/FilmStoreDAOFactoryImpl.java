package by.training.filmstore.dao.impl;

import by.training.filmstore.dao.ActorDAO;
import by.training.filmstore.dao.CommentDAO;
import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmDirectorDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.GoodOfOrderDAO;
import by.training.filmstore.dao.OrderDAO;
import by.training.filmstore.dao.UserDAO;

public class FilmStoreDAOFactoryImpl extends FilmStoreDAOFactory {
	
	private final static UserDAO userDAOImpl = new UserDAOImpl();
	private final static FilmDAO filmDAOImpl = new FilmDAOImpl();
	private final static CommentDAO commentDAOImpl = new CommentDAOImpl();
	private final static ActorDAO actorDAOImpl = new ActorDAOImpl();
	private final static FilmDirectorDAO filmDirectorDAOImpl = new FilmDirectorDAOImpl();
	private final static OrderDAO orderDAO = new OrderDAOImpl();
	private final static GoodOfOrderDAO goodOfOrderDAO = new GoodOfOrderDAOImpl();
	
	@Override
 	public UserDAO getUserDao() {
		return userDAOImpl;
	}
	
	@Override
	public FilmDAO getFilmDAO() {
		return filmDAOImpl;
	}
	
	@Override
	public CommentDAO getCommentDAO() {
		return commentDAOImpl;
	}

	@Override
	public ActorDAO getActorDAO() {
		return actorDAOImpl;
	}

	@Override
	public FilmDirectorDAO getFilmDirectorDAO() {
		return filmDirectorDAOImpl;
	}

	@Override
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	@Override
	public GoodOfOrderDAO getGoodOfOrderDAO() {
		return goodOfOrderDAO;
	}
}
