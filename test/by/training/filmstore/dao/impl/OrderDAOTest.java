package by.training.filmstore.dao.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.OrderDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
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
		} catch (FilmStoreDAOInvalidOperationException | FilmStoreDAOException e) {
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
	public void updateOrderTest(){
		String nonexistingUserEmail = "testEmail1@mail.ru";
		boolean update = true;
		
		try {
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
	
	private static Order createTestOrder(){
		String userEmail = "testEmail@mail.ru";
		BigDecimal commonPrice = new BigDecimal(15500);
		Status status = Status.UNPAID;
		KindOfDelivery kindOfDelivery = KindOfDelivery.SELFDELIVERY;
		KindOfPayment kindOfPayment = KindOfPayment.PAYMENT_BY_CARD;
		LocalDate dateOfDelivery = LocalDate.now();
		LocalDate dateOfOrder = LocalDate.now();
		String address = "��. ��������� ��� 35";
		return new Order(userEmail, commonPrice, status, kindOfDelivery, kindOfPayment,
				Date.valueOf(dateOfOrder),Date.valueOf(dateOfDelivery), address);
	}
}
