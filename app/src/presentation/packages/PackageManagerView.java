package presentation.packages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import business.game.tGame;
import business.packages.tGamesPackage;
import presentation.Controller;
import presentation.Event;

@SuppressWarnings("serial")
public class PackageManagerView extends JFrame {
    private JPanel packListPanel;

    public PackageManagerView() {
        super("Gestor de paquetes");
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Boton Crear paquete de juegos
        JPanel createPackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createPackButton = new JButton("CREAR NUEVO PAQUETE");
        createPackButton.setPreferredSize(new Dimension(150, 35));
        createPackButton.addActionListener(e -> {
        	new GamesPackageEditor(this, null);
        	updateList();
        });

        createPackPanel.add(createPackButton);

        this.add(createPackPanel, BorderLayout.NORTH);

        //Lista de paquetes
        packListPanel = new JPanel();
        packListPanel.setLayout(new BoxLayout(packListPanel, BoxLayout.Y_AXIS));

        packListPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        for (tGamesPackage pack : Controller.getInstance().getAllPackages()) packListPanel.add(packageElement(pack));
        JScrollPane packScrollPane = new JScrollPane(packListPanel);

        this.add(packScrollPane, BorderLayout.CENTER);

        //Boton back para volver al panel de staff
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");

        bottomPanel.add(backButton);

        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            Controller.getInstance().action(Event.CLOSE_PACKAGE_MANAGER, null);
            dispose();
        });

        this.setVisible(true);
    }

    private JPanel packageElement(tGamesPackage gamePack) {
        JPanel packagePanel = new JPanel(new BorderLayout());
        
        packagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        packagePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)
        ));

        // Información general del paquete
        JPanel packInfo = new JPanel();
        packInfo.setLayout(new BoxLayout(packInfo, BoxLayout.Y_AXIS));
        packInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Nombre del paquete
        JLabel nameLabel = new JLabel(gamePack.getName());
        nameLabel.setFont(nameLabel.getFont().deriveFont(java.awt.Font.BOLD, 14f));
        packInfo.add(nameLabel);

        // Total de juegos y precio
        int totalGames = 0;
        Map<tGame, Integer> gamesMap = gamePack.getGames();
        if (gamesMap != null) {
            for (Integer qty : gamesMap.values()) {
                totalGames += qty;
            }
        }
        JLabel packInfoLabel = new JLabel("Total juegos: " + totalGames + "  |  Precio: " + gamePack.getPrice() + "€");
        packInfoLabel.setForeground(Color.DARK_GRAY);
        packInfo.add(packInfoLabel);

        // Lista de los primeros 3 juegos y ... después
        if (gamesMap != null && !gamesMap.isEmpty()) {
            int count = 0;
            for (Map.Entry<tGame, Integer> entry : gamesMap.entrySet()) {
                tGame game = entry.getKey();
                int cant = entry.getValue();
                JLabel gameLabel = new JLabel("  - " + cant + "x " + game.getName() + " (" + game.getBasePoints() + " pts)");
                gameLabel.setFont(gameLabel.getFont().deriveFont(11));
                packInfo.add(gameLabel);
                
                count++;
                
                if (count >= 3) {
                    JLabel dotsLabel = new JLabel("  ...");
                    dotsLabel.setForeground(Color.GRAY);
                    packInfo.add(dotsLabel);
                    break;
                }
            }
        } else {
            JLabel emptyLabel = new JLabel("  (Paquete vacío)");
            emptyLabel.setForeground(Color.GRAY);
            packInfo.add(emptyLabel);
        }

        packagePanel.add(packInfo, BorderLayout.CENTER);

        // Botones editar y borrar paquete
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton editButton = new JButton(new javax.swing.ImageIcon("resources/icons/edit.png"));
        editButton.setPreferredSize(new Dimension(35, 35));
        editButton.addActionListener(e -> {
            new GamesPackageEditor(this, gamePack);
            updateList();
        });
        
        JButton deleteButton = new JButton(new javax.swing.ImageIcon("resources/icons/delete.png"));
        deleteButton.setPreferredSize(new Dimension(35, 35));
        deleteButton.addActionListener(e -> {
            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this, 
                "¿Eliminar definitivamente el paquete '" + gamePack.getName() + "'?", 
                "Confirmar", 
                javax.swing.JOptionPane.YES_NO_OPTION
            );

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                Controller.getInstance().action(Event.DELETE_PACKAGE, gamePack.getName());
                updateList();
            }
        });

        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        packagePanel.add(buttonsPanel, BorderLayout.EAST);

        return packagePanel;
    }
    
    private void updateList() {
        packListPanel.removeAll();
        for (tGamesPackage pack : Controller.getInstance().getAllPackages()) packListPanel.add(packageElement(pack));
        packListPanel.revalidate();
        packListPanel.repaint();
    }
}
