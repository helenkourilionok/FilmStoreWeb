package by.training.filmstore.service.exception;

public class FilmStoreServiceImageNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceImageNotFoundException(Exception exception){
		super(exception);
	}
	
	public FilmStoreServiceImageNotFoundException(String message){
		super(message);
	}
}
