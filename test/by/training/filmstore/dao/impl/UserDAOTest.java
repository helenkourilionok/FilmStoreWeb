package by.training.filmstore.dao.impl;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.UserDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.Role;
import by.training.filmstore.entity.User;
import junit.framework.Assert;

public final class UserDAOTest {

	private static UserDAO userDAO;
	private static User user;
	private final static String USER_EMAIL = "testEmail@mail.ru";
	private final static String NONEXISTENT_USER_EMAIL = "testEmail1@mail.ru";
		
	@BeforeClass
	public static void initUserDAOTest(){
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		userDAO = filmStoreDAOFactory.getUserDao();
		
		try {
			user = userDAO.find(USER_EMAIL);
			if(user==null){
				user = createTestUser();
				userDAO.create(user);
			}
		} catch (FilmStoreDAOInvalidOperationException | FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		} 
	}
	
	@Test
	public void createExistingUserTest(){
		boolean success = true;
		
		try {
			user.setEmail(USER_EMAIL);
			userDAO.create(user);
		} catch (FilmStoreDAOException e) {
			success = false;
		} catch(FilmStoreDAOInvalidOperationException e){
			fail("Test fails because of error connection with database!");
		}finally{
			Assert.assertFalse(success);
		}
	}
		
	@Test
	public void updateUserWithNotChangedFiledsTest(){
		boolean update = true;
		
		try {
			user.setEmail(USER_EMAIL);
			userDAO.update(user);
		} catch (FilmStoreDAOInvalidOperationException|FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}finally{
			Assert.assertTrue(update);
		}
	}

	@Test 
	public void updateUserWithNonexistentEmailTest(){
		boolean update = true;
		
		try {
			user.setEmail(NONEXISTENT_USER_EMAIL);
			userDAO.update(user);
		} catch (FilmStoreDAOException e) {
			update = false;
		} catch (FilmStoreDAOInvalidOperationException e) {
			fail("Test fails because of error connection with database!");
		}finally{
			Assert.assertFalse(update);
		}
	}

	@Test 
	public void deleteUserWithNonexistentEmailTest(){
		boolean delete = true;
		
		try {
			userDAO.delete(NONEXISTENT_USER_EMAIL);
		} catch (FilmStoreDAOException e) {
			delete = false;
		} catch (FilmStoreDAOInvalidOperationException e) {
			fail("Test fails because of error connection with database!");
		}finally{
			Assert.assertFalse(delete);
		}
	}

	@Test
	public void changePasswordToEqualPasswordTest(){
		boolean change = true;
		String password = "password123";
		try {
			userDAO.changePassword(USER_EMAIL,password);
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		} catch (FilmStoreDAOInvalidOperationException e) {
			change = false;
		}finally{
			Assert.assertTrue(change);
		}
	}
	
	@Test
	public void changePasswordWithNonexistentUserEmailTest(){
		boolean change = true;
		String newPassword = "password123456";
		try {			
			userDAO.changePassword(NONEXISTENT_USER_EMAIL, newPassword);
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		} catch (FilmStoreDAOInvalidOperationException e) {
			change = false;
		}finally{
			Assert.assertFalse(change);
		}
	}

	@Test
	public void makeDiscount(){
		byte sizeOfDiscount = 10;
		byte countOrders = 2;
		boolean makeDiscount = true;	
		int[][] yearMonth = new int[][]{{20,10},{2020,12},{2015,0},{2015,20},{2089,20}};
		
		for(int i = 0;i<yearMonth.length;i++){
			try {
				userDAO.makeDiscount(sizeOfDiscount,yearMonth[i][0],yearMonth[i][1], countOrders);
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			} catch (FilmStoreDAOInvalidOperationException e) {
				makeDiscount = false;
			}finally {
				Assert.assertFalse(makeDiscount);
			}
		}
	}

	@Test
	public void takeAwayDiscountTest(){
		boolean takeAwayDiscount = true;
		
		int[][] yearMonth = new int[][]{{20,10},{2020,12},{2015,0},{2015,20},{2089,20}};
		for(int i = 0;i<yearMonth.length;i++){
			try {
				userDAO.takeAwayDiscount(yearMonth[i][0], yearMonth[i][1]);
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			} catch (FilmStoreDAOInvalidOperationException e) {
				takeAwayDiscount = false;
			}finally {
				Assert.assertFalse(takeAwayDiscount);
			}
		}
		
	}
	
	@Test
	public void findUserForMakeDiscountTest(){
		List<User> listFoundedUser = null;
		byte countOrders = 1;
		int[][] yearMonth = new int[][]{{20,10},{2020,12},{2015,0},{2015,20},{2089,20}};
		
		for(int i = 0;i<yearMonth.length;i++){
			try {
				listFoundedUser = userDAO.findUserForMakeDiscount(yearMonth[i][0],yearMonth[i][1], countOrders);
				Assert.assertNotNull(listFoundedUser);
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			}
		}
	}
	
	@Test
	public void findAllTest(){
		List<User> listFoundedUser = null;
		
		try {
			listFoundedUser = userDAO.findAll();
			Assert.assertNotNull(listFoundedUser);
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}

	@Test
	public void authorizationTest(){
		User testUser = null;
		String password = user.getPassword();
		String incorrectPassword = "dsfsdfsdfsd";
		
		try {
			testUser = userDAO.find(USER_EMAIL,"password123");
			Assert.assertNotNull(testUser);
			testUser = null;
			testUser = userDAO.find(NONEXISTENT_USER_EMAIL,incorrectPassword);
			Assert.assertNull(testUser);
			testUser = null;
			testUser = userDAO.find(NONEXISTENT_USER_EMAIL,password);
			Assert.assertNull(testUser);
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}

	@Test
	public void findUserByEmailTest(){
		User user = null;

		try {
			user = userDAO.find(USER_EMAIL);
			Assert.assertNotNull(user);
			user = null;
			user = userDAO.find(NONEXISTENT_USER_EMAIL);
			Assert.assertNull(user);
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}
	
	private static User createTestUser(){

		String password = "password123";
		Role role = Role.ROLE_GUEST;
		String lastName = "lastName";
		String firstName = "firstName";
		String patronymic = "patronymic";
		String phone = "+375290001122";
		BigDecimal balance = new BigDecimal(0);
		byte discount = 12;
		
		return new User(USER_EMAIL, password, role, lastName, firstName, patronymic, phone, balance, discount);
	}
}
