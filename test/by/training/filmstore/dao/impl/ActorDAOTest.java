package by.training.filmstore.dao.impl;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import by.training.filmstore.dao.ActorDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.Actor;
import junit.framework.Assert;

public final class ActorDAOTest {

	private static ActorDAO actorDAO;
	private short[] incorrectActorId = new short[] { -90, 0, 1000 };

	@BeforeClass
	public static void initActorDAOTest() {
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		actorDAO = filmStoreDAOFactory.getActorDAO();
	}

	@Test
	public void updateActorTest() {
		boolean update = true;
		Actor testActor = new Actor();

		for (int i = 0; i < incorrectActorId.length; i++) {
			try {
				actorDAO.update(testActor);
				update = true;
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			} catch (FilmStoreDAOInvalidOperationException e) {
				update = false;
			} finally {
				Assert.assertFalse(update);
			}
		}
	}

	@Test
	public void deleteActorTest() {
		boolean delete = true;

		for (int i = 0; i < incorrectActorId.length; i++) {
			try {
				actorDAO.delete(incorrectActorId[i]);
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			} catch (FilmStoreDAOInvalidOperationException e) {
				delete = false;
			} finally {
				Assert.assertFalse(delete);
			}
		}
	}

	@Test
	public void findActorTest() {
		Actor actorTest = null;

		for (int i = 0; i < incorrectActorId.length; i++) {
			try {
				actorTest = actorDAO.find(incorrectActorId[i]);
				Assert.assertNull(actorTest);
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			}
		}
	}

	@Test
	public void findByFIO() {
		String correctFIO = "Мэтт Дэймон";
		String incorrectFIO = "Несуществующий актёр";
		Actor actor = null;

		try {
			actor = actorDAO.findByFIO(correctFIO);
			Assert.assertNotNull(actor);
			actor = actorDAO.findByFIO(incorrectFIO);
			Assert.assertNull(actor);
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}
}
