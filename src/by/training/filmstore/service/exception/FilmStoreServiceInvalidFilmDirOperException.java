package by.training.filmstore.service.exception;

public class FilmStoreServiceInvalidFilmDirOperException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceInvalidFilmDirOperException(String message){
		super(message);
	}
	
	public FilmStoreServiceInvalidFilmDirOperException(Exception exception){
		super(exception);
	}
}
