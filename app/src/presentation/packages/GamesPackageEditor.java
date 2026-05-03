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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import business.game.tGame;
import business.packages.tGamesPackage;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

public class GamesPackageEditor {
    private static final Theme theme = Theme.getInstance();

    private GamesPackageEditor() {
    }

    public static void show(JFrame parent, tGamesPackage pack) {
        Map<tGame, Integer> gamesMap = (pack != null && pack.getGames() != null) ? pack.getGames() : new HashMap<>();

        JPanel content = new JPanel(new BorderLayout());

        // Top: name/price + add-game row
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBackground(theme.BG_CARD);
        topPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, theme.GOLD), BorderFactory.createEmptyBorder(10, 14, 10, 14)));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        infoPanel.setBackground(theme.BG_CARD);

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setForeground(theme.TEXT);
        infoPanel.add(nameLabel);

        JTextField nameField = new JTextField(15);
        nameField.setBackground(theme.BG_ITEM);
        nameField.setForeground(theme.TEXT);
        nameField.setCaretColor(theme.TEXT);
        nameField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        if(pack != null) nameField.setText(pack.getName());
        infoPanel.add(nameField);

        JLabel priceLabel = new JLabel("Precio:");
        priceLabel.setForeground(theme.TEXT);
        infoPanel.add(priceLabel);

        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999.99, 0.5));
        styleSpinner(priceSpinner);
        if(pack != null) priceSpinner.setValue(pack.getPrice());
        infoPanel.add(priceSpinner);
        topPanel.add(infoPanel);

        JPanel addGamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        addGamePanel.setBackground(theme.BG_CARD);

        Map<String, List<Integer>> availableGamesMap = new HashMap<>();
        Collection<tGame> allGames = Controller.getInstance().getAllGames();
        for(tGame g: allGames) {
            availableGamesMap.putIfAbsent(g.getName(), new ArrayList<>());
            availableGamesMap.get(g.getName()).add(g.getBasePoints());
        }

        JLabel gameNameLabel = new JLabel("Juego:");
        gameNameLabel.setForeground(theme.TEXT);
        addGamePanel.add(gameNameLabel);

        JComboBox<String> gameNameComboBox = new JComboBox<>();
        gameNameComboBox.setBackground(theme.BG_ITEM);
        gameNameComboBox.setForeground(theme.TEXT);
        for(String gameName : availableGamesMap.keySet()) gameNameComboBox.addItem(gameName);
        if(gameNameComboBox.getItemCount() > 0) gameNameComboBox.setSelectedIndex(0);
        addGamePanel.add(gameNameComboBox);

        JLabel pointsLabel = new JLabel("Puntos:");
        pointsLabel.setForeground(theme.TEXT);
        addGamePanel.add(pointsLabel);

        JSpinner gamePointsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 10));
        styleSpinner(gamePointsSpinner);
        addGamePanel.add(gamePointsSpinner);

        JLabel cantLabel = new JLabel("Cant:");
        cantLabel.setForeground(theme.TEXT);
        addGamePanel.add(cantLabel);

        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        styleSpinner(qtySpinner);
        addGamePanel.add(qtySpinner);

        topPanel.add(addGamePanel);
        content.add(topPanel, BorderLayout.NORTH);

        // Game list
        JPanel gamesListPanel = new JPanel();
        gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        gamesListPanel.setBackground(theme.BG);
        gamesListPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(gamesListPanel);
        scrollPane.setPreferredSize(new Dimension(610, 240));
        scrollPane.getViewport().setBackground(theme.BG);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(theme.BORDER, 1), "Juegos incluidos en el paquete");
        titledBorder.setTitleColor(theme.TEXT_MUTED);
        scrollPane.setBorder(titledBorder);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(theme.BG);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(8, 10, 0, 10));
        centerWrapper.add(scrollPane, BorderLayout.CENTER);
        content.add(centerWrapper, BorderLayout.CENTER);

        // Total points label sits just above the formDialog button bar
        JLabel totalPointsLabel = new JLabel("Valor en puntos: 0");
        totalPointsLabel.setFont(theme.HEADING);
        totalPointsLabel.setForeground(theme.GOLD);
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 8));
        totalPanel.setBackground(theme.BG_CARD);
        totalPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, theme.BORDER));
        totalPanel.add(totalPointsLabel);
        content.add(totalPanel, BorderLayout.SOUTH);

        // updateList — Runnable array so it can reference itself from "Quitar" handlers
        double[] totalPts = {0.0};
        Runnable[] updateRef = {null};
        updateRef[0] = () -> {
            gamesListPanel.removeAll();
            totalPts[0] = 0.0;
            for(Map.Entry<tGame, Integer> entry: gamesMap.entrySet()) {
                tGame juego = entry.getKey();
                Integer cantidad = entry.getValue();
                int subtotal = juego.getBasePoints() * cantidad;
                totalPts[0] += subtotal;

                JPanel row = new JPanel(new BorderLayout());
                row.setBackground(theme.BG_ITEM);
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                row.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0),BorderFactory.createLineBorder(theme.BORDER, 1)));

                JLabel infoLabel = new JLabel(cantidad + "x " + juego.getName() + " (" + juego.getBasePoints() + " pts/u) = " + subtotal + " pts");
                infoLabel.setForeground(theme.TEXT);
                infoLabel.setFont(theme.BODY);
                infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
                row.add(infoLabel, BorderLayout.CENTER);
                row.add(theme.dangerBtn("Quitar", () -> {
								                	gamesMap.remove(juego); 
								                	updateRef[0].run();
								                	}), BorderLayout.EAST);
                gamesListPanel.add(row);
            }
            totalPointsLabel.setText("Valor en puntos: " + (int)totalPts[0]);
            gamesListPanel.revalidate();
            gamesListPanel.repaint();
        };

        addGamePanel.add(theme.primaryBtn("Añadir", () -> {
            String selectedName = (String)gameNameComboBox.getSelectedItem();
            Integer selectedPoints = (Integer)gamePointsSpinner.getValue();
            int qty = (int)qtySpinner.getValue();
            
            if(selectedName != null && selectedPoints != null) {
                if(!availableGamesMap.get(selectedName).contains(selectedPoints)) {
                    Controller.getInstance().action(Event.CREATE_GAME, new tGame(selectedName, selectedPoints));
                    availableGamesMap.get(selectedName).add(selectedPoints);
                }
                gamesMap.put(new tGame(selectedName, selectedPoints), qty);
                updateRef[0].run();
            }
        }));

        updateRef[0].run();

        theme.formDialog(parent, pack == null ? "Crear nuevo paquete" : "Editar paquete", content, "Guardar", () -> {
            String name = nameField.getText();
            double finalPrice = (double) priceSpinner.getValue();
            
            if(!name.isEmpty()) {
                if(pack == null) Controller.getInstance().action(Event.CREATE_PACKAGE, new tGamesPackage(name, finalPrice, gamesMap));
                else {
                    pack.setName(name);
                    pack.setPrice(finalPrice);
                    pack.setGames(gamesMap);
                    Controller.getInstance().action(Event.UPDATE_PACKAGE, pack);
                }
                return true;
            }
            return false;
        });
    }

    private static void styleSpinner(JSpinner spinner) {
        spinner.setBackground(theme.BG_ITEM);
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setBackground(theme.BG_ITEM);
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setForeground(theme.TEXT);
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setCaretColor(theme.TEXT);
    }
}
