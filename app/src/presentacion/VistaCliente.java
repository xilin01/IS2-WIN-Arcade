package presentacion;

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
public class VistaCliente extends JFrame{
	
	public VistaCliente() {
		super();
		initGUI();
	}
	
	void initGUI() {
		JFrame menuPrincipal = new JFrame();
		
	    menuPrincipal.setTitle("Menu CRUDs Cliente"); 
	    menuPrincipal.setBounds(100, 100, 450, 300); 
	    menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    menuPrincipal.getContentPane().setLayout(new FlowLayout()); 
	    menuPrincipal.setVisible(true);
	    
	    JButton botonAlta = new JButton();     
	    botonAlta.setText("Alta Cliente"); 
	    botonAlta.setBounds(153, 180, 132, 32); 
	    menuPrincipal.getContentPane().add(botonAlta); 
	          
	    JButton botonReadAll = new JButton();     
	    botonReadAll.setText("Mostrar clientes"); 
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
				Controlador.accion(Evento.MOSTRAR_CLIENTES, null);  
			} 
		}); 
	}
	
	public static void actualizar(int evento, Object datos) { 
		if (evento == Evento.RES_MOSTRAR_CLIENTES_OK) { 
			JTextArea textArea = new JTextArea(datos.toString());
			
			textArea.setLineWrap(true); 
			textArea.setEditable(false); 
			textArea.setWrapStyleWord(true); 
			JScrollPane scrollPane = new JScrollPane(textArea); 
			scrollPane.setPreferredSize( new Dimension( 500, 200 ) ); 
			JOptionPane.showMessageDialog(null, scrollPane, "CLIENTES", JOptionPane.DEFAULT_OPTION); 
		}
		else if (evento == Evento.RES_MOSTRAR_CLIENTES_KO) JOptionPane.showMessageDialog(null, "No se ha podido encontrar ningun cliente"); 
	} 
}
