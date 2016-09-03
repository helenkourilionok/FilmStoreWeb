package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.User;
import by.training.filmstore.service.exception.FilmStoreServiceAuthException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectUserParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidUserOperException;

public interface UserService {
	User authorisation(String email, String password) throws FilmStoreServiceException, FilmStoreServiceAuthException;
	User create(String email, String password, String copyPass,
			String lastName, String firstName,
			String patronimic, String mobilePhone, String balance) throws FilmStoreServiceException,
											FilmStoreServiceIncorrectUserParamException;
	User find(String id) throws FilmStoreServiceException,
			FilmStoreServiceIncorrectUserParamException;
	void makeDiscount(String sizeOfDiscount,String year,String month,
			String countOrders) throws FilmStoreServiceIncorrectUserParamException,
			FilmStoreServiceInvalidUserOperException,
			FilmStoreServiceException;
	List<User> findUserForMakeDiscount(String year, String month, String countOrders) 
			throws FilmStoreServiceException,FilmStoreServiceIncorrectUserParamException,
			FilmStoreServiceInvalidUserOperException;
}
