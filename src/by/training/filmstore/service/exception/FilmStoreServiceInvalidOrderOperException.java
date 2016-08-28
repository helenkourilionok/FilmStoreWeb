package by.training.filmstore.service.exception;

public final class FilmStoreServiceInvalidOrderOperException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceInvalidOrderOperException(String message){
		super(message);
	}
	
	public FilmStoreServiceInvalidOrderOperException(Exception e){
		super(e);
	}
}
