package by.training.filmstore.service.exception;

public class FilmStoreServiceInvalidFilmOperException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FilmStoreServiceInvalidFilmOperException(String message) {
		super(message);
	}
	public FilmStoreServiceInvalidFilmOperException(Exception e){
		super(e);
	}
}
