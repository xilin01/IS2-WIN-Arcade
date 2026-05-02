package presentation.packages;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import business.game.tGame;
import business.packages.tGamesPackage;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class GamesPackageEditor extends JDialog {
    private JTextField nameField;
    private JSpinner priceSpinner;
    private tGamesPackage currentPack;
    private Map<tGame, Integer> gamesMap;
    private JPanel gamesListPanel;
    private JLabel totalPointsLabel;
    private double currentTotalPoints = 0;

    public GamesPackageEditor(JFrame parent, tGamesPackage pack) {
        super(parent, pack == null ? "Crear Nuevo Paquete" : "Editar Paquete", true);
        this.currentPack = pack;
        this.gamesMap = (currentPack != null && currentPack.getGames() != null)
            ? new HashMap<>(currentPack.getGames())
            : new HashMap<>();
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(650, 500);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Theme.BG);

        // Top: name/price + add-game row
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBackground(Theme.BG_CARD);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.GOLD),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        infoPanel.setBackground(Theme.BG_CARD);

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setForeground(Theme.TEXT);
        infoPanel.add(nameLabel);

        nameField = new JTextField(15);
        nameField.setBackground(Theme.BG_ITEM);
        nameField.setForeground(Theme.TEXT);
        nameField.setCaretColor(Theme.TEXT);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        if (currentPack != null) nameField.setText(currentPack.getName());
        infoPanel.add(nameField);

        JLabel priceLabel = new JLabel("Precio:");
        priceLabel.setForeground(Theme.TEXT);
        infoPanel.add(priceLabel);

        priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999.99, 0.5));
        priceSpinner.setBackground(Theme.BG_ITEM);
        ((JSpinner.DefaultEditor) priceSpinner.getEditor()).getTextField().setBackground(Theme.BG_ITEM);
        ((JSpinner.DefaultEditor) priceSpinner.getEditor()).getTextField().setForeground(Theme.TEXT);
        ((JSpinner.DefaultEditor) priceSpinner.getEditor()).getTextField().setCaretColor(Theme.TEXT);
        if (currentPack != null) priceSpinner.setValue(currentPack.getPrice());
        infoPanel.add(priceSpinner);

        topPanel.add(infoPanel);

        // Add-game row
        JPanel addGamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        addGamePanel.setBackground(Theme.BG_CARD);

        Map<String, List<Integer>> availableGamesMap = new HashMap<>();
        Collection<tGame> allGames = Controller.getInstance().getAllGames();
        for (tGame g : allGames) {
            availableGamesMap.putIfAbsent(g.getName(), new ArrayList<>());
            availableGamesMap.get(g.getName()).add(g.getBasePoints());
        }

        JLabel gameNameLabel = new JLabel("Juego:");
        gameNameLabel.setForeground(Theme.TEXT);
        addGamePanel.add(gameNameLabel);

        JComboBox<String> gameNameComboBox = new JComboBox<>();
        gameNameComboBox.setBackground(Theme.BG_ITEM);
        gameNameComboBox.setForeground(Theme.TEXT);
        for (String gameName : availableGamesMap.keySet()) gameNameComboBox.addItem(gameName);
        if (gameNameComboBox.getItemCount() > 0) gameNameComboBox.setSelectedIndex(0);
        addGamePanel.add(gameNameComboBox);

        JLabel pointsLabel = new JLabel("Puntos:");
        pointsLabel.setForeground(Theme.TEXT);
        addGamePanel.add(pointsLabel);

        JSpinner gamePointsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 10));
        styleSpinner(gamePointsSpinner);
        addGamePanel.add(gamePointsSpinner);

        JLabel cantLabel = new JLabel("Cant:");
        cantLabel.setForeground(Theme.TEXT);
        addGamePanel.add(cantLabel);

        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        styleSpinner(qtySpinner);
        addGamePanel.add(qtySpinner);

        JButton addGameBtn = Theme.primaryBtn("Añadir");
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
                gamesMap.put(new tGame(selectedName, selectedPoints), qty);
                updateList();
            }
        });
        addGamePanel.add(addGameBtn);

        topPanel.add(addGamePanel);
        this.add(topPanel, BorderLayout.NORTH);

        // Game list
        gamesListPanel = new JPanel();
        gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        gamesListPanel.setBackground(Theme.BG);
        gamesListPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(gamesListPanel);
        scrollPane.getViewport().setBackground(Theme.BG);

        javax.swing.border.TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1), "Juegos incluidos en el paquete"
        );
        titledBorder.setTitleColor(Theme.TEXT_MUTED);
        scrollPane.setBorder(titledBorder);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(Theme.BG);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(8, 10, 0, 10));
        centerWrapper.add(scrollPane, BorderLayout.CENTER);
        this.add(centerWrapper, BorderLayout.CENTER);

        // Bottom: total points + buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Theme.BG_CARD);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));

        totalPointsLabel = new JLabel("Valor en puntos: 0");
        totalPointsLabel.setFont(Theme.HEADING);
        totalPointsLabel.setForeground(Theme.GOLD);
        bottomPanel.add(totalPointsLabel, BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonsPanel.setBackground(Theme.BG_CARD);
        JButton cancelButton = Theme.secondaryBtn("Cancelar");
        JButton saveButton = Theme.primaryBtn("Guardar Paquete");

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            double finalPrice = (double) priceSpinner.getValue();
            if (!name.isEmpty()) {
                if (currentPack == null) Controller.getInstance().action(Event.CREATE_PACKAGE, new tGamesPackage(name, finalPrice, gamesMap));
                else {
                    currentPack.setName(name);
                    currentPack.setPrice(finalPrice);
                    currentPack.setGames(gamesMap);
                    Controller.getInstance().action(Event.UPDATE_PACKAGE, currentPack);
                }
                dispose();
            }
        });

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);
        bottomPanel.add(buttonsPanel, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);

        updateList();
        this.setVisible(true);
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setBackground(Theme.BG_ITEM);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setBackground(Theme.BG_ITEM);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setForeground(Theme.TEXT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setCaretColor(Theme.TEXT);
    }

    private void updateList() {
        gamesListPanel.removeAll();
        currentTotalPoints = 0.0;

        for (Map.Entry<tGame, Integer> entry : gamesMap.entrySet()) {
            tGame juego = entry.getKey();
            Integer cantidad = entry.getValue();
            int subtotalJuego = juego.getBasePoints() * cantidad;
            currentTotalPoints += subtotalJuego;

            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(Theme.BG_ITEM);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2, 0, 2, 0),
                BorderFactory.createLineBorder(Theme.BORDER, 1)
            ));

            JLabel infoLabel = new JLabel(cantidad + "x " + juego.getName() + " (" + juego.getBasePoints() + " pts/u) = " + subtotalJuego + " pts");
            infoLabel.setForeground(Theme.TEXT);
            infoLabel.setFont(Theme.BODY);
            infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
            row.add(infoLabel, BorderLayout.CENTER);

            JButton removeBtn = Theme.dangerBtn("Quitar");
            removeBtn.addActionListener(e -> {
                gamesMap.remove(juego);
                updateList();
            });
            row.add(removeBtn, BorderLayout.EAST);

            gamesListPanel.add(row);
        }

        totalPointsLabel.setText("Valor en puntos: " + (int) currentTotalPoints);
        gamesListPanel.revalidate();
        gamesListPanel.repaint();
    }
}
