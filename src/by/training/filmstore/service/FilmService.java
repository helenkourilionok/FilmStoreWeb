package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Film;
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListFilmNotFoundException;

public interface FilmService {
	Film create(String name,	String genre,String country,
			String yearOfRelease,String quality,
			String filmDirId,String description,
			String price,String countFilms,String image) 
							throws FilmStoreServiceException,
							FilmStoreServiceIncorrectParamException,
							FilmStoreServiceInvalidOperException;
	void createFilmActor(Film entity,List<Short> idActors) throws FilmStoreServiceException,
								FilmStoreServiceIncorrectParamException,
								FilmStoreServiceInvalidOperException;

	Film find(String id, boolean lazyInit) throws FilmStoreServiceException, 
									FilmStoreServiceIncorrectParamException,
									FilmStoreServiceFilmNotFoundException;

	List<Film> findAllFilms(boolean lazyInit,int offset,int recordsPerPage,List<Integer> countAllRecords) 
											throws FilmStoreServiceException,
											FilmStoreServiceIncorrectParamException,
											FilmStoreServiceListFilmNotFoundException;
}
