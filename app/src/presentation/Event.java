package presentation;

public class Event {
	// Player
	public static final int CREATE_PLAYER = 0;
	public static final int RES_CREATE_PLAYER_SUCCESS = 1;
	public static final int RES_CREATE_PLAYER_FAILURE = 2;
	public static final int UPDATE_PLAYER = 3;
	public static final int RES_UPDATE_PLAYER_SUCCESS = 4;
	public static final int RES_UPDATE_PLAYER_FAILURE = 5;
	public static final int DELETE_PLAYER = 6;
	public static final int RES_DELETE_PLAYER_SUCCESS = 7;
	public static final int RES_DELETE_PLAYER_FAILURE = 8;

	// Sign-up
	public static final int SIGN_UP = 9;
	public static final int RES_SIGN_UP_SUCCESS = 10;
	public static final int RES_SIGN_UP_FAILURE = 11;
	// Login
	public static final int LOGIN = 12;
	public static final int RES_LOGIN_SUCCESS = 13;
	public static final int RES_LOGIN_STAFF_SUCCESS = 14;
	public static final int RES_LOGIN_FAILURE = 15;
	
	// Player navigation
	public static final int OPEN_GAMES_PANEL = 16;
	public static final int OPEN_INVENTORY = 17;
	public static final int OPEN_STORE = 18;
	public static final int LOGOUT = 19;
	// Staff navigation
	public static final int OPEN_PACKAGE_MANAGER = 20;
	public static final int CLOSE_PACKAGE_MANAGER = 21;

	// Blackjack
	public static final int BLACKJACK_PLAY = 22;
	public static final int BLACKJACK_HIT = 23;
	public static final int BLACKJACK_STAND = 24;
	// Roulette
	public static final int ROULETTE_PLAY = 25;
	public static final int ROULETTE_ROLL = 26;	
	
	// Store
	public static final int BUY_PACKAGE = 27;
	public static final int BUY_PRODUCT = 28;

	// Game
	public static final int CREATE_GAME = 29;
	// Package
	public static final int CREATE_PACKAGE = 30;
	public static final int UPDATE_PACKAGE = 31;
	public static final int DELETE_PACKAGE = 32;
	// Product
	public static final int CREATE_PRODUCT = 33;
	public static final int UPDATE_PRODUCT = 34;
	public static final int DELETE_PRODUCT = 35;
}
