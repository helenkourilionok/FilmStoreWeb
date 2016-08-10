package by.training.filmstore.service.exception;

public class FilmStoreServiceFilmNotFoundException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreServiceFilmNotFoundException(String message){
		super(message);
	}
	
	public FilmStoreServiceFilmNotFoundException(Exception e){
		super(e);
	}
}
