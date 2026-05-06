package integration.packages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import business.game.tGame;
import business.packages.tGamesPackage;
import integration.DB;

public class DAOGamesPackageImp implements DAOGamesPackage {

    @Override
    public int createGamesPackage(tGamesPackage gamesPackage) {
    	if(gamesPackage == null) throw new IllegalArgumentException("Invalid arg for createGamesPackage in DAOGamesPackageImp");
        int rowCount = 0;
        Connection connection = DB.getConnection();
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
                String query = "INSERT INTO gamesPackage (name, price) VALUES (?, ?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, gamesPackage.getName());
                pst.setDouble(2, gamesPackage.getPrice());
                rowCount = pst.executeUpdate();
                pst.close();
                if(gamesPackage.getGames() != null && !gamesPackage.getGames().isEmpty()) {
                    query = "INSERT INTO packageDetail (packageName, gameName, gameBasePoints, amount) VALUES (?, ?, ?, ?)";
                    pst = connection.prepareStatement(query);
                    for(Entry<tGame, Integer> entry: gamesPackage.getGames().entrySet()) {
                        pst.setString(1, gamesPackage.getName());
                        pst.setString(2, entry.getKey().getName());
                        pst.setInt(3, entry.getKey().getBasePoints());
                        pst.setInt(4, entry.getValue());
                        pst.executeUpdate();
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
        return rowCount;
    }

    @Override
    public tGamesPackage readGamesPackage(String name) {
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for readGamesPackage in DAOGamesPackageImp");
        tGamesPackage gamesPackage = null;
        Connection connection = DB.getConnection();
        if(connection != null) {
        	String query = "SELECT price "
        					+ "FROM gamesPackage "
        					+ "WHERE name = ?";
        	
        	try(PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, name);
                ResultSet rs = pst.executeQuery();
                if(rs.next()) {
                    double price = rs.getDouble("price");
                    Map<tGame, Integer> games = new HashMap<>();

                    query = "SELECT gameName, gameBasePoints, amount "
                            + "FROM packageDetail "
                            + "WHERE packageName = ?";
                    PreparedStatement pstDetail = connection.prepareStatement(query);
                    pstDetail.setString(1, name);
                    ResultSet rsDetail = pstDetail.executeQuery();
                    while(rsDetail.next()) games.put(new tGame(rsDetail.getString("gameName"), rsDetail.getInt("gameBasePoints")), rsDetail.getInt("amount"));
                    rsDetail.close();
                    pstDetail.close();
                    gamesPackage = new tGamesPackage(name, price, games);
                }
                rs.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return gamesPackage;
    }

    @Override
    public int updateGamesPackage(tGamesPackage gamesPackage) {
    	if(gamesPackage == null) throw new IllegalArgumentException("Invalid arg for updateGamesPackage in DAOGamesPackageImp");
        int rowCount = 0;
        Connection connection = DB.getConnection();
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
                String query = "UPDATE gamesPackage "
                                + "SET price = ? "
                                + "WHERE name = ?";
                PreparedStatement pst = connection.prepareStatement(query);

                pst.setDouble(1, gamesPackage.getPrice());
                pst.setString(2, gamesPackage.getName());
                rowCount = pst.executeUpdate();
                pst.close();
                query = "DELETE FROM packageDetail WHERE packageName = ?";
                pst = connection.prepareStatement(query);
                pst.setString(1, gamesPackage.getName());
                pst.executeUpdate();
                pst.close();
                if(gamesPackage.getGames() != null && !gamesPackage.getGames().isEmpty()) {
                    query = "INSERT INTO packageDetail (packageName, gameName, gameBasePoints, amount) VALUES (?, ?, ?, ?)";
                    pst = connection.prepareStatement(query);
                    for(Entry<tGame, Integer> entry: gamesPackage.getGames().entrySet()) {
                        pst.setString(1, gamesPackage.getName());
                        pst.setString(2, entry.getKey().getName());
                        pst.setInt(3, entry.getKey().getBasePoints());
                        pst.setInt(4, entry.getValue());
                        pst.executeUpdate();
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
        return rowCount;
    }

    @Override
    public int deleteGamesPackage(String name) {
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for deleteGamesPackage in DAOGamesPackageImp");
        int rowCount = 0;
        Connection connection = DB.getConnection();
        if(connection != null) {
        	String query = "DELETE FROM gamesPackage WHERE name = ?";
        	
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
    public Collection<tGamesPackage> readAllGamesPackages() {
        Map<String, tGamesPackage> packages = new HashMap<>();
        Connection connection = DB.getConnection();

        if(connection != null) {
        	String query = "SELECT gp.name, gp.price, pd.gameName, pd.gameBasePoints, pd.amount "
        					+ "FROM gamesPackage gp LEFT JOIN packageDetail pd ON gp.name = pd.packageName";
        	
        	try(PreparedStatement pst = connection.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
        		while(rs.next()) {
		            String packageName = rs.getString("name"), gameName;
		
		            packages.putIfAbsent(packageName, new tGamesPackage(packageName, rs.getDouble("price"), new HashMap<>()));
		            gameName = rs.getString("gameName");
		            if(gameName != null) packages.get(packageName).getGames().put(new tGame(gameName, rs.getInt("gameBasePoints")), rs.getInt("amount"));
                }
        	}
        	catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return packages.values();
    }
}
