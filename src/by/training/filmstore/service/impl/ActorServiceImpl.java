package by.training.filmstore.service.impl;

import java.util.List;

import by.training.filmstore.dao.ActorDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.service.ActorService;
import by.training.filmstore.service.exception.FilmStoreServiceException;

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

}
