package by.training.filmstore.dao;


import java.util.List;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.entity.Film;
/**
 * This interface extends {@link AbstractDAO}
 * and declares methods that inherent for {@link by.training.filmstore.entity.Film} entity
 * 
 * @author Helena
 *
 * @see AbstractDAO
 * @see by.training.filmstore.entity.Film
 */
public interface FilmDAO extends AbstractDAO<Film, Short>{
	void update(Film entity,List<Actor> listNewActor)throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
	/**
	 * The method permits to get films stored in database.
	 * recordsPerPage element specifies the max count of films 
	 * derived from the database.
	 * offset element specifies the number of the element 
	 * from which will extract data from the database.
	 * This method is used to implement pagination.
	 * 
	 * @param offset number of film from which the search is started in database
	 * @param recordsPerPage max count of films that will try to extract from database 
	 * @param countAllRecords element in which method put 
	 * number of films stored in the database 
	 * 
	 * @return list of films extracted from the database;empty list
	 * if offset element is equal to the number of records in the database
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.entity.Film
	 */
	List<Film> findAll(int offset,int recordsPerPage,
					List<Integer> countAllRecords) throws FilmStoreDAOException;
	/**
	 * The method permits find all films that belong to the given genre.
	 * 
	 * @param genre name of the film genre
	 * 
	 * @return list of films that belong to the given genre
	 *  
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.entity.Film#getGenre()
	 */
	List<Film> findFilmByGenre(String genre) throws FilmStoreDAOException;
	/**
	 * The method provides finding list of films that have the same name
	 * 
	 * @param name name of film;it use as criteria for search list of films
	 * 
	 * @return list of films that have the given name
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.entity.Film#getName()
	 */
	List<Film> findFilmByName(String name) throws FilmStoreDAOException;
	/**
	 * The method destine for find additional information about film including actors and 
	 * film director that associated with this film.
	 * 
	 * @param film film object, that will be filled with actors and film director
	 *  
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.entity.FilmDirector
	 * @see by.training.filmstore.entity.Actor
	 * @see by.training.filmstore.entity.Film#setActors(int, by.training.filmstore.entity.Actor)
	 * @see by.training.filmstore.entity.Film#setFilmDirector(by.training.filmstore.entity.FilmDirector)
	 */
	void findActorFilmDirectorForFilm(Film film) throws FilmStoreDAOException;
}
