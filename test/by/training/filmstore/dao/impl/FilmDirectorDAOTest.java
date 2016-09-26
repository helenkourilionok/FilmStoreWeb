package by.training.filmstore.dao.impl;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import by.training.filmstore.dao.FilmDirectorDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.FilmDirector;

public final class FilmDirectorDAOTest {
	
	private static FilmDirectorDAO filmDirectorDAO;
	private static short[] incorrectFilmDirId = new short[]{-90,0,700};
	
	
	@BeforeClass
	public static void initFilmDirectorDAOTest(){
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		filmDirectorDAO = filmStoreDAOFactory.getFilmDirectorDAO();
	}
	
	@Test
	public void updateFilmDirectorTest(){
		
		boolean update = true;
		FilmDirector filmDirector = new FilmDirector();
		
		
		for(int i = 0;i<incorrectFilmDirId.length;i++){
			
			try {
				filmDirector.setId(incorrectFilmDirId[i]);
				filmDirectorDAO.update(filmDirector);
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
	public void deleteFilmDirectorTest(){

		boolean delete = true;
				
		for(int i = 0;i<incorrectFilmDirId.length;i++){
			
			try {
				filmDirectorDAO.delete(incorrectFilmDirId[i]);
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
	public void findByIdTest(){
		FilmDirector filmDirector = null;
		
		for(int i = 0;i<incorrectFilmDirId.length;i++){
			try {
				filmDirector = filmDirectorDAO.find(incorrectFilmDirId[i]);
				Assert.assertNull(filmDirector);
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			}
		}
	}
	
	@Test
	public void findByFIOTest(){
		Map<String,Boolean> fioResult = getFIOResult();
		FilmDirector filmDirector = null;

		for(String fio:fioResult.keySet()){
			try {
				 filmDirector = filmDirectorDAO.findByFIO(fio);
				 boolean result = filmDirector==null?false:true;
				 Assert.assertEquals(result, fioResult.get(fio));
			} catch (FilmStoreDAOException e) {
				 fail("Test fails because of error connection with database!");
			}
		}
	}
	
	private Map<String,Boolean> getFIOResult(){
		Map<String,Boolean> fioResult = new HashMap<>();
		fioResult.put("Гэрри Маршалл", true);
		fioResult.put("ГЭРРИ МАРШАЛ", false);
		fioResult.put("гэрри маршал", false);
		fioResult.put("Несуществующий режиссёр", false);
		return fioResult;
	}
}
