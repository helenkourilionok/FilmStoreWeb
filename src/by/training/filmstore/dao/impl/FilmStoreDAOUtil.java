package by.training.filmstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.pool.PoolConnection;
import by.training.filmstore.dao.pool.PoolConnectionException;

public final class FilmStoreDAOUtil {
	
	private static final Logger logger = LogManager.getLogger(FilmStoreDAOUtil.class);
	
	private static final String SQL_COUNT_PROCESSED_ROWS = "select found_rows()";
	
	public final static int getCountProcessedRows() throws FilmStoreDAOException{
		PoolConnection poolConnection = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null; 
		int countProcessedRows = 0;
		int columnIndex = 1;
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();

			prepStatement = connection.prepareStatement(SQL_COUNT_PROCESSED_ROWS);

			resultSet = prepStatement.executeQuery();
			
			if(resultSet.next()){
				countProcessedRows = resultSet.getInt(columnIndex);
			}
		} catch (SQLException | PoolConnectionException e) {
			logger.error("Error creating of PreparedStatement.Can't found count processed rows!", e);
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				poolConnection.putbackConnection(connection);
				resultSet.close();
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}
		return countProcessedRows;
	}

}
