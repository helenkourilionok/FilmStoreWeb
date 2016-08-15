package by.training.filmstore.service.exception;

public class FilmStoreServiceIncorrectUserParamException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceIncorrectUserParamException(String message){
		super(message);
	}
	
	public FilmStoreServiceIncorrectUserParamException(Exception e){
		super(e);
	}
}
