/**
 * Pequeña prueba para los datos de mySql.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package prueba.datos;

import java.sql.*;

public class pruebaBBDD {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/AkihabaraDB_db_JELH";
		String usr = "root";
		String pwd = "campusfp";

		try (Connection conn = DriverManager.getConnection(url, usr, pwd)) {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 1. Mostrar todos los productos
			mostrarTodosLosProductos(conn);

			// 2. Buscar productos por categoría
			String categoria = "Manga";
			buscarProductosPorCategoria(conn, categoria);

		} catch (ClassNotFoundException e) {
			System.out.println("Error: No se encontró el driver JDBC");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error de conexión o consulta SQL");
			e.printStackTrace();
		}
	}

	public static void mostrarTodosLosProductos(Connection conn) throws SQLException {
		String query = "SELECT * FROM productos";
		try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

			System.out.println("Listado completo de productos:");
			while (rs.next()) {
				System.out.println("ID: " + rs.getInt("id"));
				System.out.println("Nombre: " + rs.getString("nombre"));
				System.out.println("Categoría: " + rs.getString("categoria"));
				System.out.println("Precio: " + rs.getDouble("precio"));
				System.out.println("Stock: " + rs.getInt("stock"));
				System.out.println("--------------------------");
			}
		}
	}

	public static void buscarProductosPorCategoria(Connection conn, String categoria) throws SQLException {
		String query = "SELECT * FROM productos WHERE categoria = ?";
		try (PreparedStatement pst = conn.prepareStatement(query)) {
			pst.setString(1, categoria);

			try (ResultSet rs = pst.executeQuery()) {
				System.out.println("Productos en la categoría: " + categoria);
				while (rs.next()) {
					System.out.println("Nombre: " + rs.getString("nombre"));
					System.out.println("Precio: " + rs.getDouble("precio"));
					System.out.println("Stock: " + rs.getInt("stock"));
					System.out.println("--------------------------");
				}
			}
		}
	}
}
