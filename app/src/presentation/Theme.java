package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.net.URL;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// Patrón Singleton + Facade 
public class Theme {
	 private static class ColorPalette {
        final Color BG = new Color(15, 15, 25);
        final Color BG_CARD = new Color(22, 22, 38);
        final Color BG_ITEM = new Color(32, 32, 52);
        final Color GOLD = new Color(212, 175, 55);
        final Color TEXT = Color.WHITE;
        final Color TEXT_MUTED = new Color(155, 155, 175);
        final Color DANGER = new Color(180, 50, 50);
        final Color SUCCESS = new Color(46, 148, 82);
        final Color BORDER = new Color(55, 55, 80);
	 }
	 private static class Typography {
        final Font TITLE = new Font("SansSerif", Font.BOLD, 32);
        final Font HEADING = new Font("SansSerif", Font.BOLD, 16);
        final Font BODY = new Font("SansSerif", Font.PLAIN, 13);
        final Font BTN = new Font("SansSerif", Font.BOLD, 13);
	 }
	 private static class ComponentFactory {
        private final ColorPalette c;
        private final Typography t;

        ComponentFactory(ColorPalette c, Typography t) {
            this.c = c;
            this.t = t;
        }

        JPanel header(String title) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(c.BG_CARD);
            panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, c.GOLD),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
            ));
            
            JLabel label = new JLabel(title);
            label.setFont(t.HEADING);
            label.setForeground(c.GOLD);
            panel.add(label, BorderLayout.WEST);
            return panel;
        }

        JButton primaryBtn(String text, Runnable onClick) {
            JButton b = new JButton(text);
            b.setBackground(c.GOLD);
            b.setForeground(c.BG);
            b.setFont(t.BTN);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            b.setOpaque(true);
            b.addActionListener(e -> onClick.run());
            return b;
        }

        JButton secondaryBtn(String text, Runnable onClick) {
            JButton b = new JButton(text);
            b.setBackground(c.BG_ITEM);
            b.setForeground(c.TEXT);
            b.setFont(t.BTN);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(c.BORDER, 1), BorderFactory.createEmptyBorder(6, 14, 6, 14)));
            b.setOpaque(true);
            b.addActionListener(e -> onClick.run());
            return b;
        }

        JButton dangerBtn(String text, Runnable onClick) {
            JButton b = new JButton(text);
            b.setBackground(c.DANGER);
            b.setForeground(c.TEXT);
            b.setFont(t.BTN);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
            b.setOpaque(true);
            b.addActionListener(e -> onClick.run());
            return b;
        }

        JButton successBtn(String text, Runnable onClick) {
            JButton b = new JButton(text);
            b.setBackground(c.SUCCESS);
            b.setForeground(c.TEXT);
            b.setFont(t.BTN);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
            b.setOpaque(true);
            b.addActionListener(e -> onClick.run());
            return b;
        }

        void formDialog(Component parent, String title, JPanel content, String confirmLabel, Supplier<Boolean> onConfirm) {
            JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), title, Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setLayout(new BorderLayout());
            dialog.getContentPane().setBackground(c.BG);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            content.setBackground(c.BG);
            dialog.add(content, BorderLayout.CENTER);

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            btnPanel.setBackground(c.BG_CARD);
            btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, c.BORDER));
            btnPanel.add(secondaryBtn("Cancelar", dialog::dispose));
            btnPanel.add(primaryBtn(confirmLabel, () -> {if(onConfirm.get()) dialog.dispose();}));
            dialog.add(btnPanel, BorderLayout.SOUTH);

            dialog.pack();
            dialog.setLocationRelativeTo(parent);
            dialog.setVisible(true);
        }

        void showInfo(Component parent, String message) {
            Window owner = parent != null ? SwingUtilities.getWindowAncestor(parent) : null;
            JDialog dialog = new JDialog(owner, "Información", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setLayout(new BorderLayout());
            dialog.getContentPane().setBackground(c.BG);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            
            JLabel msgLabel = new JLabel(message);
            msgLabel.setForeground(c.TEXT);
            msgLabel.setFont(t.BODY);
            msgLabel.setBorder(BorderFactory.createEmptyBorder(20, 24, 12, 24));
            dialog.add(msgLabel, BorderLayout.CENTER);
            
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            btnPanel.setBackground(c.BG_CARD);
            btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, c.BORDER));
            btnPanel.add(primaryBtn("Aceptar", dialog::dispose));
            dialog.add(btnPanel, BorderLayout.SOUTH);
            
            dialog.setSize(420, 150);
            dialog.setLocationRelativeTo(parent);
            dialog.setVisible(true);
        }

        void showError(Component parent, String message) {
            Window owner = parent != null ? SwingUtilities.getWindowAncestor(parent) : null;
            JDialog dialog = new JDialog(owner, "Error", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setLayout(new BorderLayout());
            dialog.getContentPane().setBackground(c.BG);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            
            JLabel titleLabel = new JLabel("Error");
            titleLabel.setForeground(c.DANGER);
            titleLabel.setFont(t.HEADING);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(16, 24, 4, 24));
            dialog.add(titleLabel, BorderLayout.NORTH);
            
            JLabel msgLabel = new JLabel(message);
            msgLabel.setForeground(c.TEXT);
            msgLabel.setFont(t.BODY);
            msgLabel.setBorder(BorderFactory.createEmptyBorder(4, 24, 12, 24));
            dialog.add(msgLabel, BorderLayout.CENTER);
            
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            btnPanel.setBackground(c.BG_CARD);
            btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, c.BORDER));
            btnPanel.add(dangerBtn("Cerrar", dialog::dispose));
            dialog.add(btnPanel, BorderLayout.SOUTH);
            
            dialog.setSize(420, 170);
            dialog.setLocationRelativeTo(parent);
            dialog.setVisible(true);
        }

        boolean confirm(Component parent, String message) {
            boolean[] result = {false};
            JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), "Confirmar", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setLayout(new BorderLayout());
            dialog.getContentPane().setBackground(c.BG);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JLabel msgLabel = new JLabel(message);
            msgLabel.setForeground(c.TEXT);
            msgLabel.setFont(t.BODY);
            msgLabel.setBorder(BorderFactory.createEmptyBorder(20, 24, 12, 24));
            dialog.add(msgLabel, BorderLayout.CENTER);

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            btnPanel.setBackground(c.BG_CARD);
            btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, c.BORDER));
            btnPanel.add(secondaryBtn("No", dialog::dispose));
            btnPanel.add(dangerBtn("Confirmar", () -> {result[0] = true; dialog.dispose();}));

            dialog.add(btnPanel, BorderLayout.SOUTH);
            dialog.setSize(340, 150);
            dialog.setLocationRelativeTo(parent);
            dialog.setVisible(true);

            return result[0];
        }
        
		@SuppressWarnings("serial")
		JPanel backgroundPanel(URL url) {
            if(url == null) throw new IllegalArgumentException("Invalid arg for backgroundPanel in ComponentFactory");
            Image bgImage = new ImageIcon(url).getImage();
            
            return new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if(bgImage != null) g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
        }
    }
	
    private static final Theme INSTANCE = new Theme();

    private final ColorPalette palette;
    private final Typography typography;
    private final ComponentFactory factory;

    public final Color BG;
    public final Color BG_CARD;
    public final Color BG_ITEM;
    public final Color GOLD;
    public final Color TEXT;
    public final Color TEXT_MUTED;
    public final Color DANGER;
    public final Color SUCCESS;
    public final Color BORDER;

    public final Font HEADING;
    public final Font BODY;
    public final Font BTN;
    public final Font TITLE;

    private Theme() {
        palette = new ColorPalette();
        typography = new Typography();
        factory = new ComponentFactory(palette, typography);

        BG = palette.BG;
        BG_CARD = palette.BG_CARD;
        BG_ITEM = palette.BG_ITEM;
        GOLD = palette.GOLD;
        TEXT = palette.TEXT;
        TEXT_MUTED = palette.TEXT_MUTED;
        DANGER = palette.DANGER;
        SUCCESS = palette.SUCCESS;
        BORDER = palette.BORDER;

        TITLE = typography.TITLE;
        HEADING = typography.HEADING;
        BODY = typography.BODY;
        BTN = typography.BTN;
    }
    
    public static Theme getInstance() {
    	return INSTANCE;
    }

    public JPanel header(String title) {
    	return factory.header(title);
    }
    
    public JButton primaryBtn(String text, Runnable onClick) {
    	return factory.primaryBtn(text, onClick);
    }
    
    public JButton secondaryBtn(String text, Runnable onClick) {
    	return factory.secondaryBtn(text, onClick);
    }
    
    public JButton dangerBtn(String text, Runnable onClick) {
    	return factory.dangerBtn(text, onClick);
    }
    
    public JButton successBtn(String text, Runnable onClick) {
    	return factory.successBtn(text, onClick);
    }
    
    public void showInfo(Component parent, String message) {
        factory.showInfo(parent, message);
    }

    public void showError(Component parent, String message) {
        factory.showError(parent, message);
    }

    public boolean confirm(Component parent, String message) {
    	return factory.confirm(parent, message);
    }
    
    public void formDialog(Component parent, String title, JPanel content, String confirmLabel, Supplier<Boolean> onConfirm) {
        factory.formDialog(parent, title, content, confirmLabel, onConfirm);
    }
    
    public JPanel backgroundPanel(URL url) {
        return factory.backgroundPanel(url);
    }
}
