package by.training.filmstore.dao.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.GoodOfOrderDAO;
import by.training.filmstore.dao.OrderDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.GoodOfOrder;
import by.training.filmstore.entity.GoodOfOrderPK;
import by.training.filmstore.entity.Order;
import by.training.filmstore.entity.Status;

public final class GoodOfOrderDAOTest {
	
	private static GoodOfOrderDAO goodOfOrderDAO;
	private static GoodOfOrder goodOfOrder;
	private final static String USER_EMAIL = "testEmail@mail.ru";
	private final static String NAME_TEST_FILM = "filmTest";
	
	
	@BeforeClass
	public static void initGoodOfOrderDAOTest(){
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		OrderDAO orderDAO = filmStoreDAOFactory.getOrderDAO();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		goodOfOrderDAO = filmStoreDAOFactory.getGoodOfOrderDAO();
		
		try {
			
			goodOfOrder = createGoodIfNotExist(orderDAO, filmDAO);

		} catch (FilmStoreDAOInvalidOperationException | FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}
	
	@Test
	public void createGoodOfOrderTest(){
		boolean create = true;
		GoodOfOrder goodOfOrderTest = null;
		int[][] incorOrderIdFilmId = getOrderIdFilmIdForTest(goodOfOrder);
		
		for(int i = 0;i<incorOrderIdFilmId.length;i++){
			goodOfOrderTest = createGoodOfOrder(incorOrderIdFilmId[i][0],(short)incorOrderIdFilmId[i][1]);
			try {
				goodOfOrderDAO.create(goodOfOrderTest);
				create = true;
			} catch (FilmStoreDAOException e) {
				create = false;
			} catch (FilmStoreDAOInvalidOperationException e) {
				fail("Test fails because of error connection with database!");
			}finally{
				Assert.assertFalse(create);
			}
		}
	}
	
	@Test
	public void updateGoodOfOrderTest(){
		boolean update = true;
		GoodOfOrder goodOfOrderTest = null;
		int[][] incorOrderIdFilmId = getOrderIdFilmIdForTest(goodOfOrder);
		
		for(int i = 0;i<incorOrderIdFilmId.length;i++){
			goodOfOrderTest = createGoodOfOrder(incorOrderIdFilmId[i][0],(short)incorOrderIdFilmId[i][1]);
			try {
				goodOfOrderDAO.update(goodOfOrderTest);
				update = true;
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			} catch (FilmStoreDAOInvalidOperationException e) {
				update = false;
			}finally{
				Assert.assertFalse(update);
			}
		}
	}
	
	@Test
	public void deleteGoodOfOrderTest(){
		boolean delete = true;
		GoodOfOrder goodOfOrderTest = null;
		int[][] incorOrderIdFilmId = getOrderIdFilmIdForTest(goodOfOrder);
		
		for(int i = 0;i<incorOrderIdFilmId.length;i++){
			goodOfOrderTest = createGoodOfOrder(incorOrderIdFilmId[i][0],(short)incorOrderIdFilmId[i][1]);
			try {
				goodOfOrderDAO.delete(goodOfOrderTest.getId());
				delete = true;
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			} catch (FilmStoreDAOInvalidOperationException e) {
				delete = false;
			}finally{
				Assert.assertFalse(delete);
			}
		}
	}
	
	@Test
	public void findGoodByIdTest(){
		int[][] incorOrderIdFilmId = getOrderIdFilmIdForTest(goodOfOrder);
		GoodOfOrder goodOfOrderTest = null;
		GoodOfOrderPK goodOfOrderPK = null;
		
		for(int i = 0;i<incorOrderIdFilmId.length;i++){
			goodOfOrderPK = new GoodOfOrderPK();
			goodOfOrderPK.setIdFilm((short)incorOrderIdFilmId[i][1]);
			goodOfOrderPK.setIdOrder(incorOrderIdFilmId[i][0]);
			try {
				 goodOfOrderTest = goodOfOrderDAO.find(goodOfOrderPK);
				 Assert.assertNull(goodOfOrderTest);
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			}
		}
	}
	
	@Test
	public void findGoodByOrderIdTest(){
		int[] incorOrderId = new int[]{-9,0,1000};
		List<GoodOfOrder> listGoodOfOrder = null;
		
		for(int i = 0;i<incorOrderId.length;i++){
			try {
				 listGoodOfOrder = goodOfOrderDAO.findGoodByOrderId(incorOrderId[i]);
				 Assert.assertTrue(listGoodOfOrder.isEmpty());
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			}
		}
	}
	
	@Test
	public void findGoodByFilmIdTest(){
		short[] incorFilmId = new short[]{-9,0,1000};
		List<GoodOfOrder> listGoodOfOrder = null;
		
		for(int i = 0;i<incorFilmId.length;i++){
			try {
				 listGoodOfOrder = goodOfOrderDAO.findGoodByOrderId(incorFilmId[i]);
				 Assert.assertTrue(listGoodOfOrder.isEmpty());
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			}
		}
	}
	
	private int[][] getOrderIdFilmIdForTest(GoodOfOrder goodOfOrder){
		int incorrectOrderId = 1000;
		short incorrectFilmId = 1000;
		GoodOfOrderPK goodOfOrderPK = goodOfOrder.getId();
		int[][] incorrectIdOrderIdFilm = new int[][]{
													{goodOfOrderPK.getIdOrder(), incorrectFilmId},
													{incorrectOrderId, (short)goodOfOrderPK.getIdFilm()},
													{incorrectOrderId, incorrectFilmId}
													};
		return incorrectIdOrderIdFilm;
	}
	
	private static GoodOfOrder createGoodIfNotExist(OrderDAO orderDAO,FilmDAO filmDAO) throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException{
		int index = 0;
		List<Order> listOrder = orderDAO.findOrderByUserEmailAndStatus(USER_EMAIL, Status.UNPAID.getNameStatus());
		List<Film> listFilm = filmDAO.findFilmByName(NAME_TEST_FILM);

		Order order = listOrder.get(index);
		Film film = listFilm.get(index);
		
		List<GoodOfOrder> listGoods = goodOfOrderDAO.findGoodByOrderId(order.getId());
				
		if(listGoods.isEmpty()){
			goodOfOrder = createGoodOfOrder(order.getId(),film.getId());
			goodOfOrderDAO.create(goodOfOrder);
		}else{
			goodOfOrder = listGoods.get(0);
		}

		return goodOfOrder;
	}
	
	private static GoodOfOrder createGoodOfOrder(int idOrder,short idFilm){
		byte defaultCountFilms = 12;
		GoodOfOrderPK goodOfOrderPK = new GoodOfOrderPK();
		goodOfOrderPK.setIdOrder(idOrder);
		goodOfOrderPK.setIdFilm(idFilm);
		return new GoodOfOrder(goodOfOrderPK, defaultCountFilms);  
	}
}
