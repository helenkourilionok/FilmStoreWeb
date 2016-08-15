package by.training.filmstore.service.exception;

public class FilmStoreServiceIncorrectCommentParamException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceIncorrectCommentParamException(String message){
		super(message);
	}
	
	public FilmStoreServiceIncorrectCommentParamException(Exception e){
		super(e);
	}
}
