package by.training.filmstore.command.impl;

public final class CommandParamName {
	final static String COOKIE_PREFIX_FOR_ORDER = "orderFilm";
	final static String COUNT_FILMS_IN_BASKET = "countFilmInBasket";
	final static String LANGUAGE = "language";
	final static String USER_EMAIL = "userEmail";
	final static String USER_ROLE = "userRole";
	final static String PREV_QUERY = "prev_query";
	final static String LOCALE = "locale";
	/*****Path to jsp pages************/
	final static String PATH_PAGE_INDEX = "index.jsp";
	final static String PATH_PAGE_INDEX_REDIRECT = "index_redirect.jsp";
	final static String PATH_PAGE_LOGIN = "jsp/login.jsp";
	final static String PATH_PAGE_SIGNUP = "jsp/signup.jsp";
	final static String PATH_ERROR_PAGE = "error-page.jsp";
	final static String PATH_FILM_WITH_COMMENT_PAGE="jsp/film.jsp";
		/******Path to admin jsp pages*****/
		final static String PATH_CREATE_FILM_PAGE = "jsp/filmCreate.jsp";
		final static String PATH_UPDATE_FILM_PAGE = "jsp/filmUpdate.jsp";
		final static String PATH_LIST_FILM_PAGE = "jsp/listFilm.jsp";
		/******Path to admin jsp pages*****/
	/*****Path to jsp pages************/
	final static String[] listCountries = {"США","Англия","Япония",
									"Китай","Италия","Франция","Россия",
									"Германия","Канада","Испания"};
	final static String[] listGenres = {"Драма","Комедия","Мелодрама","Триллер",
									"Криминал","Детектив","Фантастика","Боевик","Ужасы","Биография"
									,"Мистика","Мультфильм","Исторический","Документальный"};
	final static String[] listQuality = {"DVDRip","CAMPRip","WEB-DL","WEB-DLRip"};
	final static int MAX_AGE_COOKIE = 3600 * 24 * 30;
}
