package by.training.filmstore.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import by.training.filmstore.dao.CommentDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Comment;
import by.training.filmstore.entity.CommentPK;
import by.training.filmstore.service.CommentService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectCommentParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidCommentOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListCommentNotFoundException;

public class CommentServiceImpl implements CommentService {
	
	@Override
	public List<Comment> findCommentByIdFilm(String filmId)
			throws FilmStoreServiceException, 
			FilmStoreServiceIncorrectCommentParamException,
			FilmStoreServiceListCommentNotFoundException{
		if(!ValidationParamUtil.notEmpty(filmId)){
			throw new FilmStoreServiceIncorrectCommentParamException("Incorrect film id!Comments weren't found!");
		}
		short numFilmId = (short)ValidationParamUtil.validateNumber(filmId);
		if(numFilmId == -1){
			throw new FilmStoreServiceIncorrectCommentParamException("Incorrect film id!Comments weren't found!");
		}
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		CommentDAO commentDAO = filmStoreDAOFactory.getCommentDAO();
		List<Comment> listComment = null;
		
		try {
			listComment = commentDAO.findCommentByIdFilm(numFilmId);
			if(listComment.isEmpty()){
				throw new FilmStoreServiceListCommentNotFoundException("None comment wasn't found!");
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
		return listComment;
	}

	@Override
	public void create(String userEmail, String filmId, String content)
			throws FilmStoreServiceException, 
			FilmStoreServiceInvalidCommentOperException, 
			FilmStoreServiceIncorrectCommentParamException {
		
		int permissibleEmailLength = 40;
		if(!ValidationParamUtil.validateEmail(userEmail, permissibleEmailLength)){
			throw new FilmStoreServiceIncorrectCommentParamException("Incorrect user email!");
		}
		System.out.println(filmId);
		if(!ValidationParamUtil.notEmpty(filmId)){
			throw new FilmStoreServiceIncorrectCommentParamException("Incorrect film id!");
		}
		short numFilmId = (short)ValidationParamUtil.validateNumber(filmId);
		if(numFilmId == -1){
			throw new FilmStoreServiceIncorrectCommentParamException("Incorrect film id!");
		}
		System.out.println("Content"+content);
		if(!Validation.validateCharacterField(content)){
			throw new FilmStoreServiceIncorrectCommentParamException("Incorrect content!");
		}
		
		boolean succes = false;
		Timestamp dateComment = new Timestamp(new Date().getTime());
		CommentPK commentPK  = new CommentPK(userEmail,numFilmId);
		Comment entity = new Comment(commentPK,content,dateComment);
		
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		CommentDAO commentDAO = filmStoreDAOFactory.getCommentDAO(); 
		
		try {
			succes = commentDAO.create(entity);
			if(!succes){
				throw new FilmStoreServiceInvalidCommentOperException("Can't create comment!");
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
	}
	
	static class Validation{
		private final static String LETTERS_PATTERN_EN = "^[a-zA-Z\\-\\,\\)\\:\\(\\s]{8,300}$";
		private final static String LETTERS_PATTERN_RU = "^\\A[Р-пр-џ\\-\\,\\)\\:\\(\\s]\\P{Alpha}{8,300}$";
		
		static boolean validateCharacterField(String value) {
			if(!ValidationParamUtil.notEmpty(value)){
				return false;
			}
			return ValidationParamUtil.checkField(LETTERS_PATTERN_EN, value) | 
					ValidationParamUtil.checkField(LETTERS_PATTERN_RU, value);
			
		}
	}
}
