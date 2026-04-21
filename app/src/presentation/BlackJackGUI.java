package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BlackJackGUI extends JFrame{
	
	//List<>
	
	public BlackJackGUI() {
		super("Black Jack");
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 650, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//Mano del croupier. 
		JPanel croupierDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
	}
}
