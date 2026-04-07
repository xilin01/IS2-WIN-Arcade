package integracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import negocio.tUsuario;

@objid ("916a8918-6c10-4150-8235-52c01b81dbe7")
public class DAOUsuarioImp implements DAOUsuario {
    @objid ("f497545b-41c2-43b2-b1c8-0df6f0889592")
    public int createUsuario(final tUsuario usuario) {
        int rowCount = 0;
        Connection connection = DBConnection.getConnection();
                
        if (connection != null) {
            try {
                String query = "INSERT INTO usuario (dni, nombre, contrasenya, activo) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = connection.prepareStatement(query);
                		
				pst.setString(1, usuario.getDni());
				pst.setString(2, usuario.getNombre());
				pst.setString(3, usuario.getContrasenya());
				pst.setBoolean(4, usuario.getActivo());
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
    public tUsuario readUsuario(final String dni) {
    	tUsuario usuario = null;
        Connection conexion = DBConnection.getConnection();
        
        if(conexion != null) {
        	try {
        		String query = "SELECT id, nombre, dni, email, contrasenya, activo FROM usuario WHERE id = ?";
        		PreparedStatement pst = conexion.prepareStatement(query);
        		
        		pst.setString(1, dni);
        		ResultSet rs = pst.executeQuery();
        		if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String contrasenya = rs.getString("contrasenya");
                    boolean activo = rs.getBoolean("activo");
                    
                    usuario = new tUsuario(dni, nombre, contrasenya, activo);
        		}
        		rs.close();
        		pst.close();
        	} 
        	catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return usuario;
    }

    @objid ("df3573cb-7ca4-46d5-b789-f3a6bcec5034")
    public int updateUsuario(final tUsuario usuario) {
        int rowCount = 0;
        Connection conexion = DBConnection.getConnection();
        
        if(conexion != null) {
        	try {
        		String query = "UPDATE usuario SET nombre = ?, contrasenya = ?, activo = ? WHERE dni = ?";
        		
        		PreparedStatement pst = conexion.prepareStatement(query);
        		pst.setString(1, usuario.getNombre());
        		pst.setString(2, usuario.getContrasenya());
        		pst.setBoolean(3, usuario.getActivo());
        		pst.setString(4, usuario.getDni());
        		rowCount = pst.executeUpdate();
        		pst.close();
        	} 
        	catch (SQLException e) {
        		e.printStackTrace();
        	}
        }
        return rowCount;
    }

    @objid ("c6c8747e-8b83-4d56-98bc-16c48b6f140f")
    public int deleteUsuario(final String dni) {
        int rowCount = 0;
        Connection conexion = DBConnection.getConnection();
        
        if(conexion != null) {
        	try {
        		String query = "DELETE FROM usuario WHERE dni = ?";
        		PreparedStatement pst = conexion.prepareStatement(query);
        		
        		pst.setString(1, dni);
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
    public Collection<tUsuario> readAllUsuarios() {
        Collection<tUsuario> usuarios = new ArrayList<>();
        Connection conexion = DBConnection.getConnection();

        if(conexion != null) {
            try {
                String query = "SELECT dni, nombre, contrasena, activo FROM usuario";
                PreparedStatement pst = conexion.prepareStatement(query);

                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    String dni = rs.getString("dni");
                    String nombre = rs.getString("nombre");
                    String contrasenya = rs.getString("contrasena");
                    boolean activo = rs.getBoolean("activo");

                    tUsuario usuario = new tUsuario(dni, nombre, contrasenya, activo);
                    usuarios.add(usuario);
                }
                rs.close();
                pst.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuarios;
    }
}
