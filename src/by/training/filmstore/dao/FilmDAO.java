package by.training.filmstore.dao;


import java.util.List;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
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
	 * The method destine for filling intermediate table,
	 * that provides  many-to-many relationships between the films and the actors.
	 * This method add to intermediate table pair of primary key:
	 * first primary key is id of film,second primary key is id of actor,
	 * which takes part in film.The film id is the same for all 
	 * records that will be added to intermediate table.
	 * 
	 * @param filmId id(primary key) of film
	 * @param idActors list of actor primary key 
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * @throws FilmStoreDAOInvalidOperationException if it's impossible add records to database
	 * because of incorrect actor or film id;film with filmId or actor with
	 * id from idActors doesn't exist in database
	 * 
	 * @see by.training.filmstore.entity.Film
	 * @see by.training.filmstore.entity.Actor
	 * @see by.training.filmstore.entity.Film#getId()
	 * @see by.training.filmstore.entity.Actor#getId()
	 */
	void createFilmActor(short filmId,List<Short> idActors) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
	/**
	 * The method performs updating records in intermediate table,
	 * that provides  many-to-many relationships between the films and the actors.
	 * As a result this method permits update list of actor that take part in 
	 * film with filmId as a id(primary key).
	 * 
	 * @param filmId id(primary key) of film
	 * @param idNewActors list of the new actor primary keys
	 * @param idOldActors list of the old actor primary keys
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * @throws FilmStoreDAOInvalidOperationException if idNewActors or idOldActors contains incorrect 
	 * primary keys(for example actor with key from that lists doesn't exist);if film 
	 * with this filmId(primary key) doesn't exist
	 * 
	 * @see by.training.filmstore.entity.Film
	 * @see by.training.filmstore.entity.Actor
	 */
	void updateFilmActor(short filmId,List<Short> idNewActors,List<Short> idOldActors) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
	/**
	 * The method performs deleting records from intermediate in intermediate table,
	 * that provides  many-to-many relationships between the films and the actors.
	 * As a result method provides deleting actors that associated with film 
	 * that has filmId as a primary key
	 * 
	 * @param filmId id(primary key) of film
	 * @param idActors list of actor primary keys
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * @throws FilmStoreDAOInvalidOperationException if idActors contains incorrect 
	 * primary keys(for example actor with key from that list doesn't exist);if film with this filmId(primary key)
	 * doesn't exist
	 * 
	 * @see by.training.filmstore.entity.Film
	 * @see by.training.filmstore.entity.Actor
	 */
	void deleteFilmActor(short filmId,List<Short> idActors) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
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
