package presentation.user;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import presentation.Controller;
import presentation.auth.LoginView;
import presentation.packages.PackageManagerView;
import presentation.product.ProductManagerView;

@SuppressWarnings("serial")
public class StaffView extends JFrame {

    public StaffView() {
        super();
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setTitle("Menú principal - Personal");
        this.setSize(650, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Barra superior
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel userLabel = new JLabel(Controller.getInstance().getUsername() + " | ");
        JButton logOutButton = new JButton("Cerrar Sesión");

        topPanel.add(userLabel);
        topPanel.add(logOutButton);

        this.add(topPanel, BorderLayout.NORTH);

        //Panel central - Botones principales
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 100));

        JButton managePackagesButton = new JButton("GESTIONAR PAQUETES");
        managePackagesButton.addActionListener(e -> {
            new PackageManagerView();
            dispose();
        });
        JButton manageProductsButton = new JButton("GESTIONAR PRODUCTOS");
        manageProductsButton.addActionListener(e -> {
        	new ProductManagerView();
        	dispose();
        });
        JButton manageUsersButton = new JButton("GESTIONAR USUARIOS");
        manageUsersButton.addActionListener(e -> {
        	new UsersManagerView();
        	dispose();
        });

//        Dimension buttonSize = new Dimension(200, 40);
//
//        managePackagesButton.setPreferredSize(buttonSize);
//        manageProductsButton.setPreferredSize(buttonSize);
//        manageUsersButton.setPreferredSize(buttonSize);

        mainPanel.add(managePackagesButton);
        mainPanel.add(manageProductsButton);
        mainPanel.add(manageUsersButton);

        this.add(mainPanel, BorderLayout.CENTER);

        logOutButton.addActionListener(e -> {
            LoginView.open();
            dispose();
        });

        this.setVisible(true);
    }
}
