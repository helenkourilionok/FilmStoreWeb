package by.training.filmstore.dao.impl;

import static org.junit.Assert.fail;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import by.training.filmstore.dao.CommentDAO;
import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.Comment;
import by.training.filmstore.entity.CommentPK;
import by.training.filmstore.entity.Film;
import junit.framework.Assert;

public final class CommentDAOTest {

	private static CommentDAO commentDAO;
	private final static String USER_EMAIL = "testEmail@mail.ru";
	private static String FILM_NAME = "filmTest";

	@BeforeClass
	public static void initCommentDAOTest() {
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		FilmDAO filmDAO = filmStoreDAOFactory.getFilmDAO();
		commentDAO = filmStoreDAOFactory.getCommentDAO();

		try {
			createComment(commentDAO, filmDAO, FILM_NAME, USER_EMAIL);
		} catch (FilmStoreDAOInvalidOperationException | FilmStoreDAOException e) {
			e.printStackTrace();
			fail("Test fails because of error connection with database!");
		}

	}

	@Test
	public void createCommentTest() {
		boolean create = true;
		String[] userEmailsForTest = new String[] { "testEmail1@mail.ru", "testEmail1@mail.ru", "testEmail@mail.ru" };
		short[] filmIdForTest = new short[] { -90, 2, 1000 };
		Comment comment = null;
		CommentPK commentPK = null;
		String content = "content";
		Timestamp dateComment = new Timestamp(new Date().getTime());

		for (int i = 0; i < userEmailsForTest.length; i++) {
			try {
				commentPK = new CommentPK(userEmailsForTest[i], filmIdForTest[i]);
				comment = new Comment(commentPK, content, dateComment);
				commentDAO.create(comment);
				create = true;
			} catch (FilmStoreDAOException e) {
				create = false;
			} catch (FilmStoreDAOInvalidOperationException e) {
				fail("Test fails because of error connection with database!");
			} finally {
				Assert.assertFalse(create);
			}
		}
	}

	@Test
	public void updateCommentTest() {
		boolean update = true;
		String[] userEmailsForTest = new String[] { "testEmail1@mail.ru", "testEmail1@mail.ru", "testEmail@mail.ru" };
		short[] filmIdForTest = new short[] { -90, 2, 1000 };
		Comment comment = null;
		CommentPK commentPK = null;
		String content = "content";
		Timestamp dateComment = new Timestamp(new Date().getTime());

		for (int i = 0; i < userEmailsForTest.length; i++) {
			try {
				commentPK = new CommentPK(userEmailsForTest[i], filmIdForTest[i]);
				comment = new Comment(commentPK, content, dateComment);
				commentDAO.update(comment);
				update = true;
			} catch (FilmStoreDAOException e) {
				update = false;
			} catch (FilmStoreDAOInvalidOperationException e) {
				fail("Test fails because of error connection with database!");
			} finally {
				Assert.assertFalse(update);
			}
		}
	}

	@Test
	public void deleteCommentTest() {
		boolean delete = true;
		String[] userEmailsForTest = new String[] { "testEmail1@mail.ru", "testEmail1@mail.ru", "testEmail@mail.ru" };
		short[] filmIdForTest = new short[] { -90, 2, 1000 };
		CommentPK commentPK = null;

		for (int i = 0; i < userEmailsForTest.length; i++) {
			try {
				commentPK = new CommentPK(userEmailsForTest[i], filmIdForTest[i]);
				commentDAO.delete(commentPK);
				delete = true;
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
	public void findCommentTest() {
		String[] userEmailsForTest = new String[] { "testEmail1@mail.ru", "testEmail1@mail.ru", "testEmail@mail.ru" };
		short[] filmIdForTest = new short[] { -90, 2, 1000 };
		Comment comment = null;
		CommentPK commentPK = null;

		for (int i = 0; i < userEmailsForTest.length; i++) {
			try {
				commentPK = new CommentPK(userEmailsForTest[i], filmIdForTest[i]);
				comment = commentDAO.find(commentPK);
				Assert.assertNull(comment);
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			}
		}
	}

	@Test
	public void findCommentByFilmId() {
		short incorFilmId = -90;
		short corFilmId = 1;
		List<Comment> listComment = null;

		try {
			listComment = commentDAO.findCommentByIdFilm(incorFilmId);
			Assert.assertTrue(listComment.isEmpty());
			listComment = commentDAO.findCommentByIdFilm(corFilmId);
			Assert.assertFalse(listComment.isEmpty());
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}

	@Test
	public void findCommentByUserEmail() {
		String incorUserEmail = "testEmail1@mail.ru";
		String corUserEmail = "testEmail@mail.ru";
		List<Comment> listComment = null;

		try {
			listComment = commentDAO.findCommentByIdUser(incorUserEmail);
			Assert.assertTrue(listComment.isEmpty());
			listComment = commentDAO.findCommentByIdUser(corUserEmail);
			Assert.assertFalse(listComment.isEmpty());
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}

	private static Comment createComment(CommentDAO commentDAO, FilmDAO filmDAO, String filmName, String userEmail)
			throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		int index = 0;
		Film film = null;
		Comment comment = null;
		CommentPK commentPK = null;
		String content = "content";
		Timestamp dateComment = new Timestamp(new Date().getTime());

		List<Film> listFilm = filmDAO.findFilmByName(filmName);
		film = listFilm.get(index);
		commentPK = new CommentPK();
		commentPK.setFilmId(film.getId());
		commentPK.setUserEmail(userEmail);

		comment = commentDAO.find(commentPK);

		if (comment == null) {
			comment = new Comment(commentPK, content, dateComment);
			commentDAO.create(comment);
		}

		return comment;
	}
}
