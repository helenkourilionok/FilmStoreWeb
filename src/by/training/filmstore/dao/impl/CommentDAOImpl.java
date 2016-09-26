package by.training.filmstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.dao.CommentDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.dao.pool.PoolConnection;
import by.training.filmstore.dao.pool.PoolConnectionException;
import by.training.filmstore.entity.Comment;
import by.training.filmstore.entity.CommentPK;

public class CommentDAOImpl implements CommentDAO{


	private static final Logger logger = LogManager.getLogger(CommentDAOImpl.class);
	private static final String SQL_INSERT = 
			"insert into comment(comment.cm_email_user,comment.cm_film,"+
			 "comment.cm_content,comment.cm_date) values(?,?,?,?)";
	private static final String SQL_UPDATE = 
			"update comment set comment.cm_content = ?,comment.cm_date = ? "+
			" where comment.cm_film = ? and comment.cm_email_user = ? ";
	private static final String SQL_DELETE = 
			"delete from comment where comment.cm_email_user = ? and comment.cm_film = ?";
	private static final String SQL_FIND_ALL = 
			"select comment.cm_email_user,comment.cm_film,comment.cm_content,comment.cm_date "+ 
			"from comment";
	private static final String SQL_FIND_BY_ID = 
			"select comment.cm_email_user,comment.cm_film,comment.cm_content,comment.cm_date "+
			"from comment where comment.cm_film = ? and comment.cm_email_user = ?";
	private static final String SQL_FIND_BY_EMAIL_USER = 
			"select comment.cm_email_user,comment.cm_film,comment.cm_content,comment.cm_date "+
			"from comment where comment.cm_email_user = ?";
	private static final String SQL_FIND_BY_FILM_ID = 
			"select comment.cm_email_user,comment.cm_film,comment.cm_content,comment.cm_date "+ 
			"from comment where comment.cm_film = ?";
	
	@Override
	public void create(Comment entity) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		PoolConnection poolConnection = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try{
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			prepStatement = connection.prepareStatement(SQL_INSERT);
			prepStatement.setString(1, entity.getId().getUserEmail());
			prepStatement.setShort(2, entity.getId().getFilmId());
			prepStatement.setString(3, entity.getContent());
			prepStatement.setTimestamp(4, entity.getDate());
			int affectedRows = prepStatement.executeUpdate();
			if (affectedRows == 0) {
                throw new FilmStoreDAOInvalidOperationException("Operation failed!Can't create comment!");
            }
		}
		catch(PoolConnectionException|SQLException e){
			throw new FilmStoreDAOException(e);
		}
		finally {
			try {
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement",e);
			}
			try{
				poolConnection.putbackConnection(connection);
			}catch(SQLException e){
				logger.error("Error closing of Connection", e);
			}
		}
	}

	@Override
	public void update(Comment entity) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		PoolConnection poolConnection = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try{
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			prepStatement = connection.prepareStatement(SQL_UPDATE);
			prepStatement.setString(1, entity.getId().getUserEmail());
			prepStatement.setShort(2, entity.getId().getFilmId());
			prepStatement.setString(3, entity.getContent());
			int affectedRows = prepStatement.executeUpdate();
            if (affectedRows == 0) {
            	 throw new FilmStoreDAOInvalidOperationException("Operation failed!Can't update comment!");
            }
		}
		catch(PoolConnectionException|SQLException e){
			throw new FilmStoreDAOException(e);
		}
		finally {
			try {
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement");
			}
			try{
				poolConnection.putbackConnection(connection);
			}catch(SQLException e){
				logger.error("Error closing of Connection", e);
			}
		}
	}

	@Override
	public void delete(CommentPK id) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		PoolConnection poolConnection = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try{
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			prepStatement = connection.prepareStatement(SQL_DELETE);
			prepStatement.setString(1, id.getUserEmail());
			prepStatement.setShort(2, id.getFilmId());
			int affectedRows = prepStatement.executeUpdate();
            if (affectedRows == 0) {
            	 throw new FilmStoreDAOInvalidOperationException("Operation failed!Can't delete comment!");
            }
		}
		catch(PoolConnectionException|SQLException e){
			throw new FilmStoreDAOException(e);
		}
		finally {
			try {
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection",e);
			}
			try{
				poolConnection.putbackConnection(connection);
			}catch(SQLException e){
				logger.error("Error closing of Connection", e);
			}
		}
	}

	@Override
 	public Comment find(CommentPK id) throws FilmStoreDAOException {
		PoolConnection poolConnection = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		Comment comment = null;
		try
		{
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			prepStatement =  connection.prepareStatement(SQL_FIND_BY_ID);
			
			prepStatement.setShort(1, id.getFilmId());
			prepStatement.setString(2, id.getUserEmail());
			
			resultSet = prepStatement.executeQuery();
			
			if(resultSet.next())
			{
				comment = new Comment();
				fillComment(comment, resultSet);
			}
		}
		catch(PoolConnectionException|SQLException e)
		{
			throw new FilmStoreDAOException(e);
		}
		finally {
			try {
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement",e);
			}
			try{
				poolConnection.putbackConnection(connection);
			}catch(SQLException e){
				logger.error("Error closing of Connection", e);
			}
		}
		return comment;
	}

	@Override
	public List<Comment> findAll() throws FilmStoreDAOException {
		return findCommentByCriteria(0, FindCommentCriteria.FIND_ALL);
	}
	
	@Override
	public List<Comment> findCommentByIdFilm(short filmId) throws FilmStoreDAOException {
		return findCommentByCriteria(new Short(filmId), FindCommentCriteria.FIND_BY_FILM_ID);
	}

	@Override
	public List<Comment> findCommentByIdUser(String userEmail) throws FilmStoreDAOException {
		return findCommentByCriteria(userEmail, FindCommentCriteria.FIND_BY_USER_ID);
	}

	private <T> List<Comment> findCommentByCriteria(T parametr,FindCommentCriteria criteria ) throws FilmStoreDAOException{
		PoolConnection poolConnection = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		Comment comment = null;
		List<Comment> listComment = new ArrayList<Comment>();
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			switch(criteria)
			{
				case FIND_BY_FILM_ID:{
					prepStatement = connection.prepareStatement(SQL_FIND_BY_FILM_ID);
					prepStatement.setShort(1,(Short)parametr);
				}break;
				case FIND_BY_USER_ID:{
					prepStatement = connection.prepareStatement(SQL_FIND_BY_EMAIL_USER);
					prepStatement.setString(1,(String)parametr);
				}break;
				case FIND_ALL:{
					prepStatement = connection.prepareStatement(SQL_FIND_ALL);
				}break;
			}
			resultSet = prepStatement.executeQuery();
			while(resultSet.next()){
				comment = new Comment();
				fillComment(comment, resultSet);
				listComment.add(comment);
			}
		}
		catch(PoolConnectionException|SQLException e){
			throw new FilmStoreDAOException(e);
		}
		finally {
			try {
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement",e);
			}
			try{
				poolConnection.putbackConnection(connection);
			}catch(SQLException e){
				logger.error("Error closing of Connection", e);
			}
		}
		return listComment;
	}
	
	private void fillComment(Comment comment,ResultSet resultSet) throws SQLException{
		CommentPK commentPK = new CommentPK();
		commentPK.setUserEmail(resultSet.getString(ColumnName.TABLE_COMMENT_EMAIL_USER));
		commentPK.setFilmId(resultSet.getShort(ColumnName.TABLE_COMMENT_FILM_ID));
		comment.setId(commentPK);
		comment.setContent(resultSet.getString(ColumnName.TABLE_COMMENT_CONTENT));
		comment.setDate(resultSet.getTimestamp(ColumnName.TABLE_COMMENT_DATE));
	}
	
	private enum FindCommentCriteria {
		FIND_BY_USER_ID,FIND_BY_FILM_ID,FIND_ALL
	}
}
