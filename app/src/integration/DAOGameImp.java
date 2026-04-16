package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import business.tGame;

public class DAOGameImp implements DAOGame {

    @Override
    public int createGame(tGame game) {
        int rowCount = 0;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
            try {
                String query = "INSERT INTO game (name, basePoints) VALUES (?, ?)";
                PreparedStatement pst = connection.prepareStatement(query);
                
                pst.setString(1, game.getName());
                pst.setInt(2, game.getBasePoints());
                rowCount = pst.executeUpdate();
                pst.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }

    @Override
    public tGame readGame(String name, int basePoints) {
        tGame game = null;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
            try {
                String query = "SELECT name, basePoints "
                				+ "FROM game "
                				+ "WHERE name = ? AND basePoints = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                
                pst.setString(1, name);
                pst.setInt(2, basePoints);
                ResultSet rs = pst.executeQuery();
                if(rs.next()) game = new tGame(rs.getString("name"), rs.getInt("basePoints"));
                rs.close();
                pst.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return game;
    }

    @Override
    public int deleteGame(String name, int basePoints) {
        int rowCount = 0;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
            try {
                String query = "DELETE FROM game WHERE name = ? AND basePoints = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                
                pst.setString(1, name);
                pst.setInt(2, basePoints);
                rowCount = pst.executeUpdate();
                pst.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }

    @Override
    public Collection<tGame> readAllGames() {
        Collection<tGame> games = new ArrayList<>();
        Connection connection = DB.getConnection();

        if(connection != null) {
            try {
                String query = "SELECT name, basePoints "
                				+ "FROM game";
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                
                while(rs.next()) games.add(new tGame(rs.getString("name"), rs.getInt("basePoints")));
                rs.close();
                pst.close();
            }
            catch(SQLException e) {
            	games.clear();
                e.printStackTrace();
            }
        }
        return games;
    }
}
