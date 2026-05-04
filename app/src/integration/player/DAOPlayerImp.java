package integration.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import business.player.tPlayer;
import business.player.tPlayer.tInventory;
import business.user.tUser;
import integration.DB;
import integration.user.DAOUser;
import integration.user.DAOUserImp;

public class DAOPlayerImp implements DAOPlayer {
    // Composiciones
    private DAOUser daoUser = new DAOUserImp();
    private DAOInventory daoInventory = new DAOInventoryImp();

    @Override
    public int createPlayer(tPlayer player) {
    	if(player == null) throw new IllegalArgumentException("Invalid arg for createPlayer in DAOPlayerImp");
        int rowCount = 0;
        Connection connection = DB.getConnection();
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
                if(daoUser.createUser(player) == 1) {
                    String query = "INSERT INTO player (name, points) VALUES (?, 0)";
                    PreparedStatement pst = connection.prepareStatement(query);

                    pst.setString(1, player.getName());
                    rowCount = pst.executeUpdate();
                    pst.close();
                    connection.commit();
                }
                else connection.rollback();
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
    public tPlayer readPlayer(String name) {
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for readPlayer in DAOPlayerImp");
        tUser user = daoUser.readUser(name);
        if(user instanceof tPlayer) {
            tPlayer player = (tPlayer)user;
            Connection connection = DB.getConnection();

            if(connection != null) {
                try {
                    String query = "SELECT points "
                                    + "FROM player "
                                    + "WHERE name = ?";
                    PreparedStatement pst = connection.prepareStatement(query);

                    pst.setString(1, name);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()) player.setPoints(rs.getInt("points"));
                    rs.close();
                    pst.close();
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            tInventory inventory = daoInventory.readInventory(name);
            player.setInventory(inventory != null ? inventory : new tInventory(name));
            return player;
        }
        return null;
    }

    @Override
    public int updatePlayer(tPlayer player) {
    	if(player == null) throw new IllegalArgumentException("Invalid arg for updatePlayer in DAOPlayerImp");
        int rowCount = 0;
        Connection connection = DB.getConnection();
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
                daoUser.updateUser(player); 
                String query = "UPDATE player "
                                + "SET points = ? "
                                + "WHERE name = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setInt(1, player.getPoints());
                pst.setString(2, player.getName());
                pst.executeUpdate();
                pst.close();
                daoInventory.updateInventory(player.getInventory());
                connection.commit();
                rowCount = 1;
            }
            catch(SQLException e) {
                rowCount = 0;
                try {
                    connection.rollback(); // Deshacemos toda la transacción (DAOUser, DAOPlayer y DAOInventory) a la vez
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
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for deletePlayer in DAOPlayerImp");
        return daoUser.deleteUser(name);
    }

    @Override
    public Collection<tPlayer> readAllPlayers() {
        Collection<tPlayer> players = new ArrayList<>();
        Connection connection = DB.getConnection();

        for(tUser user: daoUser.readAllUsers()) {
            if(user instanceof tPlayer) {
                tPlayer player = (tPlayer)user;

                if(connection != null) {
                    try {
                        String query = "SELECT points "
                                        + "FROM player "
                                        + "WHERE name = ?";
                        PreparedStatement pst = connection.prepareStatement(query);

                        pst.setString(1, player.getName());
                        ResultSet rs = pst.executeQuery();
                        if(rs.next()) player.setPoints(rs.getInt("points"));
                        rs.close();
                        pst.close();
                    }
                    catch(SQLException e) {
                        e.printStackTrace();
                    }
                }
                tInventory inventory = daoInventory.readInventory(player.getName());
                player.setInventory(inventory != null ? inventory : new tInventory(player.getName()));
                players.add(player);
            }
        }
        return players;
    }
}
