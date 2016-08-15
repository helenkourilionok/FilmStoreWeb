package by.training.filmstore.service.exception;

public class FilmStoreServiceInvalidCommentOperException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceInvalidCommentOperException(String message){
		super(message);
	}
	
	public FilmStoreServiceInvalidCommentOperException(Exception e){
		super(e);
	}
}
