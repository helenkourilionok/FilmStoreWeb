package by.training.filmstore.service.impl;

import java.util.List;

import by.training.filmstore.command.util.ConvertStringToIntUtil;
import by.training.filmstore.dao.FilmStoreDAOFactory;
import by.training.filmstore.dao.GoodOfOrderDAO;
import by.training.filmstore.dao.exception.FilmStoreDAOException;
import by.training.filmstore.entity.GoodOfOrder;
import by.training.filmstore.entity.GoodOfOrderPK;
import by.training.filmstore.service.GoodOfOrderService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectGoodParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidGoodOperException;

public class GoodOfOrderServiceImpl implements GoodOfOrderService {

	@Override
	public void create(String idOrder, String idFilm, String countFilms)
			throws FilmStoreServiceIncorrectGoodParamException, FilmStoreServiceInvalidGoodOperException,
			FilmStoreServiceException {
		GoodOfOrder goodOfOrder = createGoodOfOrder(idOrder, idFilm, countFilms);
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		GoodOfOrderDAO goodOfOrderDAO = filmStoreDAOFactory.getGoodOfOrderDAO();
		try {
			if(!goodOfOrderDAO.create(goodOfOrder)){
				throw new FilmStoreServiceInvalidGoodOperException("Operation failed!Can't create good of order!");
			}
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
	}

	@Override
	public List<GoodOfOrder> findGoodByOrderId(String id)
			throws FilmStoreServiceException, FilmStoreServiceIncorrectGoodParamException {
		int _id = ValidationParamUtil.validateNumber(id);
		if(_id==-1){
			throw new FilmStoreServiceIncorrectGoodParamException("Incorrect film id!");
		}
		
		List<GoodOfOrder> listGoods = null;
		
		FilmStoreDAOFactory filmStoreDAOFactory = FilmStoreDAOFactory.getDAOFactory();
		GoodOfOrderDAO goodOfOrderDAO = filmStoreDAOFactory.getGoodOfOrderDAO();
		
		try {
			listGoods = goodOfOrderDAO.findGoodByOrderId(_id);
		} catch (FilmStoreDAOException e) {
			throw new FilmStoreServiceException(e);
		}
		
		return listGoods;
	}
	
	private GoodOfOrder createGoodOfOrder(String idOrder, String idFilm, String countFilms) 
			throws FilmStoreServiceIncorrectGoodParamException{
		int _idOrder = ConvertStringToIntUtil.getIntFromString(idOrder);
		if(_idOrder == -1){
			throw new FilmStoreServiceIncorrectGoodParamException("Invalid order id!");
		}
		short _idFilm = (short)ConvertStringToIntUtil.getIntFromString(idFilm);
		if(_idFilm == -1){
			throw new FilmStoreServiceIncorrectGoodParamException("Invalid film id!");
		}
		byte _countFilms = (byte)ConvertStringToIntUtil.getIntFromString(countFilms);
		if(_countFilms==-1){
			throw new FilmStoreServiceIncorrectGoodParamException("");
		}
		GoodOfOrderPK goodOfOrderPK = new GoodOfOrderPK();
		goodOfOrderPK.setIdFilm(_idFilm);
		goodOfOrderPK.setIdOrder(_idOrder);
		return new GoodOfOrder(goodOfOrderPK,_countFilms);
	}
}
