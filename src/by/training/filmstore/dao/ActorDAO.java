package by.training.filmstore.dao;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Actor;
/**
 * This interface extends {@link AbstractDAO} interface
 * and declares methods that appropriate 
 * just for {@link by.training.filmstore.entity.Actor} objects 
 * 
 * @author Helena
 *
 * @see AbstractDAO
 * @see by.training.filmstore.entity.Actor
 */
public interface ActorDAO extends AbstractDAO<Actor, Short> {
	/**
	 * The method permits find actor by value, 
	 * that {@link by.training.filmstore.entity.Actor#getFio()} return.  
	 * 
	 * @param fio element, that will be used to get from database object of Actor class. 
	 * 
	 * @return  object reference to actor or null,
	 * if actor object with this fio element doesn't exist in database
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.dao.exception.FilmStoreDAOException
	 */
	Actor findByFIO(String fio) throws FilmStoreDAOException;
}
