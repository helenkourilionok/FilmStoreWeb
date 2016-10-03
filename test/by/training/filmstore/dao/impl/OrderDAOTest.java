package by.training.filmstore.dao.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.OrderDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.GoodOfOrder;
import by.training.filmstore.entity.GoodOfOrderPK;
import by.training.filmstore.entity.KindOfDelivery;
import by.training.filmstore.entity.KindOfPayment;
import by.training.filmstore.entity.Order;
import by.training.filmstore.entity.Status;

public final class OrderDAOTest {
	private static OrderDAO orderDAO;
	private static Order order;
	private final static String USER_EMAIL = "testEmail@mail.ru";
	
	@BeforeClass
	public static void initOrderDAOTest(){
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		orderDAO = filmStoreDAOFactory.getOrderDAO();
		List<Order> listOrder = null;
		int index = 0;
		
		try {
			
			listOrder = orderDAO.findOrderByUserEmailAndStatus(USER_EMAIL, Status.UNPAID.getNameStatus());
			if(listOrder.isEmpty()){
				order = createTestOrder();
				orderDAO.create(order);
			}else{
				order = listOrder.get(index);
			}
		} catch (FilmStoreDAOInvalidOperationException | 
				FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		} 
		
	}
	
	
	@Test
	public void createOrderTest(){
		String nonexistingUserEmail = "testEmail1@mail.ru";
		boolean create = true;
		
		try {
			order.setUserEmail(nonexistingUserEmail);
			orderDAO.create(order);
			create = true;
		} catch (FilmStoreDAOException e) {
			create = false;
		} catch (FilmStoreDAOInvalidOperationException e) {
			fail("Test fails because of error connection with database!");
		}finally{
			Assert.assertFalse(create);
		}
	}
	
	@Test
	public void createGoodOfOrderTest(){
		int orderId = 1;
		boolean create = false;
		short[] incorrectFilmId = new short[]{-10,0,1000};
		List<GoodOfOrder> listGoods = new ArrayList<>();
		GoodOfOrder goodOfOrder = new GoodOfOrder();
		GoodOfOrderPK goodPK = new GoodOfOrderPK();
		goodOfOrder.setId(goodPK);
		order.setId(orderId);
		order.setListGoodOfOrder(listGoods);
		
		for(int i = 0;i<incorrectFilmId.length;i++){
			try {
				goodPK.setIdFilm(incorrectFilmId[i]);
				orderDAO.create(order);
				create = true;
			} catch (FilmStoreDAOException e) {
				create = false;
			} catch (FilmStoreDAOInvalidOperationException e) {
				fail("Test fails because of error connection with database!");
			}finally {
				assertFalse(create);
			}
		}
	}
	
	@Test
	public void updateOrderTest(){
		int orderId = 1;
		String nonexistingUserEmail = "testEmail1@mail.ru";
		boolean update = true;
		
		try {
			order.setId(orderId);
			order.setUserEmail(nonexistingUserEmail);
			orderDAO.update(order);
			update = true;
		} catch (FilmStoreDAOException e) {
			update = false;
		} catch (FilmStoreDAOInvalidOperationException e) {
			fail("Test fails because of error connection with database!");
		}finally{
			Assert.assertFalse(update);
		}
	}
	
	@Test
	public void deleteOrderTest(){
		int smallId = 0;
		int bigId = 1000;
		int[] testId = new int[]{smallId,bigId}; 
		boolean delete = true;

		for(int i=0;i<testId.length;i++){
			try {
				orderDAO.delete(testId[i]);
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
	public void findOrderByIdTest() {
		try {
			Integer smallId = 0;
			Integer bigId = 1000;
			Order _order = orderDAO.find(smallId);
			assertNull(_order);
			_order = orderDAO.find(bigId);
			assertNull(_order);
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}
	
	@Test
	public void findOrderByUserEmailTest()
	{
		String nonexistingUserEmail = "nonexistingUserEmail";
		
		try {
			List<Order> listOrderNonexistUser = orderDAO.findOrderByUserEmailAndStatus(nonexistingUserEmail,Status.UNPAID.getNameStatus());
			assertTrue(listOrderNonexistUser.isEmpty());
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		} 
		
	}

//	@Test
//	public void createOrderIncorrectEmailTest(){
//		boolean create = false;
//		String nonexistingUserEmail = "testEmail1@mail.ru";
//		Order order = createTestOrder();
//		order.setUserEmail(nonexistingUserEmail);
//		List<GoodOfOrder> listGoods = createGoodOfOrder();
//		try {
//			orderDAO.create(order,listGoods);
//			create = true;
//		} catch (FilmStoreDAOException e) {
//			create = false;
//		} catch (FilmStoreDAOInvalidOperationException e) {
//			fail("Test fails because of error connection with database!");
//		} finally {
//			Assert.assertFalse(create);
//		}
//	}
//	 
//	@Test
//	public void createOrderIncorrectIdFilmTest(){
//		short[] incorrectIdFilms = new short[]{-10,0,1000};
//		boolean create = false;
//		String existingUserEmail = "smile269@mail.ru";
//		Order order = createTestOrder();
//		order.setUserEmail(existingUserEmail);
//		List<GoodOfOrder> listGoods = createGoodOfOrder();
//		for(int i = 0;i<incorrectIdFilms.length;i++){
//				try {
//					setIncorrectGoodOfOrderPK(listGoods.get(0), incorrectIdFilms[i]);
//					orderDAO.create(order,listGoods);
//					create = true;
//				} catch (FilmStoreDAOException e) {
//					create = false;
//				} catch (FilmStoreDAOInvalidOperationException e) {
//					fail("Test fails because of error connection with database!");
//				} finally {
//					Assert.assertFalse(create);
//				}
//		}
//	}
	
	@Test
	public void payOrderIncorrectUserEmailTest(){
		boolean create = false;
		int orderId = 10;
		BigDecimal balance = new BigDecimal("20000");
		String nonexistingUserEmail = "testEmail1@mail.ru";
		Order order = createTestOrder();
		order.setUserEmail(nonexistingUserEmail);
		try{
			orderDAO.payOrder(balance, orderId, nonexistingUserEmail);
			create = true;
		}catch(FilmStoreDAOException e){
			fail("Test fails because of error connection with database!");
		}catch(FilmStoreDAOInvalidOperationException e){
			create = false;
		}finally {
			Assert.assertFalse(create);
		}
	}
	
	@Test
	public void payOrderIncorrectOrderIdTest(){
		boolean create = false;
		int[] incorrectIdOrders = new int[]{-10,0,1000};
		String existingUserEmail = "smile269@mail.ru";
		BigDecimal balance = new BigDecimal("20000");
		
		for(int i = 0;i<incorrectIdOrders.length;i++){
			try{
				orderDAO.payOrder(balance,incorrectIdOrders[i], existingUserEmail);
				create = true;
			}catch(FilmStoreDAOException e){
				fail("Test fails because of error connection with database!");
			}catch(FilmStoreDAOInvalidOperationException e){
				create = false;
			}finally {
				Assert.assertFalse(create);
			}
		}
	}
	
	private static Order createTestOrder(){
		String userEmail = "testEmail@mail.ru";
		BigDecimal commonPrice = new BigDecimal(15500);
		Status status = Status.UNPAID;
		KindOfDelivery kindOfDelivery = KindOfDelivery.SELFDELIVERY;
		KindOfPayment kindOfPayment = KindOfPayment.PAYMENT_BY_CARD;
		LocalDate dateOfDelivery = LocalDate.now();
		LocalDate dateOfOrder = LocalDate.now();
		String address = "ул. Сурганова дом 35";
		return new Order(userEmail, commonPrice, status, kindOfDelivery, kindOfPayment,
				Date.valueOf(dateOfOrder),Date.valueOf(dateOfDelivery), address);
	}
}
