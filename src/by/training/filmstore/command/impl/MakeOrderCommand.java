package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CheckUserRoleUtil;
import by.training.filmstore.command.util.ConvertStringToIntUtil;
import by.training.filmstore.command.util.CookieUtil;

import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.GoodOfOrder;
import by.training.filmstore.entity.GoodOfOrderPK;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.OrderService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectOrderParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmOperException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOrderOperException;

public class MakeOrderCommand implements Command {

	private final static Logger logger = LogManager.getLogger(MakeOrderCommand.class);

	private final static String STATUS = "status";
	private final static String COMMON_PRICE = "commonPrice";
	private final static String KIND_OF_DELIVERY = "kindOfDelivery";
	private final static String PAYMENT = "payment";
	private final static String ADDRESS = "address";
	private final static String DATE_OF_DELIVERY = "dateOfDelivery";
	private final static String LIST_FILMS = "listFilm"; 

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession sessionCheckRole = request.getSession(false);
		if(CheckUserRoleUtil.isGuest(sessionCheckRole)){
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			return;
		}

		List<GoodOfOrder> listGoods = null;
		List<Film> listFilms = null;

		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		OrderService orderService = filmStoreServiceFactory.getOrderService();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		
		try {

			String prefix = CommandParamName.COOKIE_PREFIX_FOR_ORDER;
			listGoods = makeListGoodFromCookie(request, prefix);
			createOrder(request,orderService,sessionCheckRole,listGoods);
			
			listFilms = extractFilmFromObject(sessionCheckRole.getAttribute(LIST_FILMS));
			updateFilmCount(listFilms, filmService, request);

			CookieUtil.removeOrderCookie(request, response, prefix);
			sessionCheckRole.setAttribute(LIST_FILMS, null);
			sessionCheckRole.setAttribute(CommandParamName.COUNT_FILMS_IN_BASKET,0);
			
			request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectOrderParamException 
				|FilmStoreServiceIncorrectFilmParamException e) {
			logger.error("Invalid parametrs!", e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidOrderOperException
				|FilmStoreServiceInvalidFilmOperException e) {
			logger.error("Entity creation failed!", e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}catch (FilmStoreServiceException e) {
			logger.error("Operation failed!Can't make order!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}

	private void createOrder(HttpServletRequest request,OrderService orderService,HttpSession session,List<GoodOfOrder> listGoods) 
			throws FilmStoreServiceException, FilmStoreServiceInvalidOrderOperException, FilmStoreServiceIncorrectOrderParamException {
		String userEmail = (String)session.getAttribute(CommandParamName.USER_EMAIL);
		String commonPrice = request.getParameter(COMMON_PRICE);
		String status = request.getParameter(STATUS);
		String kindOfDelivery = request.getParameter(KIND_OF_DELIVERY);
		String payment = request.getParameter(PAYMENT);
		String address = request.getParameter(ADDRESS);
		String dateOfDelivery = request.getParameter(DATE_OF_DELIVERY);
		orderService.create(userEmail, commonPrice, status, kindOfDelivery, payment, dateOfDelivery, address, listGoods);

	}
	
	private List<GoodOfOrder> makeListGoodFromCookie(HttpServletRequest request, String prefix) {
		List<GoodOfOrder> listGoods = new ArrayList<>();
		Cookie[] cookies = request.getCookies();
		String replacement = "";
		String idFilmStr;
		byte countFilms = 0;
		short idFilm = -1;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().contains(prefix)) {

				idFilmStr = cookies[i].getName().replace(prefix, replacement);
				countFilms = (byte) ConvertStringToIntUtil.convert(cookies[i].getValue());
				idFilm = (short) ConvertStringToIntUtil.convert(idFilmStr);

				GoodOfOrderPK goodPK = new GoodOfOrderPK();
				goodPK.setIdFilm(idFilm);
				listGoods.add(new GoodOfOrder(goodPK, countFilms));
			}
		}
		return listGoods;
	}

	private List<Film> extractFilmFromObject(Object object) {
		List<Film> films = new ArrayList<Film>();

		if (object instanceof Iterable<?>) {
			Iterable<?> iterable = (Iterable<?>) object;
			for (Object i : iterable) {
				if (i instanceof Film) {
					films.add((Film) i);
				}
			}
		}
		return films;
	}
	
	private void updateFilmCount(List<Film> listFilms, FilmService filmService, HttpServletRequest request)
			throws FilmStoreServiceIncorrectFilmParamException, FilmStoreServiceInvalidFilmOperException,
			FilmStoreServiceException {
		Map<Short, Short> mapIdFilmCountFilm = CookieUtil.getMapIdCountFromCookie(request,
				CommandParamName.COOKIE_PREFIX_FOR_ORDER);
		for (Film film : listFilms) {
			short countOrderedFilm = mapIdFilmCountFilm.get(film.getId());
			short countCurrentFilm = film.getCountFilm();
			film.setCountFilm((short) (countCurrentFilm - countOrderedFilm));
			filmService.update(Short.toString(film.getId()), film.getName(), film.getGenre(), film.getCountry(),
					Short.toString(film.getYearOfRelease()), film.getQuality().getNameQuality(),
					Short.toString(film.getFilmDirector().getId()), film.getDescription(), film.getPrice().toString(),
					Short.toString(film.getCountFilm()), film.getImage());
		}
	}
}
