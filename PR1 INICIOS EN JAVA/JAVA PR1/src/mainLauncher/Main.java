package mainLauncher;

import javax.swing.SwingUtilities;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import presentacion.Controlador;
import presentacion.VistaAltaCliente;
import presentacion.VistaCliente;

@objid ("e7eced22-b4e5-44ef-b114-f8e3351b0184")
public class Main {
    @objid ("bbb38086-cbc6-4677-ac74-5b8d386c9692")
    private Controlador controlador;

    @objid ("0d116f45-18c3-405b-926e-d75c67498599")
    private VistaAltaCliente vista;

    @objid ("0699632e-2ac8-43b2-b795-5017a07d6fd5")
    public static void main(String[] args) {
    	SwingUtilities.invokeLater (new Runnable() {
    		@Override 
    		public void run() {new VistaCliente();}
    	});
    }

}
