package presentation;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import business.auth.AuthResult;
import business.auth.SAAuth;
import business.game.Blackjack;
import business.game.Roulette;
import business.game.Roulette.Bet.tBet;
import business.player.SAPlayer;
import business.ServiceLocator;
import business.user.SAUser;
import business.user.tUser;
import presentation.auth.LoginCredentials;
import presentation.auth.LoginView;
import presentation.auth.SignUpCredentials;
import presentation.auth.SignUpView;
import presentation.game.BlackJackGUI;
import presentation.game.RouletteGUI;
import presentation.packages.PackageManagerView;
import presentation.user.StaffView;
import presentation.user.UserView;

@objid ("89dcc83d-dee8-45f7-aee4-45b7f33b671f")
public class Controller {

    private static final Controller INSTANCE = new Controller();
    private BlackJackGUI blackJackGUI;
    private Blackjack blackjack;
    private RouletteGUI rouletteGUI;
    private Roulette roulette;
    private int puntosRuleta;
    private Controller() {}
    public static Controller getInstance() { return INSTANCE; }

    @objid ("7d6f7991-228b-4d7a-a5ff-094d1cd303bf")
    public void action(final int event, final Object data) {
        switch (event) {
            case Event.CREATE_USER: {
                int res = ServiceLocator.get(SAUser.class).createUser((tUser) data);
                viewResult(res > 0, UserView::update, Event.RES_CREATE_USER_SUCCESS, res, Event.RES_CREATE_USER_FAILURE);
                break;
            }
            case Event.LIST_USERS: {
                Collection<tUser> res = ServiceLocator.get(SAUser.class).readAllUsers();
                viewResult(!res.isEmpty(), UserView::update, Event.RES_LIST_USERS_SUCCESS, res, Event.RES_LIST_USERS_FAILURE);
                break;
            }
            case Event.FIND_USER: {
                tUser user = ServiceLocator.get(SAUser.class).readUser((String) data);
                viewResult(user != null, UserView::update, Event.RES_FIND_USER_SUCCESS, user, Event.RES_FIND_USER_FAILURE);
                break;
            }
            case Event.DELETE_USER: {
                int res = ServiceLocator.get(SAUser.class).deleteUser((String) data);
                viewResult(res > 0, UserView::update, Event.RES_DELETE_USER_SUCCESS, data, Event.RES_DELETE_USER_FAILURE);
                break;
            }
            case Event.UPDATE_USER: {
                int res = ServiceLocator.get(SAUser.class).updateUser((tUser) data);
                viewResult(res > 0, UserView::update, Event.RES_UPDATE_USER_SUCCESS, data, Event.RES_UPDATE_USER_FAILURE);
                break;
            }
            case Event.LOGIN: {
                SAAuth saAuth = ServiceLocator.get(SAAuth.class);
                LoginCredentials credentials = (LoginCredentials) data;
                AuthResult result = saAuth.authenticate(credentials.name(), credentials.password());
                int loginEvent = switch (result.getOutcome()) {
                    case STAFF_OK -> Event.RES_LOGIN_STAFF_SUCCESS;
                    case PLAYER_OK -> Event.RES_LOGIN_SUCCESS;
                    case FAILURE -> Event.RES_LOGIN_FAILURE;
                };
                LoginView.update(loginEvent, result.getPayload());
                break;
            }
            case Event.SIGN_UP: {
                SignUpCredentials creds = (SignUpCredentials) data;
                int res = ServiceLocator.get(SAPlayer.class).createPlayer(creds.name(), creds.password());
                viewResult(res > 0, SignUpView::update, Event.RES_SIGN_UP_SUCCESS, null, Event.RES_SIGN_UP_FAILURE);
                break;
            }
            case Event.LOGOUT: {
                LoginView.open();
                break;
            }
            case Event.OPEN_PACKAGE_MANAGER: {
                String username = (String) data;
                new PackageManagerView(username);
                break;
            }
            case Event.CLOSE_PACKAGE_MANAGER: {
                String username = (String) data;
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

            //Games
            case Event.BLACKJACK_PLAY: {
                blackjack = new Blackjack((Integer) data);
                int croupierPoints = blackjack.play();
                blackJackGUI = new BlackJackGUI(blackjack.getCroupierVisibleCard(),
                        blackjack.getPlayerCards(), croupierPoints, blackjack.getPlayerPoints());
                break;
            }
            case Event.BLACKJACK_HIT: {
                if (blackjack == null || blackJackGUI == null) { System.err.println("BLACKJACK_HIT sin partida activa"); break; }
                int res = blackjack.playerHit();
                blackJackGUI.update(blackjack.getCroupierCards(), blackjack.getPlayerCards(),
                        blackjack.getCroupierPoints(), blackjack.getPlayerPoints(), false);
                if (res != Blackjack.CONTINUE)
                    blackJackGUI.end(res == Blackjack.WON ? "Victoria. Llegaste a 21" : "Derrota. Te pasaste");
                break;
            }
            case Event.BLACKJACK_STAND: {
                if (blackjack == null || blackJackGUI == null) { System.err.println("BLACKJACK_STAND sin partida activa"); break; }
                if (blackjack.getCroupierPoints() < blackjack.getPlayerPoints()) blackjack.croupierPlay();
                blackJackGUI.update(blackjack.getCroupierCards(), blackjack.getPlayerCards(),
                        blackjack.getCroupierPoints(), blackjack.getPlayerPoints(), true);
                int res = blackjack.getResult();
                switch (res) {
                    case Blackjack.LOST: blackJackGUI.end("Lo siento, gana el croupier.."); break;
                    case Blackjack.CONTINUE: blackJackGUI.end("Empate"); break;
                    case Blackjack.WON: {
                        blackJackGUI.end("Felicidades, has ganado " + blackjack.getPointsWon() + " puntos!");
                        //TODO meterle los puntos al jugador
                        break;
                    }
                }
                break;
            }
            case Event.ROULETTE_PLAY: {
                rouletteGUI = new RouletteGUI();
                puntosRuleta = (Integer) data;
                break;
            }
            case Event.ROULETTE_ROLL: {
                if (rouletteGUI == null) { System.err.println("ROULETTE_ROLL sin partida activa"); break; }
                roulette = new Roulette(puntosRuleta, (tBet) data);
                int puntos = roulette.play();
                rouletteGUI.showResult(puntos, roulette.getWinningNumber());
                break;
            }
            default: System.err.println("Aún no esta implementado");
        }
    }

    private static void viewResult(boolean ok, ViewUpdater view, int okEvent, Object okPayload, int failEvent) {
        if (ok) view.update(okEvent, okPayload);
        else view.update(failEvent, null);
    }
}
