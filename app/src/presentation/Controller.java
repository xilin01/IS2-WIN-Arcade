package presentation;

import java.util.Collection;
import javax.swing.JOptionPane;
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
import business.user.SAUser;
import business.user.tUser;
import business.auth.tLoginCredentials;
import business.auth.tSignUpCredentials;
import business.store.SAStore;
import presentation.auth.LoginView;
import presentation.auth.SignUpView;
import presentation.games.BlackjackGUI;
import presentation.games.GamesPanelView;
import presentation.games.RouletteGUI;
import presentation.packages.PackageManagerView;
import presentation.user.InventoryView;
import presentation.user.StaffView;
import presentation.user.StoreView;
import presentation.user.UserView;

// Patrón Singleton
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
                tLoginCredentials credentials = (tLoginCredentials)data;
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
                tSignUpCredentials creds = (tSignUpCredentials)data;
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
                new StoreView(currUser);
                break;
            }
            case Event.BUY_PACKAGE: {
                tGamesPackage pack = (tGamesPackage)data;
                boolean ok = ServiceLocator.get(SAStore.class).buyPackage(currUser, pack);
                if (ok) JOptionPane.showMessageDialog(null, "¡" + pack.getName() + " comprado con éxito!", "Compra exitosa", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(null, "No se pudo completar la compra.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }
            case Event.BUY_PRODUCT: {
                tProduct prod = (tProduct)data;
                boolean ok = ServiceLocator.get(SAStore.class).buyProduct(currUser, prod);
                if (ok) JOptionPane.showMessageDialog(null, "¡" + prod.getName() + " canjead@ con éxito!", "Canje exitoso", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(null, "No tienes suficientes puntos para canjear este producto.", "Puntos insuficientes", JOptionPane.ERROR_MESSAGE);
                break;
            }
            case Event.OPEN_INVENTORY: {
                new InventoryView(currUser);
                break;
            }
            case Event.GAMES_PANEL: {
                new GamesPanelView(currUser);
                break;
            }
            case Event.BLACKJACK_PLAY: {
                basePoints = (Integer)data;
                ServiceLocator.get(SAInventory.class).consumeGame(currUser, new tGame("Blackjack", basePoints));
                blackjack = new Blackjack(basePoints);
                int croupierVisiblePoints = blackjack.play();
                blackJackGUI = new BlackjackGUI(blackjack.getCroupierVisibleCard(), blackjack.getPlayerCards(), croupierVisiblePoints, blackjack.getPlayerPoints());
                break;
            }
            case Event.BLACKJACK_HIT: {
                if (blackjack == null || blackJackGUI == null) { System.err.println("BLACKJACK_HIT sin partida activa"); break; }
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
                if (blackjack == null || blackJackGUI == null) { System.err.println("BLACKJACK_STAND sin partida activa"); break; }
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
                ServiceLocator.get(SAInventory.class).consumeGame(currUser, new tGame("Ruleta", basePoints));
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
            case Event.CREATE_PACKAGE: { ServiceLocator.get(SAGamesPackage.class).createGamesPackage((tGamesPackage)data); break; }
            case Event.UPDATE_PACKAGE: { ServiceLocator.get(SAGamesPackage.class).updateGamesPackage((tGamesPackage)data); break; }
            case Event.DELETE_PACKAGE: { ServiceLocator.get(SAGamesPackage.class).deleteGamesPackage((String)data); break; }
            case Event.CREATE_PRODUCT: { ServiceLocator.get(SAProduct.class).createProduct((tProduct)data); break; }
            case Event.UPDATE_PRODUCT: { ServiceLocator.get(SAProduct.class).updateProduct((tProduct)data); break; }
            case Event.DELETE_PRODUCT: { ServiceLocator.get(SAProduct.class).deleteProduct((String)data); break; }
            default: System.err.println("Aún no esta implementado"); break;
        }
    }

    public int getPlayerPoints(String username) {
        return ServiceLocator.get(SAPlayer.class).readPlayer(username).getPoints();
    }

    public tPlayer getPlayer(String username) {
        return ServiceLocator.get(SAPlayer.class).readPlayer(username);
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
    
    private void addPoints(int points) {
        SAPlayer saPlayer = ServiceLocator.get(SAPlayer.class);
        tPlayer player = saPlayer.readPlayer(currUser);
        
        if(player != null) {
            player.setPoints(player.getPoints() + points);
            saPlayer.updatePlayer(player);
        }
    }
}
