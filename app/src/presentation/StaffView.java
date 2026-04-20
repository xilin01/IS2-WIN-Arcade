package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import business.tUser;

public class StaffView extends JFrame {
	
	private String userName;
	
	public StaffView(String userName) {
		super();
		this.userName = userName;
		initGUI();
	}
	
	public StaffView(tUser user) {
		super();
		this.userName = user.getName();
		initGUI();		
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setTitle("Menú principal - Personal");
		this.setBounds(100, 100, 650, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Barra superior
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel userLabel = new JLabel(userName + " | ");
		JButton logOutButton = new JButton("Cerrar Sesión");
		
		topPanel.add(userLabel);
		topPanel.add(logOutButton);
		
		this.add(topPanel, BorderLayout.NORTH);
		
		
		//Panel central - Botones principales
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 100));
		
		JButton gestPaqBut = new JButton("GESTIONAR PAQUETES");
		gestPaqBut.addActionListener((e) -> {
			Controller.action(Event.ABRIR_GESTOR_PAQUETES, this.userName);
			
			dispose();
		});
		JButton gestProdBut = new JButton("GESTIONAR PRODUCTOS");
		gestPaqBut.addActionListener((e) -> {
			
		});
		JButton gestUsuBut = new JButton("GESTIONAR USUARIOS");
		gestPaqBut.addActionListener((e) -> {
			
		});
		
		Dimension buttonSize = new Dimension(160, 40);
		
		gestPaqBut.setPreferredSize(buttonSize);
		gestProdBut.setPreferredSize(buttonSize);
		gestUsuBut.setPreferredSize(buttonSize);
		
		mainPanel.add(gestPaqBut);
		mainPanel.add(gestProdBut);
		mainPanel.add(gestUsuBut);
		
		this.add(mainPanel, BorderLayout.CENTER);
		
		
		logOutButton.addActionListener((e) -> {
			Controller.action(Event.LOGOUT, null);
			dispose();
		});


		this.setVisible(true);
	}

}











