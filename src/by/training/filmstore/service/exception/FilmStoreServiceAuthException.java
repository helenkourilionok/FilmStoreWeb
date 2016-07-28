package by.training.filmstore.service.exception;


public class FilmStoreServiceAuthException extends Exception{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 988475139843000425L;

	public FilmStoreServiceAuthException(String message){
		super(message);
	}
	
	public FilmStoreServiceAuthException(Exception e){
		super(e);
	}
}
