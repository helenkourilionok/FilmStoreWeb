package by.training.filmstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.pool.PoolConnection;
import by.training.filmstore.dao.pool.PoolConnectionException;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.entity.Quality;

public class FilmDAOImpl implements FilmDAO {

	private static final Logger logger = LogManager.getLogger(FilmDAOImpl.class);
	
	private static final String SQL_INSERT = "insert into film(film.fm_name,film.fm_genre,film.fm_country,film.fm_year_of_release,"
			+ "film.fm_quality,film.fm_film_director,film.fm_description,film.fm_price,"
			+ "film.fm_count_film,film.fm_image) " 
			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_INSERT_FILM_ACTOR = "INSERT INTO film_actor(film_actor.fm_id,film_actor.act_id) VALUES(?,?)";
	private static final String SQL_UPDATE = "update film  SET film.fm_name = ?,film.fm_genre = ?,"
			+ "film.fm_country = ?,film.fm_year_of_release = ?," 
			+ "film.fm_quality = ?,film.fm_film_director = ?,"
			+ "film.fm_description = ?,film.fm_price = ?,film.fm_count_film = ?,film.fm_image = ? "
			+ "where film.fm_uid = ?";
	private static final String SQL_UPDATE_FILM_ACTOR = 
			"update film_actor  SET film_actor.act_id = ? where film_actor.fm_id = ? and film_actor.act_id = ?";
	private static final String SQL_DELETE = "DELETE FROM film WHERE film.fm_uid = ?";
	private static final String SQL_DELETE_FILM_ACTOR = "DELETE FROM film_actor WHERE "
			+ "film_actor.fm_id = ? and film_actor.act_id=?";
	
	private static final String SQL_FIND_ALL = "select "+
			"film.fm_uid,film.fm_name,film.fm_genre,film.fm_country,"+
			"film.fm_year_of_release,film.fm_quality,"+
			"film.fm_film_director,film.fm_description,film.fm_price,"+
			"film.fm_count_film,film.fm_image from film";
	
	private static final String SQL_FIND_ALL_PAG = 
			"select sql_calc_found_rows "+
			"film.fm_uid,film.fm_name,film.fm_genre,film.fm_country,"+
			"film.fm_year_of_release,film.fm_quality,"+
			"film.fm_film_director,film.fm_description,film.fm_price,"+
			"film.fm_count_film,film.fm_image from film limit ?,?";
	private static final String SQL_COUNT_PROCESSED_ROWS = "select found_rows()";
	
	private static final String SQL_FIND_FILM_BY_ID = "SELECT film.fm_uid,film.fm_name,film.fm_genre,film.fm_country,film.fm_year_of_release,"
			+ "film.fm_quality,film.fm_film_director,film.fm_description,"
			+ "film.fm_price,film.fm_count_film,film.fm_image " + "FROM film where film.fm_uid = ?";
	private static final String SQL_FIND_BY_GENRE = "select film.fm_uid,film.fm_name,film.fm_genre,film.fm_country,film.fm_year_of_release,"
			+ "film.fm_quality,film.fm_film_director,film.fm_description,film.fm_price,"
			+ "film.fm_count_film,film.fm_image from film where find_in_set(?,film.fm_genre)";
	private static final String SQL_FIND_BY_NAME = "select film.fm_uid,film.fm_name,film.fm_genre,film.fm_country,film.fm_year_of_release,"
			+ "film.fm_quality,film.fm_film_director,film.fm_description,film.fm_price,"
			+ "film.fm_count_film,film.fm_image from film where lcase(film.fm_name) = lcase(?)";

	private static final String SQL_FIND_ACTOR_BY_FILM_ID = 
			"select film.fm_uid,film_actor.act_id,actor.act_fio "+
			" from film inner join film_actor on film.fm_uid = film_actor.fm_id inner "+
			" join actor on film_actor.act_id = actor.act_uid where film.fm_uid = ?";
	private static final String SQL_FIND_FILM_DIRECTOR_BY_FILM_ID = "SELECT film_director.fd_uid,film_director.fd_fio FROM film_director"
			+ " WHERE film_director.fd_uid = (SELECT film.fm_film_director " 
			+ " FROM film WHERE film.fm_uid = ?);";

	@Override
	public boolean create(Film entity) throws FilmStoreDAOException {
		return updateByCriteria(CommandDAO.INSERT, entity);
	}

	@Override
	public boolean update(Film entity) throws FilmStoreDAOException {
		return updateByCriteria(CommandDAO.UPDATE, entity);
	}

	@Override
	public boolean delete(Short id) throws FilmStoreDAOException {
		return updateByCriteria(CommandDAO.DELETE, id);
	}

	@Override
	public boolean createFilmActor(short filmId,List<Short> idActors) throws FilmStoreDAOException {
		Connection connection = null;
		PreparedStatement prepStatement = null;
		PoolConnection poolConnection = null;
		boolean success = false;
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			prepStatement = connection.prepareStatement(SQL_INSERT_FILM_ACTOR);

			fillBatchForExecute(filmId,idActors, prepStatement);

			int[] results = prepStatement.executeBatch();

			success = isBatchExecuteSuccessful(results);

		} catch (SQLException | PoolConnectionException e) {
			logger.error("Error creating of PreparedStatement.Can't write in film_actor table", e);
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				poolConnection.putbackConnection(connection);
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}
		return success;
	}

	@Override
	public boolean updateFilmActor(short filmId, List<Short> idNewActors,List<Short> idOldActors) throws FilmStoreDAOException {
		Connection connection = null;
		PreparedStatement prepStatement = null;
		PoolConnection poolConnection = null;
		boolean success = false;
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			prepStatement = connection.prepareStatement(SQL_UPDATE_FILM_ACTOR);

			fillBatchForExecute(filmId,idNewActors, idOldActors,prepStatement);

			int[] results = prepStatement.executeBatch();

			success = isBatchExecuteSuccessful(results);

		} catch (SQLException | PoolConnectionException e) {
			logger.error("Error creating of PreparedStatement.Can't update film_actor table", e);
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				poolConnection.putbackConnection(connection);
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}
		return success;
	}
	
	@Override
	public boolean deleteFilmActor(short filmId,List<Short> idActors) throws FilmStoreDAOException {
		Connection connection = null;
		PreparedStatement prepStatement = null;
		PoolConnection poolConnection = null;
		boolean success = false;
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			prepStatement = connection.prepareStatement(SQL_DELETE_FILM_ACTOR);

			fillBatchForExecute(filmId,idActors, prepStatement);

			int[] results = prepStatement.executeBatch();

			success = isBatchExecuteSuccessful(results);

		} catch (SQLException | PoolConnectionException e) {
			logger.error("Error creating of PreparedStatement.Can't fill film_actor table", e);
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				poolConnection.putbackConnection(connection);
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}
		return success;
	}
	
	@Override
	public boolean findActorFilmDirectorForFilm(Film film) throws FilmStoreDAOException {
		Connection connection = null;
		PreparedStatement prepStatementForActor = null;
		PreparedStatement prepStatementForFilmDirector = null;
		ResultSet resultSetActor = null;
		ResultSet resultSetFilmDir = null;
		PoolConnection poolConnection = null;
		boolean success = false;
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			
			prepStatementForActor = connection.prepareStatement(SQL_FIND_ACTOR_BY_FILM_ID);
			prepStatementForFilmDirector = connection.prepareStatement(SQL_FIND_FILM_DIRECTOR_BY_FILM_ID);
			
			prepStatementForActor.setShort(1, film.getId());
			prepStatementForFilmDirector.setShort(1, film.getId());

			resultSetActor = prepStatementForActor.executeQuery();
			resultSetFilmDir = prepStatementForFilmDirector.executeQuery();

			film.setActors(fillListActor(resultSetActor));
			film.setFilmDirector(fillFilmDirector(resultSetFilmDir));
		} catch (SQLException | PoolConnectionException e) {
			logger.error("Error creating of PreparedStatement.Can't fill film_actor table", e);
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				poolConnection.putbackConnection(connection);
				resultSetActor.close();
				resultSetFilmDir.close();
				prepStatementForActor.close();
				prepStatementForFilmDirector.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}
		return success;
	}

	@Override
	public Film find(Short id) throws FilmStoreDAOException {
		List<Film> listFilms = findFilmByCriteria(id, FindFilmCriteria.FIND_BY_ID);
		if (listFilms.isEmpty()) {
			return null;
		}
		return listFilms.get(0);
	}

	@Override
	public List<Film> findAll() throws FilmStoreDAOException {
		return findFilmByCriteria(0, FindFilmCriteria.FIND_ALL);
	}

	@Override
	public List<Film> findAll(int offset, int recordsPerPage,List<Integer> countAllRecords) throws FilmStoreDAOException {
		PoolConnection poolConnection = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		Film film = null;
		List<Film> listFilm = new ArrayList<Film>();
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();

			prepStatement = connection.prepareStatement(SQL_FIND_ALL_PAG);
			prepStatement.setInt(1,offset);
			prepStatement.setInt(2, recordsPerPage);
			
			resultSet = prepStatement.executeQuery();
			while (resultSet.next()) {
				film = new Film();
				fillFilm(film, resultSet);
				listFilm.add(film);
			}
			resultSet.close();
			
			resultSet = prepStatement.executeQuery(SQL_COUNT_PROCESSED_ROWS);
			if(resultSet.next()){
				countAllRecords.add(resultSet.getInt(1));
			}
		} catch (PoolConnectionException | SQLException e) {
			logger.error("Error creating of PreparedStatement.Can't find list film(pagination)", e);
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
		return listFilm;

	}
	
	@Override
	public List<Film> findFilmByGenre(String genre) throws FilmStoreDAOException {
		return findFilmByCriteria(genre, FindFilmCriteria.FIND_BY_GENGE);
	}

	@Override
	public List<Film> findFilmByName(String name) throws FilmStoreDAOException {
		return findFilmByCriteria(name, FindFilmCriteria.FIND_BY_NAME);
	}

	private <T> boolean updateByCriteria(CommandDAO commandDAO, T parametr) throws FilmStoreDAOException {
		Connection connection = null;
		PreparedStatement prepStatement = null;
		PoolConnection poolConnection = null;
		boolean success = false;
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();

			prepStatement = createPrepStatementByCommandCriteria(connection, parametr, commandDAO);

			int affectedRows = prepStatement.executeUpdate();
			if (affectedRows != 0) {
				success = true;
			}

			if (commandDAO == CommandDAO.INSERT) {
				fillGeneratedIdIfInsert(prepStatement, (Film) parametr);
			}
		} catch (SQLException | PoolConnectionException e) {
			logger.error("Error creating of PreparedStatement.Can't " + commandDAO.name() + " film", e);
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				poolConnection.putbackConnection(connection);
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}
		return success;
	}

	private <T> PreparedStatement createPrepStatementByCommandCriteria(Connection connection, T parametr,
			CommandDAO commandDAO) throws SQLException {
		PreparedStatement prepStatement = null;
		boolean insert = true;
		switch (commandDAO) {
		case INSERT: {
			prepStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			fillPreparedStatementForFilm(prepStatement, (Film) parametr, insert);
		}
			break;
		case UPDATE: {
			insert = false;
			prepStatement = connection.prepareStatement(SQL_UPDATE);
			fillPreparedStatementForFilm(prepStatement, (Film) parametr, insert);
		}
			break;
		case DELETE: {
			prepStatement = connection.prepareStatement(SQL_DELETE);
			prepStatement.setShort(1, (Short) parametr);
		}
			break;
		}
		return prepStatement;
	}

	private <T> List<Film> findFilmByCriteria(T parametr, FindFilmCriteria criteria) throws FilmStoreDAOException {
		PoolConnection poolConnection = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		Film film = null;
		List<Film> listFilm = new ArrayList<Film>();
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();

			prepStatement = createPrepStatementByFilmCriteria(criteria, connection, parametr);

			resultSet = prepStatement.executeQuery();
			while (resultSet.next()) {
				film = new Film();
				fillFilm(film, resultSet);
				listFilm.add(film);
			}
		} catch (PoolConnectionException | SQLException e) {
			logger.error("Error creating of PreparedStatement.Can't find film(" + criteria.name() + ")", e);
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
		return listFilm;

	}

	private <T> PreparedStatement createPrepStatementByFilmCriteria(FindFilmCriteria criteria, Connection connection,
			T parametr) throws SQLException {
		PreparedStatement prepStatement = null;
		switch (criteria) {
			case FIND_BY_ID: {
				prepStatement = connection.prepareStatement(SQL_FIND_FILM_BY_ID);
				prepStatement.setShort(1, (Short) parametr);
			}
				break;
			case FIND_BY_GENGE: {
				prepStatement = connection.prepareStatement(SQL_FIND_BY_GENRE);
				prepStatement.setString(1, (String) parametr);
			}
				break;
			case FIND_BY_NAME: {
				prepStatement = connection.prepareStatement(SQL_FIND_BY_NAME);
				prepStatement.setString(1, (String) parametr);
			}
				break;
			case FIND_ALL: {
				prepStatement = connection.prepareStatement(SQL_FIND_ALL);
			}
				break;
		}
		return prepStatement;
	}

	private void fillPreparedStatementForFilm(PreparedStatement preparedStatement, Film entity, boolean insert)
			throws SQLException {
		preparedStatement.setString(1, entity.getName());
		preparedStatement.setString(2, entity.getGenre());
		preparedStatement.setString(3, entity.getCountry());
		preparedStatement.setInt(4, entity.getYearOfRelease());
		preparedStatement.setString(5, entity.getQuality().getNameQuality());
		preparedStatement.setShort(6, entity.getFilmDirector().getId());
		preparedStatement.setString(7, entity.getDescription());
		preparedStatement.setBigDecimal(8, entity.getPrice());
		preparedStatement.setShort(9, entity.getCountFilms());
		preparedStatement.setString(10, entity.getImage());
		if (!insert) {
			preparedStatement.setShort(11, entity.getId());
		}
	}

	private void fillBatchForExecute(short filmId,List<Short> idActors, PreparedStatement prepStatement) throws SQLException {
		for (Short id : idActors) {
			prepStatement.setShort(1,filmId);
			prepStatement.setShort(2, id);
			prepStatement.addBatch();
		}
	}

	private void fillBatchForExecute(short filmId,List<Short> idNewActors, List<Short>  idOldActors,PreparedStatement prepStatement) throws SQLException{
		for(int i=0;i<idNewActors.size();i++){
			prepStatement.setShort(1,idNewActors.get(i));
			prepStatement.setShort(2, filmId);
			prepStatement.setShort(3, idOldActors.get(i));
			prepStatement.addBatch();
		}
	}
	
	private boolean isBatchExecuteSuccessful(int[] results) {
		boolean success = false;
		int countUpdatedRows = 0;

		for (int i = 0; i < results.length; i++) {
			if (results[i] >= 0) {
				countUpdatedRows++;
			}
		}

		if (countUpdatedRows == results.length) {
			success = true;
		}

		return success;
	}

	private void fillGeneratedIdIfInsert(PreparedStatement prepStatement, Film film)
			throws SQLException {
			ResultSet resultset = prepStatement.getGeneratedKeys();
			if (resultset != null && resultset.next()) {
				film.setId(resultset.getShort(1));
			}
	}

	private List<Actor> fillListActor(ResultSet resultSet) throws SQLException {
		List<Actor> listActor = new ArrayList<Actor>();
		while (resultSet.next()) {
			short id = resultSet.getShort(ColumnName.TABLE_FILM_ACTOR_ACT_ID);
			String fio = resultSet.getString(ColumnName.TABLE_ACTOR_ACT_FIO);
			listActor.add(new Actor(id, fio));
		}
		return listActor;
	}

	private FilmDirector fillFilmDirector(ResultSet resultSet) throws SQLException{
		FilmDirector filmDirector = null;
		if(resultSet.next()){
				String fio = resultSet.getString(ColumnName.TABLE_FILM_DIRECTOR_FD_FIO);
				short id = resultSet.getShort(ColumnName.TABLE_FILM_DIRECTOR_FD_UID);
				filmDirector = new FilmDirector(id,fio);
		}
		return filmDirector;
	}
	
	private void fillFilm(Film film, ResultSet resultSet) throws SQLException {
		film.setId(resultSet.getShort(ColumnName.TABLE_FILM_UID));
		film.setName(resultSet.getString(ColumnName.TABLE_FILM_NAME));
		film.setGenre(resultSet.getString(ColumnName.TABLE_FILM_GENRE));
		film.setCountry(resultSet.getString(ColumnName.TABLE_FILM_COUNTRY));
		film.setYearOfRelease(resultSet.getShort(ColumnName.TABLE_FILM_YEAR_OF_RELEASE));
		Quality quality = Quality
				.valueOf(resultSet.getString(ColumnName.TABLE_FILM_QUALITY).replaceFirst("-", ""));
		film.setQuality(quality);
		film.setDescription(resultSet.getString(ColumnName.TABLE_FILM_DESCRIPTION));
		film.setPrice(resultSet.getBigDecimal(ColumnName.TABLE_FILM_PRICE));
		film.setCountFilms(resultSet.getShort(ColumnName.TABLE_FILM_COUNT_FILM));
		film.setImage(resultSet.getString(ColumnName.TABLE_FILM_IMAGE));
	}

	private enum FindFilmCriteria {
		FIND_BY_ID, FIND_BY_GENGE, FIND_BY_NAME, FIND_ALL
	}
}