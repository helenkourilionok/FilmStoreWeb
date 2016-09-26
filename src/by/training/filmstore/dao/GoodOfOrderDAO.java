package by.training.filmstore.dao;

import java.util.List;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.GoodOfOrder;
import by.training.filmstore.entity.GoodOfOrderPK;
/**
 * This interface extends {@link AbstractDAO}
 * and declares methods that inherent for {@link by.training.filmstore.entity.GoodOfOrder} entity
 * 
 * @author Helena
 *
 *@see AbstractDAO
 *@see by.training.filmstore.entity.GoodOfOrder
 *@see by.training.filmstore.entity.GoodOfOrderPK
 */
public interface GoodOfOrderDAO extends AbstractDAO<GoodOfOrder, GoodOfOrderPK> {
	/**
	 * The method permits find good by id of order.
	 * With each object of good is associated id of order and id of film.
	 * If in database exist goods that have specified id of order,the method returns 
	 * list of these goods;otherwise returns empty list
	 * 
	 * @param id id(primary key) of order
	 * @return list of goods,if suitable goods are in database;otherwise return empty list
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 */
	List<GoodOfOrder> findGoodByOrderId(int id) throws FilmStoreDAOException;
	/**
	 * The method permits find goods by id of film.
	 * If in database exist goods that have specified id of film,the method returns 
	 * list of these goods;otherwise returns empty list
	 * 
	 * @param id id(primary key) of film
	 * @return list of goods,if suitable goods are in database;otherwise return empty list
	 * 
	 * @throws FilmStoreDAOException if a database access error occurs,
	 * error interaction with connection pool
	 */
	List<GoodOfOrder> findGoodByFilmId(short id) throws FilmStoreDAOException;
}
