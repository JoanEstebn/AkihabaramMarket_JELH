/**
 * DAO para gestionar operaciones CRUD de productos otaku en la base de datos.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package dao;

import java.util.*;
import conexion.ConexionBBDD;
import model.ProductoOtaku;
import java.sql.*;

public class ProductoDAO {

    // Añade un nuevo producto si no existe ya con el mismo nombre (ignorando mayúsculas)
    public static void agregarProducto(ProductoOtaku p) {
        String existeNombre = "SELECT * FROM productos WHERE LOWER(nombre) = LOWER(?)";
        String insert = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBBDD.getConnection()) {

            // Verifica si ya existe un producto con el mismo nombre
            try (PreparedStatement ps1 = conn.prepareStatement(existeNombre)) {
                ps1.setString(1, p.getNombre());
                if (ps1.executeQuery().next()) {
                    System.out.println("Error: Ya existe un producto con ese nombre.");
                    return;
                }
            }

            // Inserta el nuevo producto si no existe duplicado
            try (PreparedStatement ps2 = conn.prepareStatement(insert)) {
                ps2.setString(1, p.getNombre());
                ps2.setString(2, p.getCategoria());
                ps2.setDouble(3, p.getPrecio());
                ps2.setInt(4, p.getStock());
                ps2.executeUpdate();
                System.out.println("Producto añadido correctamente.");
            }

        } catch (SQLException e) {
            System.out.println("Error al añadir producto: " + e.getMessage());
        }
    }

    // Devuelve un producto por su ID o null si no existe
    public ProductoOtaku obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        ProductoOtaku producto = null;

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                producto = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    // Devuelve una lista con todos los productos registrados
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                productos.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    // Actualiza los datos de un producto existente por su ID
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        boolean actualizado = false;

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, producto.getNombre());
            pst.setString(2, producto.getCategoria());
            pst.setDouble(3, producto.getPrecio());
            pst.setInt(4, producto.getStock());
            pst.setInt(5, producto.getId());

            actualizado = pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actualizado;
    }

    // Elimina un producto de la base de datos por su ID
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        boolean eliminado = false;

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            eliminado = pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminado;
    }

    // Busca productos cuyo nombre contenga el texto dado (ignorando mayúsculas/minúsculas)
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE LOWER(nombre) LIKE LOWER(?)";

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + nombre + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                productos.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
}
