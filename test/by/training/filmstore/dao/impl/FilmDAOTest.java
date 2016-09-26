package by.training.filmstore.dao.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import by.training.filmstore.dao.FilmDAO;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.dao.exception.FilmStoreDAOInvalidOperationException;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.entity.Quality;

public final class FilmDAOTest {
	private static FilmDAO filmDAO;
	private static String FILM_NAME = "filmTest";
	private static Film film;

	@BeforeClass
	public static void initFilmDAOTest() {
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		filmDAO = filmStoreDAOFactory.getFilmDAO();
		List<Film> listFilm = null;
		int index = 0;

		try {

			listFilm = filmDAO.findFilmByName(FILM_NAME);
			if (listFilm.isEmpty()) {
				film = createTestFilm();
				filmDAO.create(film);
				createTestFilmActor(filmDAO, film.getId());
			} else {
				film = listFilm.get(index);
				filmDAO.findActorFilmDirectorForFilm(film);
			}
		} catch (FilmStoreDAOInvalidOperationException | FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}

	}

	@Test
	public void createFilmWithIncorrectGenre(){ 
		boolean create = true;
		String[] incorrectGenre = new String[]{"Кооомедия"," комедия"};

		for(int i = 0;i<incorrectGenre.length;i++){
			film.setGenre(incorrectGenre[i]);
			try {
				filmDAO.create(film);
				create = true;
			} catch (FilmStoreDAOException e) {
				create = false;
			} catch (FilmStoreDAOInvalidOperationException e) {
				fail("Test fails because of error connection with database!");
			}
			finally{
				assertFalse(create);
			}
		}
	}
	
	@Test
	public void createFilmActorTest() {
		boolean create = true;

		Map<Short, List<Short>> idFilmIdActor = getIdFilmIdActor();

		for (Short key : idFilmIdActor.keySet()) {
			try {

				List<Short> actorIdForTest = idFilmIdActor.get(key);
				filmDAO.createFilmActor(key, actorIdForTest);

			} catch (FilmStoreDAOException e) {
				create = false;
			} catch (FilmStoreDAOInvalidOperationException e) {
				fail("Test fails because of error connection with database!");
			} finally {
				assertFalse(create);
			}
		}
	}

	@Test
	public void updateFilmTest() {
		boolean update = true;
		List<Short> listNonexistingActorId = Arrays.asList((short) -90, (short) 0, (short) 1000);

		for (int i = 0; i < listNonexistingActorId.size(); i++) {
			try {
				film.setId(listNonexistingActorId.get(i));
				filmDAO.update(film);
				update = true;
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			} catch (FilmStoreDAOInvalidOperationException e) {
				update = false;
			} finally {
				assertFalse(update);
			}
		}
	}

	@Test
	public void updateFilmActorTest() {
		boolean update = true;
		List<Short> listIncorrectActorId = Arrays.asList((short) -90, (short) 0, (short) 1000);
		List<Short> listActorIdForTest = getActorId(film);
		for (int i = 0; i < listIncorrectActorId.size(); i++) {
			try {
				List<Short> incorrectActorId = Arrays.asList((short) -90);
				filmDAO.updateFilmActor((short) 11, incorrectActorId, listActorIdForTest);
				update = true;
			} catch (FilmStoreDAOException e) {
				update = false;
			} catch (FilmStoreDAOInvalidOperationException e) {
				fail("Test fails because of error connection with database!");
			} finally {
				assertFalse(update);
			}
		}
	}

	@Test
	public void deleteFilmTest() {
		boolean delete = true;
		short id = 1010;

		try {
			filmDAO.delete(id);
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		} catch (FilmStoreDAOInvalidOperationException e) {
			delete = false;
		} finally {
			assertFalse(delete);
		}
	}

	@Test
	public void deleteFilmActorTest() {
		boolean delete = true;

		Map<Short, List<Short>> idFilmIdActor = getIdFilmIdActor();

		for (Short key : idFilmIdActor.keySet()) {
			try {

				List<Short> actorIdForTest = idFilmIdActor.get(key);
				filmDAO.deleteFilmActor(key, actorIdForTest);

			} catch (FilmStoreDAOException e) {
				delete = false;
			} catch (FilmStoreDAOInvalidOperationException e) {
				fail("Test fails because of error connection with database!");
			} finally {
				assertTrue(delete);
			}
		}
	}

	@Test
	public void findFilmByIdTest() {
		short nonexistentId = 1000;
		Film film = null;

		try {
			film = filmDAO.find(nonexistentId);
			assertNull(film);
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}

	@Test
	public void findAllFilmsTest() throws FilmStoreDAOException {
		List<Film> listFilms = filmDAO.findAll();
		assertNotNull("Operation failed. No film was found", listFilms);
		assertEquals("Operation failed. No film was found", false, listFilms.isEmpty());
	}

	@Test
	public void findAllFilmsForPaginationTest() {
		int smallOffset = 0;
		int bigOffset = 1000;
		int recordsPerPage = 5;
		List<Integer> countAllRecords = new ArrayList<>();
		List<Film> testListFilm = null;

		try {
			testListFilm = filmDAO.findAll(smallOffset, recordsPerPage, countAllRecords);
			assertFalse(testListFilm.isEmpty());
			testListFilm = filmDAO.findAll(bigOffset, recordsPerPage, countAllRecords);
			assertTrue(testListFilm.isEmpty());
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}

	@Test
	public void findActorFilmDirectorForFilmTest() {
		boolean find = true;
		short nonexistentId = 1000;
		Film _film = new Film();

		try {
			_film.setId(nonexistentId);
			filmDAO.findActorFilmDirectorForFilm(_film);
		} catch (FilmStoreDAOException e) {
			find = false;
		} finally {
			assertTrue(find);
		}
	}

	@Test
	public void findFilmByNameTest() {
		try {
			List<Film> listFilms = filmDAO.findFilmByName(film.getName());
			assertFalse(listFilms.isEmpty());

			String nameNonexistingFilm = "nonexistingFilm";

			listFilms = filmDAO.findFilmByName(nameNonexistingFilm);
			assertTrue(listFilms.isEmpty());
		} catch (FilmStoreDAOException e) {
			fail("Test fails because of error connection with database!");
		}
	}

	@Test
	public void findFilmByGenreTest() {
		List<Film> listFilms = null;
		Map<String,Boolean> genreExpectation = getGenreExpectation();
		for(String genre:genreExpectation.keySet()){
			try {
				listFilms = filmDAO.findFilmByGenre(genre);
				assertEquals(genreExpectation.get(genre),!listFilms.isEmpty());
			} catch (FilmStoreDAOException e) {
				fail("Test fails because of error connection with database!");
			} 
		}
	}

	private static Film createTestFilm() {
		String name = "filmTest";
		String genre = "Комедия";
		String country = "США,Россия";
		short yearOfRelease = 2015;
		short countFilms = 8;
		Quality quality = Quality.WEBDL;
		String description = "description";
		BigDecimal price = new BigDecimal(155000);
		String image = "images/1.jpg";
		short filmDirId = 1;
		Film film = new Film(name, genre, country, yearOfRelease, quality, description, price, countFilms, image);
		FilmDirector filmDirector = new FilmDirector();
		filmDirector.setId((short) (filmDirId));
		film.setFilmDirector(filmDirector);
		return film;
	}

	private static void createTestFilmActor(FilmDAO filmDAO, short filmId)
			throws FilmStoreDAOException, FilmStoreDAOInvalidOperationException {
		List<Short> idActors = Arrays.asList((short) 5, (short) 7, (short) 10);
		filmDAO.createFilmActor(filmId, idActors);
	}

	private static Map<Short, List<Short>> getIdFilmIdActor() {
		short incorrectFilmId = 1000;
		short incorrectActorId = 1000;
		short correctActorId = 1;

		List<Short> incorActorId = new ArrayList<Short>();
		incorActorId.add(incorrectActorId);
		List<Short> corActorId = new ArrayList<>();
		corActorId.add(correctActorId);

		Map<Short, List<Short>> idFilmIdActor = new HashMap<>();

		idFilmIdActor.put(incorrectFilmId, incorActorId);
		idFilmIdActor.put(film.getId(), incorActorId);
		idFilmIdActor.put(incorrectFilmId, corActorId);

		return idFilmIdActor;
	}

	private List<Short> getActorId(Film film) {
		int index = 0;
		List<Actor> listActor = film.getActors();
		Actor actor = listActor.get(index);
		return Arrays.asList(actor.getId());
	}

	private Map<String,Boolean> getGenreExpectation(){
		Map<String,Boolean> genreExcpectation = new HashMap<>();
		genreExcpectation.put("Коммммедия", false);
		genreExcpectation.put("Комедя", false);
		genreExcpectation.put(" Комедия",false);
		return genreExcpectation;
	}

}
