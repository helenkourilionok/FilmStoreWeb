package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Film;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceImageNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectIdException;

public interface FilmService {
	Film find(Short id) throws FilmStoreServiceException,
										FilmStoreServiceIncorrectIdException;
	List<Film> findAllFilms() throws FilmStoreServiceException;
	byte[] findFilmImageByFilmId(Short id) throws FilmStoreServiceException,
										FilmStoreServiceIncorrectIdException,
										FilmStoreServiceImageNotFoundException;
}
