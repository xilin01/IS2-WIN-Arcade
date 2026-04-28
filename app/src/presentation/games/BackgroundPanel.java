package presentation.games;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {
    private Image background;

    public BackgroundPanel(String path) {
        background = new ImageIcon(path).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
