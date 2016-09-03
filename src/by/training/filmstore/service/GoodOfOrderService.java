package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.GoodOfOrder;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectGoodParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidGoodOperException;

public interface GoodOfOrderService {
	void create(String idOrder, String idFilm, String countFilms)
	throws FilmStoreServiceIncorrectGoodParamException,
			FilmStoreServiceInvalidGoodOperException,
			FilmStoreServiceException;
	List<GoodOfOrder> findGoodByOrderId(String id) throws FilmStoreServiceException,
	FilmStoreServiceIncorrectGoodParamException;
}
