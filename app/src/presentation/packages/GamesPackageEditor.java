package presentation.packages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
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
import javax.swing.border.EmptyBorder;
import java.util.Collection;
import business.game.tGame;
import business.packages.tGamesPackage;
import presentation.Controller;
import presentation.Event;

@SuppressWarnings("serial")
public class GamesPackageEditor extends JDialog {
    private JTextField nameField;
    private JSpinner priceSpinner;
    
    private tGamesPackage currentPack; 
    private Map<tGame, Integer> gamesMap; // Juegos que se van a guardar en el paquete
    
    private JPanel gamesListPanel;
    private JLabel totalPointsLabel; 
    private double currentTotalPoints = 0; 


    public GamesPackageEditor(JFrame parent, tGamesPackage pack) {
        super(parent, pack == null ? "Crear Nuevo Paquete" : "Editar Paquete", true);
        this.currentPack = pack;
        if (currentPack != null && currentPack.getGames() != null) this.gamesMap = new HashMap<>(currentPack.getGames());
        else this.gamesMap = new HashMap<>();
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(650, 500);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        // Nombre y precio
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        
        infoPanel.add(new JLabel("Nombre:"));
        nameField = new JTextField(15);
        if (currentPack != null) nameField.setText(currentPack.getName());
        infoPanel.add(nameField);

        infoPanel.add(new JLabel("Precio (€):"));
        priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999.99, 0.5));
        if (currentPack != null) priceSpinner.setValue(currentPack.getPrice());
        infoPanel.add(priceSpinner);
        
        topPanel.add(infoPanel);

        
        // Barra crear juego
        JPanel addGamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Lista de Juegos posibles para añadir
        Map<String, List<Integer>> availableGamesMap = new HashMap<>();
        Collection<tGame> allGames = Controller.getInstance().getAllGames();
        for (tGame g : allGames) {
            availableGamesMap.putIfAbsent(g.getName(), new ArrayList<>());
            availableGamesMap.get(g.getName()).add(g.getBasePoints());
        }

        // Opciones añadir juego al paquete
        addGamePanel.add(new JLabel("Juego:"));
        JComboBox<String> gameNameComboBox = new JComboBox<>();
        for (String gameName : availableGamesMap.keySet()) gameNameComboBox.addItem(gameName);
        addGamePanel.add(gameNameComboBox);

        addGamePanel.add(new JLabel("Puntos:"));
        JSpinner gamePointsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 10));
        addGamePanel.add(gamePointsSpinner);

        addGamePanel.add(new JLabel("Cant:"));
        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        addGamePanel.add(qtySpinner);

        if (gameNameComboBox.getItemCount() > 0) gameNameComboBox.setSelectedIndex(0);

        JButton addGameBtn = new JButton("Añadir");
        addGameBtn.addActionListener(e -> {
            String selectedName = (String) gameNameComboBox.getSelectedItem();
            Integer selectedPoints = (Integer) gamePointsSpinner.getValue();
            int qty = (int) qtySpinner.getValue();
            
            if(selectedName != null && selectedPoints != null) {
            	if (!availableGamesMap.get(selectedName).contains(selectedPoints)) {
            		tGame newGame = new tGame(selectedName, selectedPoints);
            		
            		Controller.getInstance().action(Event.CREATE_GAME, newGame);
            		
            		availableGamesMap.get(selectedName).add(selectedPoints);
            	}
                tGame gameToAdd = new tGame(selectedName, selectedPoints);
                
                gamesMap.put(gameToAdd, qty);
                updateList(); 
            }
        });
        addGamePanel.add(addGameBtn);
        topPanel.add(addGamePanel);

        this.add(topPanel, BorderLayout.NORTH);
        

        // Lista de juegos
        gamesListPanel = new JPanel();
        gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(gamesListPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Juegos incluidos en el paquete"));
        
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBorder(new EmptyBorder(0, 10, 0, 10));
        centerWrapper.add(scrollPane, BorderLayout.CENTER);
        this.add(centerWrapper, BorderLayout.CENTER);


        // Panel abajo: Puntos totales y botones guardar/cancelar
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
        
        totalPointsLabel = new JLabel("Valor en puntos: 0");
        totalPointsLabel.setFont(totalPointsLabel.getFont().deriveFont(java.awt.Font.BOLD, 14f));
        totalPointsLabel.setForeground(Color.GRAY);
        bottomPanel.add(totalPointsLabel, BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancelar");
        JButton saveButton = new JButton("Guardar Paquete");

        cancelButton.addActionListener((e) -> dispose());

        saveButton.addActionListener((e) -> {
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

    private void updateList() {
        gamesListPanel.removeAll();
        currentTotalPoints = 0.0; 
        
        for (Map.Entry<tGame, Integer> entry : gamesMap.entrySet()) {
            tGame juego = entry.getKey();
            Integer cantidad = entry.getValue();
            int subtotalJuego = juego.getBasePoints() * cantidad;
            
            currentTotalPoints += subtotalJuego; 

            JPanel row = new JPanel(new BorderLayout());
            row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2, 2, 2, 2),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY)
            ));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            JLabel infoLabel = new JLabel("  " + cantidad + "x " + juego.getName() + " (" + juego.getBasePoints() + " pts/u) = " + subtotalJuego + " pts"); 
            row.add(infoLabel, BorderLayout.CENTER);

            JButton removeBtn = new JButton("X");
            removeBtn.setForeground(java.awt.Color.RED);
            removeBtn.addActionListener(e -> {
                gamesMap.remove(juego);
                updateList(); 
            });
            row.add(removeBtn, BorderLayout.EAST);

            gamesListPanel.add(row);
        }
        
        totalPointsLabel.setText("Valor en puntos: " + (int)currentTotalPoints);
        
        gamesListPanel.revalidate();
        gamesListPanel.repaint();
    }
}