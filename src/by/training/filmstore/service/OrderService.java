package by.training.filmstore.service;

import by.training.filmstore.entity.Order;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectOrderParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOrderOperException;

public interface OrderService {
	Order create(String userEmail, String commonPrice, String status, String kindOfDelivery, String kindOfPayment,
			String dateOfDelivery, String address) 
			throws FilmStoreServiceIncorrectOrderParamException,
			FilmStoreServiceInvalidOrderOperException, FilmStoreServiceException;
}
