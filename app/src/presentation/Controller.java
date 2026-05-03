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
import presentation.auth.LoginView;
import presentation.auth.SignUpView;
import presentation.games.BlackjackGUI;
import presentation.games.GamesPanelView;
import presentation.games.RouletteGUI;
import presentation.packages.PackageManagerView;
import presentation.product.ProductManagerView;
import presentation.user.InventoryView;
import presentation.user.PlayerManagerView;
import presentation.user.PlayerView;
import presentation.user.StaffView;
import presentation.user.StoreView;
import presentation.user.UserView;

// Patrón Singleton
public class Controller {    
	private static final Controller INSTANCE = new Controller();
	 
	private String username;
	private RouletteGUI rouletteGUI;
	private Roulette roulette;
	private int basePoints;
	private BlackjackGUI blackJackGUI;
	private Blackjack blackjack;
	
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
            case Event.OPEN_PLAYER_VIEW: {
                new PlayerView();
                break;
            }
            case Event.OPEN_GAMES_PANEL: {
                new GamesPanelView();
                break;
            }
            case Event.OPEN_INVENTORY: {
                new InventoryView();
                break;
            }
            case Event.OPEN_STORE: {
                new StoreView();
                break;
            }
            case Event.LOGOUT: {
            	username = null;
                LoginView.open();
                break;
            }
            case Event.OPEN_STAFF_VIEW: {
                new StaffView();
                break;
            }
            case Event.OPEN_PACKAGE_MANAGER: {
                new PackageManagerView();
                break;
            }
            case Event.OPEN_PRODUCT_MANAGER: {
            	new ProductManagerView();
                break;
            }
            case Event.OPEN_PLAYER_MANAGER: {
            	new PlayerManagerView();
                break;
            }
            case Event.BLACKJACK_PLAY: {
                basePoints = (Integer)data;
                ServiceLocator.get(SAInventory.class).consumeGame(username, new tGame(tGame.BLACKJACK, basePoints));
                blackjack = new Blackjack(basePoints);
                int croupierVisiblePoints = blackjack.play();
                blackJackGUI = new BlackjackGUI(blackjack.getCroupierVisibleCard(), blackjack.getPlayerCards(), croupierVisiblePoints, blackjack.getPlayerPoints());
                break;
            }
            case Event.BLACKJACK_HIT: {
                if(blackjack == null || blackJackGUI == null) {
                	System.err.println("BLACKJACK_HIT sin partida activa");
                	break;
                }
                int res = blackjack.playerHit();
                blackJackGUI.update(blackjack.getCroupierCards(), blackjack.getPlayerCards(), blackjack.getCroupierPoints(), blackjack.getPlayerPoints(), false);
                if(res != Blackjack.CONTINUE) {
                    if(res == Blackjack.WON) {
                        ServiceLocator.get(SAPlayer.class).addPoints(username, blackjack.getPointsWon());
                        blackJackGUI.end("¡Victoria, has ganado " + blackjack.getPointsWon() + " puntos!");
                    } 
                    else blackJackGUI.end("Te has pasado de 21...");
                }
                break;
            }
            case Event.BLACKJACK_STAND: {
                if(blackjack == null || blackJackGUI == null) {
                	System.err.println("BLACKJACK_STAND sin partida activa");
                	break;
                }
                if(blackjack.getCroupierPoints() < blackjack.getPlayerPoints()) blackjack.croupierPlay();
                blackJackGUI.update(blackjack.getCroupierCards(), blackjack.getPlayerCards(), blackjack.getCroupierPoints(), blackjack.getPlayerPoints(), true);
                switch(blackjack.getResult()) {
	                case Blackjack.LOST: blackJackGUI.end("Lo siento, gana el croupier..."); break;
	                case Blackjack.CONTINUE: blackJackGUI.end("Empate"); break;
	                case Blackjack.WON: 
	                	ServiceLocator.get(SAPlayer.class).addPoints(username, blackjack.getPointsWon());
	                    blackJackGUI.end("¡Felicidades, has ganado " + blackjack.getPointsWon() + " puntos!");
	                    break;
                }
                break;
            }
            case Event.ROULETTE_PLAY: {
                basePoints = (Integer)data;
                ServiceLocator.get(SAInventory.class).consumeGame(username, new tGame(tGame.ROULETTE, basePoints));
                rouletteGUI = new RouletteGUI();
                break;
            }
            case Event.ROULETTE_ROLL: {
                roulette = new Roulette(basePoints, (tBet)data);
                int points = roulette.play();
                if(points > 0) ServiceLocator.get(SAPlayer.class).addPoints(username, points);
                rouletteGUI.showResult(points, roulette.getWinningNumber());
                break;
            }
            case Event.BUY_PACKAGE: {
                tGamesPackage pack = (tGamesPackage)data;
                if(ServiceLocator.get(SAStore.class).buyPackage(username, pack))
                    Theme.getInstance().showInfo(null, "¡" + pack.getName() + " comprado con éxito!");
                else Theme.getInstance().showError(null, "No se pudo completar la compra");
                break;
            }
            case Event.BUY_PRODUCT: {
                tProduct prod = (tProduct)data;
                if(ServiceLocator.get(SAStore.class).buyProduct(username, prod))
                    Theme.getInstance().showInfo(null, "¡" + prod.getName() + " canjeado con éxito!");
                else Theme.getInstance().showError(null, "No tienes suficientes puntos para canjear este producto");
                break;
            }
            case Event.CREATE_GAME: ServiceLocator.get(SAGame.class).createGame((tGame)data); break;
            case Event.CREATE_PACKAGE: ServiceLocator.get(SAGamesPackage.class).createGamesPackage((tGamesPackage)data); break;
            case Event.UPDATE_PACKAGE: ServiceLocator.get(SAGamesPackage.class).updateGamesPackage((tGamesPackage)data); break;
            case Event.DELETE_PACKAGE: ServiceLocator.get(SAGamesPackage.class).deleteGamesPackage((String)data); break;
            case Event.CREATE_PRODUCT: ServiceLocator.get(SAProduct.class).createProduct((tProduct)data); break;
            case Event.UPDATE_PRODUCT: ServiceLocator.get(SAProduct.class).updateProduct((tProduct)data); break;
            case Event.DELETE_PRODUCT: ServiceLocator.get(SAProduct.class).deleteProduct((String)data); break;
            default: System.err.println("No está implementado"); break;
        }
    }

    public int getPlayerPoints(String name) {
        return ServiceLocator.get(SAPlayer.class).readPlayer(name).getPoints();
    }

    public tPlayer getPlayer(String name) {
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
