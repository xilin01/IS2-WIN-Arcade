package mainLauncher;

import javax.swing.SwingUtilities;
import business.auth.SAAuth;
import business.auth.SAAuthImp;
import business.game.SAGame;
import business.game.SAGameImp;
import business.packages.SAGamesPackage;
import business.packages.SAGamesPackageImp;
import business.player.SAInventory;
import business.player.SAInventoryImp;
import business.player.SAPlayer;
import business.player.SAPlayerImp;
import business.product.SAProduct;
import business.product.SAProductImp;
import business.ServiceLocator;
import business.user.SAUser;
import business.user.SAUserImp;
import presentation.auth.LoginView;

public class Main {

    public static void main(String[] args) {
        ServiceLocator.register(SAUser.class, new SAUserImp());
        ServiceLocator.register(SAPlayer.class, new SAPlayerImp());
        ServiceLocator.register(SAInventory.class, new SAInventoryImp());
        ServiceLocator.register(SAAuth.class, new SAAuthImp());
        ServiceLocator.register(SAGame.class, new SAGameImp());
        ServiceLocator.register(SAGamesPackage.class, new SAGamesPackageImp());
        ServiceLocator.register(SAProduct.class, new SAProductImp());
        SwingUtilities.invokeLater(LoginView::open);
    }
}
