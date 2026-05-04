package presentation.packages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import business.game.tGame;
import business.packages.tGamesPackage;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class PackageManagerView extends JFrame {
    private static final Theme theme = Theme.getInstance();
    
    private JPanel packListPanel;

    public PackageManagerView() {
        super("Gestor de paquetes");
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(theme.BG);

        JPanel header = theme.header("Gestión de paquetes");
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setBackground(theme.BG_CARD);
        headerRight.add(theme.primaryBtn("Crear paquete", () -> {
        	GamesPackageEditor.show(this, null);
        	updateList();
        }));
        headerRight.add(theme.secondaryBtn("Atrás", () -> {
        	Controller.getInstance().action(Event.OPEN_STAFF_VIEW, null);
        	dispose();
        }));
        header.add(headerRight, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        packListPanel = new JPanel();
        packListPanel.setLayout(new BoxLayout(packListPanel, BoxLayout.Y_AXIS));
        packListPanel.setBackground(theme.BG);
        packListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(packListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(theme.BG);
        this.add(scrollPane, BorderLayout.CENTER);

        updateList();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private JPanel packageElement(tGamesPackage gamePack) {
        JPanel packagePanel = new JPanel(new BorderLayout());
        packagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));
        packagePanel.setBackground(theme.BG_ITEM);
        packagePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0), 
        		BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(12, 16, 12, 16))));

        JPanel packInfo = new JPanel();
        packInfo.setLayout(new BoxLayout(packInfo, BoxLayout.Y_AXIS));
        packInfo.setBackground(theme.BG_ITEM);

        JLabel nameLabel = new JLabel(gamePack.getName());
        nameLabel.setFont(theme.HEADING);
        nameLabel.setForeground(theme.TEXT);
        packInfo.add(nameLabel);

        int totalGames = 0;
        Map<tGame, Integer> gamesMap = gamePack.getGames();
        if(gamesMap != null) {
            for(Integer qty: gamesMap.values()) totalGames += qty;
        }
        JLabel infoLabel = new JLabel("Total: " + totalGames + " juegos, precio: " + gamePack.getPrice() + "€");
        infoLabel.setFont(theme.BODY);
        infoLabel.setForeground(theme.TEXT_MUTED);
        packInfo.add(infoLabel);

        if(gamesMap != null && !gamesMap.isEmpty()) {
            int count = 0;
            for(Map.Entry<tGame, Integer> entry: gamesMap.entrySet()) {
                JLabel gameLabel = new JLabel(entry.getValue() + "x " + entry.getKey().getName() + " (" + entry.getKey().getBasePoints() + "pts)");
                
                gameLabel.setFont(theme.BODY);
                gameLabel.setForeground(theme.TEXT_MUTED);
                gameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                packInfo.add(gameLabel);
                if(++count >= 3) {
                    JLabel moreLabel = new JLabel("...");
                    moreLabel.setForeground(theme.TEXT_MUTED);
                    moreLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                    packInfo.add(moreLabel);
                    break;
                }
            }
        }
        else {
            JLabel emptyLabel = new JLabel("Paquete vacío");
            emptyLabel.setForeground(theme.TEXT_MUTED);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            packInfo.add(emptyLabel);
        }
        packagePanel.add(packInfo, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonsPanel.setBackground(theme.BG_ITEM);
        buttonsPanel.add(theme.secondaryBtn("Editar", () -> {
        	GamesPackageEditor.show(this, gamePack);
        	updateList();
        }));
        buttonsPanel.add(theme.dangerBtn("Eliminar", () -> {
            if(theme.confirm(this, "Eliminar definitivamente el paquete " + gamePack.getName())) {
                Controller.getInstance().action(Event.DELETE_PACKAGE, gamePack.getName());
                updateList();
            }
        }));
        packagePanel.add(buttonsPanel, BorderLayout.EAST);

        return packagePanel;
    }

    private void updateList() {
        packListPanel.removeAll();
        for(tGamesPackage pack: Controller.getInstance().getAllPackages()) packListPanel.add(packageElement(pack));
        packListPanel.revalidate();
        packListPanel.repaint();
    }
}
