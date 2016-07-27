package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Film;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public interface FilmService {
	List<Film> findAllFilms() throws FilmStoreServiceException;
}
