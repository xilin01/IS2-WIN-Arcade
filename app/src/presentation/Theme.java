package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
}
