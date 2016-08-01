package by.training.filmstore.service.exception;

public class FilmStoreServiceIncorrectIdException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceIncorrectIdException(String message){
		super(message);
	}
	
	public FilmStoreServiceIncorrectIdException(Exception e){
		super(e);
	}
}
