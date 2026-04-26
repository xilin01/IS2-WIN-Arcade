package presentation.product;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import business.product.SAProduct;
import business.product.SAProductImp;
import business.product.tProduct;

@SuppressWarnings("serial")
public class ProductEditor extends JDialog {
    private JTextField nameField;
    private JSpinner priceSpinner;
    private tProduct currentProduct; 
    private SAProduct saProduct = new SAProductImp();

    public ProductEditor(JFrame parent, tProduct product) {
        super(parent, product == null ? "Crear Nuevo Producto" : "Editar Producto", true);
        this.currentProduct = product;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(350, 180);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        formPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        formPanel.add(new JLabel("Nombre:"));
        nameField = new JTextField();
        if (currentProduct != null) {
            nameField.setText(currentProduct.getName());
            nameField.setEditable(false); 
        }
        formPanel.add(nameField);

        formPanel.add(new JLabel("Precio (puntos):"));
        priceSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 999999, 1));
        if (currentProduct != null) priceSpinner.setValue(currentProduct.getPrice());
        formPanel.add(priceSpinner);

        this.add(formPanel, BorderLayout.CENTER);

        // Boton cancelar y guardar
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancelar");
        JButton saveButton = new JButton("Guardar");

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            int price = (int)priceSpinner.getValue();
            
            if (!name.isEmpty()) {
                if (currentProduct == null) saProduct.createProduct(new tProduct(name, price)); 
                else {
                    currentProduct.setPrice(price);
                    saProduct.updateProduct(currentProduct); 
                }
                dispose(); 
            }
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }
}