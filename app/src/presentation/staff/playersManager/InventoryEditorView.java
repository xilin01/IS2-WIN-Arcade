package presentation.staff.playersManager;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import business.game.tGame;
import business.player.tPlayer;
import business.product.tProduct;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class InventoryEditorView extends JDialog {
    private static final Theme theme = Theme.getInstance();
    
    private tPlayer player;
    private JPanel productsListPanel;
    private JPanel gamesListPanel;

    public InventoryEditorView(Window parent, tPlayer player) {
        super(parent, "Inventario de " + player.getName(), Dialog.ModalityType.APPLICATION_MODAL);
        this.player = player;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 450);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(theme.BG);

        JTabbedPane tabsPanel = new JTabbedPane();
        tabsPanel.setBackground(theme.BG_CARD);
        tabsPanel.setForeground(theme.TEXT);
        tabsPanel.setFont(theme.BODY);
        tabsPanel.addTab("Juegos", createGamesPanel());
        tabsPanel.addTab("Productos", createProductsPanel());
        this.add(tabsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(theme.BG_CARD);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, theme.BORDER));
        bottomPanel.add(theme.secondaryBtn("Atrás", this::dispose));
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(theme.BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(theme.BG);
        topPanel.add(theme.primaryBtn("Añadir producto", () -> {
            Collection<tProduct> allProducts = Controller.getInstance().getAllProducts();

            JPanel formPanel = new JPanel(new GridLayout(2, 2, 8, 10));
            formPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));

            JLabel productLabel = new JLabel("Producto:");
            productLabel.setForeground(theme.TEXT);
            formPanel.add(productLabel);
            
            JComboBox<tProduct> productCombo = new JComboBox<>(allProducts.toArray(new tProduct[0]));
            productCombo.setBackground(theme.BG_ITEM);
            productCombo.setForeground(theme.TEXT);
            formPanel.add(productCombo);

            JLabel qtyLabel = new JLabel("Cant	:");
            qtyLabel.setForeground(theme.TEXT);
            formPanel.add(qtyLabel);
            
            JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setBackground(theme.BG_ITEM);
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setForeground(theme.TEXT);
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setCaretColor(theme.TEXT);
            formPanel.add(qtySpinner);

            theme.formDialog(this, "Añadir producto", formPanel, "Añadir", () -> {
                tProduct selectedProduct = (tProduct)productCombo.getSelectedItem();
                int qty = (int)qtySpinner.getValue();
                
                if(selectedProduct != null) {
                    Map<tProduct, Integer> productsMap = player.getInventory().getProducts();
                    
                    if(productsMap == null) {
                        productsMap = new HashMap<>();
                        player.getInventory().setProducts(productsMap);
                    }
                    productsMap.put(selectedProduct, productsMap.getOrDefault(selectedProduct, 0) + qty);
                    Controller.getInstance().action(Event.UPDATE_PLAYER, player);
                    updateProductsList();
                }
                return true;
            });
        }));
        panel.add(topPanel, BorderLayout.NORTH);

        productsListPanel = new JPanel();
        productsListPanel.setLayout(new BoxLayout(productsListPanel, BoxLayout.Y_AXIS));
        productsListPanel.setBackground(theme.BG);

        JScrollPane scrollPane = new JScrollPane(productsListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(theme.BG);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        updateProductsList();
        return panel;
    }

    private void updateProductsList() {
        productsListPanel.removeAll();
        Map<tProduct, Integer> productsMap = player.getInventory().getProducts();
        if(productsMap != null && !productsMap.isEmpty()) {
            for(tProduct product: productsMap.keySet()) {
            	productsListPanel.add(buildRow(productsMap.get(product) + "x " + product.getName(), () -> {
					productsMap.remove(product);
					Controller.getInstance().action(Event.UPDATE_PLAYER, player);
					updateProductsList();
				}));
            }
        }
        else {
            JLabel emptyLabel = new JLabel("El inventario no tiene objetos");
            emptyLabel.setForeground(theme.TEXT_MUTED);
            productsListPanel.add(emptyLabel);
        }
        productsListPanel.revalidate();
        productsListPanel.repaint();
    }

    private JPanel createGamesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(theme.BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Map<String, List<Integer>> availableGamesMap = new HashMap<>();
        Collection<tGame> allGames = Controller.getInstance().getAllGames();
        for(tGame game: allGames) {
            availableGamesMap.putIfAbsent(game.getName(), new ArrayList<>());
            availableGamesMap.get(game.getName()).add(game.getBasePoints());
        }

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        topPanel.setBackground(theme.BG);

        JLabel gameNameLabel = new JLabel("Juego:");
        gameNameLabel.setForeground(theme.TEXT);
        topPanel.add(gameNameLabel);

        JComboBox<String> gameNameComboBox = new JComboBox<>();
        gameNameComboBox.setBackground(theme.BG_ITEM);
        gameNameComboBox.setForeground(theme.TEXT);
        for(String gameName: availableGamesMap.keySet()) gameNameComboBox.addItem(gameName);
        if(gameNameComboBox.getItemCount() > 0) gameNameComboBox.setSelectedIndex(0);
        topPanel.add(gameNameComboBox);

        JLabel pointsLabel = new JLabel("Puntos:");
        pointsLabel.setForeground(theme.TEXT);
        topPanel.add(pointsLabel);

        JSpinner gamePointsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 10));
        topPanel.add(gamePointsSpinner);

        JLabel cantLabel = new JLabel("Cant:");
        cantLabel.setForeground(theme.TEXT);
        topPanel.add(cantLabel);

        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        topPanel.add(qtySpinner);

        topPanel.add(theme.primaryBtn("Añadir", () -> {
            String selectedName = (String)gameNameComboBox.getSelectedItem();
            Integer selectedPoints = (Integer)gamePointsSpinner.getValue();
            int qty = (int)qtySpinner.getValue();
            
            if(selectedName != null && selectedPoints != null) {
                if(!availableGamesMap.get(selectedName).contains(selectedPoints)) {
                    Controller.getInstance().action(Event.CREATE_GAME, new tGame(selectedName, selectedPoints));
                    availableGamesMap.get(selectedName).add(selectedPoints);
                }
                Map<tGame, Integer> gamesMap = player.getInventory().getGames();
                if(gamesMap == null) {
                    gamesMap = new HashMap<>();
                    player.getInventory().setGames(gamesMap);
                }
                tGame gameToAdd = new tGame(selectedName, selectedPoints);
                gamesMap.put(gameToAdd, gamesMap.getOrDefault(gameToAdd, 0) + qty);
                updateGamesList();
            }
        }));
        panel.add(topPanel, BorderLayout.NORTH);

        gamesListPanel = new JPanel();
        gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        gamesListPanel.setBackground(theme.BG);

        JScrollPane scrollPane = new JScrollPane(gamesListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(theme.BG);
        panel.add(scrollPane, BorderLayout.CENTER);

        updateGamesList();
        return panel;
    }

    private void updateGamesList() {
        gamesListPanel.removeAll();
        Map<tGame, Integer> gamesMap = player.getInventory().getGames();
        if(gamesMap != null && !gamesMap.isEmpty()) {
            for(tGame game: gamesMap.keySet()) {
            	gamesListPanel.add(buildRow(gamesMap.get(game) + "x " + game.getName() + " (" + game.getBasePoints() + "pts)", () -> {
            		gamesMap.remove(game);
            		updateGamesList();
            	}));
            }
        }
        else {
            JLabel emptyLabel = new JLabel("El inventario no tiene juegos");
            emptyLabel.setForeground(theme.TEXT_MUTED);
            gamesListPanel.add(emptyLabel);
        }
        gamesListPanel.revalidate();
        gamesListPanel.repaint();
    }

    private JPanel buildRow(String text, Runnable onRemove) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(theme.BG_ITEM);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        row.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0), BorderFactory.createLineBorder(theme.BORDER, 1)));

        JLabel infoLabel = new JLabel(text);
        infoLabel.setForeground(theme.TEXT);
        infoLabel.setFont(theme.BODY);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        row.add(infoLabel, BorderLayout.CENTER);
        row.add(theme.dangerBtn("Quitar", onRemove), BorderLayout.EAST);
        return row;
    }
}
