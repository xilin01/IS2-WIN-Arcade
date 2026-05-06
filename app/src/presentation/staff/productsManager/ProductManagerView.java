package presentation.staff.productsManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import business.product.tProduct;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class ProductManagerView extends JFrame {
    private static final Theme theme = Theme.getInstance();
    
    private JPanel productListPanel;

    public ProductManagerView() {
        super("Gestor de productos");
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(theme.BG);

        JPanel header = theme.header("Gestión de productos");
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setBackground(theme.BG_CARD);
        headerRight.add(theme.primaryBtn("Crear producto", () -> {
        	ProductEditor.show(this, null);
        	updateList();
        }));
        headerRight.add(theme.secondaryBtn("Atrás", () -> {
        	Controller.getInstance().action(Event.OPEN_STAFF_VIEW, null);
        	dispose();
        }));
        header.add(headerRight, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        productListPanel = new JPanel();
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        productListPanel.setBackground(theme.BG);
        productListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(productListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(theme.BG);
        this.add(scrollPane, BorderLayout.CENTER);

        updateList();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private JPanel productElement(tProduct product) {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        productPanel.setBackground(theme.BG_ITEM);
        productPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0),
            BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(10, 16, 10, 16))));
        
        JPanel prodInfo = new JPanel();
        prodInfo.setLayout(new BoxLayout(prodInfo, BoxLayout.Y_AXIS));
        prodInfo.setBackground(theme.BG_ITEM);

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(theme.HEADING);
        nameLabel.setForeground(theme.TEXT);
        prodInfo.add(nameLabel);
        prodInfo.add(nameLabel);
        
        JLabel priceLabel = new JLabel("Precio: " + product.getPrice() + "pts");
        priceLabel.setFont(theme.BODY);
        priceLabel.setForeground(theme.TEXT_MUTED);
        prodInfo.add(priceLabel);
        productPanel.add(prodInfo, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonsPanel.setBackground(theme.BG_ITEM);
        buttonsPanel.add(theme.secondaryBtn("Editar", () -> {
        	ProductEditor.show(this, product);
        	updateList();
        }));
        buttonsPanel.add(theme.dangerBtn("Eliminar", () -> {
            if (theme.confirm(this, "Eliminar definitivamente el producto " + product.getName())) {
                Controller.getInstance().action(Event.DELETE_PRODUCT, product.getName());
                updateList();
            }
        }));
        productPanel.add(buttonsPanel, BorderLayout.EAST);

        return productPanel;
    }

    private void updateList() {
        productListPanel.removeAll();
        Collection<tProduct> products = Controller.getInstance().getAllProducts();
        for(tProduct prod: products) productListPanel.add(productElement(prod));
        productListPanel.revalidate();
        productListPanel.repaint();
    }
}
