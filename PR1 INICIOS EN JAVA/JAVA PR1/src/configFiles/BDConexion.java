package configFiles;

import java.sql.*;

public class BDConexion {
	/**Parametros de conexion**/
	static String bd = "win";
	static String login = "root";
	static String password = "equipo4";
	static String url = "jdbc:mysql://localhost/" + bd;
	Connection connection = null;

	public BDConexion() {
		try {
			// String url = " jdbc : mysql :// hostname / database - name ";
			connection = DriverManager.getConnection (url,login,password);
		}
		catch ( SQLException ex) {
			connection = null ;
			ex. printStackTrace ();
			System.out.println (" SQLException : " + ex. getMessage ());
			System.out.println (" SQLState : " + ex. getSQLState ());
			System.out.println (" VendorError : " + ex. getErrorCode ());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void desconectar() {
		connection = null;
	}
}