package by.training.filmstore.service.impl;

import java.util.List;

import by.training.filmstore.dao.FilmDirectorDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.service.FilmDirectorService;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public class FilmDirectorServiceImpl implements FilmDirectorService {

	@Override
	public List<FilmDirector> findAllFilmDirectors() throws FilmStoreServiceException {
		
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

}
