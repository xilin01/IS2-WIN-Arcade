package presentation.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import business.user.tUser;
import presentation.Controller;
import presentation.Event;

public class StaffView extends JFrame {

    private String userName;

    public StaffView(String userName) {
        super();
        this.userName = userName;
        initGUI();
    }

    public StaffView(tUser user) {
        super();
        this.userName = user.getName();
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
        JLabel userLabel = new JLabel(userName + " | ");
        JButton logOutButton = new JButton("Cerrar Sesión");

        topPanel.add(userLabel);
        topPanel.add(logOutButton);

        this.add(topPanel, BorderLayout.NORTH);

        //Panel central - Botones principales
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 100));

        JButton managePackagesButton = new JButton("GESTIONAR PAQUETES");
        managePackagesButton.addActionListener((e) -> {
            Controller.getInstance().action(Event.OPEN_PACKAGE_MANAGER, this.userName);
            dispose();
        });
        JButton manageProductsButton = new JButton("GESTIONAR PRODUCTOS");
        manageProductsButton.addActionListener((e) -> {
        	new ProductManagerView(this.userName);
        	
        	dispose();
        });
        JButton manageUsersButton = new JButton("GESTIONAR USUARIOS");
        manageUsersButton.addActionListener((e) -> {
        	new UsersManagerView(userName);
        	
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

        logOutButton.addActionListener((e) -> {
            Controller.getInstance().action(Event.LOGOUT, null);
            dispose();
        });

        this.setVisible(true);
    }
}
