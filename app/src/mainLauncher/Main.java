package mainLauncher;

import javax.swing.SwingUtilities;
import business.auth.SAAuth;
import business.auth.SAAuthImp;
import business.player.SAPlayer;
import business.player.SAPlayerImp;
import business.ServiceLocator;
import business.user.SAUser;
import business.user.SAUserImp;
import presentation.auth.LoginView;

public class Main {

    public static void main(String[] args) {
    	ServiceLocator.register(SAUser.class, new SAUserImp());
    	ServiceLocator.register(SAPlayer.class, new SAPlayerImp());
    	ServiceLocator.register(SAAuth.class, new SAAuthImp());
    	SwingUtilities.invokeLater(LoginView::open);
    }
}
