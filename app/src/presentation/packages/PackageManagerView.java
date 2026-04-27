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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import business.packages.tGamesPackage;
import presentation.Controller;
import presentation.Event;
import presentation.user.StaffView;

@SuppressWarnings("serial")
public class PackageManagerView extends JFrame {
    private JPanel packListPanel;
    private String userName;

    public PackageManagerView(String userName) {
        super("Gestor de paquetes");
        this.userName = userName;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Boton Crear paquete de juegos
        JPanel createPackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createPackButton = new JButton("CREAR NUEVO PAQUETE");
        createPackButton.setPreferredSize(new Dimension(150, 35));
        createPackButton.addActionListener(e -> {
        	new GamesPackageEditor(this, null);
        	updateList();
        });

        createPackPanel.add(createPackButton);

        this.add(createPackPanel, BorderLayout.NORTH);

        //Lista de paquetes
        packListPanel = new JPanel();
        packListPanel.setLayout(new BoxLayout(packListPanel, BoxLayout.Y_AXIS));

        packListPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        for (tGamesPackage pack : Controller.getInstance().getAllPackages()) packListPanel.add(packageElement(pack));
        JScrollPane packScrollPane = new JScrollPane(packListPanel);

        this.add(packScrollPane, BorderLayout.CENTER);

        //Boton back para volver al panel de staff
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");

        bottomPanel.add(backButton);

        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            new StaffView(Controller.getInstance().getUsername());
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
        editButton.addActionListener(e -> {
        	new GamesPackageEditor(this, gamePack);
        	updateList();
        });
        JButton deleteButton = new JButton(new ImageIcon("resources/icons/delete.png"));
        deleteButton.setPreferredSize(new Dimension(35, 35));
        deleteButton.addActionListener(e -> {
        	int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar definitivamente el paquete '" + gamePack.getName() + "'? Esta acción no se puede deshacer.",
        			"Confirmar", JOptionPane.YES_NO_OPTION);

    	   	if (confirm == JOptionPane.YES_OPTION) {
    	        Controller.getInstance().action(Event.DELETE_PACKAGE, gamePack.getName());
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
        for (tGamesPackage pack : Controller.getInstance().getAllPackages()) packListPanel.add(packageElement(pack));
        packListPanel.revalidate();
        packListPanel.repaint();
    }
}
