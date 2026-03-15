package integracion;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

import configFiles.BDConexion;
import negocio.tCliente;
import java.sql.*;

@objid ("916a8918-6c10-4150-8235-52c01b81dbe7")
public class DAOClienteImp implements DAOCliente {
    @objid ("f497545b-41c2-43b2-b1c8-0df6f0889592")
    public int createCliente(final tCliente cliente) {
    	int rowCount = 0;
    	
        BDConexion miConexion = new BDConexion();
        Connection conexion = miConexion.getConnection();
        if (conexion != null) {
        	try {
        		String query = "INSERT INTO usuario (dni, nombre, activo) VALUES (?, ?, ?)";
        		PreparedStatement pst = conexion.prepareStatement(query);
        		
        		pst.setString(1, cliente.getDni());
        		pst.setString(2, cliente.getNombre());
        		pst.setBoolean(3, cliente.getActivo());
        		
        		rowCount = pst.executeUpdate();
        		
        		pst.close();
        		
        	} catch(SQLException e) {
        		e.printStackTrace();
        	} finally {
        		miConexion.desconectar();
        	}
        }
        return rowCount;
    }

    @objid ("c84ad11e-2c71-4ffc-9afb-16c89e062d95")
    public tCliente readCliente(int idCliente) {
    	tCliente cliente = null;
        
        BDConexion miConexion = new BDConexion();
        Connection conexion = miConexion.getConnection();
        
        if (conexion != null) {
        	try {
        		String query = "SELECT id, nombre, dni, email, contrasenya, activo FROM usuario WHERE id = ?";
        		PreparedStatement pst = conexion.prepareStatement(query);
        		pst.setInt(1, idCliente);
        		
        		ResultSet rs = pst.executeQuery();
        		
        		if (rs.next()) {
	                int id = rs.getInt("id");
	                String dni = rs.getString("dni");
	                String nombre = rs.getString("nombre");
	                String email = rs.getString("email");
	                String contrasenya = rs.getString("contrasenya");
	                boolean activo = rs.getBoolean("activo");
	                
	                cliente = new tCliente(id, dni, nombre, activo);
        		}
        		rs.close();
        		pst.close();
        		
        	} catch(SQLException e) {
        		e.printStackTrace();
        	} finally {
        		miConexion.desconectar();
        	}
        }

        return cliente;
    }

    @objid ("df3573cb-7ca4-46d5-b789-f3a6bcec5034")
    public int updateCliente(final tCliente cliente) {
    	int rowCount = 0;
    	
    	BDConexion miConexion = new BDConexion();
        Connection conexion = miConexion.getConnection();
        if (conexion != null) {
        	try {
        		String query = "UPDATE usuario SET dni = ?, nombre = ?, activo = ? WHERE id = ?";
        		
        		PreparedStatement pst = conexion.prepareStatement(query);
        		pst.setString(1, cliente.getDni());
        		pst.setString(2, cliente.getNombre());
        		pst.setBoolean(3, cliente.getActivo());
        		pst.setInt(4, cliente.getId());
        		
        		rowCount = pst.executeUpdate();
        		
        		pst.close();
        		
        	} catch (SQLException e) {
        		e.printStackTrace();
        	} finally {
        		miConexion.desconectar();
        	}
        }
    	
        return rowCount;
    }

    @objid ("c6c8747e-8b83-4d56-98bc-16c48b6f140f")
    public int deleteCliente(final String dni) {
    	int rowCount = 0;
    	
    	BDConexion miConexion = new BDConexion();
        Connection conexion = miConexion.getConnection();
        if (conexion != null) {
        	try {
        		String query = "DELETE FROM usuario WHERE dni = ?";
        		
        		PreparedStatement pst = conexion.prepareStatement(query);
        		pst.setString(1, dni);
        		
        		rowCount = pst.executeUpdate();
        		
        		pst.close();
        		
        	} catch (SQLException e) {
        		e.printStackTrace();
        	} finally {
        		miConexion.desconectar();
        	}
        }
        return rowCount;
    }

}
