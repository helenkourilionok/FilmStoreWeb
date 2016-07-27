package by.training.filmstore.dao.exception;

public class FilmStoreDAOException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1563660909597020858L;

	public FilmStoreDAOException(String message) {
        super(message);
    }

    public FilmStoreDAOException(Exception exception) {
        super(exception);
    }
}
