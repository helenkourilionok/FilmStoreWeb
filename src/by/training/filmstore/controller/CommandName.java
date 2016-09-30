package by.training.filmstore.controller;

public enum CommandName {
	CHANGE_LANGUAGE,
	SHOW_LIST_FILM,
	LOGINATION,
	LOGOUT,
	SIGN_UP,
	SIGN_UP_SHOW_PAGE, 
	LOGIN_SHOW_PAGE,
	INIT_POOL_CONNECTION, 
	DESTROY_POOL_CONNECTION, 
	SHOW_COMMENT_PAGE,
	MAKE_COMMENT,
	PUT_IN_BASKET,
	REMOVE_FILM_FROM_BASKET,
	MAKE_ORDER_SHOW_PAGE,
	MAKE_ORDER,
	PERSONAL_INFO_SHOW,
	ORDER_FOR_USER_SHOW,
	CHANGE_PASSWORD_SHOW_PAGE,
	CHANGE_PASSWORD,
	PAY_ORDER,
	ANNUL_ORDER,
	UPDATE_USER,
	FIND_FILM_BY_NAME,
	/*admin commands*/
	A_CREATE_FILM_SHOW_PAGE, 
	A_CREATE_FILM,
	A_UPDATE_FILM_SHOW_PAGE,
	A_UPDATE_FILM,
	A_DELETE_FILM,
	A_SHOW_LIST_FILM,
	A_MAKE_DISCOUNT_SHOW_PAGE,
	A_MAKE_DISCOUNT_SHOW_USER,
	A_MAKE_DISCOUNT,
	A_SHOW_LIST_ACTOR,
	A_CREATE_ACTOR_SHOW_PAGE,
	A_CREATE_ACTOR,
	A_CREATE_FILMDIR_SHOW_PAGE,
	A_CREATE_FILMDIR
}
