package by.training.filmstore.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

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
	
	@Override
	public void update(String userEmail, String commonPrice, String status, String kindOfDelivery, String kindOfPayment,
			String dateOfDelivery, String dateOfOrder, String address) throws FilmStoreServiceException, FilmStoreServiceIncorrectOrderParamException,
			FilmStoreServiceInvalidOrderOperException {
		
		LocalDate _dateOfDelivery = Validation.validateDate(dateOfOrder); 
		if(_dateOfDelivery == null){
			throw new FilmStoreServiceIncorrectOrderParamException("Incorrect date of order!");
		}
		
		Order order = validateOrder(userEmail, commonPrice, status, kindOfDelivery, kindOfPayment,
				  dateOfDelivery, address);
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		OrderDAO orderDAO = filmStoreDAOFactory.getOrderDAO();
		
		try {
			if(!orderDAO.update(order)){
				throw new FilmStoreServiceInvalidOrderOperException("Operation failed!Can't update film!");
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
	}

	@Override
	public Order find(String id) throws FilmStoreServiceException, FilmStoreServiceIncorrectOrderParamException {
		
		int _id = ValidationParamUtil.validateNumber(id);
		if(_id == -1){
			throw new FilmStoreServiceIncorrectOrderParamException("Incorrect order id!");
		}
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		OrderDAO orderDAO = filmStoreDAOFactory.getOrderDAO();
		
		Order order = null;
		
		try {
			order = orderDAO.find(_id);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
		return order;
	}
	
	@Override
	public List<Order> findOrderByUserEmailAndStatus(String userEmail,String status)
			throws FilmStoreServiceException, FilmStoreServiceIncorrectOrderParamException {
		int permissibleEmailLength = 40;
		if(!ValidationParamUtil.validateEmail(userEmail, permissibleEmailLength)){
			throw new FilmStoreServiceIncorrectOrderParamException("Incorrect user email!");
		}
		Status _status = Validation.validateStatus(status);
		if(_status == null){
			throw new FilmStoreServiceIncorrectOrderParamException("Incorrect status of order!");
		}
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		OrderDAO orderDAO = filmStoreDAOFactory.getOrderDAO();
		
		List<Order> listOrder = null;
		
		try {
			listOrder = orderDAO.findOrderByUserEmailAndStatus(userEmail,status);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
		return listOrder;
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
		LocalDate dateOfDel = Validation.validateDate(dateOfDelivery);
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
		private final static String ADDRESS_PATTERN_RU = "^\\A[Р-пр-џ0-9\\s\\.\\,\\-]\\P{Alpha}{7,40}$";
		private final static String DATE_PATTERN = "dd/MM/uuuu";
		
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
		
		static LocalDate validateDate(String dateOfDelivery){
			if (!ValidationParamUtil.notEmpty(dateOfDelivery)) {
				return null;
			}
			try{ 
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
				return LocalDate.parse(dateOfDelivery,dateTimeFormatter);
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
