package presentation;

public class Event {
	// User events
	public static final int CREATE_USER = 100;
	public static final int DELETE_USER = 101;
	public static final int LIST_USERS  = 102;
	public static final int UPDATE_USER = 103;
	public static final int FIND_USER   = 104;
	public static final int RES_CREATE_USER_SUCCESS = 400;
	public static final int RES_CREATE_USER_FAILURE = 401;
	public static final int RES_DELETE_USER_SUCCESS = 500;
	public static final int RES_DELETE_USER_FAILURE = 501;
	public static final int RES_FIND_USER_SUCCESS   = 600;
	public static final int RES_FIND_USER_FAILURE   = 601;
	public static final int RES_UPDATE_USER_SUCCESS = 700;
	public static final int RES_UPDATE_USER_FAILURE = 701;
	public static final int RES_LIST_USERS_SUCCESS  = 800;
	public static final int RES_LIST_USERS_FAILURE  = 801;

	// Auth events
	public static final int LOGIN                = 106;
	public static final int RES_LOGIN_SUCCESS    = 402;
	public static final int RES_LOGIN_STAFF_SUCCESS = 403;
	public static final int RES_LOGIN_FAILURE    = 404;
	public static final int LOGOUT               = 108;

	// Navigation events
	public static final int OPEN_PACKAGE_MANAGER  = 110;
	public static final int CLOSE_PACKAGE_MANAGER = 111;
}
