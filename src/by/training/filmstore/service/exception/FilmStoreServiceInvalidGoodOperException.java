package by.training.filmstore.service.exception;

public class FilmStoreServiceInvalidGoodOperException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FilmStoreServiceInvalidGoodOperException(String message) {
		super(message);
	}
	public FilmStoreServiceInvalidGoodOperException(Exception e){
		super(e);
	}
}
