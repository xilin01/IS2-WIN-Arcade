package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import business.tUser;

@objid ("916a8918-6c10-4150-8235-52c01b81dbe7")
public class DAOUserImp implements DAOUser {
    @objid ("f497545b-41c2-43b2-b1c8-0df6f0889592")    
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

    @objid ("c84ad11e-2c71-4ffc-9afb-16c89e062d95")    
    public tUser readUser(String name) {
    	tUser user = null;
        Connection connection = DB.getConnection();
        
        if(connection != null) {
        	try {
        		String query = "SELECT password, active "
        					+ "FROM user "
        					+ "WHERE name = ?";
        		PreparedStatement pst = connection.prepareStatement(query);
        		
        		pst.setString(1, name);
        		ResultSet rs = pst.executeQuery();
        		if(rs.next()) user = new tUser(name, rs.getString("password"), rs.getBoolean("active"));
        		rs.close();
        		pst.close();
        	} 
        	catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return user;
    }

    @objid ("df3573cb-7ca4-46d5-b789-f3a6bcec5034")
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

    @objid ("c6c8747e-8b83-4d56-98bc-16c48b6f140f")
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
    public Collection<tUser> readAllUsers() {
        Collection<tUser> users = new ArrayList<>();
        Connection connection = DB.getConnection();

        if(connection != null) {
            try {
                String query = "SELECT name, password, active "
                			+ "FROM user";
                PreparedStatement pst = connection.prepareStatement(query);

                ResultSet rs = pst.executeQuery();
                while(rs.next()) users.add(new tUser(rs.getString("name"), rs.getString("password"), rs.getBoolean("active")));
                rs.close();
                pst.close();
            } 
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }   
}
