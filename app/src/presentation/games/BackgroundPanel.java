package presentation.games;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {
    private Image background;

    public BackgroundPanel(URL url) {
    	if(url == null) throw new IllegalArgumentException("Invalid arg for Builder in BackgroundPanel");
    	background = new ImageIcon(url).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
