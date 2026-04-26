package presentation.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import presentation.Controller;
import presentation.Event;

@SuppressWarnings("serial")
public class PlayerView extends JFrame {
    private final String userName;

    public PlayerView(String userName) {
        super("Menú principal - Jugador");
        this.userName = userName;
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel userLabel = new JLabel(userName + " | ");
        JButton logOutButton = new JButton("Cerrar Sesión");
        topPanel.add(userLabel);
        topPanel.add(logOutButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 80));
        JButton playButton = new JButton("Jugar");
        playButton.setPreferredSize(new Dimension(200, 100));
        playButton.setFont(playButton.getFont().deriveFont(Font.BOLD, 28f));
        playButton.addActionListener(e -> {
        	Controller.getInstance().action(Event.GAMES_PANEL, userName);
        	dispose();
        });
        centerPanel.add(playButton);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        JButton storeButton = new JButton("Tienda");
        JButton inventoryButton = new JButton("Inventario");
        Dimension buttonSize = new Dimension(120, 35);
        storeButton.setPreferredSize(buttonSize);
        inventoryButton.setPreferredSize(buttonSize);
        storeButton.addActionListener(e -> {
        	Controller.getInstance().action(Event.OPEN_STORE, null);
        	dispose();
        });
        inventoryButton.addActionListener(e -> {
        	Controller.getInstance().action(Event.OPEN_INVENTORY, userName);
        	dispose();
        });
        bottomPanel.add(storeButton);
        bottomPanel.add(inventoryButton);
        add(bottomPanel, BorderLayout.SOUTH);

        logOutButton.addActionListener(e -> {
            Controller.getInstance().action(Event.LOGOUT, null);
            dispose();
        });

        setVisible(true);
    }
}
