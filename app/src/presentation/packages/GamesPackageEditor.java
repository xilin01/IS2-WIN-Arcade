package presentation.packages;

import java.awt.BorderLayout;
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

import business.game.SAGame;
import business.game.SAGameImp;
import business.game.tGame;
import business.packages.SAGamesPackage;
import business.packages.SAGamesPackageImp;
import business.packages.tGamesPackage;

public class GamesPackageEditor extends JDialog {

    private JTextField nameField;
    private JSpinner priceSpinner;
    private tGamesPackage currentPack; 
    
    private Map<tGame, Integer> gamesMap; // Juegos que se van a guardar en el paquete
    
    private JPanel gamesListPanel;
    private JLabel totalPointsLabel; 
    private double currentTotalPoints = 0.0; 

    private SAGamesPackage saGamesPackage = new SAGamesPackageImp();
    private SAGame saGame = new SAGameImp();

    public GamesPackageEditor(JFrame parent, tGamesPackage pack) {
        super(parent, pack == null ? "Crear Nuevo Paquete" : "Editar Paquete", true);
        this.currentPack = pack;
        
        if (currentPack != null && currentPack.getGames() != null) {
            this.gamesMap = new HashMap<>(currentPack.getGames());
        } else {
            this.gamesMap = new HashMap<>();
        }
        
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(650, 500);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        // --- INFO DEL PAQUETE (Nombre y Precio) ---
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


        // --- BARRA PARA AÑADIR JUEGOS ---
        JPanel addGamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // 1. CARGAMOS LOS DATOS Y CREAMOS EL MAPA (Juego -> Lista de Puntos)
        Map<String, List<Integer>> availableGamesMap = new HashMap<>();
        for (tGame g : saGame.readAllGames()) {
            // Si el juego no está en el mapa, lo metemos con una lista vacía
            availableGamesMap.putIfAbsent(g.getName(), new ArrayList<>());
            // Añadimos los puntos a la lista de ese juego
            availableGamesMap.get(g.getName()).add(g.getBasePoints());
        }

        // 2. CREAMOS LOS DESPLEGABLES
        addGamePanel.add(new JLabel("Juego:"));
        JComboBox<String> gameNameComboBox = new JComboBox<>();
        for (String gameName : availableGamesMap.keySet()) {
            gameNameComboBox.addItem(gameName);
        }
        addGamePanel.add(gameNameComboBox);

        addGamePanel.add(new JLabel("Puntos:"));
        JComboBox<Integer> gamePointsComboBox = new JComboBox<>();
        addGamePanel.add(gamePointsComboBox);

        addGamePanel.add(new JLabel("Cant:"));
        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        addGamePanel.add(qtySpinner);

        // 3. LÓGICA: Cuando cambie el juego seleccionado, actualizamos la lista de puntos
        gameNameComboBox.addActionListener(e -> {
            String selectedGame = (String) gameNameComboBox.getSelectedItem();
            gamePointsComboBox.removeAllItems(); // Vaciamos los puntos anteriores
            if (selectedGame != null) {
                // Llenamos con los puntos válidos para el juego actual
                for (Integer pts : availableGamesMap.get(selectedGame)) {
                    gamePointsComboBox.addItem(pts);
                }
            }
        });

        // Forzamos la selección del primer elemento para que se rellene la lista de puntos al abrir la ventana
        if (gameNameComboBox.getItemCount() > 0) {
            gameNameComboBox.setSelectedIndex(0);
        }

        // 4. BOTÓN AÑADIR
        JButton addGameBtn = new JButton("Añadir");
        addGameBtn.addActionListener(e -> {
            String selectedName = (String) gameNameComboBox.getSelectedItem();
            Integer selectedPoints = (Integer) gamePointsComboBox.getSelectedItem();
            int qty = (int) qtySpinner.getValue();
            
            if(selectedName != null && selectedPoints != null) {
                // Creamos el objeto del juego exactamente con los datos de la base de datos
                tGame gameToAdd = new tGame(selectedName, selectedPoints);
                
                // Como tu tGame tiene métodos equals() y hashCode() basados en nombre y puntos,
                // podemos meterlo directamente al mapa. Si ya existía, machacará la cantidad antigua.
                gamesMap.put(gameToAdd, qty);
                updateList(); 
            }
        });
        addGamePanel.add(addGameBtn);
        topPanel.add(addGamePanel);

        this.add(topPanel, BorderLayout.NORTH);


        // --- PANEL CENTRAL (Lista de Juegos añadidos) ---
        gamesListPanel = new JPanel();
        gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(gamesListPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Juegos incluidos en el paquete"));
        
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBorder(new EmptyBorder(0, 10, 0, 10));
        centerWrapper.add(scrollPane, BorderLayout.CENTER);
        this.add(centerWrapper, BorderLayout.CENTER);


        // --- PANEL INFERIOR (Puntos Totales y Botones) ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
        
        totalPointsLabel = new JLabel("Valor en puntos: 0");
        totalPointsLabel.setFont(totalPointsLabel.getFont().deriveFont(java.awt.Font.BOLD, 14f));
        totalPointsLabel.setForeground(java.awt.Color.GRAY);
        bottomPanel.add(totalPointsLabel, BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancelar");
        JButton saveButton = new JButton("Guardar Paquete");

        cancelButton.addActionListener((e) -> dispose());

        saveButton.addActionListener((e) -> {
            String name = nameField.getText();
            double finalPrice = (double) priceSpinner.getValue();
            
            if (!name.isEmpty()) {
                if (currentPack == null) {
                    tGamesPackage nuevoPaquete = new tGamesPackage(name, finalPrice, gamesMap); 
                    saGamesPackage.createGamesPackage(nuevoPaquete); 
                } else {
                    currentPack.setName(name);
                    currentPack.setPrice(finalPrice); 
                    currentPack.setGames(gamesMap); 
                    saGamesPackage.updateGamesPackage(currentPack); 
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
                BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY)
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