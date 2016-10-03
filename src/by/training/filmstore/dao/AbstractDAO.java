package by.training.filmstore.dao;

import java.io.Serializable;
import java.util.List;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;


/**
 *This abstract class describe the basic CRUD methods
 *to interact with database.
 *
 * @author Helena
 *
 * @param <T>  type of entity
 * @param <K>  type of primary key 
 */
public interface AbstractDAO <T extends Serializable,K extends Serializable>{
	 /**
	  * The method permits to get entity from database by id
	  * 
	  * @param id  primary key of entity,it will be used for find all entity information
	  * @return entity, if in database exist record with this id;
	  * or null if record with this id doesn't exist in database
	  * 
	  * @throws FilmStoreDAOException  if a database access error occurs or
	  * error interaction with connection pool
	  * 
	  * @see by.training.filmstore.dao.exception.FilmStoreDAOException
	  */
	 T find(K id) throws FilmStoreDAOException;
	 /**
	  * The method provides extraction all entities from database
	  * 
	  * @return list of entities or empty list,if entity table is empty
	  * 
	  * @throws FilmStoreDAOException if a database access error occurs,
	  * error interaction with connection pool
	  * 
	  * @see by.training.filmstore.dao.exception.FilmStoreDAOException
	  */
	 List<T> findAll() throws FilmStoreDAOException;
	 /**
	  * The method permits to create entity object
	  * 
	  * @param entity object that will be created
	  * @throws FilmStoreDAOException if a database access error occurs,
	  * error interaction with connection pool
	  * 
	  * @throws FilmStoreDAOInvalidOperationException if database error occurred while creating entity object
	  * 
	  * @see by.training.filmstore.dao.exception.FilmStoreDAOException
	  * @see by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException
	  */
	 void create(T entity) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
	 /**
	  * The method provides updating entity object
	  * 
	  * @param entity object that will be updated
	  * 
	  * @throws FilmStoreDAOException if a database access error occurs,
	  * error interaction with connection pool
	  * @throws FilmStoreDAOInvalidOperationException if database error occurred while updating entity object
	  * or if entity for update doesn't exist in database
	  */
	 void update(T entity) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
	 /**
	  * The method permits to delete entity object
	  * 
	  * @param id primary key of entity,it will use for deleting entity from database
	  * @throws FilmStoreDAOException if a database access error occurs,
	  * error interaction with connection pool
	  * 
	  * @throws FilmStoreDAOInvalidOperationException if database error occurred while creating entity object
	  * or if entity with specified id doesn't exist in database.
	  * 
	  * @see by.training.filmstore.dao.exception.FilmStoreDAOException
	  * @see by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException
	  */
	 void delete(K id) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
}
