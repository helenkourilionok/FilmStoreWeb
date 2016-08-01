package by.training.filmstore.service.exception;

public class FilmStoreServiceParamIncorrectException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceParamIncorrectException(String message){
		super(message);
	}
	
	public FilmStoreServiceParamIncorrectException(Exception e){
		super(e);
	}
}
