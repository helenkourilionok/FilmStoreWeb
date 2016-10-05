package by.training.filmstore.service.impl;

import java.util.List;

import by.training.filmstore.dao.FilmDirectorDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.service.FilmDirectorService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmDirParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmDirOperException;

public class FilmDirectorServiceImpl implements FilmDirectorService {

	@Override
	public List<FilmDirector> findAllFilmDirector() throws FilmStoreServiceException {
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDirectorDAO filmDirectorDAO = filmStoreDAOFactory.getFilmDirectorDAO();
		
		List<FilmDirector> listFilmDir = null;
		
		try {
			listFilmDir = filmDirectorDAO.findAll();
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
		return listFilmDir;
	}

	@Override
	public void create(String fio) throws FilmStoreServiceException, FilmStoreServiceIncorrectFilmDirParamException,
			FilmStoreServiceInvalidFilmDirOperException {
		if(!Validation.validateCharacterField(fio)){
			throw new FilmStoreServiceIncorrectFilmDirParamException("Incorrect film director last name/name!");
		}
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDirectorDAO filmDirectorDAO = filmStoreDAOFactory.getFilmDirectorDAO();
		
		FilmDirector filmDirector = new FilmDirector();
		filmDirector.setFio(fio);
		
		try {
			filmDirectorDAO.create(filmDirector);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		} catch (FilmStoreDAOInvalidOperationException e) {
			throw new FilmStoreServiceInvalidFilmDirOperException(e);
		}
		
	}

	static class Validation {

		private static final String LETTERS_PATTERN = "^\\A[Р-пр-џ\\-\\s]\\P{Alpha}{4,70}$";

		static boolean validateCharacterField(String value) {
			if (!ValidationParamUtil.notEmpty(value)) {
				return false;
			}
			return ValidationParamUtil.checkField(LETTERS_PATTERN, value);
		}

	}
}
