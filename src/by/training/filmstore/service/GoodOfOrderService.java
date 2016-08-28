package by.training.filmstore.service;

import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectGoodParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidGoodOperException;

public interface GoodOfOrderService {
	void create(String idOrder, String idFilm, String countFilms)
	throws FilmStoreServiceIncorrectGoodParamException,
			FilmStoreServiceInvalidGoodOperException,
			FilmStoreServiceException;
}
