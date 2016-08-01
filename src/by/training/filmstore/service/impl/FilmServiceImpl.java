package by.training.filmstore.service.impl;

import java.util.List;

import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceImageNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectIdException;

public class FilmServiceImpl implements FilmService {

	@Override
	public List<Film> findAllFilms() throws FilmStoreServiceException {
		// Validation
		List<Film> listFilm = null;
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();

		try {
			listFilm = filmDAO.findAll();
			for(Film film:listFilm){
				filmDAO.findActorFilmDirectorForFilm(film);
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}

		return listFilm;
	}

	@Override
	public byte[] findFilmImageByFilmId(Short id) throws FilmStoreServiceException, 
											  FilmStoreServiceIncorrectIdException, 
											  FilmStoreServiceImageNotFoundException {
		
		if (!validateId(id)) {
			throw new FilmStoreServiceIncorrectIdException("Incorrect film id!");
		}
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		byte[] image = null;
		
		try {
			image = filmDAO.findFilmImageByFilmId(id);
			if(image == null){
				throw new FilmStoreServiceImageNotFoundException("Image not found!");
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
		return image;
	}

	@Override
	public Film find(Short id) throws FilmStoreServiceException, FilmStoreServiceIncorrectIdException {
		
		if(!validateId(id)){
			throw new FilmStoreServiceIncorrectIdException("Incorrect film id!");
		}
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		
		Film film = null;
		
		try {			
			film = filmDAO.find(id);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
		return film;
	}
	
	private boolean validateId(Short id){
		if(id == null || id<=0){
			return false;
		}
		return true;
	}

}
