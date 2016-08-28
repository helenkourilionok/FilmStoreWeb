package by.training.filmstore.service.exception;

public class FilmStoreServiceIncorrectGoodParamException extends Exception{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceIncorrectGoodParamException(String message){
		super(message);
	}
	
	public FilmStoreServiceIncorrectGoodParamException(Exception e){
		super(e);
	}
}
