package by.training.filmstore.service;

import by.training.filmstore.service.impl.FilmStoreServiceFactoryImpl;

public abstract class FilmStoreServiceFactory {
	
	private static final FilmStoreServiceFactory filmStoreServiceFactoryImpl = 
											new FilmStoreServiceFactoryImpl();
	
	public abstract FilmService getFilmService();
	public abstract UserService getUserService();
	public abstract CommentService getCommentService();
	public abstract ActorService getActorService();
	public abstract FilmDirectorService getFilmDirectorService();
	public abstract OrderService getOrderService();
	public abstract GoodOfOrderService getGoodOfOrderService();
	
	public static FilmStoreServiceFactory getServiceFactory(){
		return filmStoreServiceFactoryImpl;
	}
}
