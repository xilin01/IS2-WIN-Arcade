package presentation.games;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import business.game.Roulette.Bet.Bets;
import business.game.Roulette.Bet.tBet;
import presentation.Controller;
import presentation.Event;

@SuppressWarnings("serial")
public class RouletteGUI extends JFrame {
    private class RotatedRoulette extends JPanel {
    	private static final int SIZE = 500;
    	
        private Image image;
        private double angle;

        public RotatedRoulette() {
        	try {
                BufferedImage original = ImageIO.read(new File("resources/icons/ruleta.png"));
                BufferedImage compatible = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = compatible.createGraphics();
                
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(original, 0, 0, SIZE, SIZE, null);
                g2d.dispose();
                this.image = compatible;

            } 
        	catch (Exception e) {
                e.printStackTrace();
            }
        	this.angle = 0;
            setPreferredSize(new Dimension(SIZE, SIZE));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g.create();
            g2.translate(SIZE / 2, SIZE / 2);
            g2.rotate(angle);
            g2.drawImage(image, -SIZE / 2, -SIZE / 2, null);
            drawPointer(g);
            g2.dispose();
        }
        
        private void drawPointer(Graphics g) {
        	Graphics2D gPointer = (Graphics2D)g.create();
        	int centerX = SIZE / 2;
            int[] xPoints = {centerX - 15, centerX + 15, centerX};
            int[] yPoints = {10, 10, 50};

            gPointer.setColor(Color.RED);
            gPointer.fillPolygon(xPoints, yPoints, 3);
            gPointer.dispose();
        }

        public void advance() {
            this.angle = (this.angle - ((2 * Math.PI) / 37)) % (2 * Math.PI);
            repaint();
        }
    }
    private class ImputDialog extends JDialog {
    	private static final int BETS_HEIGHT = 300;
        private static final int BETS_WIDTH = BETS_HEIGHT * 2;
        
    	private Bets betName;
    	private DefaultComboBoxModel <String> select;
    	private JLabel text;
    	
    	public ImputDialog(Bets betName) {
    		super(RouletteGUI.this, "Panel de seleccion", true);
    		this.betName = betName;
    		select = new DefaultComboBoxModel<>();
    		text = new JLabel();
    		initGUI();
    	}
    	
    	private void initGUI() {
    		switch(betName) {
	    		case Sencillo:
	    			for(int i = 0; i < 36; i++) select.addElement(""+i); 
	    			text.setText("Elige el número (del 0 al 36)");
	    			break;
	            case Calle:
	            	for(int i = 1; i <= 34; i+=3) select.addElement(i+","+(i+1)+","+(i+2)); 
	            	text.setText("Elige calle de entre las opciones");
	            	break;
	            case Columna:
	            case Docena: {
	            	for(int i = 1; i <= 3; i++) select.addElement(""+i); 
	            	text.setText("Elige entre una de las tres");
	            	break;
	            }
				default: break;
    		}
    
    		JPanel mainPanel = new JPanel();
    		setContentPane(mainPanel);
    		
    		ImageIcon icon = new ImageIcon("resources/icons/apuestas_ruleta.png");
            Image img = icon.getImage().getScaledInstance(BETS_WIDTH, BETS_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            mainPanel.add(new JLabel(scaledIcon));
    		
    		JButton goButton = new JButton("Listo");
    		goButton.addActionListener(a -> ready());
    		mainPanel.add(text);
    		mainPanel.add(goButton);
    		mainPanel.add(new JComboBox<>(select));
    	
    		
    		this.setLocation(RouletteGUI.this.getWidth() / 2 - BETS_WIDTH / 2, RouletteGUI.this.getHeight() / 2 - BETS_HEIGHT / 2);
    		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    		setPreferredSize(new Dimension(BETS_WIDTH, BETS_HEIGHT + 80));
    		pack();
    		setVisible(true);
    	}
    	
    	private void ready() {
    		switch(betName) {
	    		case Columna:
	    		case Docena:
	            case Sencillo: bet = new tBet(betName, new int[]{Integer.parseInt("" + select.getSelectedItem())}); break;
	            case Calle: {
	                String[] parts = ((String)select.getSelectedItem()).split(",");
	                
	                bet = new tBet(betName, new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),Integer.parseInt(parts[2])});
	                break;
	            }
				default: break;
    		}
    		dispose();
    	}
    }
    
    private static final Bets[] BETS = Bets.values();
    
    private Map<Integer, Integer> numberToSteps;
    private RotatedRoulette roulette;
    private tBet bet;
    private JButton playButton;
    private int animationCount = 0;

    public RouletteGUI() {
        super("Roulette");
        initNumberToSteps();
        initGUI();
    }

    private void initGUI() {
        BackgroundPanel mainPanel = new BackgroundPanel("resources/icons/fondo_ruleta.png");
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        roulette = new RotatedRoulette();
        JPanel panelRuleta = new JPanel();
        panelRuleta.setOpaque(false);
        panelRuleta.add(roulette);
        panelRuleta.setBorder(BorderFactory.createEmptyBorder(200, 100, 0, 0));
        mainPanel.add(panelRuleta, BorderLayout.CENTER);

        JPanel betsPanel = new JPanel(new GridLayout(5, 2, 1, 1));
        betsPanel.setOpaque(false);
        betsPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 80, 80));
        mainPanel.add(betsPanel, BorderLayout.EAST);
        for(Bets b: BETS) {
            JButton betButton = new JButton(""+b);
            betButton.addActionListener(a -> setBet(b));
            betsPanel.add(betButton);
        }
        
        JPanel optionsPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(350, 80, 350, 0));
        optionsPanel.setPreferredSize(new Dimension(150, 0));
        mainPanel.add(optionsPanel, BorderLayout.WEST);

        playButton = new JButton("Tirar");
        playButton.setEnabled(false);
        playButton.addActionListener(a -> playRoulette());
        optionsPanel.add(playButton);

        JButton backButton = new JButton("Atrás");
        backButton.addActionListener(a -> giveUp());
        optionsPanel.add(backButton);
        
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setBet(Bets betName) {
        switch (betName) {
            case Sencillo:
            case Calle: 
            case Columna: 
            case Docena: new ImputDialog(betName); break;
            default: bet = new tBet(betName, new int[]{}); break;
        }
        playButton.setEnabled(true);
    }

    private void playRoulette() { // En setBet se activa el botón
        Controller.getInstance().action(Event.ROULETTE_ROLL, bet);
        playButton.setEnabled(false);
    }

    public void end(String message) {
        JOptionPane.showMessageDialog(this, message);
        new GamesPanelView();
        dispose();
    }
    
    private void giveUp() {
        int option = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres abandonar?\n La partida se perderá",
                "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION) {
        	new GamesPanelView(); 
            dispose();
        }
    }

    public void showResult(int puntos, int winningNumber) {
    	String message = puntos == 0 ? "Perdiste. El número fue el " + winningNumber : "Ganaste con el " + winningNumber;
    	showAnimation(winningNumber, () -> end(message));
    }
    
    private void showAnimation(int n, Runnable onFinish) {
    	animationCount = 0;
    	int steps = numberToSteps.get(n) + 37;
        Timer timer = new Timer(50, null);
        timer.addActionListener(e -> {
        	roulette.advance();
        	animationCount++;
	        if(animationCount >= steps) {
	            timer.stop();
	            onFinish.run();
	        }
	    });
        timer.start();
    }
    
    private void initNumberToSteps() {
    	numberToSteps = new HashMap<>();
    	numberToSteps.put(0, 0);    numberToSteps.put(1, 23);
    	numberToSteps.put(2, 6);    numberToSteps.put(3, 35);
    	numberToSteps.put(4, 4);    numberToSteps.put(5, 19);
    	numberToSteps.put(6, 10);   numberToSteps.put(7, 31);
    	numberToSteps.put(8, 16);   numberToSteps.put(9, 27);
    	numberToSteps.put(10, 18);  numberToSteps.put(11, 14);
    	numberToSteps.put(12, 33);  numberToSteps.put(13, 12);
    	numberToSteps.put(14, 25);  numberToSteps.put(15, 2);
    	numberToSteps.put(16, 21);  numberToSteps.put(17, 8);
    	numberToSteps.put(18, 29);  numberToSteps.put(19, 3);
    	numberToSteps.put(20, 24);  numberToSteps.put(21, 5);
    	numberToSteps.put(22, 28);  numberToSteps.put(23, 17);
    	numberToSteps.put(24, 20);  numberToSteps.put(25, 7);
    	numberToSteps.put(26, 36);  numberToSteps.put(27, 11);
    	numberToSteps.put(28, 32);  numberToSteps.put(29, 30);
    	numberToSteps.put(30, 15);  numberToSteps.put(31, 26);
    	numberToSteps.put(32, 1);   numberToSteps.put(33, 22);
    	numberToSteps.put(34, 9);   numberToSteps.put(35, 34);
    	numberToSteps.put(36, 13);
    }
}
