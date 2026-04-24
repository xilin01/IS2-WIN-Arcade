package presentation.packages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import business.packages.SAGamesPackage;
import business.packages.SAGamesPackageImp;
import business.packages.tGamesPackage;
import presentation.Controller;
import presentation.Event;

public class PackageManagerView extends JFrame {

    private JPanel packListPanel;
    private SAGamesPackage gamePackages;
    private String userName;

    public PackageManagerView(String userName) {
        super();
        this.gamePackages = new SAGamesPackageImp();
        this.userName = userName;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setTitle("Gestor de paquetes");
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Boton Crear paquete de juegos
        JPanel createPackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createPackButton = new JButton("CREAR NUEVO PAQUETE");
        createPackButton.setPreferredSize(new Dimension(150, 35));
        createPackButton.addActionListener((e) -> {
        	new GamesPackageEditor(this, null);
        	updateList();
        });

        createPackPanel.add(createPackButton);

        this.add(createPackPanel, BorderLayout.NORTH);

        //Lista de paquetes
        packListPanel = new JPanel();
        packListPanel.setLayout(new BoxLayout(packListPanel, BoxLayout.Y_AXIS));

        packListPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        for (tGamesPackage pack : gamePackages.readAllGamesPackages()) {
            JPanel gamePanel = packageElement(pack);
            packListPanel.add(gamePanel);
        }
        JScrollPane packScrollPane = new JScrollPane(packListPanel);

        this.add(packScrollPane, BorderLayout.CENTER);

        //Boton back para volver al panel de staff
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");

        bottomPanel.add(backButton);

        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener((e) -> {
            Controller.getInstance().action(Event.CLOSE_PACKAGE_MANAGER, userName);
            dispose();
        });

        this.setVisible(true);
    }

    private JPanel packageElement(tGamesPackage gamePack) {
        JPanel packagePanel = new JPanel(new BorderLayout());

        packagePanel.setMaximumSize(new Dimension((int)1e6, 100));

        //Informacion general del paquete
        JPanel packInfo = new JPanel();
        packInfo.setLayout(new BoxLayout(packInfo, BoxLayout.Y_AXIS));
        packInfo.add(new JLabel(gamePack.getName()));

        packagePanel.add(packInfo, BorderLayout.CENTER);

        Border packageBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        packagePanel.setBorder(packageBorder);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton editButton = new JButton(new ImageIcon("resources/icons/edit.png"));
        editButton.setPreferredSize(new Dimension(35, 35));
        editButton.addActionListener((e) -> {
        	new GamesPackageEditor(this, gamePack);
        	updateList();
        });
        JButton deleteButton = new JButton(new ImageIcon("resources/icons/delete.png"));
        deleteButton.setPreferredSize(new Dimension(35, 35));
        deleteButton.addActionListener((e) -> {
        	int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
        	        "¿Borrar el paquete '" + gamePack.getName() + "'?", "Confirmar",
        	        javax.swing.JOptionPane.YES_NO_OPTION
        	    );

    	   	if (confirm == javax.swing.JOptionPane.YES_OPTION) {
    	        this.gamePackages.deleteGamesPackage(gamePack.getName()); 
    	        updateList();
    	    }
        });

        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        packagePanel.add(buttonsPanel, BorderLayout.EAST);

        return packagePanel;
    }
    
    private void updateList() {
        packListPanel.removeAll();
        for (tGamesPackage pack : gamePackages.readAllGamesPackages()) {
            packListPanel.add(packageElement(pack));
        }
        packListPanel.revalidate();
        packListPanel.repaint();
    }
}
