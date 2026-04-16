package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import business.tGame;
import business.tInventory;
import business.tProduct;

public class DAOInventoryImp implements DAOInventory {

    @Override
    public int createInventory(tInventory inventory) {
        int rowCount = 0;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
                if(inventory.getGames() != null && !inventory.getGames().isEmpty()) {
                    String query = "INSERT INTO gamesInventory (playerName, gameName, gameBasePoints, amount) VALUES (?, ?, ?, ?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    
                    for(Entry<tGame, Integer> entry: inventory.getGames().entrySet()) {
                        pst.setString(1, inventory.getPlayerName());
                        pst.setString(2, entry.getKey().getName());
                        pst.setInt(3, entry.getKey().getBasePoints());
                        pst.setInt(4, entry.getValue());
                        rowCount += pst.executeUpdate();
                    }
                    pst.close();
                }
                if(inventory.getProducts() != null && !inventory.getProducts().isEmpty()) {
                    String query = "INSERT INTO productsInventory (playerName, productName, amount) VALUES (?, ?, ?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    
                    for(Entry<tProduct, Integer> entry: inventory.getProducts().entrySet()) {
                        pst.setString(1, inventory.getPlayerName());
                        pst.setString(2, entry.getKey().getName());
                        pst.setInt(3, entry.getValue());
                        rowCount += pst.executeUpdate();
                    }
                    pst.close();
                }
                connection.commit();
            }
            catch(SQLException e) {
            	rowCount = 0;
                try {
                    connection.rollback();
                }
                catch(SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
            finally {
                try {
                    connection.setAutoCommit(true);
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rowCount > 0 ? 1 : 0;
    }

    @Override
    public tInventory readInventory(String playerName) {
        tInventory inventory = null;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
            try {
                Map<tGame, Integer> games = new HashMap<>();
                Map<tProduct, Integer> products = new HashMap<>();
                String query = "SELECT gameName, gameBasePoints, amount "
                				+ "FROM gamesInventory "
                				+ "WHERE playerName = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                
                pst.setString(1, playerName);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) games.put(new tGame(rs.getString("gameName"), rs.getInt("gameBasePoints")), rs.getInt("amount"));
                rs.close();
                pst.close();
                query = "SELECT pi.productName, p.price, pi.amount "
                		+ "FROM productsInventory pi JOIN product p ON pi.productName = p.name "
                		+ "WHERE pi.playerName = ?";
                pst = connection.prepareStatement(query);
                pst.setString(1, playerName);
                rs = pst.executeQuery();
                while(rs.next()) products.put(new tProduct(rs.getString("productName"), rs.getInt("price")), rs.getInt("amount"));
                rs.close();
                pst.close();
                inventory = new tInventory(playerName, games, products);
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return inventory;
    }

    @Override
    public int updateInventory(tInventory inventory) {
        int rowCount = 0;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
                String query = "DELETE FROM gamesInventory WHERE playerName = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                
                pst.setString(1, inventory.getPlayerName());
                pst.executeUpdate();
                pst.close();
                query = "DELETE FROM productsInventory WHERE playerName = ?";
                pst = connection.prepareStatement(query);
                pst.setString(1, inventory.getPlayerName());
                pst.executeUpdate();
                pst.close();
                if(inventory.getGames() != null && !inventory.getGames().isEmpty()) {
                    query = "INSERT INTO gamesInventory (playerName, gameName, gameBasePoints, amount) VALUES (?, ?, ?, ?)";
                    pst = connection.prepareStatement(query);
                    for(Entry<tGame, Integer> entry: inventory.getGames().entrySet()) {
                        pst.setString(1, inventory.getPlayerName());
                        pst.setString(2, entry.getKey().getName());
                        pst.setInt(3, entry.getKey().getBasePoints());
                        pst.setInt(4, entry.getValue());
                        rowCount += pst.executeUpdate();
                    }
                    pst.close();
                }
                if(inventory.getProducts() != null && !inventory.getProducts().isEmpty()) {
                    query = "INSERT INTO productsInventory (playerName, productName, amount) VALUES (?, ?, ?)";
                    pst = connection.prepareStatement(query);
                    for(Entry<tProduct, Integer> entry: inventory.getProducts().entrySet()) {
                        pst.setString(1, inventory.getPlayerName());
                        pst.setString(2, entry.getKey().getName());
                        pst.setInt(3, entry.getValue());
                        rowCount += pst.executeUpdate();
                    }
                    pst.close();
                }
                connection.commit();
                if(rowCount == 0) rowCount = 1; // Si el inventario está vacío, el update sigue siendo exitoso
            }
            catch(SQLException e) {
            	rowCount = 0;
                try {
                    connection.rollback();
                }
                catch(SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
            finally {
                try {
                    connection.setAutoCommit(true);
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rowCount > 0 ? 1 : 0;
    }

    @Override
    public int deleteInventory(String playerName) {
        int rowCount = 0;
        Connection connection = DB.getConnection();

        if(connection != null) {
            try {
                connection.setAutoCommit(false);
                String query = "DELETE FROM gamesInventory WHERE playerName = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, playerName);
                rowCount += pst.executeUpdate();
                pst.close();
                query = "DELETE FROM productsInventory WHERE playerName = ?";
                pst = connection.prepareStatement(query);
                pst.setString(1, playerName);
                rowCount += pst.executeUpdate();
                pst.close();
                connection.commit();
            }
            catch(SQLException e) {
            	rowCount = 0;
                try {
                    connection.rollback();
                }
                catch(SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
            finally {
                try {
                    connection.setAutoCommit(true);
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rowCount > 0 ? 1 : 0;
    }

    @Override
    public Collection<tInventory> readAllInventories() {
        Map<String, tInventory> inventories = new HashMap<>();
        Connection connection = DB.getConnection();

        if(connection != null) {
            try {
                String query = "SELECT playerName, gameName, gameBasePoints, amount "
                				+ "FROM gamesInventory";
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                
                while(rs.next()) {
                    String playerName = rs.getString("playerName");
                    
                    inventories.putIfAbsent(playerName, new tInventory(playerName, new HashMap<>(), new HashMap<>()));
                    inventories.get(playerName).getGames().put(new tGame(rs.getString("gameName"), rs.getInt("gameBasePoints")), rs.getInt("amount"));
                }
                rs.close();
                pst.close();
                query = "SELECT pi.playerName, pi.productName, p.price, pi.amount "
                		+ "FROM productsInventory pi JOIN product p ON pi.productName = p.name";
                pst = connection.prepareStatement(query);
                rs = pst.executeQuery();
                while(rs.next()) {
                    String playerName = rs.getString("playerName");
                    
                    inventories.putIfAbsent(playerName, new tInventory(playerName, new HashMap<>(), new HashMap<>()));
                    inventories.get(playerName).getProducts().put(new tProduct(rs.getString("productName"), rs.getInt("price")), rs.getInt("amount"));
                }
                rs.close();
                pst.close();
            }
            catch(SQLException e) {
            	inventories.clear();
                e.printStackTrace();
            }
        }
        return inventories.values();
    }
}
