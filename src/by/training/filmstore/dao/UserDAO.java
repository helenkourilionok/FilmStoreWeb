package by.training.filmstore.dao;

import java.util.List;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.User;
/**
 * This interface extends {@link AbstractDAO}
 * and declares methods that inherent for {@link by.training.filmstore.entity.User} entity
 * 
 * @author Helena
 *
 * @see AbstractDAO
 * @see by.training.filmstore.entity.User
 */
public interface UserDAO extends AbstractDAO<User, String> {
	/**
	 * The method permits take away discount from user.
	 * In fact this method update user field with name discount.
	 * Input method parameters are used to search for users, 
	 * who will be deprived of discount.
	 * 
	 * @param year  the year in which the orders were made by user
	 * @param month the number of month during which the orders was made
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * @throws FilmStoreDAOInvalidOperationException if database hasn't
	 * any user that made orders in the given month and year;if year and month
	 * are incorrect (it means the given year less or equal than 0 or the given month less or equal 0) 
	 * 
	 * @see by.training.filmstore.entity.User#setDiscount(byte)
	 */
	void takeAwayDiscount(int year,int month)throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
	/**
	 * The method permits make discount for users that made the given count of orders
	 * in the given moth and in the given year.
	 * In fact this method update user field with name discount.
	 * 
	 * @param sizeOfDiscount the size of discount
	 * @param year the year,in which the orders were made by user
	 * @param month the number of month, in which the orders were made by user
	 * @param countOrders the count of orders that user must make for get the discount
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * @throws FilmStoreDAOInvalidOperationException if none user make the given count of orders;
	 * if input parameters are incorrect (it means the given year less or equal than 0 or 
	 * the given month less or equal 0)
	 * 
	 * @see by.training.filmstore.entity.User#setDiscount(byte)
	 */
	void makeDiscount(byte sizeOfDiscount,int year,int month,byte countOrders) 
														throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
	/**
	 * The method permits change the user password.The given email used to
	 * search the user whose password will be updated.
	 * 
	 * @param email  the email of user whose password will be updated
	 * @param newPassword the new user password
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * @throws FilmStoreDAOInvalidOperationException if user with the given email is not found;
	 * because of incorrect input parameters (for example email or newPassword is empty or null)
	 * 
	 * @see by.training.filmstore.entity.User#setPassword(String)
	 */
	void changePassword(String email,String newPassword) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException;
	/**
	 * The method permits make user authorization.
	 * This method find user by email and password. 
	 *  
	 * @param email the email of user
	 * @param password the password of user
	 * 
	 * @return 	If in database exists user with the given email and password, the method return 
	 * a found user;otherwise it return null
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 * 
	 */
	User find(String email, String password) throws FilmStoreDAOException;
	/**
	 * The method permits find users for whom will be made a discount.
	 * In fact this method try to find user 
	 * 
	 * @param year the year in which the orders were made by user
	 * @param month the number of month during which the orders was made
	 * @param countOrders the number of orders that the user has to do to get a discount
	 * 
	 * @return list of user whom will be made the discount; list can be empty if in 
	 * database doesn't have any user who made the given count of orders
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 */
	 List<User> findUserForMakeDiscount(int year, int month, byte countOrders) throws FilmStoreDAOException;
}
