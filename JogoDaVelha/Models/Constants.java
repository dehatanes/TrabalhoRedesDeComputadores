package Models;

public class Constants {

    // STATUS CONSTANTS
    public static final int UNKNOWN_STATUS = 999;

    public static final int STATUS_NEW_CLIENT = 101;
    public static final int STATUS_CLIENT_CREATED = 102;
    public static final int STATUS_I_AM_ALIVE = 103;
    public static final int STATUS_USERNAME_UNAVAILABLE = 104;
    public static final int STATUS_CLIENT_LOGOUT = 109;

    public static final int STATUS_NEW_MULT_GAME = 201;
    public static final int STATUS_NEW_SINGL_GAME = 202;
    public static final int STATUS_EXISTING_GAME = 209;

    public static final int STATUS_NEXT_TURN = 211;
    public static final int STATUS_LIST_GAMES = 212;
    public static final int STATUS_GAME_OVER = 219;

    public static final int STATUS_ERRO = 666;


    // APPLICATION CONSTANTS
    public static final int TIMEOUT_TO_BE_DEAD = 4000;   // 4 min
    public static final int KEEP_ALIVE_TIMER = 500;      // 1/2 min
    public static final int SERVER_SOCKET = 6031;

}
