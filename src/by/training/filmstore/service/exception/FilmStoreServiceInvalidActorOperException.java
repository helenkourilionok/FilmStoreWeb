package by.training.filmstore.service.exception;

public class FilmStoreServiceInvalidActorOperException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceInvalidActorOperException(String message){
		super(message);
	}
	
	public FilmStoreServiceInvalidActorOperException(Exception e){
		super(e);
	}
}
