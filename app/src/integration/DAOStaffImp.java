package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import business.tStaff;
import business.tUser;

public class DAOStaffImp implements DAOStaff {
    private DAOUser daoUser = new DAOUserImp(); // Composición

    @Override
    public int createStaff(tStaff staff) {
        int rowCount = 0;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
            try {
                connection.setAutoCommit(false); 
                if(daoUser.createUser(staff) == 1) {
                    String query = "INSERT INTO staff (name) VALUES (?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    
                    pst.setString(1, staff.getName());
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
    public tStaff readStaff(String name) {
        tUser user = daoUser.readUser(name);
        
        if(user instanceof tStaff) return (tStaff) user;
        return null;
    }

    @Override
    public int updateStaff(tStaff staff) {
        return daoUser.updateUser(staff);
    }

    @Override
    public int deleteStaff(String name) {
        return daoUser.deleteUser(name);
    }

    @Override
    public Collection<tStaff> readAllStaffs() {
        Collection<tStaff> staffs = new ArrayList<>();
        
        for(tUser user: daoUser.readAllUsers()) {
            if(user instanceof tStaff) staffs.add((tStaff)user);
        }
        return staffs;
    }
}
