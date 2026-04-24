package mainLauncher;

import javax.swing.SwingUtilities;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import business.auth.SAAuth;
import business.auth.SAAuthImp;
import business.player.SAPlayer;
import business.player.SAPlayerImp;
import business.ServiceLocator;
import business.user.SAUser;
import business.user.SAUserImp;
import presentation.auth.LoginView;
import presentation.Controller;
import presentation.Event;

@objid ("e7eced22-b4e5-44ef-b114-f8e3351b0184")
public class Main {

    @objid ("0699632e-2ac8-43b2-b795-5017a07d6fd5")
    public static void main(String[] args) {
        ServiceLocator.register(SAUser.class, new SAUserImp());
        ServiceLocator.register(SAAuth.class, new SAAuthImp());
        ServiceLocator.register(SAPlayer.class, new SAPlayerImp());
        SwingUtilities.invokeLater(LoginView::open);
        
    	Controller.getInstance().action(Event.BLACKJACK_PLAY, 20);

    }
}
