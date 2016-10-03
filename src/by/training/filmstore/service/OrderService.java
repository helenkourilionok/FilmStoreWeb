package by.training.filmstore.service;

import java.math.BigDecimal;
import java.util.List;

import by.training.filmstore.entity.GoodOfOrder;
import by.training.filmstore.entity.Order;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectOrderParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOrderOperException;

public interface OrderService {
	Order create(String userEmail, String commonPrice, String status, String kindOfDelivery, String kindOfPayment,
			String dateOfDelivery, String address,List<GoodOfOrder> listGoods) throws FilmStoreServiceException,FilmStoreServiceInvalidOrderOperException,
	FilmStoreServiceIncorrectOrderParamException;
	void update(String orderId,String userEmail, String commonPrice, String status, String kindOfDelivery, String kindOfPayment,
			String dateOfDelivery, String dateOfOrder,String address) throws FilmStoreServiceException,
						FilmStoreServiceIncorrectOrderParamException,
						FilmStoreServiceInvalidOrderOperException;
	Order find(String id) throws FilmStoreServiceException,
									FilmStoreServiceIncorrectOrderParamException; 
	List<Order> findOrderByUserEmailAndStatus(String userEmail,String status) throws FilmStoreServiceException,
											FilmStoreServiceIncorrectOrderParamException;
	void payOrder(BigDecimal balance, String orderId, String userEmail) 
	throws FilmStoreServiceException,FilmStoreServiceInvalidOrderOperException,
									FilmStoreServiceIncorrectOrderParamException;
}
