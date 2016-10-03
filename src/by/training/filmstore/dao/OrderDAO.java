package by.training.filmstore.dao;

import java.math.BigDecimal;
import java.util.List;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.Order;
import by.training.filmstore.entity.Status;
/**
 * This interface extends {@link AbstractDAO} interface
 * and declares methods that appropriate 
 * just for {@link by.training.filmstore.entity.Order} objects 
 * 
 * @author Helena
 *
 * @see AbstractDAO
 * @see by.training.filmstore.entity.Order
 */
public interface OrderDAO extends AbstractDAO<Order, Integer>{
	/**
	 * Method permits user that has specified email to pay order 
	 * with specified id.
	 * 
	 * @param balance the value of user balance after payment of order
	 * @param orderId order number that will be paid
	 * @param userEmail email of user that pay order
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * @throws FilmStoreDAOInvalidOperationException if in database doesn't exist user with 
	 * specified email or if order with specified id wasn't found
	 * 
	 * 
	 * @see by.training.filmstore.dao.exception.FilmStoreDAOException
	 * @see by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException
	 */
	void payOrder(BigDecimal balance,int orderId,String userEmail) throws FilmStoreDAOException,
												FilmStoreDAOInvalidOperationException;
	/**
	 * The method permits find order by status.
	 * 
	 * 
	 * @param status name of order status
	 *  
	 * @return list of orders that have the specified status or empty list 
	 * if in the database there are no orders with the specified status
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.entity.Order#getStatus()
	 * @see by.training.filmstore.entity.Order#setStatus(Status)
	 */
	List<Order> findOrderByStatus(Status status) throws FilmStoreDAOException;
	/**
	 * The method permits find order not only by status but also
	 * by email of user. As a result the method permits find orders 
	 * that were created by user,that has the specified email, and
	 * that have the given status.
	 *   
	 * @param userEmail email of user
	 * @param status name of order status
	 * @return list of order that has the specified email of user and status
	 * or empty list otherwise
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 * @see by.training.filmstore.entity.User#getEmail()
	 */
	List<Order> findOrderByUserEmailAndStatus(String userEmail,String status)throws FilmStoreDAOException;
} 
