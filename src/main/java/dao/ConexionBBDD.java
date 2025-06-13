package dao;

import java.sql.*;

public class ConexionBBDD {

	private static final String URL = "jdbc:mysql://localhost:3306/AkihabaraDB_db_JELH";
	private static final String USER = "root";
	private static final String PASS = "campusfp";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
