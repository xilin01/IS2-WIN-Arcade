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
import presentation.auth.LoginView;
import presentation.games.BackgroundPanel;
import presentation.games.GamesPanelView;

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
        
        //Panel de transparencia para el texto
        JPanel auxUserLabel = new JPanel();
        auxUserLabel.setBackground(new Color(0, 0, 0, 175));
        JLabel userLabel = new JLabel(Controller.getInstance().getUsername() + " (" + Controller.getInstance().getPlayerPoints(Controller.getInstance().getUsername()) + "p) | ");
        userLabel.setForeground(Color.WHITE);
        auxUserLabel.add(userLabel);
        
        JButton logOutButton = new JButton("Cerrar Sesión");
        topPanel.add(auxUserLabel);
        topPanel.add(logOutButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
        centerPanel.setOpaque(false);
        JButton playButton = new JButton("Jugar");
        playButton.setPreferredSize(new Dimension(260, 100));
        playButton.setFont(playButton.getFont().deriveFont(Font.BOLD, 28f));
        playButton.addActionListener(e -> {
        	new GamesPanelView();
        	dispose();
        });
        centerPanel.add(playButton);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setOpaque(false);
        JButton storeButton = new JButton("Tienda");
        JButton inventoryButton = new JButton("Inventario");
        Dimension buttonSize = new Dimension(120, 120);
        storeButton.setPreferredSize(buttonSize);
        inventoryButton.setPreferredSize(buttonSize);
        storeButton.addActionListener(e -> {
        	new StoreView();
        	dispose();
        });
        inventoryButton.addActionListener(e -> {
        	new InventoryView();
        	dispose();
        });
        bottomPanel.add(storeButton);
        bottomPanel.add(inventoryButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        logOutButton.addActionListener(e -> {
            LoginView.open();
            dispose();
        });

        setVisible(true);
    }
}
