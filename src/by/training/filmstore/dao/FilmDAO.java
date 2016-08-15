package by.training.filmstore.dao;


import java.util.List;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Film;

public interface FilmDAO extends AbstractDAO<Film, Short>{
	List<Film> findAll(int offset,int recordsPerPage,
					List<Integer> countAllRecords) throws FilmStoreDAOException;
	List<Film> findFilmByGenre(String genre) throws FilmStoreDAOException;
	List<Film> findFilmByName(String name) throws FilmStoreDAOException;
	boolean createFilmActor(short filmId,List<Short> idActors) throws FilmStoreDAOException;
	boolean updateFilmActor(short filmId,List<Short> idActors) throws FilmStoreDAOException;
	boolean deleteFilmActor(Short filmId) throws FilmStoreDAOException;
	boolean findActorFilmDirectorForFilm(Film film) throws FilmStoreDAOException;
}
