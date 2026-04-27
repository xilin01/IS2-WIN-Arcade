package presentation;

public class Event {
	
	// User
	public static final int CREATE_USER = 0;
	public static final int RES_CREATE_USER_SUCCESS = 1;
	public static final int RES_CREATE_USER_FAILURE = 2;
	public static final int DELETE_USER = 3;
	public static final int RES_DELETE_USER_SUCCESS = 4;
	public static final int RES_DELETE_USER_FAILURE = 5;
	public static final int LIST_USERS = 6;
	public static final int RES_LIST_USERS_SUCCESS = 7;
	public static final int RES_LIST_USERS_FAILURE = 8;
	public static final int UPDATE_USER = 9;
	public static final int RES_UPDATE_USER_SUCCESS = 10;
	public static final int RES_UPDATE_USER_FAILURE = 11;
	public static final int FIND_USER = 12;
	public static final int RES_FIND_USER_SUCCESS = 13;
	public static final int RES_FIND_USER_FAILURE = 14;

	// Login
	public static final int LOGIN = 15;
	public static final int RES_LOGIN_SUCCESS = 16;
	public static final int RES_LOGIN_STAFF_SUCCESS = 17;
	public static final int RES_LOGIN_FAILURE = 18;

	// Sign-up
	public static final int SIGN_UP = 19;
	public static final int RES_SIGN_UP_SUCCESS = 20;
	public static final int RES_SIGN_UP_FAILURE = 21;

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

	// Package
	public static final int CREATE_PACKAGE = 29;
	public static final int UPDATE_PACKAGE = 30;
	public static final int DELETE_PACKAGE = 31;

	// Product
	public static final int CREATE_PRODUCT = 32;
	public static final int UPDATE_PRODUCT = 33;
	public static final int DELETE_PRODUCT = 34;
}
