package by.training.filmstore.service.exception;

/**
 * @author Helena
 *
 */
public class FilmStoreServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -757659477942440362L;

	public FilmStoreServiceException(String message){
		super(message);
	}
	
	public FilmStoreServiceException(Exception exception){
		super(exception);
	}
}
