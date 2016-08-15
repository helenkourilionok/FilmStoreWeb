package by.training.filmstore.service;

import by.training.filmstore.entity.User;
import by.training.filmstore.service.exception.FilmStoreServiceAuthException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectUserParamException;

public interface UserService {
	User authorisation(String email, String password) throws FilmStoreServiceException, FilmStoreServiceAuthException;
	User create(String email, String password, String copyPass,
			String lastName, String firstName,
			String patronimic, String mobilePhone, String balance) throws FilmStoreServiceException,
											FilmStoreServiceIncorrectUserParamException;
}
