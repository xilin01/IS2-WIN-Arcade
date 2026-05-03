package integration.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import business.staff.tStaff;
import business.user.tUser;
import integration.DB;
import integration.user.DAOUser;
import integration.user.DAOUserImp;

public class DAOStaffImp implements DAOStaff {
    private DAOUser daoUser = new DAOUserImp(); // Composición

    @Override
    public int createStaff(tStaff staff) {
    	if(staff == null) new IllegalArgumentException("Invalid arg for createStaff in DAOStaffImp");	
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
    public tStaff readStaff(String name) {
    	if(name == null || name.trim().isEmpty()) new IllegalArgumentException("Invalid arg for readStaff in DAOStaffImp");
        tUser user = daoUser.readUser(name);
        if(user instanceof tStaff) return (tStaff)user;
        return null;
    }

    @Override
    public int updateStaff(tStaff staff) {
    	if(staff == null) new IllegalArgumentException("Invalid arg for updateStaff in DAOStaffImp");	
        return daoUser.updateUser(staff);
    }

    @Override
    public int deleteStaff(String name) {
    	if(name == null || name.trim().isEmpty()) new IllegalArgumentException("Invalid arg for deleteStaff in DAOStaffImp");
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
