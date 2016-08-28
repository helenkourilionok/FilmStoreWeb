package by.training.filmstore.service.exception;

public final class FilmStoreServiceIncorrectOrderParamException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceIncorrectOrderParamException(String message){
		super(message);
	}
	
	public FilmStoreServiceIncorrectOrderParamException(Exception e){
		super(e);
	}
}
