package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import business.tPlayer;
import business.tUser;

public class DAOPlayerImp implements DAOPlayer {
    private DAOUser daoUser = new DAOUserImp(); // Composición

    @Override
    public int createPlayer(tPlayer player) {
        int rowCount = 0;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
                if(daoUser.createUser(player) == 1) {
                    String query = "INSERT INTO player (name, points) VALUES (?, ?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    
                    pst.setString(1, player.getName());
                    pst.setInt(2, player.getPoints());
                    rowCount = pst.executeUpdate();
                    pst.close();
                    connection.commit();
                }
                else connection.rollback();
            } 
            catch(SQLException e) {
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
    public tPlayer readPlayer(String name) {
        tUser user = daoUser.readUser(name);
        
        if(user instanceof tPlayer) return (tPlayer) user;
        return null;
    }

    @Override
    public int updatePlayer(tPlayer player) {
        int rowCount = 0;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
                if(daoUser.updateUser(player) == 1) {
                    String query = "UPDATE player "
                    			+ "SET points = ? "
                    			+ "WHERE name = ?";
                    PreparedStatement pst = connection.prepareStatement(query);
                    
                    pst.setInt(1, player.getPoints());
                    pst.setString(2, player.getName());
                    rowCount = pst.executeUpdate();
                    pst.close();
                    connection.commit();
                } 
                else connection.rollback();
            } 
            catch(SQLException e) {
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
    public int deletePlayer(String name) {
    	return daoUser.deleteUser(name);
    }

    @Override
    public Collection<tPlayer> readAllPlayers() {
        Collection<tPlayer> players = new ArrayList<>();
   
        for(tUser user: daoUser.readAllUsers()) {
            if(user instanceof tPlayer) players.add((tPlayer)user);
        }
        return players;
    }
}
