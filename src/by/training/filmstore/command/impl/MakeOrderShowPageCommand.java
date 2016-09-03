package by.training.filmstore.command.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CookieUtil;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.KindOfDelivery;
import by.training.filmstore.entity.KindOfPayment;
import by.training.filmstore.entity.Order;
import by.training.filmstore.entity.Status;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;

public class MakeOrderShowPageCommand implements Command {

	private final static Logger logger = LogManager.getLogger(MakeOrderShowPageCommand.class);
	
	private final static String ORDER = "order";
	private final static String COUNT_ORDERED_FILM = "countOrderedFilm";
	private final static String LIST_FILMS = "listFilm";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)||(sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_GUEST"))) {
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			return;
		}
		
		String query = QueryUtil.createHttpQueryString(request);
		sessionCheckRole.setAttribute(CommandParamName.PREV_QUERY, query);
	
		Map<Short,Short> mapIdFilmCountFilm = CookieUtil.getMapIdCountFromCookies(request, CommandParamName.COOKIE_PREFIX_FOR_ORDER);
		List<Film> listFilm = new ArrayList<>();
		boolean lazyInit = false;
		String userEmail = (String)sessionCheckRole.getAttribute(CommandParamName.USER_EMAIL);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		
		try{
			for(Short id:mapIdFilmCountFilm.keySet()){
					Film filmFromCookie = filmService.find(id.toString(), lazyInit);
					listFilm.add(filmFromCookie);
			}
			Order order = createOrder(userEmail, mapIdFilmCountFilm, listFilm,sessionCheckRole);
			
			Short[] countOrderedFilm = new Short[mapIdFilmCountFilm.size()];
			mapIdFilmCountFilm.values().toArray(countOrderedFilm);
	
			request.setAttribute(COUNT_ORDERED_FILM, countOrderedFilm);
			request.setAttribute(ORDER, order);
			
			sessionCheckRole.setAttribute(CommandParamName.COUNT_FILMS_IN_BASKET, CookieUtil.getCountGoodsInCookie(request,CommandParamName.COOKIE_PREFIX_FOR_ORDER));
			sessionCheckRole.setAttribute(LIST_FILMS, listFilm);
			
			request.getRequestDispatcher(CommandParamName.PATH_MAKE_ORDER_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}catch(FilmStoreServiceIncorrectFilmParamException e){
			logger.error("Can't find film!Incorrect film id!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}catch(FilmStoreServiceFilmNotFoundException e){
			logger.error("Film wasn't found with this id!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
		
	}

	private Order createOrder(String userEmail,Map<Short,Short> mapIdFilmCountFilm,List<Film> listFilm,HttpSession session){
		LocalDate dateOfDelivery = LocalDate.now();
		dateOfDelivery = dateOfDelivery.plusMonths(1);
		Long discount = ((Byte)session.getAttribute(CommandParamName.DISCOUNT)).longValue();
		Order order = new Order();
		order.setUserEmail(userEmail);
		order.setStatus(Status.UNPAID);
		order.setCommonPrice(calculateCommonPriceForOrder(mapIdFilmCountFilm, listFilm,discount));
		order.setKindOfDelivery(KindOfDelivery.SELFDELIVERY);
		order.setKindOfPayment(KindOfPayment.PAYMENT_IN_CASH);
		order.setAddress(CommandParamName.DEFAULT_ADDRESS_OF_DELIVERY);
		order.setDateOfDelivery(java.sql.Date.valueOf(dateOfDelivery));
		return order;
	}
	//!!!!!
	private BigDecimal calculateCommonPriceForOrder(Map<Short,Short> mapIdFilmCountFilm,List<Film> listFilm,Long discount){
		BigDecimal commonPrice = new BigDecimal(0);
		BigDecimal _discount = new BigDecimal(discount);
		BigDecimal hundred = new BigDecimal(100);
		for(Film film:listFilm){
			short countFilm = mapIdFilmCountFilm.get(film.getId());
			if(countFilm>film.getCountFilms()){
				countFilm = film.getCountFilms();
				mapIdFilmCountFilm.put(film.getId(), countFilm);
			}			
			BigDecimal buf = BigDecimal.valueOf(countFilm);
			commonPrice = commonPrice.add(film.getPrice().multiply(buf));
		}
		BigDecimal mul = commonPrice.multiply(_discount);
		BigDecimal intermResult = mul.divide(hundred);
		commonPrice = commonPrice.subtract(intermResult);
		return commonPrice;
	}

}
