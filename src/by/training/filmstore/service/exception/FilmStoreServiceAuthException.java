package by.training.filmstore.service.exception;


public class FilmStoreServiceAuthException extends Exception{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceAuthException(String message){
		super(message);
	}
	
	public FilmStoreServiceAuthException(Exception e){
		super(e);
	}
}
