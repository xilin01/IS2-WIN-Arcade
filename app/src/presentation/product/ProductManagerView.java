package presentation.product; 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import business.product.SAProduct;
import business.product.SAProductImp;
import business.product.tProduct;
import presentation.user.StaffView;

public class ProductManagerView extends JFrame {

    private JPanel productListPanel;
    private SAProduct saProduct;
    private String userName;

    public ProductManagerView(String userName) {
        super();
        this.userName = userName;
        this.saProduct = new SAProductImp(); 
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setTitle("Gestor de Productos");
        this.setBounds(100, 100, 500, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Boton crear nuevo producto
        JPanel createProdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createProdButton = new JButton("CREAR NUEVO PRODUCTO");
        createProdButton.setPreferredSize(new Dimension(200, 35));
        createProdButton.addActionListener((e) -> {
            new ProductEditor(this, null);
            updateList(); 
        });
        createProdPanel.add(createProdButton);
        this.add(createProdPanel, BorderLayout.NORTH);

        // Lista de productos
        productListPanel = new JPanel();
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        
        JPanel listWrapper = new JPanel(new BorderLayout());
        listWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane prodScrollPane = new JScrollPane(productListPanel);
        listWrapper.add(prodScrollPane, BorderLayout.CENTER);
        
        this.add(listWrapper, BorderLayout.CENTER);

        // Boton atras
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener((e) -> {
            new StaffView(userName); 
            dispose();
        });

        updateList();
        
        this.setVisible(true);
    }

    // Una fila de la lista
    private JPanel productElement(tProduct product) {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        productPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)
        ));

        JPanel prodInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        prodInfo.add(new JLabel("  " + product.getName() + "  -  " + product.getPrice() + " pts"));
        productPanel.add(prodInfo, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton editButton = new JButton("Editar"); 
        editButton.addActionListener((e) -> {
            new ProductEditor(this, product);
            updateList();
        });

        JButton deleteButton = new JButton("X");
        deleteButton.setForeground(Color.RED);
        deleteButton.addActionListener((e) -> {
            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this, 
                "¿Borrar el producto '" + product.getName() + "'?",
                "Confirmar",
                javax.swing.JOptionPane.YES_NO_OPTION
            );

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Borra directo desde la vista
                this.saProduct.deleteProduct(product.getName()); 
                updateList();
            }
        });

        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        productPanel.add(buttonsPanel, BorderLayout.EAST);

        return productPanel;
    }

    
    private void updateList() {
        productListPanel.removeAll();
        for (tProduct prod : saProduct.readAllProducts()) {
            productListPanel.add(productElement(prod));
        }
        productListPanel.revalidate();
        productListPanel.repaint();
    }
}