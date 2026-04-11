package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class UserView extends JFrame{
	
	public UserView() {
		super();
		initGUI();
	}
	
	void initGUI() {
		JFrame menuPrincipal = new JFrame();
		
	    menuPrincipal.setTitle("Menu CRUDs Usuario"); 
	    menuPrincipal.setBounds(100, 100, 450, 300); 
	    menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    menuPrincipal.getContentPane().setLayout(new FlowLayout()); 
	    menuPrincipal.setVisible(true);
	    
	    JButton botonAlta = new JButton();     
	    botonAlta.setText("Alta Usuario"); 
	    botonAlta.setBounds(153, 180, 132, 32); 
	    menuPrincipal.getContentPane().add(botonAlta); 
	          
	    JButton botonReadAll = new JButton();     
	    botonReadAll.setText("Mostrar usuarios"); 
	    botonReadAll.setBounds(253, 380, 132, 32); 
	    menuPrincipal.getContentPane().add(botonReadAll); 
	    
	    // Listener's 
	    botonAlta.addActionListener(new ActionListener() { 
			public void actionPerformed (ActionEvent e) { 
			// TBD (To Be Developed) 
			} 
	    }); 
	    botonReadAll.addActionListener(new ActionListener() { 
			public void actionPerformed (ActionEvent e) { 
				Controller.accion(Event.MOSTRAR_USUARIOS, null);  
			} 
		}); 
	}
	
	public static void actualizar(int evento, Object datos) { 
		if (evento == Event.RES_MOSTRAR_USUARIOS_OK) { 
			JTextArea textArea = new JTextArea(datos.toString());
			
			textArea.setLineWrap(true); 
			textArea.setEditable(false); 
			textArea.setWrapStyleWord(true); 
			JScrollPane scrollPane = new JScrollPane(textArea); 
			scrollPane.setPreferredSize( new Dimension( 500, 200 ) ); 
			JOptionPane.showMessageDialog(null, scrollPane, "USUARIOS", JOptionPane.DEFAULT_OPTION); 
		}
		else if (evento == Event.RES_MOSTRAR_USUARIOS_KO) JOptionPane.showMessageDialog(null, "No se ha podido encontrar ningun usuario"); 
	} 
}
