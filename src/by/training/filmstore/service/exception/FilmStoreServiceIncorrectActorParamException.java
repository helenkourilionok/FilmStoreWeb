package by.training.filmstore.service.exception;

public class FilmStoreServiceIncorrectActorParamException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceIncorrectActorParamException(String message){
		super(message);
	}
	
	public FilmStoreServiceIncorrectActorParamException(Exception e){
		super(e);
	}
}
