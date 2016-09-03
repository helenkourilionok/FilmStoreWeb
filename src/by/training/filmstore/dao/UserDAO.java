package by.training.filmstore.dao;

import java.util.List;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.User;

public interface UserDAO extends AbstractDAO<User, String> {
	boolean takeAwayDiscount(int year,int month)throws FilmStoreDAOException;
	boolean makeDiscount(byte sizeOfDiscount,int year,int month,byte countOrders) 
														throws FilmStoreDAOException;
	boolean changePassword(String email,String newPassword) throws FilmStoreDAOException;
	boolean changeEmail(String oldEmail, String newEmail) throws FilmStoreDAOException;
	User find(String email, String password) throws FilmStoreDAOException;
	List<User> findUserForMakeDiscount(int year, int month, byte countOrders) throws FilmStoreDAOException;
}
