package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Actor;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListFilmNotFoundException;

public interface FilmService {
	Film create(String name,	String genre,String country,
			String yearOfRelease,String quality,
			String filmDirId,String description,
			String price,String countFilms,String image,List<Actor> listActors) 
							throws FilmStoreServiceException,
							FilmStoreServiceIncorrectFilmParamException,
							FilmStoreServiceInvalidFilmOperException;
	void update(String filmId,String name,	String genre,String country,
			String yearOfRelease,String quality,
			String filmDirId,String description,
			String price,String countFilms,String image) 
					throws FilmStoreServiceIncorrectFilmParamException,
					FilmStoreServiceInvalidFilmOperException,
							FilmStoreServiceException;
	void update(String filmId,String name,	String genre,String country,
			String yearOfRelease,String quality,
			String filmDirId,String description,
			String price,String countFilms,String image,List<Actor> listOldActor,List<Actor> listNewActor) 
					throws FilmStoreServiceIncorrectFilmParamException,
					FilmStoreServiceInvalidFilmOperException,
							FilmStoreServiceException;
	void delete(short id) throws FilmStoreServiceException,
	FilmStoreServiceInvalidFilmOperException,
	FilmStoreServiceIncorrectFilmParamException;
	Film find(String id, boolean lazyInit) throws FilmStoreServiceException, 
									FilmStoreServiceIncorrectFilmParamException,
									FilmStoreServiceFilmNotFoundException;
	List<Film> findFilmByName(String name) throws FilmStoreServiceException,
											FilmStoreServiceIncorrectFilmParamException,
											FilmStoreServiceListFilmNotFoundException;
	List<Film> findAllFilms(boolean lazyInit,int offset,int recordsPerPage,List<Integer> countAllRecords) 
											throws FilmStoreServiceException,
											FilmStoreServiceIncorrectFilmParamException,
											FilmStoreServiceListFilmNotFoundException;
}
