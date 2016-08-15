package by.training.filmstore.service;

import java.util.List;

import by.training.filmstore.entity.Comment;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectCommentParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidCommentOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListCommentNotFoundException;

public interface CommentService {
	void create(String userEmail, String filmId, String content)
			throws FilmStoreServiceException,
			FilmStoreServiceIncorrectCommentParamException,
			FilmStoreServiceInvalidCommentOperException;

	List<Comment> findCommentByIdFilm(String filmId)
			throws FilmStoreServiceException, 
			FilmStoreServiceIncorrectCommentParamException,
			FilmStoreServiceListCommentNotFoundException;
}
