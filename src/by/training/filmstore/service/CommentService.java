package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Comment;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListCommentNotFoundException;

public interface CommentService {
	void create(String userEmail, String filmId, String content)
			throws FilmStoreServiceException,
			FilmStoreServiceIncorrectParamException,
			FilmStoreServiceInvalidOperException;

	List<Comment> findCommentByIdFilm(String filmId)
			throws FilmStoreServiceException, 
			FilmStoreServiceIncorrectParamException,
			FilmStoreServiceListCommentNotFoundException;
}
