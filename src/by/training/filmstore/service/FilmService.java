package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.Quality;
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListFilmNotFoundException;

public interface FilmService {
	boolean create(String name,	String genre,String country,
			String yearOfRelease,Quality quality,String description,
			String price,String countFilms,String image) 
							throws FilmStoreServiceException,
							FilmStoreServiceIncorrectParamException,
							FilmStoreServiceInvalidOperException;
	Film find(String id, boolean lazyInit) throws FilmStoreServiceException, 
									FilmStoreServiceIncorrectParamException,
									FilmStoreServiceFilmNotFoundException;

	List<Film> findAllFilms(boolean lazyInit) throws FilmStoreServiceException, 
											FilmStoreServiceListFilmNotFoundException;
}
