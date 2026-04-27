package integration.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import business.player.tPlayer;
import business.staff.tStaff;
import business.user.tUser;
import integration.DB;

public class DAOUserImp implements DAOUser {

    public int createUser(tUser user) {
        int rowCount = 0;
        Connection connection = DB.getConnection();

        if(connection != null) {
            try {
                String query = "INSERT INTO user (name, password, active) VALUES (?, ?, ?)";
                PreparedStatement pst = connection.prepareStatement(query);

                pst.setString(1, user.getName());
                pst.setString(2, user.getPassword());
                pst.setBoolean(3, user.getActive());
                rowCount = pst.executeUpdate();
                pst.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }

    public tUser readUser(String name) { // Leer usuario activo
        tUser user = null;
        Connection connection = DB.getConnection();

        if(connection != null) {
            try {
                String query = "SELECT u.password, u.active, s.name AS staffName "
                                + "FROM user u LEFT JOIN staff s ON u.name = s.name "
                                + "WHERE u.name = ?";
                PreparedStatement pst = connection.prepareStatement(query);

                pst.setString(1, name);
                ResultSet rs = pst.executeQuery();
                if(rs.next()) {
                    String password = rs.getString("password");
                    boolean active = rs.getBoolean("active");

                    if(rs.getString("staffName") != null) user = new tStaff(name, password, active);
                    else user = new tPlayer(name, password, active, null, 0);
                }
                rs.close();
                pst.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public int updateUser(tUser user) {
        int rowCount = 0;
        Connection connection = DB.getConnection();

        if(connection != null) {
            try {
                String query = "UPDATE user "
                                + "SET password = ?, active = ? "
                                + "WHERE name = ?";
                PreparedStatement pst = connection.prepareStatement(query);

                pst.setString(1, user.getPassword());
                pst.setBoolean(2, user.getActive());
                pst.setString(3, user.getName());
                rowCount = pst.executeUpdate();
                pst.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }

    public int deleteUser(String name) { // Borrado lógico
        int rowCount = 0;
        Connection connection = DB.getConnection();

        if(connection != null) {
            try {
                String query = "UPDATE user "
                                + "SET active = FALSE "
                                + "WHERE name = ?";
                PreparedStatement pst = connection.prepareStatement(query);

                pst.setString(1, name);
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
    public Collection<tUser> readAllUsers() { // Leer usuarios activos y no activos
        Collection<tUser> users = new ArrayList<>();
        Connection connection = DB.getConnection();

        if(connection != null) {
            try {
                String query = "SELECT u.name, u.password, u.active, s.name AS staffName "
                                + "FROM user u LEFT JOIN staff s ON u.name = s.name";
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rs = pst.executeQuery();

                while(rs.next()) {
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    boolean active = rs.getBoolean("active");

                    if(rs.getString("staffName") != null) users.add(new tStaff(name, password, active));
                    else users.add(new tPlayer(name, password, active, null, 0));
                }
                rs.close();
                pst.close();
            }
            catch(SQLException e) {
                users.clear();
                e.printStackTrace();
            }
        }
        return users;
    }
}
