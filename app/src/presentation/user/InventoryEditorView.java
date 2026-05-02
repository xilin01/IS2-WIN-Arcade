package presentation.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
        this.getContentPane().setBackground(Theme.BG);

        JTabbedPane tabsPanel = new JTabbedPane();
        tabsPanel.setBackground(Theme.BG_CARD);
        tabsPanel.setForeground(Theme.TEXT);
        tabsPanel.setFont(Theme.BODY);
        tabsPanel.addTab("Objetos", createObjectsPanel());
        tabsPanel.addTab("Juegos", createGamesPanel());
        this.add(tabsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(Theme.BG_CARD);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        JButton backButton = Theme.secondaryBtn("Volver");
        backButton.addActionListener(e -> dispose());
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private JPanel createObjectsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Theme.BG);
        JButton addObjectBtn = Theme.primaryBtn("Añadir Objeto");
        addObjectBtn.addActionListener(e -> {
            Collection<tProduct> allProducts = Controller.getInstance().getAllProducts();
            JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            JComboBox<tProduct> productCombo = new JComboBox<>(allProducts.toArray(new tProduct[0]));
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

        objectsListPanel = new JPanel();
        objectsListPanel.setLayout(new BoxLayout(objectsListPanel, BoxLayout.Y_AXIS));
        objectsListPanel.setBackground(Theme.BG);

        JScrollPane scrollPane = new JScrollPane(objectsListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Theme.BG);
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
                objectsListPanel.add(buildRow(
                    cant + "x " + product.getName(),
                    e -> {
                        productsMap.remove(product);
                        Controller.getInstance().action(Event.UPDATE_USER, player);
                        updateObjectsList();
                    }
                ));
            }
        } else {
            JLabel emptyLabel = new JLabel("El inventario no tiene objetos.");
            emptyLabel.setForeground(Theme.TEXT_MUTED);
            objectsListPanel.add(emptyLabel);
        }

        objectsListPanel.revalidate();
        objectsListPanel.repaint();
    }

    private JPanel createGamesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Map<String, List<Integer>> availableGamesMap = new HashMap<>();
        Collection<tGame> allGames = Controller.getInstance().getAllGames();
        for (tGame g : allGames) {
            availableGamesMap.putIfAbsent(g.getName(), new ArrayList<>());
            availableGamesMap.get(g.getName()).add(g.getBasePoints());
        }

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        topPanel.setBackground(Theme.BG);

        JLabel gameNameLabel = new JLabel("Juego:");
        gameNameLabel.setForeground(Theme.TEXT);
        topPanel.add(gameNameLabel);

        JComboBox<String> gameNameComboBox = new JComboBox<>();
        gameNameComboBox.setBackground(Theme.BG_ITEM);
        gameNameComboBox.setForeground(Theme.TEXT);
        for (String gameName : availableGamesMap.keySet()) gameNameComboBox.addItem(gameName);
        if (gameNameComboBox.getItemCount() > 0) gameNameComboBox.setSelectedIndex(0);
        topPanel.add(gameNameComboBox);

        JLabel pointsLabel = new JLabel("Puntos:");
        pointsLabel.setForeground(Theme.TEXT);
        topPanel.add(pointsLabel);

        JSpinner gamePointsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 10));
        topPanel.add(gamePointsSpinner);

        JLabel cantLabel = new JLabel("Cant:");
        cantLabel.setForeground(Theme.TEXT);
        topPanel.add(cantLabel);

        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        topPanel.add(qtySpinner);

        JButton addGameBtn = Theme.primaryBtn("Añadir");
        addGameBtn.addActionListener(e -> {
            String selectedName = (String) gameNameComboBox.getSelectedItem();
            Integer selectedPoints = (Integer) gamePointsSpinner.getValue();
            int qty = (int) qtySpinner.getValue();
            if (selectedName != null && selectedPoints != null) {
                if (!availableGamesMap.get(selectedName).contains(selectedPoints)) {
                    Controller.getInstance().action(Event.CREATE_GAME, new tGame(selectedName, selectedPoints));
                    availableGamesMap.get(selectedName).add(selectedPoints);
                }
                Map<tGame, Integer> gamesMap = player.getInventory().getGames();
                if (gamesMap == null) {
                    gamesMap = new HashMap<>();
                    player.getInventory().setGames(gamesMap);
                }
                tGame gameToAdd = new tGame(selectedName, selectedPoints);
                gamesMap.put(gameToAdd, gamesMap.getOrDefault(gameToAdd, 0) + qty);
                updateGamesList();
            }
        });
        topPanel.add(addGameBtn);
        panel.add(topPanel, BorderLayout.NORTH);

        gamesListPanel = new JPanel();
        gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        gamesListPanel.setBackground(Theme.BG);

        JScrollPane scrollPane = new JScrollPane(gamesListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Theme.BG);
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
                gamesListPanel.add(buildRow(
                    cant + "x " + game.getName() + " (" + game.getBasePoints() + "p)",
                    e -> {
                        gamesMap.remove(game);
                        updateGamesList();
                    }
                ));
            }
        } else {
            JLabel emptyLabel = new JLabel("El inventario no tiene juegos.");
            emptyLabel.setForeground(Theme.TEXT_MUTED);
            gamesListPanel.add(emptyLabel);
        }

        gamesListPanel.revalidate();
        gamesListPanel.repaint();
    }

    private JPanel buildRow(String text, java.awt.event.ActionListener onRemove) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Theme.BG_ITEM);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(2, 0, 2, 0),
            BorderFactory.createLineBorder(Theme.BORDER, 1)
        ));

        JLabel infoLabel = new JLabel(text);
        infoLabel.setForeground(Theme.TEXT);
        infoLabel.setFont(Theme.BODY);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        row.add(infoLabel, BorderLayout.CENTER);

        JButton removeBtn = Theme.dangerBtn("Quitar");
        removeBtn.addActionListener(onRemove);
        row.add(removeBtn, BorderLayout.EAST);

        return row;
    }
}
