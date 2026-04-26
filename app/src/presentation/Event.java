package presentation;

public class Event {
	// User events
	public static final int CREATE_USER = 0;
	public static final int DELETE_USER = 1;
	public static final int LIST_USERS  = 2;
	public static final int UPDATE_USER = 3;
	public static final int FIND_USER   = 4;
	public static final int RES_CREATE_USER_SUCCESS = 5;
	public static final int RES_CREATE_USER_FAILURE = 6;
	public static final int RES_DELETE_USER_SUCCESS = 7;
	public static final int RES_DELETE_USER_FAILURE = 8;
	public static final int RES_FIND_USER_SUCCESS   = 9;
	public static final int RES_FIND_USER_FAILURE   = 10;
	public static final int RES_UPDATE_USER_SUCCESS = 11;
	public static final int RES_UPDATE_USER_FAILURE = 12;
	public static final int RES_LIST_USERS_SUCCESS  = 13;
	public static final int RES_LIST_USERS_FAILURE  = 14;

	// Auth events
	public static final int LOGIN                = 15;
	public static final int RES_LOGIN_SUCCESS    = 16;
	public static final int RES_LOGIN_STAFF_SUCCESS = 17;
	public static final int RES_LOGIN_FAILURE    = 18;
	public static final int LOGOUT               = 19;

	// Sign-up events
	public static final int SIGN_UP                 = 20;
	public static final int RES_SIGN_UP_SUCCESS     = 21;
	public static final int RES_SIGN_UP_FAILURE     = 22;

	// Navigation events
	public static final int OPEN_PACKAGE_MANAGER  = 23;
	public static final int CLOSE_PACKAGE_MANAGER = 24;
	
	// Games
	public static final int GAMES_PANEL = 25;
	// BlackJack
	public static final int BLACKJACK_PLAY = 26;
	public static final int BLACKJACK_HIT = 27;
	public static final int BLACKJACK_STAND = 28;
	// Roulette
	public static final int ROULETTE_PLAY = 29;
	public static final int ROULETTE_ROLL = 30;
	
	// Player navigation
	public static final int OPEN_STORE     = 31;
	public static final int OPEN_INVENTORY = 32;
}
