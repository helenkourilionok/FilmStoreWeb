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
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectUserParamException;

public class UserServiceImpl implements UserService {

	@Override
	public User authorisation(String email, String password)
			throws FilmStoreServiceException, FilmStoreServiceAuthException {

		if (!ValidationParamUtil.notEmpty(email) && !ValidationParamUtil.notEmpty(password)) {
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

	@Override
	public User create(String email, String password, String copyPass, String lastName, String firstName,
			String patronimic, String mobilePhone, String balance)
			throws FilmStoreServiceException, FilmStoreServiceIncorrectUserParamException {
		int permissibleEmailLength = 40;
		byte discount = 0;
		if (!ValidationParamUtil.validateEmail(email, permissibleEmailLength)) {
			throw new FilmStoreServiceIncorrectUserParamException("Incorrect email!");
		}
		if (!Validation.validatePassword(password, copyPass)) {
			throw new FilmStoreServiceIncorrectUserParamException("Incorrect password!");
		}
		if (!Validation.validateCharacterField(lastName, false)) {
			throw new FilmStoreServiceIncorrectUserParamException("Incorrect last name!");
		}
		if (!Validation.validateCharacterField(firstName, true)) {
			throw new FilmStoreServiceIncorrectUserParamException("Incorrect first name!");
		}
		if (!Validation.validateCharacterField(patronimic, false)) {
			throw new FilmStoreServiceIncorrectUserParamException("Incorrect patronimic!");
		}
		String phone = Validation.validatePhone(mobilePhone);
		if (phone == null) {
			throw new FilmStoreServiceIncorrectUserParamException("Incorrect phone!");
		}
		BigDecimal balanceBD = Validation.validateBalance(balance);
		if (balanceBD == null) {
			throw new FilmStoreServiceIncorrectUserParamException("Incorrect balance!");
		}
		User user = new User(email, password, Role.ROLE_USER, lastName, firstName, patronimic, phone, balanceBD,
				discount);
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		UserDAO userDAO = filmStoreDAOFactory.getUserDao();
		try {
			userDAO.create(user);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		return user;
	}

	static class Validation {

		private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9#@%=\\-\\>\\<\\?\\.\\!\\(\\)\\+\\|\\*]{8,40}$";
		private static final String LETTERS15_PATTERN_EN = "^[a-zA-Z\\-]{2,15}$";
		private static final String LETTERS15_PATTERN_RU = "^\\A[Р-пр-џ\\-]\\P{Alpha}{2,15}$";
		private static final String LETTERS10_PATTERN_EN = "^[a-zA-Z\\-]{2,10}$";
		private static final String LETTERS10_PATTERN_RU = "^\\A[Р-пр-џ\\-]\\P{Alpha}{2,10}$";
		private static final String PHONE_PATTERN = "^\\+375\\-([0-9]{2})\\-([0-9]{3})\\-([0-9]{2})\\-([0-9]{2})$";

		static boolean validatePassword(String password, String copyPass) {
			if (!ValidationParamUtil.notEmpty(password) || !ValidationParamUtil.notEmpty(copyPass)) {
				return false;
			}

			if (!password.equals(copyPass)) {
				return false;
			}

			return ValidationParamUtil.checkField(PASSWORD_PATTERN, password);
		}

		static boolean validateCharacterField(String value, boolean name) {
			if (name) {
				return ValidationParamUtil.checkField(LETTERS10_PATTERN_EN, value) || 
						ValidationParamUtil.checkField(LETTERS10_PATTERN_RU, value);
			}
			return ValidationParamUtil.checkField(LETTERS15_PATTERN_EN, value) || 
					ValidationParamUtil.checkField(LETTERS15_PATTERN_RU, value);
		}

		static String validatePhone(String phone) {
			String phoneDB = null;
			if (!ValidationParamUtil.notEmpty(phone)) {
				return phoneDB;
			}

			Pattern pattern = Pattern.compile(PHONE_PATTERN);
			Matcher matcher = pattern.matcher(phone);

			if (matcher.find()) {
				phoneDB = "+375" + matcher.group(1) + matcher.group(2) + matcher.group(3) + matcher.group(4);
			}
			return phoneDB;
		}

		static BigDecimal validateBalance(String balance) {
			if (!ValidationParamUtil.notEmpty(balance)) {
				return null;
			}
			try {
				return new BigDecimal(balance.replaceAll(" ", ""));
			} catch (NumberFormatException e) {
				return null;
			}
		}

	}
}
