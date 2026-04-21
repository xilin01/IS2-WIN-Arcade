package mainLauncher;

import javax.swing.SwingUtilities;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import business.SAAuth;
import business.SAAuthImp;
import business.SAUser;
import business.SAUserImp;
import business.ServiceLocator;
import presentation.LoginView;

@objid ("e7eced22-b4e5-44ef-b114-f8e3351b0184")
public class Main {

    @objid ("0699632e-2ac8-43b2-b795-5017a07d6fd5")
    public static void main(String[] args) {
        ServiceLocator.register(SAUser.class, new SAUserImp());
        ServiceLocator.register(SAAuth.class, new SAAuthImp());

        SwingUtilities.invokeLater(LoginView::open);
    }
}
