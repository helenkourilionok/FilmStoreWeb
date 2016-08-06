package by.training.filmstore.service.exception;

public class FilmStoreServiceIncorrectParamException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceIncorrectParamException(String message){
		super(message);
	}
	
	public FilmStoreServiceIncorrectParamException(Exception e){
		super(e);
	}
}
