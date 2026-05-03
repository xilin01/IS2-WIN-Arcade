package presentation.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import presentation.Controller;
import presentation.Event;
import presentation.games.BackgroundPanel;

@SuppressWarnings("serial")
public class PlayerView extends JFrame {

    public PlayerView() {
        super("Menú principal - Jugador");
        initGUI();
    }

    private void initGUI() {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    	
        BackgroundPanel background = new BackgroundPanel("resources/icons/zona_seleccion.png");
        background.setLayout(new GridBagLayout());
        setContentPane(background);
        
        GridBagConstraints gbcCentrado = new GridBagConstraints();
        
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(600, 400));
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        gbcCentrado.insets = new Insets(100, 0, 0, 0); 
        background.add(mainPanel, gbcCentrado);
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        JPanel userPanel = new JPanel(); // Panel de transparencia para el texto
        userPanel.setBackground(new Color(0, 0, 0, 175));
        JLabel userLabel = new JLabel(Controller.getInstance().getUsername() + " (" + Controller.getInstance().getPlayerPoints(Controller.getInstance().getUsername()) + "p) | ");
        userLabel.setForeground(Color.WHITE);
        userPanel.add(userLabel);
        topPanel.add(userPanel);
        JButton logOutButton = new JButton("Cerrar Sesión");
        logOutButton.addActionListener(e -> {
            Controller.getInstance().action(Event.LOGOUT, null);
            dispose();
        });
        topPanel.add(logOutButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
        centerPanel.setOpaque(false);
        JButton playButton = new JButton("Jugar");
        playButton.setPreferredSize(new Dimension(260, 100));
        playButton.setFont(playButton.getFont().deriveFont(Font.BOLD, 28f));
        playButton.addActionListener(e -> {
        	Controller.getInstance().action(Event.OPEN_GAMES_PANEL, null);
        	dispose();
        });
        centerPanel.add(playButton);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setOpaque(false);
        JButton storeButton = new JButton("Tienda");
        storeButton.addActionListener(e -> {
        	Controller.getInstance().action(Event.OPEN_STORE, null);
        	dispose();
        });
        JButton inventoryButton = new JButton("Inventario");
        inventoryButton.addActionListener(e -> {
        	Controller.getInstance().action(Event.OPEN_INVENTORY, null);
        	dispose();
        });
        Dimension buttonSize = new Dimension(120, 120);
        storeButton.setPreferredSize(buttonSize);
        inventoryButton.setPreferredSize(buttonSize);
        bottomPanel.add(inventoryButton);
        bottomPanel.add(storeButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
