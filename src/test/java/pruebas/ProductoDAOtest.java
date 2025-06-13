package pruebas;

import org.junit.jupiter.api.*;

import dao.ProductoDAO;
import model.ProductoOtaku;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ProductoDAOtest {

    private static ProductoDAO dao;

    @BeforeAll
    public static void setup() {
        dao = new ProductoDAO();
    }

    @Test
    public void testAgregarYObtenerProducto() {
        ProductoOtaku producto = new ProductoOtaku(0, "Test Producto", "TestCat", 10.0, 5);
        ProductoDAO.agregarProducto(producto);
        List<ProductoOtaku> productos = dao.obtenerTodosLosProductos();
        assertTrue(productos.stream().anyMatch(p -> "Test Producto".equals(p.getNombre())));
    }

    @Test
    public void testActualizarProducto() {
        List<ProductoOtaku> productos = dao.obtenerTodosLosProductos();
        if (!productos.isEmpty()) {
            ProductoOtaku producto = productos.get(0);
            producto.setPrecio(99.99);
            boolean actualizado = dao.actualizarProducto(producto);
            assertTrue(actualizado);
        }
    }

    @Test
    public void testEliminarProducto() {
        ProductoOtaku producto = new ProductoOtaku(0, "Eliminar Producto", "TestCat", 1.0, 1);
        ProductoDAO.agregarProducto(producto);
        List<ProductoOtaku> productos = dao.obtenerTodosLosProductos();
        int lastId = productos.get(productos.size() - 1).getId();
        assertTrue(dao.eliminarProducto(lastId));
    }
}
