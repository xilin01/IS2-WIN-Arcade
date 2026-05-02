package presentation.product;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import business.product.tProduct;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;
import presentation.user.StaffView;

@SuppressWarnings("serial")
public class ProductManagerView extends JFrame {
    private JPanel productListPanel;

    public ProductManagerView() {
        super("Gestor de productos");
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Theme.BG);

        JPanel header = Theme.header("Gestión de Productos");
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setBackground(Theme.BG_CARD);
        JButton createProdButton = Theme.primaryBtn("Crear Producto");
        JButton backButton = Theme.secondaryBtn("Volver");
        headerRight.add(createProdButton);
        headerRight.add(backButton);
        header.add(headerRight, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        productListPanel = new JPanel();
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        productListPanel.setBackground(Theme.BG);
        productListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(productListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Theme.BG);
        this.add(scrollPane, BorderLayout.CENTER);

        createProdButton.addActionListener(e -> {
            new ProductEditor(this, null);
            updateList();
        });
        backButton.addActionListener(e -> {
            new StaffView();
            dispose();
        });

        updateList();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private JPanel productElement(tProduct product) {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        productPanel.setBackground(Theme.BG_ITEM);
        productPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(4, 0, 4, 0),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)
            )
        ));

        JPanel prodInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        prodInfo.setBackground(Theme.BG_ITEM);

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(Theme.BODY);
        nameLabel.setForeground(Theme.TEXT);

        JLabel priceLabel = new JLabel(product.getPrice() + " pts");
        priceLabel.setFont(Theme.BODY);
        priceLabel.setForeground(Theme.GOLD);

        prodInfo.add(nameLabel);
        prodInfo.add(priceLabel);
        productPanel.add(prodInfo, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonsPanel.setBackground(Theme.BG_ITEM);

        JButton editButton = Theme.secondaryBtn("Editar");
        editButton.addActionListener(e -> {
            new ProductEditor(this, product);
            updateList();
        });

        JButton deleteButton = Theme.dangerBtn("Eliminar");
        deleteButton.addActionListener(e -> {
            if (Theme.confirm(this, "Eliminar definitivamente el producto " + product.getName())) {
                Controller.getInstance().action(Event.DELETE_PRODUCT, product.getName());
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
        Collection<tProduct> products = Controller.getInstance().getAllProducts();
        for (tProduct prod : products) productListPanel.add(productElement(prod));
        productListPanel.revalidate();
        productListPanel.repaint();
    }
}
