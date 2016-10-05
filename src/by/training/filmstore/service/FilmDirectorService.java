package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmDirParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmDirOperException;

public interface FilmDirectorService {
	void create(String fio) throws FilmStoreServiceException,
	FilmStoreServiceIncorrectFilmDirParamException,
	FilmStoreServiceInvalidFilmDirOperException;
	List<FilmDirector> findAllFilmDirector() throws FilmStoreServiceException;
}
