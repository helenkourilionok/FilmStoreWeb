package by.training.filmstore.service.exception;

public class FilmStoreServiceListFilmNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceListFilmNotFoundException(String message){
		super(message);
	}
	
	public FilmStoreServiceListFilmNotFoundException(Exception e){
		super(e);
	}
}
