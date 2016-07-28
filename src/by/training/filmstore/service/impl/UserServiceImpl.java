package by.training.filmstore.service.impl;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.UserDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Role;
import by.training.filmstore.entity.User;
import by.training.filmstore.service.UserService;
import by.training.filmstore.service.exception.FilmStoreServiceAuthException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceParamIncorrectException;

public class UserServiceImpl implements UserService {

	@Override
	public User authorisation(String email, String password)
			throws FilmStoreServiceException, FilmStoreServiceAuthException {

		if (!Validation.notEmpty(email) && !Validation.notEmpty(password)) {
			throw new FilmStoreServiceAuthException("Wrong parameters!");
		}

		FilmStoreDAOFactory filmStoreDAOFactoryImpl = FilmStoreDAOFactory.getDAOFactory();
		UserDAO userDAO = filmStoreDAOFactoryImpl.getUserDao();
		User user = null;

		try {
			user = userDAO.find(email, password);
			if (user == null) {
				throw new FilmStoreServiceAuthException("User with this email and password doesn't exist!");
			}

		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}

		return user;
	}

	static class Validation {

		/*
		 * private static final String EMAIL_PATTERN =
		 * "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
		 * "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		 */
		//необходимо добавить возможность ввода русских букв
		private static final String EMAIL_PATTERN = "^[A-Za-z0-9\\-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,4}$";
		private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9#@%=\\-\\>\\<\\?\\.\\!\\(\\)\\+\\|\\*]{8,40}$";
		private static final String LETTERS15_PATTERN = "^[a-zA-Z]{2,15}$";
		private static final String LETTERS10_PATTERN = "^[a-zA-Z]{2,10}$";
		private static final String PHONE_PATTERN = "^\\+375\\-([0-9]{2})\\-([0-9]{3})\\-([0-9]{2})\\-([0-9]{2})$";

		private static boolean checkLength(String value, int length) {
			if (value.length() > length) {
				return false;
			}
			return true;
		}

		static boolean notEmpty(String value) {
			if (value == null || value.isEmpty()) {
				return false;
			}

			return true;
		}

		static boolean checkField(String patternStr, String field) {
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(field);

			return matcher.matches();
		}

		static boolean validateEmail(String email, int length) {
			if (!notEmpty(email) || !checkLength(email, length)) {
				return false;
			}

			return checkField(EMAIL_PATTERN, email);
		}

		static boolean validatePassword(String password, String copyPass) {
			if (!notEmpty(password) || !notEmpty(copyPass)) {
				return false;
			}

			if (!password.equals(copyPass)) {
				return false;
			}

			return checkField(PASSWORD_PATTERN, password);
		}

		static boolean validateCharacterField(String value, boolean name) {
			if (name) {
				return checkField(LETTERS10_PATTERN, value);
			}
			return checkField(LETTERS15_PATTERN, value);
		}

		static String validatePhone(String phone) {
			String phoneDB = null;
			if (!notEmpty(phone)) {
				return phoneDB;
			}

			Pattern pattern = Pattern.compile(PHONE_PATTERN);
			Matcher matcher = pattern.matcher(phone);

			if (matcher.find()) {
				phoneDB = "+375"+matcher.group(1)+matcher.group(2)+matcher.group(3)+matcher.group(4);
			}
			return phoneDB;
		}

		static BigDecimal validateBalance(String balance) {
			if (!notEmpty(balance)) {
				return null;
			}
			try {
				return new BigDecimal(balance.replaceAll(" ", ""));
			} catch (NumberFormatException e) {
				return null;
			}
		}

	}

	@Override
	public User create(String email, String password, String copyPass, String lastName, String firstName,
			String patronimic, String mobilePhone, String balance) throws FilmStoreServiceException, FilmStoreServiceParamIncorrectException {
		int permissibleEmailLength = 40;
		byte discount = 0;
		if(!Validation.validateEmail(email, permissibleEmailLength)){
			throw new FilmStoreServiceParamIncorrectException("Incorrect email!");
		}
		if(!Validation.validatePassword(password, copyPass)){
			throw new FilmStoreServiceParamIncorrectException("Incorrect password!");
		}
		if(!Validation.validateCharacterField(lastName, false)){
			throw new FilmStoreServiceParamIncorrectException("Incorrect last name!");
		}
		if(!Validation.validateCharacterField(firstName, true)){
			throw new FilmStoreServiceParamIncorrectException("Incorrect first name!");
		}
		if(!Validation.validateCharacterField(patronimic, false)){
			throw new FilmStoreServiceParamIncorrectException("Incorrect patronimic!");
		}
		String phone = Validation.validatePhone(mobilePhone);
		if(phone == null){
			throw new FilmStoreServiceParamIncorrectException("Incorrect phone!");
		}
		BigDecimal balanceBD =  Validation.validateBalance(balance);
		if(balanceBD == null){
			throw new FilmStoreServiceParamIncorrectException("Incorrect balance!");
		}
		User user = new User(email,password,Role.ROLE_USER,lastName,
							firstName,patronimic,phone,balanceBD,discount);
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		UserDAO userDAO = filmStoreDAOFactory.getUserDao();
		try {
			userDAO.create(user);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		return user;
	}
}
