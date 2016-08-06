package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Actor;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public interface ActorService {
	List<Actor> findAllActors() throws FilmStoreServiceException;
} 
