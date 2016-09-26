package by.training.filmstore.dao;

import java.util.List;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.Comment;
import by.training.filmstore.entity.CommentPK;
/**
 * This interface extends {@link AbstractDAO}
 * and declares methods that inherent for {@link by.training.filmstore.entity.Comment} entity
 * 
 * @author Helena
 *
 *@see AbstractDAO
 *@see by.training.filmstore.entity.Comment
 *@see by.training.filmstore.entity.CommentPK
 */
public interface CommentDAO extends AbstractDAO<Comment, CommentPK>{
	/**
	 * Method permits to find list of comments
	 * using id(primary key) of film entity
	 * 
	 * @param filmId id of film entity
	 * 
	 * @return list of comments that associated with film
	 * entity(which has this filmId as primary key) 
	 * or empty list if film entity doesn't have any comments.
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.entity.Film#getId()
	 * @see by.training.filmstore.dao.exception.FilmStoreDAOException
	 */
	List<Comment> findCommentByIdFilm(short filmId) throws FilmStoreDAOException;
	/**
	 * Method permits to find list of comments that were made by 
	 * the user. The input parameter
	 * userEmail is necessary for find comments of concrete user.
	 * 
	 * @param userEmail element,that will be used to find list comments
	 * that belong to concrete user
	 * 
	 * @return list of comments or empty list if user with 
	 * that userEmail 
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.entity.User#getEmail()
	 * @see by.training.filmstore.dao.exception.FilmStoreDAOException
	 */
	List<Comment> findCommentByIdUser(String userEmail) throws FilmStoreDAOException;
}
