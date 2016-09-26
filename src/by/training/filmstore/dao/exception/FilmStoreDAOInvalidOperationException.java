package by.training.filmstore.dao.exception;

public class FilmStoreDAOInvalidOperationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmStoreDAOInvalidOperationException(String message) {
        super(message);
    }

    public FilmStoreDAOInvalidOperationException(Exception exception) {
        super(exception);
    }
}
