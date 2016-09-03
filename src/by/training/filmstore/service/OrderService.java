package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Order;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectOrderParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOrderOperException;

public interface OrderService {
	Order create(String userEmail, String commonPrice, String status, String kindOfDelivery, String kindOfPayment,
			String dateOfDelivery, String address) 
			throws FilmStoreServiceIncorrectOrderParamException,
			FilmStoreServiceInvalidOrderOperException, FilmStoreServiceException;
	void update(String userEmail, String commonPrice, String status, String kindOfDelivery, String kindOfPayment,
			String dateOfDelivery, String dateOfOrder,String address) throws FilmStoreServiceException,
						FilmStoreServiceIncorrectOrderParamException,
						FilmStoreServiceInvalidOrderOperException;
	Order find(String id) throws FilmStoreServiceException,
									FilmStoreServiceIncorrectOrderParamException; 
	List<Order> findOrderByUserEmailAndStatus(String userEmail,String status) throws FilmStoreServiceException,
											FilmStoreServiceIncorrectOrderParamException;
}
