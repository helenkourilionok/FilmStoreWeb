package by.training.filmstore.service.impl;

import by.training.filmstore.service.ActorService;
import by.training.filmstore.service.CommentService;
import by.training.filmstore.service.FilmDirectorService;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.GoodOfOrderService;
import by.training.filmstore.service.OrderService;
import by.training.filmstore.service.UserService;

public class FilmStoreServiceFactoryImpl extends FilmStoreServiceFactory{

	private final static FilmService filmServiceImpl = new FilmServiceImpl();
	private final static UserService userServiceImpl = new UserServiceImpl();
	private final static CommentService commentServiceImpl = new CommentServiceImpl();
	private final static ActorService actorServiceImpl = new ActorServiceImpl();
	private final static FilmDirectorService filmDirectorServiceImpl = new FilmDirectorServiceImpl();
	private final static OrderService orderService = new OrderServiceImpl();
	private final static GoodOfOrderService goodOfOrderService = new GoodOfOrderServiceImpl();
	
	@Override
	public FilmService getFilmService() {
		return filmServiceImpl;
	}

	@Override
	public UserService getUserService() {
		return userServiceImpl;
	}

	@Override
	public CommentService getCommentService() {
		return commentServiceImpl;
	}

	@Override
	public ActorService getActorService() {
		return actorServiceImpl;
	}

	@Override
	public FilmDirectorService getFilmDirectorService() {
		return filmDirectorServiceImpl;
	}

	@Override
	public OrderService getOrderService() {
		return orderService;
	}

	@Override
	public by.training.filmstore.service.GoodOfOrderService getGoodOfOrderService() {
		return goodOfOrderService;
	}

}
