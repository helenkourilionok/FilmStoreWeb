package by.training.filmstore.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.OrderDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.KindOfDelivery;
import by.training.filmstore.entity.KindOfPayment;
import by.training.filmstore.entity.Order;
import by.training.filmstore.entity.Status;
import by.training.filmstore.service.OrderService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectOrderParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOrderOperException;

public class OrderServiceImpl implements OrderService {

	@Override
	public Order create(String userEmail, String commonPrice, String status, String kindOfDelivery, String kindOfPayment,
			String dateOfDelivery, String address) throws FilmStoreServiceIncorrectOrderParamException,
			FilmStoreServiceInvalidOrderOperException, FilmStoreServiceException {

		Order order = validateOrder(userEmail, commonPrice, status, kindOfDelivery, kindOfPayment,
					  dateOfDelivery, address);
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		OrderDAO orderDAO = filmStoreDAOFactory.getOrderDAO();
		try {
			if(!orderDAO.create(order)){
				throw new FilmStoreServiceInvalidOrderOperException("Operation failed!Can't create order!");
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		return order;
	}
	
	private Order validateOrder(String userEmail, String commonPrice, String status, String kindOfDelivery, String kindOfPayment,
			String dateOfDelivery, String address) throws FilmStoreServiceIncorrectOrderParamException{
		int length = 40;
		if(!ValidationParamUtil.validateEmail(userEmail, length)){
			throw new FilmStoreServiceIncorrectOrderParamException("Incorrect user email!");
		}
		BigDecimal comPrice = ValidationParamUtil.validateBalance(commonPrice); 
		if(comPrice == null){
			throw new FilmStoreServiceIncorrectOrderParamException("Common price isn't valid!");
		}
		Status statusForOrder = Validation.validateStatus(status);
		if(statusForOrder == null){
			throw new FilmStoreServiceIncorrectOrderParamException("Incorrect order status!");
		}
		KindOfDelivery kindOfDel = Validation.validateKindOfDelivery(kindOfDelivery);
		if(kindOfDel == null){
			throw new FilmStoreServiceIncorrectOrderParamException("Invalid kind of delivery!");
		}
		KindOfPayment kindOfPay = Validation.validateKindOfPayment(kindOfPayment);
		if(kindOfPay == null){
			throw new FilmStoreServiceIncorrectOrderParamException("Invalid kind of payment!");
		}
		Date dateOfOrder = Date.valueOf(LocalDate.now());
		LocalDate dateOfDel = Validation.validateDateOfDelivery(dateOfDelivery);
		if(dateOfDel == null){
			throw new FilmStoreServiceIncorrectOrderParamException("Invalid date of delivery!");
		}
		Date dateOfDel2 = Date.valueOf(dateOfDel);
		if(!Validation.validateAddress(address)){
			throw new FilmStoreServiceIncorrectOrderParamException("Incorrect address of delivery!");
		}
		Order order = new Order(userEmail,comPrice,statusForOrder,kindOfDel,
				kindOfPay,dateOfOrder,dateOfDel2,address);
		return order;
	}
	
	static class Validation{
		
		private final static String ADDRESS_PATTERN_EN = "^[a-zA-Z0-9\\s\\.\\,\\-]{7,40}$";
		private static final String ADDRESS_PATTERN_RU = "^\\A[Р-пр-џ0-9\\s\\.\\,\\-]\\P{Alpha}{7,40}$";
		
		static Status validateStatus(String status) {
			if (!ValidationParamUtil.notEmpty(status)) {
				return null;
			}
			try {
				return Status.getStatusByName(status);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		
		static KindOfDelivery validateKindOfDelivery(String kindOfDelivery) {
			if (!ValidationParamUtil.notEmpty(kindOfDelivery)) {
				return null;
			}
			try {
				return KindOfDelivery.valueOf(kindOfDelivery);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		
		static KindOfPayment validateKindOfPayment(String kindOfPayment) {
			if (!ValidationParamUtil.notEmpty(kindOfPayment)) {
				return null;
			}
			try {
				return KindOfPayment.valueOf(kindOfPayment);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		
		static LocalDate validateDateOfDelivery(String dateOfDelivery){
			if (!ValidationParamUtil.notEmpty(dateOfDelivery)) {
				return null;
			}
			try{ 
				return LocalDate.parse(dateOfDelivery);
			}catch(DateTimeParseException e){
				return null;
			}
			
		}
	
		static boolean validateAddress(String address){
			if (!ValidationParamUtil.notEmpty(address)) {
				return false;
			}
			return ValidationParamUtil.checkField(ADDRESS_PATTERN_RU, address) ||
					ValidationParamUtil.checkField(ADDRESS_PATTERN_EN,address);
		}
	}
}
