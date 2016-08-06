package by.training.filmstore.service.exception;

public class FilmStoreServiceListCommentNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceListCommentNotFoundException(String message){
		super(message);
	}
	
	public FilmStoreServiceListCommentNotFoundException(Exception e){
		super(e);
	}
}
