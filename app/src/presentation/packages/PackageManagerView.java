package presentation.packages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
    private JPanel packListPanel;

    public PackageManagerView() {
        super("Gestor de paquetes");
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Theme.BG);

        JPanel header = Theme.header("Gestión de Paquetes");
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setBackground(Theme.BG_CARD);
        JButton createPackButton = Theme.primaryBtn("Crear Paquete");
        JButton backButton = Theme.secondaryBtn("Volver");
        headerRight.add(createPackButton);
        headerRight.add(backButton);
        header.add(headerRight, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        packListPanel = new JPanel();
        packListPanel.setLayout(new BoxLayout(packListPanel, BoxLayout.Y_AXIS));
        packListPanel.setBackground(Theme.BG);
        packListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(packListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Theme.BG);
        this.add(scrollPane, BorderLayout.CENTER);

        createPackButton.addActionListener(e -> {
            new GamesPackageEditor(this, null);
            updateList();
        });
        backButton.addActionListener(e -> {
            Controller.getInstance().action(Event.CLOSE_PACKAGE_MANAGER, null);
            dispose();
        });

        updateList();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private JPanel packageElement(tGamesPackage gamePack) {
        JPanel packagePanel = new JPanel(new BorderLayout());
        packagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));
        packagePanel.setBackground(Theme.BG_ITEM);
        packagePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(4, 0, 4, 0),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
            )
        ));

        JPanel packInfo = new JPanel();
        packInfo.setLayout(new BoxLayout(packInfo, BoxLayout.Y_AXIS));
        packInfo.setBackground(Theme.BG_ITEM);

        JLabel nameLabel = new JLabel(gamePack.getName());
        nameLabel.setFont(Theme.HEADING);
        nameLabel.setForeground(Theme.TEXT);
        packInfo.add(nameLabel);

        int totalGames = 0;
        Map<tGame, Integer> gamesMap = gamePack.getGames();
        if (gamesMap != null) {
            for (Integer qty : gamesMap.values()) totalGames += qty;
        }
        JLabel infoLabel = new JLabel("Total: " + totalGames + " juegos, Precio: " + gamePack.getPrice() + " EUR");
        infoLabel.setFont(Theme.BODY);
        infoLabel.setForeground(Theme.TEXT_MUTED);
        packInfo.add(infoLabel);

        if (gamesMap != null && !gamesMap.isEmpty()) {
            int count = 0;
            for (Map.Entry<tGame, Integer> entry : gamesMap.entrySet()) {
                JLabel gameLabel = new JLabel(entry.getValue() + "x " + entry.getKey().getName());
                gameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                gameLabel.setFont(Theme.BODY);
                gameLabel.setForeground(Theme.TEXT_MUTED);
                packInfo.add(gameLabel);
                if (++count >= 3) {
                    JLabel moreLabel = new JLabel("...");
                    moreLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                    moreLabel.setForeground(Theme.TEXT_MUTED);
                    packInfo.add(moreLabel);
                    break;
                }
            }
        } else {
            JLabel emptyLabel = new JLabel("Paquete vacio");
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            emptyLabel.setForeground(Theme.TEXT_MUTED);
            packInfo.add(emptyLabel);
        }

        packagePanel.add(packInfo, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonsPanel.setBackground(Theme.BG_ITEM);

        JButton editButton = Theme.secondaryBtn("Editar");
        editButton.addActionListener(e -> {
            new GamesPackageEditor(this, gamePack);
            updateList();
        });

        JButton deleteButton = Theme.dangerBtn("Eliminar");
        deleteButton.addActionListener(e -> {
            if (Theme.confirm(this, "Eliminar definitivamente el paquete " + gamePack.getName())) {
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
