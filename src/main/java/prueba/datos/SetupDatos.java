/**
 * Inicialización de los datos de productos.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package prueba.datos;


import java.util.List;

import dao.ProductoDAO;
import model.ProductoOtaku;

public class SetupDatos {

    public static void inicializarInventario() {
        ProductoDAO dao = new ProductoDAO();
        List<ProductoOtaku> productos = dao.obtenerTodosLosProductos();

        if (productos.isEmpty()) {
            System.out.println("Inventario vacío. Cargando productos de ejemplo...");

            ProductoDAO.agregarProducto(new ProductoOtaku(0, "Figura Levi Ackerman", "Figura", 69.99, 10));
            ProductoDAO.agregarProducto(new ProductoOtaku(0, "Manga Attack on Titan Vol.1", "Manga", 8.50, 30));
            ProductoDAO.agregarProducto(new ProductoOtaku(0, "Póster Muro María - Attack on Titan", "Póster", 12.95, 20));

            ProductoDAO.agregarProducto(new ProductoOtaku(0, "Cuaderno Death Note (réplica)", "Accesorio", 14.99, 15));
            ProductoDAO.agregarProducto(new ProductoOtaku(0, "Figura Light Yagami con manzana", "Figura", 54.95, 7));
            ProductoDAO.agregarProducto(new ProductoOtaku(0, "Camiseta Ryuk - Death Note (Talla L)", "Ropa", 21.50, 18));

            System.out.println("Tesoros otaku cargados correctamente.");
        } else {
            System.out.println("La base de datos ya contiene productos. No se insertaron duplicados.");
        }
    }
}
