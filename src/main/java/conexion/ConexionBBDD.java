/**
 * La conexión con la base de datos.
 * @author Joan Esteban Londoño Hernández
 * @version 1.0.0.0
 * @date 13/06/2025
 */

package conexion;

import java.sql.*;

public class ConexionBBDD {

	private static final String URL = "jdbc:mysql://localhost:3306/AkihabaraDB_db_JELH";
	private static final String USER = "root";
	private static final String PASS = "campusfp";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
