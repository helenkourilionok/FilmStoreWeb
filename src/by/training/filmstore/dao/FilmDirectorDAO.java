package by.training.filmstore.dao;

import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.FilmDirector;

public interface FilmDirectorDAO extends AbstractDAO<FilmDirector, Short>{
	FilmDirector findByFIO(String fio) throws FilmStoreDAOException;
}
