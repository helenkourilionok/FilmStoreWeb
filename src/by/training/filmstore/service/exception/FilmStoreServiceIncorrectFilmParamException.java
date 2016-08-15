package by.training.filmstore.service.exception;

public class FilmStoreServiceIncorrectFilmParamException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceIncorrectFilmParamException(String message){
		super(message);
	}
	
	public FilmStoreServiceIncorrectFilmParamException(Exception e){
		super(e);
	}
}
