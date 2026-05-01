package presentation.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import business.game.tGame;
import business.player.tPlayer;
import business.product.tProduct;
import presentation.Controller;
import presentation.Event;

public class InventoryEditorView extends JDialog {
	
	private tPlayer player;
	private JPanel objectsListPanel;
	private JPanel gamesListPanel;
	
	public InventoryEditorView(JDialog parent, tPlayer player) {
		super(parent, "Inventario de " + player.getName(), true);
		this.player = player;
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setSize(500, 450);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JTabbedPane tabsPanel = new JTabbedPane();
        
        // Pestaña objetos
        JPanel objectsPanel = createObjectsPanel();
        tabsPanel.addTab("Objetos", objectsPanel);
        
        // Pestaña juegos
        JPanel gamesPanel = createGamesPanel();
        tabsPanel.addTab("Juegos", gamesPanel);
        
        this.add(tabsPanel, BorderLayout.CENTER);
        
        // Boton back
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        bottomPanel.add(backButton);
        
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
	}
	
	private JPanel createObjectsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Botón añadir
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addObjectBtn = new JButton("Añadir Objeto");
        
        addObjectBtn.addActionListener(e -> {
            java.util.Collection<tProduct> allProducts = Controller.getInstance().getAllProducts();

            JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            
            javax.swing.JComboBox<tProduct> productCombo = new javax.swing.JComboBox<>(allProducts.toArray(new tProduct[0]));
            JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));

            inputPanel.add(new JLabel("Selecciona objeto:"));
            inputPanel.add(productCombo);
            inputPanel.add(new JLabel("Cantidad:"));
            inputPanel.add(qtySpinner);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Añadir Objeto", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                tProduct selectedProduct = (tProduct) productCombo.getSelectedItem();
                int qty = (int) qtySpinner.getValue();

                if (selectedProduct != null) {
                    Map<tProduct, Integer> productsMap = player.getInventory().getProducts();
                    if (productsMap == null) {
                        productsMap = new HashMap<>();
                        player.getInventory().setProducts(productsMap);
                    }
                    
                    productsMap.put(selectedProduct, productsMap.getOrDefault(selectedProduct, 0) + qty);
                    Controller.getInstance().action(Event.UPDATE_USER, player);
                    updateObjectsList();
                }
            }
        });
        
        topPanel.add(addObjectBtn);
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Lista de objetos
        objectsListPanel = new JPanel();
        objectsListPanel.setLayout(new BoxLayout(objectsListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(objectsListPanel);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        updateObjectsList();
        
        return panel;
    }
	
	private void updateObjectsList() {
        objectsListPanel.removeAll();
        
        Map<tProduct, Integer> productsMap = player.getInventory().getProducts();
        
        if (productsMap != null && !productsMap.isEmpty()) {
            for (tProduct product : productsMap.keySet()) {
                int cant = productsMap.get(product);
                
                JPanel row = new JPanel(new BorderLayout());
                row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(2, 2, 2, 2),
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY)
                ));
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

                JLabel infoLabel = new JLabel("  " + cant + "x " + product.getName());
                row.add(infoLabel, BorderLayout.CENTER);

                JButton removeBtn = new JButton("X");
                removeBtn.setForeground(Color.RED);
                removeBtn.addActionListener(e -> {
                    productsMap.remove(product);
                    Controller.getInstance().action(Event.UPDATE_USER, player);
                    updateObjectsList(); 
                });
                row.add(removeBtn, BorderLayout.EAST);

                objectsListPanel.add(row);
            }
        } else {
            JLabel emptyLabel = new JLabel("El inventario no tiene objetos.");
            emptyLabel.setForeground(Color.GRAY);
            objectsListPanel.add(emptyLabel);
        }
        
        objectsListPanel.revalidate();
        objectsListPanel.repaint();
    }
	
	private JPanel createGamesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Cargar lista de juegos posibles
        Map<String, java.util.List<Integer>> availableGamesMap = new HashMap<>();
        java.util.Collection<tGame> allGames = Controller.getInstance().getAllGames();
        for (tGame g : allGames) {
            availableGamesMap.putIfAbsent(g.getName(), new java.util.ArrayList<>());
            availableGamesMap.get(g.getName()).add(g.getBasePoints());
        }

        // Nombre del juego
        topPanel.add(new JLabel("Juego:"));
        javax.swing.JComboBox<String> gameNameComboBox = new javax.swing.JComboBox<>();
        for (String gameName : availableGamesMap.keySet()) gameNameComboBox.addItem(gameName);
        topPanel.add(gameNameComboBox);

        // Cantidad de puntos del juego
        topPanel.add(new JLabel("Puntos:"));
        JSpinner gamePointsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 10));
        topPanel.add(gamePointsSpinner);

        // Cantidad de juegos
        topPanel.add(new JLabel("Cant:"));
        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        topPanel.add(qtySpinner);

        if (gameNameComboBox.getItemCount() > 0) gameNameComboBox.setSelectedIndex(0);

        // Botón Añadir
        JButton addGameBtn = new JButton("Añadir");
        addGameBtn.addActionListener(e -> {
            String selectedName = (String) gameNameComboBox.getSelectedItem();
            Integer selectedPoints = (Integer) gamePointsSpinner.getValue();
            int qty = (int) qtySpinner.getValue();
            
            if (selectedName != null && selectedPoints != null) {
                if (!availableGamesMap.get(selectedName).contains(selectedPoints)) {
                    tGame newGame = new tGame(selectedName, selectedPoints);
                    Controller.getInstance().action(Event.CREATE_GAME, newGame);
                    availableGamesMap.get(selectedName).add(selectedPoints);
                }
                
                tGame gameToAdd = new tGame(selectedName, selectedPoints);
                
                Map<tGame, Integer> gamesMap = player.getInventory().getGames();
                if (gamesMap == null) {
                    gamesMap = new HashMap<>();
                    player.getInventory().setGames(gamesMap);
                }
                
                gamesMap.put(gameToAdd, gamesMap.getOrDefault(gameToAdd, 0) + qty);
                updateGamesList(); 
            }
        });
        
        topPanel.add(addGameBtn);
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Lista de juegos añadidos
        gamesListPanel = new JPanel();
        gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(gamesListPanel);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        updateGamesList();
        
        return panel;
    }
	
	private void updateGamesList() {
        gamesListPanel.removeAll();
        
        Map<tGame, Integer> gamesMap = player.getInventory().getGames();
        
        if (gamesMap != null && !gamesMap.isEmpty()) {
            for (tGame game : gamesMap.keySet()) {
                int cant = gamesMap.get(game);
                
                JPanel row = new JPanel(new BorderLayout());
                row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(2, 2, 2, 2),
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY)
                ));
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

                JLabel infoLabel = new JLabel("  " + cant + "x " + game.getName() + " (" + game.getBasePoints() + "p)");
                row.add(infoLabel, BorderLayout.CENTER);

                JButton removeBtn = new JButton("X");
                removeBtn.setForeground(Color.RED);
                removeBtn.addActionListener(e -> {
                    gamesMap.remove(game);
                    
                    updateGamesList(); 
                });
                row.add(removeBtn, BorderLayout.EAST);

                gamesListPanel.add(row);
            }
        } else {
            JLabel emptyLabel = new JLabel("El inventario no tiene juegos.");
            emptyLabel.setForeground(Color.GRAY);
            gamesListPanel.add(emptyLabel);
        }
        
        gamesListPanel.revalidate();
        gamesListPanel.repaint();
    }
}
