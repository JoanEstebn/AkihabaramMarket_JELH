/**
 * DAO para gestionar operaciones CRUD de Clientes otakus en la base de datos.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package dao;

import conexion.ConexionBBDD;
import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Inserta un nuevo cliente en la base de datos
    public static void agregarCliente(Cliente c) {
        String sql = "INSERT INTO clientes (nombre, email, telefono) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, c.getNombre());
            pst.setString(2, c.getEmail());
            pst.setString(3, c.getTelefono());
            pst.executeUpdate();
            System.out.println("Cliente agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }
    }

    // Busca un cliente por su ID y lo devuelve como objeto Cliente
    public static Cliente obtenerClientePorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_registro").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Devuelve una lista con todos los clientes registrados
    public static List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_registro").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    // Actualiza los datos de un cliente existente
    public static boolean actualizarCliente(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ? WHERE id = ?";
        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, c.getNombre());
            pst.setString(2, c.getEmail());
            pst.setString(3, c.getTelefono());
            pst.setInt(4, c.getId());
            return pst.executeUpdate() > 0; // Devuelve true si se actualizó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Elimina un cliente por ID
    public static boolean eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0; // true si se eliminó alguna fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Busca un cliente por su email (opcional)
    public Cliente buscarPorEmail(String email) {
        String sql = "SELECT * FROM clientes WHERE email = ?";
        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_registro").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
