package integration.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import business.product.tProduct;
import integration.DB;

public class DAOProductImp implements DAOProduct {

    @Override
    public int createProduct(tProduct product) {
    	if(product == null) throw new IllegalArgumentException("Invalid arg for createProduct in DAOProductImp");
        int rowCount = 0;
        Connection connection = DB.getConnection();
        if(connection != null) {
        	String query = "INSERT INTO product (name, price) VALUES (?, ?)";
        	
            try(PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, product.getName());
                pst.setInt(2, product.getPrice());
                rowCount = pst.executeUpdate();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }

    @Override
    public tProduct readProduct(String name) {
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for readProduct in DAOProductImp");
        tProduct product = null;
        Connection connection = DB.getConnection();
        if(connection != null) {
        	String query = "SELECT name, price "
        					+ "FROM product "
                    		+ "WHERE name = ?";
        	
        	try(PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, name);
                ResultSet rs = pst.executeQuery();
                if(rs.next()) product = new tProduct(rs.getString("name"), rs.getInt("price"));
                rs.close();
            }
        	catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return product;
    }

    @Override
    public int updateProduct(tProduct product) {
    	if(product == null) throw new IllegalArgumentException("Invalid arg for updateProduct in DAOProductImp");
        int rowCount = 0;
        Connection connection = DB.getConnection();
        if(connection != null) {
        	String query = "UPDATE product "
        					+ "SET price = ? "
                    		+ "WHERE name = ?";
        	
        	try(PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setInt(1, product.getPrice());
                pst.setString(2, product.getName());
                rowCount = pst.executeUpdate();
            }
        	catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }

    @Override
    public int deleteProduct(String name) {
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for deleteProduct in DAOProductImp");
        int rowCount = 0;
        Connection connection = DB.getConnection();
        if(connection != null) {
        	String query = "DELETE FROM product WHERE name = ?";
            
            try(PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, name);
                rowCount = pst.executeUpdate();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }

    @Override
    public Collection<tProduct> readAllProducts() {
        Collection<tProduct> products = new ArrayList<>();
        Connection connection = DB.getConnection();

        if(connection != null) {
        	String query = "SELECT name, price "
        					+ "FROM product";
        	
        	try(PreparedStatement pst = connection.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
        		while(rs.next()) products.add(new tProduct(rs.getString("name"), rs.getInt("price")));
            }
        	catch(SQLException e) {
        		e.printStackTrace();
            }
        }
        return products;
    }
}
