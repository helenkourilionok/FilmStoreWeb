package by.training.filmstore.service.impl;

import java.util.List;

import by.training.filmstore.command.util.ValidationParamUtil;
import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.Quality;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListFilmNotFoundException;

public class FilmServiceImpl implements FilmService {

	@Override
	public List<Film> findAllFilms(boolean lazyInit)
			throws FilmStoreServiceException, FilmStoreServiceListFilmNotFoundException {
		// Validation - boolean
		List<Film> listFilm = null;

		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();

		try {
			listFilm = filmDAO.findAll();
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
		if (!ValidationParamUtil.validateId(filmId)) {
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
	public boolean create(String name, String genre, String country, String yearOfRelease, Quality quality,
			String description, String price, String countFilms, String image)
			throws FilmStoreServiceException,
			FilmStoreServiceIncorrectParamException,
			FilmStoreServiceInvalidOperException {
		boolean succes = false;
		int permissibleNameLength = 50;
		if(!ValidationParamUtil.checkLength(name, permissibleNameLength)){
			throw new FilmStoreServiceIncorrectParamException("Film name is wrong length");
		}
		if(!Validation.validateCharacterField(genre)){
			throw new FilmStoreServiceIncorrectParamException("Invalid genre of film!");
		}
		if(!Validation.validateCharacterField(country)){
			throw new FilmStoreServiceIncorrectParamException("Country must contains just characters!");
		}
//		if(ValidationParamUtil.){
//			
//		} провалидировать и вернуть short 
		return succes;
	}

	static class Validation{
		private final static String LETTERS_PATTERN_RU = "^\\A[А-Яа-я\\,]\\P{Alpha}$";
		static boolean validateCharacterField(String value) {
			if(!ValidationParamUtil.notEmpty(value)){
				return false;
			}
			return ValidationParamUtil.checkField(LETTERS_PATTERN_RU, value);	
		}
	}
}
