package by.training.filmstore.service.impl;

import java.util.List;

import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public class FilmServiceImpl implements FilmService {

	@Override
	public List<Film> findAllFilms() throws FilmStoreServiceException {
		// Validation
		List<Film> listFilm = null;
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();

		try {
			listFilm = filmDAO.findAll();
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}

		return listFilm;
	}

}
