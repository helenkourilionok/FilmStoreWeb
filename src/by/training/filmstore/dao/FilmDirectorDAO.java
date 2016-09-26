package by.training.filmstore.dao;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.FilmDirector;
/**
 * This interface extends {@link AbstractDAO}
 * and declares methods that inherent for {@link by.training.filmstore.entity.FilmDirector} entity
 * 
 * @author Helena
 *
 *@see AbstractDAO
 *@see by.training.filmstore.entity.FilmDirector
 */
public interface FilmDirectorDAO extends AbstractDAO<FilmDirector, Short>{
	/**
	 * The method permits find film director using fio element.
	 * 
	 * @param fio element is used to find film director in database
	 * @return object of FilmDirector class if in database exist film director
	 * that has the value appropriate column equal to the specified element;otherwise
	 * returns null  
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.entity.FilmDirector#getFio()
	 * @see by.training.filmstore.entity.FilmDirector#setFio(String)
	 */
	FilmDirector findByFIO(String fio) throws FilmStoreDAOException;
}
