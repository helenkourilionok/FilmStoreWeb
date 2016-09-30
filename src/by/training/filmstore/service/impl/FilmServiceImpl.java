package by.training.filmstore.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.entity.Quality;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListFilmNotFoundException;

public class FilmServiceImpl implements FilmService {

	@Override
	public List<Film> findAllFilms(boolean lazyInit, int offset, int recordsPerPage, List<Integer> countAllRecords)
			throws FilmStoreServiceException, FilmStoreServiceIncorrectFilmParamException,
			FilmStoreServiceListFilmNotFoundException {

		if (offset < 0) {
			throw new FilmStoreServiceIncorrectFilmParamException("Offset must be positive integer!");
		}
		if (recordsPerPage < 1) {
			throw new FilmStoreServiceIncorrectFilmParamException("Records per page must be positive integer!");
		}
		if (countAllRecords == null) {
			countAllRecords = new ArrayList<>();
		}

		List<Film> listFilm = null;

		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();

		try {
			listFilm = filmDAO.findAll(offset, recordsPerPage, countAllRecords);
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
			FilmStoreServiceIncorrectFilmParamException, FilmStoreServiceFilmNotFoundException {

		if (!ValidationParamUtil.notEmpty(id)) {
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect film id!");
		}
		short filmId = (short) ValidationParamUtil.validateNumber(id);
		if (filmId == -1) {
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect film id!");
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
			throws FilmStoreServiceException, FilmStoreServiceIncorrectFilmParamException,
			FilmStoreServiceInvalidFilmOperException {
		
		Film film = validateFilm(name, genre, country, yearOfRelease, quality, filmDirId, 
				description, price, countFilms, image);

		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
	
		try {

			filmDAO.create(film);

		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		} catch (FilmStoreDAOInvalidOperationException e) {
			throw new FilmStoreServiceInvalidFilmOperException(e);
		}

		return film;
	}

	@Override
	public void createFilmActor(short filmId, List<Short> idActors) throws FilmStoreServiceException,
			FilmStoreServiceIncorrectFilmParamException, FilmStoreServiceInvalidFilmOperException {
		if (filmId<=0) {
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect id film!");
		}
		if ((idActors == null) || (idActors.isEmpty())) {
			throw new FilmStoreServiceIncorrectFilmParamException("Film hasn't any actors!");
		}

		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		try {
			filmDAO.createFilmActor(filmId, idActors);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		} catch (FilmStoreDAOInvalidOperationException e) {
			throw new FilmStoreServiceInvalidFilmOperException(e);
		}
	}

	@Override
	public void update(String filmId, String name, String genre, String country, String yearOfRelease,
			String quality, String filmDirId, String description, String price, String countFilms, String image)
			throws FilmStoreServiceIncorrectFilmParamException, FilmStoreServiceInvalidFilmOperException,
			FilmStoreServiceException {
		
		short id = Validation.validateNumber(filmId);
		
		if(id==-1){
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect film id!");
		}
		
		Film film = validateFilm(name, genre, country, yearOfRelease, quality, filmDirId, 
				description, price, countFilms, image);
		film.setId(id);
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		
		try{
			filmDAO.update(film);
		}catch(FilmStoreDAOException e){
			throw new FilmStoreServiceException(e);
		} catch (FilmStoreDAOInvalidOperationException e) {
			throw new FilmStoreServiceInvalidFilmOperException(e);
		}
	}

	@Override
	public void updateFilmActor(short filmId, List<Short> idNewActors,List<Short> idOldActors) throws FilmStoreServiceException,
			FilmStoreServiceIncorrectFilmParamException, FilmStoreServiceInvalidFilmOperException {
		if (filmId<=0) {
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect id film!");
		}
		if ((idNewActors == null) || (idNewActors.isEmpty())) {
			throw new FilmStoreServiceIncorrectFilmParamException("List new actors is null or empty!");
		}
		if((idOldActors==null)||(idOldActors.isEmpty())){
			throw new FilmStoreServiceIncorrectFilmParamException("List old actors is null or empty!");
		}
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		try {
			filmDAO.updateFilmActor(filmId, idNewActors,idOldActors);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		} catch (FilmStoreDAOInvalidOperationException e) {
			throw new FilmStoreServiceInvalidFilmOperException(e);
		}
	}
	
	@Override
	public void delete(short id) throws FilmStoreServiceException,
	FilmStoreServiceInvalidFilmOperException,
	FilmStoreServiceIncorrectFilmParamException{
		if(id<=0){
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect film id!");
		}
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		
		try {
			filmDAO.delete(id);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		} catch (FilmStoreDAOInvalidOperationException e) {
			throw new FilmStoreServiceInvalidFilmOperException(e);
		}
	}

	@Override
	public void deleteFilmActor(short filmId,List<Short> idActors) throws FilmStoreServiceException,												FilmStoreServiceIncorrectFilmParamException,
		FilmStoreServiceIncorrectFilmParamException,FilmStoreServiceInvalidFilmOperException {
		if(filmId<=0){
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect film id!");
		}
		if((idActors==null)||(idActors.isEmpty())){
			throw new FilmStoreServiceIncorrectFilmParamException("List actors is empty!");
		}
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		
		try {
			filmDAO.deleteFilmActor(filmId,idActors);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		} catch (FilmStoreDAOInvalidOperationException e) {
			throw new FilmStoreServiceException(e);
		}
	}
	
	@Override
	public List<Film> findFilmByName(String name)
			throws FilmStoreServiceException, FilmStoreServiceIncorrectFilmParamException,
					FilmStoreServiceListFilmNotFoundException{
		int permissibleNameLength = 50;
		if(!ValidationParamUtil.checkLength(name, permissibleNameLength)){
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect film name!Check length!");
		}
		
		List<Film> listFilm = null;
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		
		try {
			listFilm = filmDAO.findFilmByName(name);
			if(listFilm.isEmpty()){
				throw new FilmStoreServiceListFilmNotFoundException("Can't find films by this name!");
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
		return listFilm;
	}
	
	private Film validateFilm(String name, String genre, String country, String yearOfRelease, String quality,
			String filmDirId, String description, String price, String countFilms, String image) 
					throws FilmStoreServiceIncorrectFilmParamException{
		int permissibleNameLength = 50;
		int permissibleDescriptionLength = 500;
		short yearOfRel = 0;
		short countFilm = 0;
		short filmDir = 0;
		Quality filmQuality = null;
		BigDecimal filmPrice = null;
		if (!ValidationParamUtil.checkLength(name, permissibleNameLength)) {
			throw new FilmStoreServiceIncorrectFilmParamException("Film name is wrong length");
		}
		if (!Validation.validateCharacterField(genre)) {
			throw new FilmStoreServiceIncorrectFilmParamException("Invalid genre of film!");
		}
		if (!Validation.validateCharacterField(country)) {
			throw new FilmStoreServiceIncorrectFilmParamException("Country must contains just characters!");
		}
		yearOfRel = Validation.validateNumber(yearOfRelease);
		if (yearOfRel == -1) {
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect year of release!");
		}
		filmQuality = Validation.validateQuality(quality);
		if (filmQuality == null) {
			throw new FilmStoreServiceIncorrectFilmParamException("Invalid film quality!");
		}
		filmDir = Validation.validateNumber(filmDirId);
		if (filmDir == -1) {
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect film director id!");
		}
		if (!ValidationParamUtil.checkLength(description, permissibleDescriptionLength)) {
			throw new FilmStoreServiceIncorrectFilmParamException("Description is wrong length!");
		}
		filmPrice = Validation.validatePrice(price);
		if (filmPrice == null) {
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect price of film!");
		}
		countFilm = Validation.validateNumber(countFilms);
		if (countFilm == -1) {
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect count films!");
		}
		if (!Validation.validateImage(image)) {
			throw new FilmStoreServiceIncorrectFilmParamException("Incorrect image name!");
		}
		FilmDirector filmDirector = new FilmDirector();
		filmDirector.setId(filmDir);
		Film film = new Film(name, genre, country, yearOfRel, filmQuality, description, filmPrice, countFilm, image);
		film.setFilmDirector(filmDirector);
		return film;
	}
	
	static class Validation {
		private final static String LETTERS_PATTERN_RU = "^\\A[Р-пр-џ\\,]\\P{Alpha}+$";
		private final static String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

		static boolean validateCharacterField(String value) {
			if (!ValidationParamUtil.notEmpty(value)) {
				return false;
			}
			return ValidationParamUtil.checkField(LETTERS_PATTERN_RU, value);
		}

		static short validateNumber(String number) {
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
