package by.training.filmstore.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.dao.OrderDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.dao.pool.PoolConnection;
import by.training.filmstore.dao.pool.PoolConnectionException;
import by.training.filmstore.entity.GoodOfOrder;
import by.training.filmstore.entity.KindOfDelivery;
import by.training.filmstore.entity.KindOfPayment;
import by.training.filmstore.entity.Order;
import by.training.filmstore.entity.Status;

public class OrderDAOImpl implements OrderDAO {

	private static final Logger logger = LogManager.getLogger(OrderDAOImpl.class);
	private static final String SQL_INSERT = "insert into `order`(`order`.ord_email_user,`order`.ord_common_price,`order`.ord_status,"
			+ "`order`.ord_kind_of_delivery,`order`.ord_kind_of_payment,`order`.ord_date_of_order,"
			+ "`order`.ord_date_of_delivery,`order`.ord_address) values(?,?,?,?,?,?,?,?)";
	private static final String SQL_INSERT_GOOD = 
			"insert into good_of_order(good_of_order.ord_id,good_of_order.fm_id,"
			+ "good_of_order.gd_count_films) values(?,?,?)";
	private static final String SQL_UPDATE = 
			"update `order`  SET `order`.ord_email_user = ?,`order`.ord_common_price = ?,"
			+ "`order`.ord_status = ?,`order`.ord_kind_of_delivery = ?,"
			+ "`order`.ord_kind_of_payment = ?,`order`.ord_date_of_order = ?,"
			+ "`order`.ord_date_of_delivery = ?,`order`.ord_address = ? where `order`.ord_uid = ?";
	private static final String SQL_UPDATE_STATUS_AND_BALANCE = 
			"update filmstore.`order` as orderTable,filmstore.user as userTable "+
			"set orderTable.ord_status= ? ,userTable.us_balance = ? "+ 
			"where orderTable.ord_uid = ? and userTable.us_email=? and orderTable.ord_status='не оплачено'";
	private static final String SQL_DELETE = "DELETE FROM `order` WHERE `order`.ord_uid = ?";
	private static final String SQL_FIND_BY_ID = "select `order`.ord_uid,`order`.ord_email_user,`order`.ord_common_price,`order`.ord_status,"
			+ "`order`.ord_kind_of_delivery,`order`.ord_kind_of_payment,`order`.ord_date_of_order,"
			+ "`order`.ord_date_of_delivery,`order`.ord_address from `order` where `order`.ord_uid = ?";
	private static final String SQL_FIND_ALL = "select `order`.ord_email_user,`order`.ord_common_price,`order`.ord_status,"
			+ "`order`.ord_kind_of_delivery,`order`.ord_kind_of_payment,`order`.ord_date_of_order,"
			+ "`order`.ord_date_of_delivery,`order`.ord_address from `order`";
	private static final String SQL_FIND_BY_STATUS = "select `order`.ord_email_user,`order`.ord_common_price,`order`.ord_status,"
			+ "`order`.ord_kind_of_delivery,`order`.ord_kind_of_payment,`order`.ord_date_of_order,"
			+ "`order`.ord_date_of_delivery,`order`.ord_address from `order` where `order`.ord_status = ?";
	private static final String SQL_FIND_EMAIL_USER_AND_STATUS = "select `order`.ord_uid,`order`.ord_email_user,`order`.ord_common_price,`order`.ord_status,"
			+ "`order`.ord_kind_of_delivery,`order`.ord_kind_of_payment,`order`.ord_date_of_order,"
			+ "`order`.ord_date_of_delivery,`order`.ord_address from `order` where `order`.ord_email_user = ? and `order`.ord_status = ?";

	@Override
	public void create(Order entity)
			throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		Connection connection = null;
		PreparedStatement prepStatement = null;
		PoolConnection poolConnection = null;

		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			
			connection.setAutoCommit(false);
			
			prepStatement = createPrepStatementByCommandCriteria(connection, (Order)entity, CommandDAO.INSERT);
			int affectedRows = prepStatement.executeUpdate();
			
			if (affectedRows == 0) {
                throw new FilmStoreDAOInvalidOperationException("Operation with order failed (create)");
			}
			
			fillGeneratedIdIfInsert(prepStatement, (Order)entity);
			prepStatement.close();	 
	
			prepStatement = connection.prepareStatement(SQL_INSERT_GOOD);
			fillBatchForExecute(prepStatement,entity.getListGoodOfOrder(),entity.getId());
			int[] results = prepStatement.executeBatch();	
			
			if(!isBatchExecuteSuccessful(results)){
				 throw new FilmStoreDAOInvalidOperationException("Operation failed!Can't create list goods!");
			}
				
			connection.commit();
		} catch (SQLException | PoolConnectionException e) {
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new FilmStoreDAOException(e1);
			}
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
			try{
				poolConnection.putbackConnection(connection);
			}catch(SQLException e){
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}
	}
	
	@Override
	public void update(Order entity) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		updateByCriteria(CommandDAO.UPDATE, entity);
	}

	@Override
	public void delete(Integer id) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		updateByCriteria(CommandDAO.DELETE, id);
	}
	
	@Override
	public Order find(Integer id) throws FilmStoreDAOException {
		List<Order> listOrder = findOrderByCriteria(id, FindOrderCriteria.FIND_BY_ID);
		if (listOrder.isEmpty()) {
			return null;
		}
		return listOrder.get(0);
	}

	@Override
	public List<Order> findAll() throws FilmStoreDAOException {
		return findOrderByCriteria(0, FindOrderCriteria.FIND_ALL);
	}

	@Override
	public List<Order> findOrderByStatus(Status status) throws FilmStoreDAOException {
		return findOrderByCriteria(status.getNameStatus(), FindOrderCriteria.FIND_BY_STATUS);
	}

	@Override
	public List<Order> findOrderByUserEmailAndStatus(String userEmail,String status) throws FilmStoreDAOException {
		Order stub = new Order();
		stub.setUserEmail(userEmail);
		stub.setStatus(Status.getStatusByName(status));
		return findOrderByCriteria(stub, FindOrderCriteria.FIND_BY_USER_EMAIL_AND_STATUS);
	}

	@Override
	public void payOrder(BigDecimal balance, int orderId, String userEmail)
			throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		Connection connection = null;
		PreparedStatement prepStatement = null;
		PoolConnection poolConnection = null;

		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();

			prepStatement = connection.prepareStatement(SQL_UPDATE_STATUS_AND_BALANCE);
			prepStatement.setString(1,Status.PAID.getNameStatus());
			prepStatement.setBigDecimal(2,balance);
			prepStatement.setInt(3, orderId);
			prepStatement.setString(4, userEmail);

			int affectedRows = prepStatement.executeUpdate();

			if (affectedRows == 0) {
                throw new FilmStoreDAOInvalidOperationException("Operation with order failed!Can't pay order!");
            }
			
		} catch (SQLException | PoolConnectionException e) {
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
			try{
				poolConnection.putbackConnection(connection);
			}catch(SQLException e){
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}		
	}
	
	private <T> void updateByCriteria(CommandDAO commandDAO, T parametr) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		Connection connection = null;
		PreparedStatement prepStatement = null;
		PoolConnection poolConnection = null;

		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();

			prepStatement = createPrepStatementByCommandCriteria(connection, parametr, commandDAO);

			int affectedRows = prepStatement.executeUpdate();

			if (affectedRows == 0) {
                throw new FilmStoreDAOInvalidOperationException("Operation with order failed ("+commandDAO.name()+")");
            }
			
			if(commandDAO == CommandDAO.INSERT){
				fillGeneratedIdIfInsert(prepStatement, (Order)parametr);
			}
		} catch (SQLException | PoolConnectionException e) {
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
			try{
				poolConnection.putbackConnection(connection);
			}catch(SQLException e){
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}
	}
	
	private <T> PreparedStatement createPrepStatementByCommandCriteria(Connection connection, T parametr,
			CommandDAO commandDAO) throws SQLException {
		PreparedStatement prepStatement = null;
		switch (commandDAO) {
		case INSERT: {
			boolean insert = true;
			prepStatement = connection.prepareStatement(SQL_INSERT,PreparedStatement.RETURN_GENERATED_KEYS);
			fillPreparedStatementForOrder(prepStatement,(Order) parametr, insert);
		}
			break;
		case UPDATE: {
			boolean insert = false;
			prepStatement = connection.prepareStatement(SQL_UPDATE);
			fillPreparedStatementForOrder(prepStatement, (Order) parametr, insert);
		}
			break;
		case DELETE: {
			prepStatement = connection.prepareStatement(SQL_DELETE);
			prepStatement.setInt(1, (Integer) parametr);
		}
			break;
		}
		return prepStatement;
	}

	private void fillPreparedStatementForOrder(PreparedStatement preparedStatement,Order entity,boolean insert) throws SQLException{
		preparedStatement.setString(1, entity.getUserEmail());
		preparedStatement.setBigDecimal(2, entity.getCommonPrice());
		preparedStatement.setString(3, entity.getStatus().getNameStatus());
		preparedStatement.setString(4, entity.getKindOfDelivery().getNameKindOfDelivery());
		preparedStatement.setString(5, entity.getKindOfPayment().getNameKindOfPayment());
		preparedStatement.setDate(6, entity.getDateOfOrder());
		preparedStatement.setDate(7, entity.getDateOfDelivery());
		preparedStatement.setString(8, entity.getAddress());
		if(!insert){
			preparedStatement.setInt(9, entity.getId());
		}
	}
	
	private void fillGeneratedIdIfInsert(PreparedStatement prepStatement,Order order) throws SQLException{
			ResultSet resultset = prepStatement.getGeneratedKeys();
			if (resultset != null && resultset.next()) {
				order.setId(resultset.getInt(1));
			}
	}

	private void fillBatchForExecute(PreparedStatement prepStatement,List<GoodOfOrder> listGoods,int orderId) throws SQLException{
		for(GoodOfOrder entity:listGoods){
			prepStatement.setInt(1,orderId);
			prepStatement.setInt(2, entity.getId().getIdFilm());
			prepStatement.setByte(3, entity.getCountFilms());
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
	
	private <T> List<Order> findOrderByCriteria(T parametr, FindOrderCriteria criteria) throws FilmStoreDAOException {
		PoolConnection poolConnection = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		Order order = null;
		List<Order> listOrder = new ArrayList<Order>();
		try {
			poolConnection = PoolConnection.getInstance();
			connection = poolConnection.takeConnection();
			
			prepStatement = createPrepStatementByOrderCriteria(connection, criteria, parametr);

			resultSet = prepStatement.executeQuery();
			

			while (resultSet.next()) {
				order = new Order();
				fillOrder(order, resultSet);
				listOrder.add(order);
			}
			
		} catch (PoolConnectionException | SQLException e) {
			throw new FilmStoreDAOException(e);
		} finally {
			try {
				prepStatement.close();
			} catch (SQLException e) {
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
			try{
				poolConnection.putbackConnection(connection);
			}catch(SQLException e){
				logger.error("Error closing of PreparedStatement or Connection", e);
			}
		}
		return listOrder;
	}

	private <T> PreparedStatement createPrepStatementByOrderCriteria(Connection connection, FindOrderCriteria criteria,
			T parametr) throws SQLException {
		PreparedStatement prepStatement = null;
		switch (criteria) {
		case FIND_BY_ID: {
			prepStatement = connection.prepareStatement(SQL_FIND_BY_ID);
			prepStatement.setInt(1, (Integer) parametr);
		}
			break;
		case FIND_ALL: {
			prepStatement = connection.prepareStatement(SQL_FIND_ALL);
		}
			break;
		case FIND_BY_STATUS: {
			prepStatement = connection.prepareStatement(SQL_FIND_BY_STATUS);
			prepStatement.setString(1, (String) parametr);
		}
			break;
		case FIND_BY_USER_EMAIL_AND_STATUS: {
			prepStatement = connection.prepareStatement(SQL_FIND_EMAIL_USER_AND_STATUS);
			Order stub = (Order)parametr;
			prepStatement.setString(1,stub.getUserEmail());
			prepStatement.setString(2, stub.getStatus().getNameStatus());
		}
			break;
		}
		return prepStatement;
	}

	private void fillOrder(Order order, ResultSet resultSet) throws SQLException {
		order.setId(resultSet.getInt(ColumnName.TABLE_ORDER_UID));
		order.setUserEmail(resultSet.getString(ColumnName.TABLE_ORDER_EMAIL_USER));
		order.setCommonPrice(resultSet.getBigDecimal(ColumnName.TABLE_ORDER_COMMON_PRICE));
		order.setStatus(Status.getStatusByName(resultSet.getString(ColumnName.TABLE_ORDER_STATUS)));
		order.setKindOfDelivery(KindOfDelivery
				.getKindOfDeliveryByName(resultSet.getString(ColumnName.TABLE_ORDER_KIND_OF_DELIVERY)));
		order.setKindOfPayment(KindOfPayment
				.getKindOfPaymentByName(resultSet.getString(ColumnName.TABLE_ORDER_KIND_OF_PAYMENT)));
		order.setDateOfOrder(resultSet.getDate(ColumnName.TABLE_ORDER_DATE_OF_ORDER));
		order.setDateOfDelivery(resultSet.getDate(ColumnName.TABLE_ORDER_DATE_OF_DELIVERY));
		order.setAddress(resultSet.getString(ColumnName.TABLE_ORDER_ADDRESS));
	}

	private enum FindOrderCriteria {
		FIND_BY_ID, FIND_BY_USER_EMAIL_AND_STATUS, FIND_BY_STATUS, FIND_ALL
	}

}
