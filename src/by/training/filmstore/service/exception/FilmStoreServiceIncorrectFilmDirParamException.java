package by.training.filmstore.service.exception;

public class FilmStoreServiceIncorrectFilmDirParamException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceIncorrectFilmDirParamException(String message){
		super(message);
	}
	
	public FilmStoreServiceIncorrectFilmDirParamException(Exception e){
		super(e);
	}
}
