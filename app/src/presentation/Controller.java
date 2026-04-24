package presentation;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import business.auth.AuthResult;
import business.auth.SAAuth;
import business.game.Blackjack;
import business.player.SAPlayer;
import business.ServiceLocator;
import business.user.SAUser;
import business.user.tUser;
import presentation.auth.LoginCredentials;
import presentation.auth.LoginView;
import presentation.auth.SignUpCredentials;
import presentation.auth.SignUpView;
import presentation.game.BlackJackGUI;
import presentation.packages.PackageManagerView;
import presentation.user.StaffView;
import presentation.user.UserView;

@objid ("89dcc83d-dee8-45f7-aee4-45b7f33b671f")
public class Controller {

    private static BlackJackGUI blackJackGUI;
    private static Blackjack blackjack;

    @objid ("7d6f7991-228b-4d7a-a5ff-094d1cd303bf")
    public static void action(final int event, final Object data) {
        switch (event) {
            case Event.CREATE_USER: {
                SAUser saUser = ServiceLocator.get(SAUser.class);
                tUser newUser = (tUser) data;
                int res = saUser.createUser(newUser);
                if (res > 0) UserView.update(Event.RES_CREATE_USER_SUCCESS, res);
                else UserView.update(Event.RES_CREATE_USER_FAILURE, null);
                break;
            }
            case Event.LIST_USERS: {
                SAUser saUser = ServiceLocator.get(SAUser.class);
                Collection<tUser> res = saUser.readAllUsers();
                if (!res.isEmpty()) UserView.update(Event.RES_LIST_USERS_SUCCESS, res);
                else UserView.update(Event.RES_LIST_USERS_FAILURE, null);
                break;
            }
            case Event.FIND_USER: {
                SAUser saUser = ServiceLocator.get(SAUser.class);
                String name = (String) data;
                tUser user = saUser.readUser(name);
                if (user != null) UserView.update(Event.RES_FIND_USER_SUCCESS, user);
                else UserView.update(Event.RES_FIND_USER_FAILURE, null);
                break;
            }
            case Event.DELETE_USER: {
                SAUser saUser = ServiceLocator.get(SAUser.class);
                String name = (String) data;
                int res = saUser.deleteUser(name);
                if (res > 0) UserView.update(Event.RES_DELETE_USER_SUCCESS, data);
                else UserView.update(Event.RES_DELETE_USER_FAILURE, null);
                break;
            }
            case Event.UPDATE_USER: {
                SAUser saUser = ServiceLocator.get(SAUser.class);
                tUser modUser = (tUser) data;
                int res = saUser.updateUser(modUser);
                if (res > 0) UserView.update(Event.RES_UPDATE_USER_SUCCESS, data);
                else UserView.update(Event.RES_UPDATE_USER_FAILURE, null);
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
                SAPlayer saPlayer = ServiceLocator.get(SAPlayer.class);
                SignUpCredentials credentials = (SignUpCredentials) data;
                int res = saPlayer.createPlayer(credentials.name(), credentials.password());
                if (res > 0) SignUpView.update(Event.RES_SIGN_UP_SUCCESS, null);
                else SignUpView.update(Event.RES_SIGN_UP_FAILURE, null);
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
                int res = blackjack.playerHit();
                blackJackGUI.update(blackjack.getCroupierCards(), blackjack.getPlayerCards(),
                        blackjack.getCroupierPoints(), blackjack.getPlayerPoints(), false);
                if(res != Blackjack.CONTINUE)
                    blackJackGUI.end(res == Blackjack.WON ? "Victoria. Llegaste a 21" : "Derrota. Te pasaste");
                break;
            }
            case Event.BLACKJACK_STAND: {
                if(blackjack.getCroupierPoints() < blackjack.getPlayerPoints()) blackjack.croupierPlay();
                blackJackGUI.update(blackjack.getCroupierCards(), blackjack.getPlayerCards(),
                        blackjack.getCroupierPoints(), blackjack.getPlayerPoints(), true);
                int res = blackjack.getResult();
                switch(res) {
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
            default: System.err.println("Aún no esta implementado");
        }
    }
}
