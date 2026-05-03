package presentation.product;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
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

public class ProductEditor {
    private static final Theme theme = Theme.getInstance();

    private ProductEditor() {
    }

    public static void show(JFrame parent, tProduct product) {
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setForeground(theme.TEXT);
        formPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBackground(theme.BG_ITEM);
        nameField.setForeground(theme.TEXT);
        nameField.setCaretColor(theme.TEXT);
        nameField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        if(product != null) {
            nameField.setText(product.getName());
            nameField.setEditable(false);
            nameField.setBackground(theme.BG_CARD);
            nameField.setForeground(theme.TEXT_MUTED);
        }
        formPanel.add(nameField);

        JLabel priceLabel = new JLabel("Precio (pts):");
        priceLabel.setForeground(theme.TEXT);
        formPanel.add(priceLabel);

        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 999999, 1));
        priceSpinner.setBackground(theme.BG_ITEM);
        ((JSpinner.DefaultEditor)priceSpinner.getEditor()).getTextField().setBackground(theme.BG_ITEM);
        ((JSpinner.DefaultEditor)priceSpinner.getEditor()).getTextField().setForeground(theme.TEXT);
        ((JSpinner.DefaultEditor)priceSpinner.getEditor()).getTextField().setCaretColor(theme.TEXT);
        if(product != null) priceSpinner.setValue(product.getPrice());
        formPanel.add(priceSpinner);

        theme.formDialog(parent, product == null ? "Crear nuevo producto" : "Editar producto", formPanel, "Guardar", () -> {
            String name = nameField.getText().trim();
            int price = (int)priceSpinner.getValue();
            
            if(!name.isEmpty()) {
                if(product == null) Controller.getInstance().action(Event.CREATE_PRODUCT, new tProduct(name, price));
                else {
                    product.setPrice(price);
                    Controller.getInstance().action(Event.UPDATE_PRODUCT, product);
                }
                return true;
            }
            return false;
        });
    }
}
