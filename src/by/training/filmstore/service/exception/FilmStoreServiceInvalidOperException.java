package by.training.filmstore.service.exception;

public class FilmStoreServiceInvalidOperException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceInvalidOperException(String message){
		super(message);
	}
	
	public FilmStoreServiceInvalidOperException(Exception exception){
		super(exception);
	}
}
