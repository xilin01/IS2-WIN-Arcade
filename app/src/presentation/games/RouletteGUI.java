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
import javax.swing.JPanel;
import javax.swing.Timer;
import business.game.Roulette.Bet.Bets;
import business.game.Roulette.Bet.tBet;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class RouletteGUI extends JFrame {
    private static final Theme theme = Theme.getInstance();

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
                image = compatible;
            }
        	catch (Exception e) {
                e.printStackTrace();
            }
        	angle = 0;
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
            angle = (angle - ((2 * Math.PI) / 37)) % (2 * Math.PI);
            repaint();
        }
    }

    private class ImputDialog extends JDialog {
    	private static final int BETS_HEIGHT = 300;
        private static final int BETS_WIDTH = BETS_HEIGHT * 2;

    	private Bets betName;
    	private DefaultComboBoxModel<String> select;
    	private JLabel text;

    	public ImputDialog(Bets betName) {
    		super(RouletteGUI.this, "Panel de selección", true);
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
	            	text.setText("Elige una calle");
	            	break;
	            case Columna:
	            case Docena:
	            	for(int i = 1; i <= 3; i++) select.addElement(""+i);
	            	text.setText("Elige entre una de las tres");
	            	break;
				default: break;
    		}

    		JPanel mainPanel = new JPanel();
    		mainPanel.setBackground(theme.BG);
    		getContentPane().setBackground(theme.BG);
    		setContentPane(mainPanel);

    		ImageIcon icon = new ImageIcon("resources/icons/apuestas_ruleta.png");
            Image img = icon.getImage().getScaledInstance(BETS_WIDTH, BETS_HEIGHT, Image.SCALE_SMOOTH);
            mainPanel.add(new JLabel(new ImageIcon(img)));

    		text.setForeground(theme.TEXT);
    		text.setFont(theme.BODY);
    		mainPanel.add(text);

    		JComboBox<String> combo = new JComboBox<>(select);
    		combo.setBackground(theme.BG_ITEM);
    		combo.setForeground(theme.TEXT);
    		mainPanel.add(combo);

    		mainPanel.add(theme.primaryBtn("Listo", this::ready));

    		setLocation(RouletteGUI.this.getWidth() / 2 - BETS_WIDTH / 2, RouletteGUI.this.getHeight() / 2 - BETS_HEIGHT / 2);
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
	                bet = new tBet(betName, new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])});
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
        super("Ruleta");
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
        panelRuleta.setBorder(BorderFactory.createEmptyBorder(180, 100, 0, 0));
        mainPanel.add(panelRuleta, BorderLayout.CENTER);

        JPanel betsPanel = new JPanel(new GridLayout(5, 2, 4, 4));
        betsPanel.setOpaque(false);
        betsPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 80, 80));
        mainPanel.add(betsPanel, BorderLayout.EAST);
        for(Bets b: BETS) {
            betsPanel.add(theme.secondaryBtn(""+b, () -> setBet(b)));
        }

        JPanel optionsPanel = new JPanel(new GridLayout(2, 1, 0, 12));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(350, 80, 350, 0));
        optionsPanel.setPreferredSize(new Dimension(150, 0));
        mainPanel.add(optionsPanel, BorderLayout.WEST);

        playButton = theme.primaryBtn("Tirar", this::playRoulette);
        playButton.setEnabled(false);
        optionsPanel.add(playButton);

        optionsPanel.add(theme.secondaryBtn("Atrás", this::giveUp));

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setBet(Bets betName) {
        switch(betName) {
            case Sencillo:
            case Calle:
            case Columna:
            case Docena: new ImputDialog(betName); break;
            default: bet = new tBet(betName, new int[]{}); break;
        }
        playButton.setEnabled(true);
    }

    private void playRoulette() {
        Controller.getInstance().action(Event.ROULETTE_ROLL, bet);
        playButton.setEnabled(false);
    }

    public void end(String message) {
        theme.showInfo(this, message);
        Controller.getInstance().action(Event.OPEN_GAMES_PANEL, null);
        dispose();
    }

    private void giveUp() {
        if(theme.confirm(this, "¿Estás seguro de que quieres abandonar? La partida se perderá")) {
            Controller.getInstance().action(Event.OPEN_GAMES_PANEL, null);
            dispose();
        }
    }

    public void showResult(int puntos, int winningNumber) {
    	String message = puntos == 0 ? "El número ganador fue el " + winningNumber + ", perdiste..." : "¡Ganaste con el " + winningNumber + "!";
    	showAnimation(winningNumber, () -> end(message));
    }
    
    //50 es el minimo
    private void showAnimation(int n, Runnable onFinish) {
    	animationCount = 0;
    	int steps = numberToSteps.get(n) + (37*3);
        Timer timer = new Timer(interruptionTime(steps), null);
        timer.addActionListener(e -> {
        	roulette.advance();
        	animationCount++;
        	timer.setDelay(interruptionTime(steps));
	        if(animationCount >= steps) {
	            timer.stop();
	            onFinish.run();
	        }
	    });
        timer.start();
    }
    
    private int interruptionTime(int steps) {
    	double t = (double) animationCount / steps; 
        double ease = (1 - Math.cos(Math.PI * t))/2;
        return (int) (10 + ease * 190);
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
