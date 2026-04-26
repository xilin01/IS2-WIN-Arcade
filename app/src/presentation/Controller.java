package presentation;

import java.util.Collection;
import java.util.Map;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import business.auth.AuthResult;
import business.auth.SAAuth;
import business.game.Blackjack;
import business.game.Roulette;
import business.game.Roulette.Bet.tBet;
import business.game.tGame;
import business.player.SAPlayer;
import business.player.tPlayer;
import business.ServiceLocator;
import business.user.SAUser;
import business.user.tUser;
import presentation.auth.LoginView;
import presentation.auth.LoginView.LoginCredentials;
import presentation.auth.SignUpView;
import presentation.auth.SignUpView.SignUpCredentials;
import presentation.games.BlackjackGUI;
import presentation.games.GamesPanelView;
import presentation.games.RouletteGUI;
import presentation.packages.PackageManagerView;
import presentation.user.StaffView;
import presentation.user.UserView;

// Patrón Singleton
@objid ("89dcc83d-dee8-45f7-aee4-45b7f33b671f")
public class Controller {    
	private static final Controller INSTANCE = new Controller();
	 
	private String currUser;
	private BlackjackGUI blackJackGUI;
	private Blackjack blackjack;
	private RouletteGUI rouletteGUI;
	private Roulette roulette;
	private int basePoints;
	
	private Controller() {
	}
	
	public static Controller getInstance() {
		return INSTANCE;
	}
	
	public String getCurrentUser() {
		return currUser;
	}

    @objid ("7d6f7991-228b-4d7a-a5ff-094d1cd303bf")
    public void action(final int event, final Object data) {
        switch(event) {
            case Event.CREATE_USER: {
                int res = ServiceLocator.get(SAUser.class).createUser((tUser)data);
                
                viewResult(res > 0, UserView::update, Event.RES_CREATE_USER_SUCCESS, res, Event.RES_CREATE_USER_FAILURE);
                break;
            }
            case Event.LIST_USERS: {
                Collection<tUser> res = ServiceLocator.get(SAUser.class).readAllUsers();
                
                viewResult(!res.isEmpty(), UserView::update, Event.RES_LIST_USERS_SUCCESS, res, Event.RES_LIST_USERS_FAILURE);
                break;
            }
            case Event.FIND_USER: {
                tUser user = ServiceLocator.get(SAUser.class).readUser((String)data);
                
                viewResult(user != null, UserView::update, Event.RES_FIND_USER_SUCCESS, user, Event.RES_FIND_USER_FAILURE);
                break;
            }
            case Event.DELETE_USER: {
                int res = ServiceLocator.get(SAUser.class).deleteUser((String)data);
                
                viewResult(res > 0, UserView::update, Event.RES_DELETE_USER_SUCCESS, data, Event.RES_DELETE_USER_FAILURE);
                break;
            }
            case Event.UPDATE_USER: {
                int res = ServiceLocator.get(SAUser.class).updateUser((tUser)data);
                
                viewResult(res > 0, UserView::update, Event.RES_UPDATE_USER_SUCCESS, data, Event.RES_UPDATE_USER_FAILURE);
                break;
            }
            case Event.LOGIN: {
            	SAAuth saAuth = ServiceLocator.get(SAAuth.class);
                LoginCredentials credentials = (LoginCredentials)data;
                AuthResult result = saAuth.authenticate(credentials.name(), credentials.password());
                
                if(result.getOutcome() != AuthResult.Outcome.FAILURE) currUser = (String)result.getPayload();
                int loginEvent = switch (result.getOutcome()) {
                    case STAFF_OK -> Event.RES_LOGIN_STAFF_SUCCESS;
                    case PLAYER_OK -> Event.RES_LOGIN_SUCCESS;
                    case FAILURE -> Event.RES_LOGIN_FAILURE;
                };
                LoginView.update(loginEvent, result.getPayload());
                break;
            }
            case Event.SIGN_UP: {
                SignUpCredentials creds = (SignUpCredentials)data;
                int res = ServiceLocator.get(SAPlayer.class).createPlayer(creds.name(), creds.password());
                
                viewResult(res > 0, SignUpView::update, Event.RES_SIGN_UP_SUCCESS, null, Event.RES_SIGN_UP_FAILURE);
                break;
            }
            case Event.LOGOUT: {
            	currUser = null;
                LoginView.open();
                break;
            }
            case Event.OPEN_PACKAGE_MANAGER: {
                String username = (String)data;
                
                new PackageManagerView(username);
                break;
            }
            case Event.CLOSE_PACKAGE_MANAGER: {
                String username = (String)data;
                
                new StaffView(username);
                break;
            }
            case Event.OPEN_STORE: {
                System.err.println("Tienda: aún no implementada");
                break;
            }
            case Event.OPEN_INVENTORY: {
                System.err.println("Inventario: aún no implementado");
                break;
            }
            case Event.GAMES_PANEL: {
                new GamesPanelView(currUser);
                break;
            }
            case Event.BLACKJACK_PLAY: {
            	basePoints = (Integer)data;
                useGame(new tGame("Blackjack", basePoints));
                blackjack = new Blackjack(basePoints);
                blackJackGUI = new BlackjackGUI(blackjack.getCroupierVisibleCard(), blackjack.getPlayerCards(), blackjack.play(), blackjack.getPlayerPoints());
                break;
            }
            case Event.BLACKJACK_HIT: {
                int res = blackjack.playerHit();
                blackJackGUI.update(blackjack.getCroupierCards(), blackjack.getPlayerCards(), blackjack.getCroupierPoints(), blackjack.getPlayerPoints(), false);
                if(res != Blackjack.CONTINUE) {
                    if(res == Blackjack.WON) {
                        addPoints(blackjack.getPointsWon());
                        blackJackGUI.end("¡Victoria, has ganado " + blackjack.getPointsWon() + " puntos!");
                    } 
                    else blackJackGUI.end("Te has pasado de 21...");
                }
                break;
            }
            case Event.BLACKJACK_STAND: {
                if(blackjack.getCroupierPoints() < blackjack.getPlayerPoints()) blackjack.croupierPlay();
                blackJackGUI.update(blackjack.getCroupierCards(), blackjack.getPlayerCards(), blackjack.getCroupierPoints(), blackjack.getPlayerPoints(), true);
                switch(blackjack.getResult()) {
	                case Blackjack.LOST: blackJackGUI.end("Lo siento, gana el croupier..."); break;
	                case Blackjack.CONTINUE: blackJackGUI.end("Empate"); break;
	                case Blackjack.WON: 
	                	addPoints(blackjack.getPointsWon());
	                    blackJackGUI.end("¡Felicidades, has ganado " + blackjack.getPointsWon() + " puntos!");
	                    break;
                }
                break;
            }
            case Event.ROULETTE_PLAY: {
                basePoints = (Integer)data;
                useGame(new tGame("Ruleta", basePoints));
                rouletteGUI = new RouletteGUI();
                break;
            }
            case Event.ROULETTE_ROLL: {
                roulette = new Roulette(basePoints, (tBet)data);
                int points = roulette.play();
                if(points > 0) addPoints(points);
                rouletteGUI.showResult(points, roulette.getWinningNumber());
                break;
            }
            default: System.err.println("Aún no esta implementado"); break;
        }
    }

    private static void viewResult(boolean ok, ViewUpdater view, int okEvent, Object okPayload, int failEvent) {
        if(ok) view.update(okEvent, okPayload);
        else view.update(failEvent, null);
    }
    
    private void useGame(tGame game) {
        SAPlayer saPlayer = ServiceLocator.get(SAPlayer.class);
        tPlayer player = saPlayer.readPlayer(currUser);
        
        if(!player.getInventory().getGames().isEmpty()) {
            Map<tGame, Integer> games = player.getInventory().getGames();
            int amount = games.get(game);
            
            if(amount >= 2) games.put(game, amount - 1);
            else games.remove(game);
            saPlayer.updateInventory(player.getInventory());
        }
    }
    
    private void addPoints(int points) {
        SAPlayer saPlayer = ServiceLocator.get(SAPlayer.class);
        tPlayer player = saPlayer.readPlayer(currUser);
        
        if(player != null) {
            player.setPoints(player.getPoints() + points);
            saPlayer.updatePlayer(player);
        }
    }
}
