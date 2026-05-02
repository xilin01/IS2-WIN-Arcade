package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Theme {

    public static final Color BG = new Color(15, 15, 25);
    public static final Color BG_CARD = new Color(22, 22, 38);
    public static final Color BG_ITEM = new Color(32, 32, 52);
    public static final Color GOLD = new Color(212, 175, 55);
    public static final Color TEXT = Color.WHITE;
    public static final Color TEXT_MUTED = new Color(155, 155, 175);
    public static final Color DANGER = new Color(180, 50, 50);
    public static final Color SUCCESS = new Color(46, 148, 82);
    public static final Color BORDER = new Color(55, 55, 80);

    public static final Font TITLE = new Font("SansSerif", Font.BOLD, 32);
    public static final Font HEADING = new Font("SansSerif", Font.BOLD, 16);
    public static final Font BODY = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font BTN = new Font("SansSerif", Font.BOLD, 13);

    public static JPanel header(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, GOLD),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        JLabel label = new JLabel(title);
        label.setFont(HEADING);
        label.setForeground(GOLD);
        panel.add(label, BorderLayout.WEST);
        return panel;
    }

    public static JButton primaryBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(GOLD);
        b.setForeground(BG);
        b.setFont(BTN);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        b.setOpaque(true);
        return b;
    }

    public static JButton secondaryBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(BG_ITEM);
        b.setForeground(TEXT);
        b.setFont(BTN);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        b.setOpaque(true);
        return b;
    }

    public static JButton dangerBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(DANGER);
        b.setForeground(TEXT);
        b.setFont(BTN);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        b.setOpaque(true);
        return b;
    }

    public static JButton successBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(SUCCESS);
        b.setForeground(TEXT);
        b.setFont(BTN);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        b.setOpaque(true);
        return b;
    }

    public static boolean confirm(Component parent, String message) {
        boolean[] result = {false};
        Window owner = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(owner, "Confirmar", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BG);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel msgLabel = new JLabel(message);
        msgLabel.setForeground(TEXT);
        msgLabel.setFont(BODY);
        msgLabel.setBorder(BorderFactory.createEmptyBorder(20, 24, 12, 24));
        dialog.add(msgLabel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setBackground(BG_CARD);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER));

        JButton noBtn = secondaryBtn("No");
        JButton yesBtn = dangerBtn("Confirmar");

        noBtn.addActionListener(e -> dialog.dispose());
        yesBtn.addActionListener(e -> { result[0] = true; dialog.dispose(); });

        btnPanel.add(noBtn);
        btnPanel.add(yesBtn);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setSize(340, 150);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return result[0];
    }
}
