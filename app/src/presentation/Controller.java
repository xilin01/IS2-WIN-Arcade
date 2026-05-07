package presentation;

import java.util.Collection;
import business.auth.AuthResult;
import business.auth.SAAuth;
import business.game.Blackjack;
import business.game.Roulette;
import business.game.Roulette.Bet.tBet;
import business.game.SAGame;
import business.game.tGame;
import business.packages.SAGamesPackage;
import business.packages.tGamesPackage;
import business.player.SAInventory;
import business.player.SAPlayer;
import business.player.tPlayer;
import business.product.SAProduct;
import business.product.tProduct;
import business.ServiceLocator;
import business.auth.tCredentials;
import business.store.SAStore;
import presentation.games.BlackjackGUI;
import presentation.games.RouletteGUI;
import presentation.staff.playersManager.UserView;
import presentation.user.auth.LoginView;
import presentation.user.auth.SignUpView;

// Patrón Singleton
public class Controller {    
	private static final Controller INSTANCE = new Controller();
	 
	private String username;
	private record BlackjackSession(Blackjack blackjack, BlackjackGUI gui) {}
	private record RouletteSession(int basePoints, RouletteGUI gui) {}
	private BlackjackSession blackjackSession;
	private RouletteSession rouletteSession;
	
	private Controller() {
	}
	
	public static Controller getInstance() {
		return INSTANCE;
	}
	
	public String getUsername() {
		return username;
	}

    public void action(final int event, final Object data) {
        switch(event) {
            case Event.CREATE_PLAYER: {
            	int res = ServiceLocator.get(SAPlayer.class).createPlayer((tPlayer)data);
                
                viewResult(res > 0, UserView::update, Event.RES_CREATE_PLAYER_SUCCESS, res, Event.RES_CREATE_PLAYER_FAILURE);
                break;
            }
            case Event.UPDATE_PLAYER: {
            	int res = ServiceLocator.get(SAPlayer.class).updatePlayer((tPlayer)data);
               
                viewResult(res > 0, UserView::update, Event.RES_UPDATE_PLAYER_SUCCESS, data, Event.RES_UPDATE_PLAYER_FAILURE);
                break;
           }
            case Event.DELETE_PLAYER: {
                int res = ServiceLocator.get(SAPlayer.class).deletePlayer((String)data);
                
                viewResult(res > 0, UserView::update, Event.RES_DELETE_PLAYER_SUCCESS, data, Event.RES_DELETE_PLAYER_FAILURE);
                break;
            }
            case Event.SIGN_UP: {
                tCredentials creds = (tCredentials)data;
                int res = ServiceLocator.get(SAPlayer.class).createPlayer(creds.name(), creds.password());
                
                viewResult(res > 0, SignUpView::update, Event.RES_SIGN_UP_SUCCESS, null, Event.RES_SIGN_UP_FAILURE);
                break;
            }
            case Event.LOGIN: {
            	SAAuth saAuth = ServiceLocator.get(SAAuth.class);
                tCredentials credentials = (tCredentials)data;
                AuthResult result = saAuth.authenticate(credentials.name(), credentials.password());
                
                if(result.getOutcome() != AuthResult.Outcome.FAILURE) username = (String)result.getPayload();
                int loginEvent = switch(result.getOutcome()) {
                    case STAFF_OK -> Event.RES_LOGIN_STAFF_SUCCESS;
                    case PLAYER_OK -> Event.RES_LOGIN_SUCCESS;
                    case FAILURE -> Event.RES_LOGIN_FAILURE;
                };
                LoginView.update(loginEvent, result.getPayload());
                break;
            }
            case Event.LOGOUT: {
            	username = null;
                LoginView.open();
                break;
            }
            case Event.BLACKJACK_PLAY: {
                int basePoints = (Integer)data;
                ServiceLocator.get(SAInventory.class).consumeGame(username, new tGame(tGame.BLACKJACK, basePoints));
                Blackjack blackjack = new Blackjack(basePoints);
                blackjack.play(); 
                BlackjackGUI gui = GUIFactory.createBlackjackView(blackjack);
                blackjackSession = new BlackjackSession(blackjack, gui);
                break;
            }
            case Event.BLACKJACK_HIT: {
                if(blackjackSession == null) {
                	System.err.println("BLACKJACK_HIT sin partida activa");
                	break;
                }
                Blackjack bj = blackjackSession.blackjack();
                BlackjackGUI bjGui = blackjackSession.gui();
                Blackjack.GameResult res = bj.playerHit();
                bjGui.update(bj.getCroupierCards(), bj.getPlayerCards(), bj.getCroupierPoints(), bj.getPlayerPoints(), false);
                if(res != Blackjack.GameResult.CONTINUE) {
                    if(res == Blackjack.GameResult.WON) {
                        ServiceLocator.get(SAPlayer.class).addPoints(username, bj.getPointsWon());
                        bjGui.end("¡Victoria, has ganado " + bj.getPointsWon() + " puntos!");
                    }
                    else bjGui.end("Te has pasado de 21...");
                }
                break;
            }
            case Event.BLACKJACK_STAND: {
                if(blackjackSession == null) {
                	System.err.println("BLACKJACK_STAND sin partida activa");
                	break;
                }
                Blackjack bj = blackjackSession.blackjack();
                BlackjackGUI bjGui = blackjackSession.gui();
                if(bj.getCroupierPoints() < bj.getPlayerPoints()) bj.croupierPlay();
                bjGui.update(bj.getCroupierCards(), bj.getPlayerCards(), bj.getCroupierPoints(), bj.getPlayerPoints(), true);
                switch(bj.getResult()) {
	                case LOST: bjGui.end("Lo siento, gana el croupier..."); break;
	                case CONTINUE: bjGui.end("Empate"); break;
	                case WON:
	                	ServiceLocator.get(SAPlayer.class).addPoints(username, bj.getPointsWon());
	                    bjGui.end("¡Felicidades, has ganado " + bj.getPointsWon() + " puntos!");
	                    break;
                }
                break;
            }
            case Event.ROULETTE_PLAY: {
                int basePoints = (Integer)data;
                ServiceLocator.get(SAInventory.class).consumeGame(username, new tGame(tGame.ROULETTE, basePoints));
                rouletteSession = new RouletteSession(basePoints, GUIFactory.createRouletteView());
                break;
            }
            case Event.ROULETTE_ROLL: {
                if(rouletteSession == null) {
                	System.err.println("ROULETTE_ROLL sin partida activa");
                	break;
                }
                Roulette roulette = new Roulette(rouletteSession.basePoints(), (tBet)data);
                int points = roulette.play();
                if(points > 0) ServiceLocator.get(SAPlayer.class).addPoints(username, points);
                rouletteSession.gui().showResult(points, roulette.getWinningNumber());
                break;
            }
            case Event.BUY_PACKAGE: {
                tGamesPackage pack = (tGamesPackage)data;
                if(ServiceLocator.get(SAStore.class).buyPackage(username, pack)) Theme.getInstance().showInfo(null, "¡" + pack.getName() + " comprado con éxito!");
                else Theme.getInstance().showError(null, "No se pudo completar la compra");
                break;
            }
            case Event.BUY_PRODUCT: {
                tProduct prod = (tProduct)data;
                if(ServiceLocator.get(SAStore.class).buyProduct(username, prod)) Theme.getInstance().showInfo(null, "¡" + prod.getName() + " canjeado con éxito!");
                else Theme.getInstance().showError(null, "No tienes suficientes puntos para canjear este producto");
                break;
            }
            case Event.OPEN_PLAYER_VIEW:
            case Event.OPEN_GAMES_PANEL:
            case Event.OPEN_INVENTORY:
            case Event.OPEN_STORE:
            case Event.OPEN_STAFF_VIEW:
            case Event.OPEN_PACKAGE_MANAGER:
            case Event.OPEN_PRODUCT_MANAGER:
            case Event.OPEN_PLAYER_MANAGER: {
            	try {
            		GUIFactory.createView(event);
            	}
                catch(IllegalArgumentException e) {
                	System.err.println(e.getMessage());
                }
                break;
        	}
            default: System.err.println("Unknown event: " + event); break;
        }
    }

    public int getPlayerPoints(String name) {
        if(name == null || name.trim().isEmpty()) return 0;
        tPlayer player = ServiceLocator.get(SAPlayer.class).readPlayer(name);
        return player != null ? player.getPoints() : 0;
    }

    public tPlayer getPlayer(String name) {
        if(name == null || name.trim().isEmpty()) return null;
        return ServiceLocator.get(SAPlayer.class).readPlayer(name);
    }

    public Collection<tPlayer> getAllPlayers() {
        return ServiceLocator.get(SAPlayer.class).readAllPlayers();
    }

    public Collection<tGamesPackage> getAllPackages() {
        return ServiceLocator.get(SAGamesPackage.class).readAllGamesPackages();
    }

    public Collection<tProduct> getAllProducts() {
        return ServiceLocator.get(SAProduct.class).readAllProducts();
    }

    public Collection<tGame> getAllGames() {
        return ServiceLocator.get(SAGame.class).readAllGames();
    }

    private static void viewResult(boolean ok, ViewUpdater view, int okEvent, Object okPayload, int failEvent) {
        if(ok) view.update(okEvent, okPayload);
        else view.update(failEvent, null);
    }
}
