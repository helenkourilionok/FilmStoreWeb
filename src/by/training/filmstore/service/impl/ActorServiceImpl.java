package by.training.filmstore.service.impl;

import java.util.List;

import by.training.filmstore.dao.ActorDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.service.ActorService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectActorParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidActorOperException;

public class ActorServiceImpl implements ActorService {

	@Override
	public List<Actor> findAllActors() throws FilmStoreServiceException{
		List<Actor> listActor = null;
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		ActorDAO actorDAO = filmStoreDAOFactory.getActorDAO();
		
		try {

			listActor = actorDAO.findAll();
			
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
		return listActor;
	}

	@Override
	public void create(String fio) throws FilmStoreServiceException, FilmStoreServiceInvalidActorOperException,
			FilmStoreServiceIncorrectActorParamException {
		if(!Validation.validateCharacterField(fio)){
			throw new FilmStoreServiceIncorrectActorParamException("Incorrect actor last name/name!");
		}
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		ActorDAO actorDAO = filmStoreDAOFactory.getActorDAO();
		
		Actor actor = new Actor();
		actor.setFio(fio);
		
		try {
			actorDAO.create(actor);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		} catch (FilmStoreDAOInvalidOperationException e) {
			throw new FilmStoreServiceInvalidActorOperException(e);
		}
	}

	static class Validation {

		private static final String LETTERS_PATTERN = "^\\A[Р-пр-џ\\-\\s]\\P{Alpha}{4,45}$";

		static boolean validateCharacterField(String value) {
			if (!ValidationParamUtil.notEmpty(value)) {
				return false;
			}
			return ValidationParamUtil.checkField(LETTERS_PATTERN, value);
		}

	}

}
