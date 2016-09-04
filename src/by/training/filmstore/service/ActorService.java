package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Actor;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectActorParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidActorOperException;

public interface ActorService {
	void create(String fio) throws FilmStoreServiceException,
	FilmStoreServiceInvalidActorOperException,
	FilmStoreServiceIncorrectActorParamException;
	List<Actor> findAllActors() throws FilmStoreServiceException;
} 
