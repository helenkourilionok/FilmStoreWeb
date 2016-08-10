package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public interface FilmDirectorService {
	List<FilmDirector> findAllFilmDirectors() throws FilmStoreServiceException;
}
