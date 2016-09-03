package by.training.filmstore.service.exception;

public class FilmStoreServiceInvalidUserOperException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceInvalidUserOperException(String message){
		super(message);
	}
	
	public FilmStoreServiceInvalidUserOperException(Exception e){
		super(e);
	}
}
