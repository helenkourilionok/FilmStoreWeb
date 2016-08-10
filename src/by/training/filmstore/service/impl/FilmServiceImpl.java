package by.training.filmstore.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.entity.Quality;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListFilmNotFoundException;

public class FilmServiceImpl implements FilmService {

	@Override
	public List<Film> findAllFilms(boolean lazyInit,int offset,int recordsPerPage,
			List<Integer> countAllRecords)
			throws FilmStoreServiceException,
				FilmStoreServiceIncorrectParamException,
				FilmStoreServiceListFilmNotFoundException {

		if(offset<0){
			throw new FilmStoreServiceIncorrectParamException("Offset must be positive integer!");
		}
		if(!ValidationParamUtil.validateNumber(recordsPerPage)){
			throw new FilmStoreServiceIncorrectParamException("Records per page must be positive integer!");
		}
		if(countAllRecords == null){
			countAllRecords = new ArrayList<>();
		}
		
		List<Film> listFilm = null;

		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		
		try {
			listFilm = filmDAO.findAll(offset, recordsPerPage,countAllRecords);
			if (listFilm.isEmpty()) {
				throw new FilmStoreServiceListFilmNotFoundException("None film wasn't found!");
			}
			if (!lazyInit) {
				for (Film film : listFilm) {
					filmDAO.findActorFilmDirectorForFilm(film);
				}
			}

		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}

		return listFilm;
	}

	@Override
	public Film find(String id, boolean lazyInit) throws FilmStoreServiceException,
			FilmStoreServiceIncorrectParamException, FilmStoreServiceFilmNotFoundException {

		if (!ValidationParamUtil.notEmpty(id)) {
			throw new FilmStoreServiceIncorrectParamException("Incorrect film id!");
		}
		short filmId = Short.parseShort(id);
		if (!ValidationParamUtil.validateNumber((int)filmId)) {
			throw new FilmStoreServiceIncorrectParamException("Incorrect film id!");
		}

		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();

		Film film = null;

		try {
			film = filmDAO.find(filmId);
			if (film == null) {
				throw new FilmStoreServiceFilmNotFoundException("Film not found!");
			}
			if (!lazyInit) {
				filmDAO.findActorFilmDirectorForFilm(film);
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}

		return film;
	}

	@Override
	public Film create(String name, String genre, String country, String yearOfRelease, String quality,
			String filmDirId, String description, String price, String countFilms, String image)
			throws FilmStoreServiceException, FilmStoreServiceIncorrectParamException,
			FilmStoreServiceInvalidOperException {
		int permissibleNameLength = 50;
		int permissibleDescriptionLength = 500;
		short yearOfRel = 0;
		short countFilm = 0;
		short filmDir = 0;
		Quality filmQuality = null;
		BigDecimal filmPrice = null;
		if (!ValidationParamUtil.checkLength(name, permissibleNameLength)) {
			throw new FilmStoreServiceIncorrectParamException("Film name is wrong length");
		}
		if (!Validation.validateCharacterField(genre)) {
			System.out.println(genre);
			throw new FilmStoreServiceIncorrectParamException("Invalid genre of film!");
		}
		if (!Validation.validateCharacterField(country)) {
			throw new FilmStoreServiceIncorrectParamException("Country must contains just characters!");
		}
		yearOfRel = Validation.varalidateNumber(yearOfRelease);
		if (yearOfRel == -1) {
			throw new FilmStoreServiceIncorrectParamException("Incorrect year of release!");
		}
		filmQuality = Validation.validateQuality(quality);
		if (filmQuality == null) {
			throw new FilmStoreServiceIncorrectParamException("Invalid film quality!");
		}
		filmDir = Validation.varalidateNumber(filmDirId);
		if (filmDir == -1) {
			throw new FilmStoreServiceIncorrectParamException("Incorrect film director id!");
		}
		if (!ValidationParamUtil.checkLength(description, permissibleDescriptionLength)) {
			throw new FilmStoreServiceIncorrectParamException("Description is wrong length!");
		}
		filmPrice = Validation.validatePrice(price);
		if (filmPrice == null) {
			throw new FilmStoreServiceIncorrectParamException("Incorrect price of film!");
		}
		countFilm = Validation.varalidateNumber(countFilms);
		if (countFilm == -1) {
			throw new FilmStoreServiceIncorrectParamException("Incorrect count films!");
		}
		if (!Validation.validateImage(image)) {
			throw new FilmStoreServiceIncorrectParamException("Incorrect image name!");
		}

		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();

		Film film = null;
		FilmDirector filmDirector = new FilmDirector();
		filmDirector.setId(filmDir);

		try {

			film = new Film(name, genre, country, yearOfRel, filmQuality, description, filmPrice, countFilm, image);
			film.setFilmDirector(filmDirector);
			if (!filmDAO.create(film)) {
				throw new FilmStoreServiceInvalidOperException("Operation failed!Film isn't created!");
			}

		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}

		return film;
	}

	@Override
	public void createFilmActor(Film entity,List<Short> idActors) throws FilmStoreServiceException, FilmStoreServiceIncorrectParamException,
			FilmStoreServiceInvalidOperException {

		if (entity == null) {
			throw new FilmStoreServiceIncorrectParamException("Film is null");
		}
		if ((idActors == null) || (idActors.isEmpty())) {
			throw new FilmStoreServiceIncorrectParamException("Film hasn't any actors!");
		}

		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		try {
			if (!filmDAO.createFilmActor(entity,idActors)) {
				throw new FilmStoreServiceInvalidOperException("Can't fill film_actor table!");
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
	}
	
	static class Validation {
		private final static String LETTERS_PATTERN_RU = "^\\A[Р-пр-џ\\,]\\P{Alpha}+$";
		private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

		static boolean validateCharacterField(String value) {
			if (!ValidationParamUtil.notEmpty(value)) {
				return false;
			}
			return ValidationParamUtil.checkField(LETTERS_PATTERN_RU, value);
		}

		static short varalidateNumber(String number) {
			try {
				return Short.parseShort(number);
			} catch (NumberFormatException e) {
				return -1;
			}
		}

		static Quality validateQuality(String quality) {
			if (!ValidationParamUtil.notEmpty(quality)) {
				return null;
			}
			try {
				return Quality.getQualityByName(quality);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}

		static BigDecimal validatePrice(String balance) {
			if (!ValidationParamUtil.notEmpty(balance)) {
				return null;
			}
			try {
				return new BigDecimal(balance.replaceAll(" ", ""));
			} catch (NumberFormatException e) {
				return null;
			}
		}

		static boolean validateImage(String image) {
			return ValidationParamUtil.checkField(IMAGE_PATTERN, image);
		}
	}

}
