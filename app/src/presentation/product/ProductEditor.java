package presentation.product;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import business.product.tProduct;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class ProductEditor extends JDialog {
    private JTextField nameField;
    private JSpinner priceSpinner;
    private tProduct currentProduct;

    public ProductEditor(JFrame parent, tProduct product) {
        super(parent, product == null ? "Crear Nuevo Producto" : "Editar Producto", true);
        this.currentProduct = product;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(380, 180);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Theme.BG);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 12));
        formPanel.setBackground(Theme.BG);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setForeground(Theme.TEXT);
        formPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBackground(Theme.BG_ITEM);
        nameField.setForeground(Theme.TEXT);
        nameField.setCaretColor(Theme.TEXT);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        if (currentProduct != null) {
            nameField.setText(currentProduct.getName());
            nameField.setEditable(false);
            nameField.setBackground(Theme.BG_CARD);
            nameField.setForeground(Theme.TEXT_MUTED);
        }
        formPanel.add(nameField);

        JLabel priceLabel = new JLabel("Precio (puntos):");
        priceLabel.setForeground(Theme.TEXT);
        formPanel.add(priceLabel);

        priceSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 999999, 1));
        priceSpinner.setBackground(Theme.BG_ITEM);
        ((JSpinner.DefaultEditor) priceSpinner.getEditor()).getTextField().setBackground(Theme.BG_ITEM);
        ((JSpinner.DefaultEditor) priceSpinner.getEditor()).getTextField().setForeground(Theme.TEXT);
        ((JSpinner.DefaultEditor) priceSpinner.getEditor()).getTextField().setCaretColor(Theme.TEXT);
        if (currentProduct != null) priceSpinner.setValue(currentProduct.getPrice());
        formPanel.add(priceSpinner);

        this.add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(Theme.BG_CARD);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));

        JButton cancelButton = Theme.secondaryBtn("Cancelar");
        JButton saveButton = Theme.primaryBtn("Guardar");

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            int price = (int) priceSpinner.getValue();
            if (!name.isEmpty()) {
                if (currentProduct == null) Controller.getInstance().action(Event.CREATE_PRODUCT, new tProduct(name, price));
                else {
                    currentProduct.setPrice(price);
                    Controller.getInstance().action(Event.UPDATE_PRODUCT, currentProduct);
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
