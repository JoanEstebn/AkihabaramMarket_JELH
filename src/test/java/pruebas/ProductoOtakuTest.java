package pruebas;
import org.junit.jupiter.api.Test;

import model.ProductoOtaku;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoOtakuTest {

    @Test
    public void testCrearProducto() {
        ProductoOtaku p = new ProductoOtaku(1, "Póster Ghibli", "Póster", 15.5, 10);
        assertEquals("Póster Ghibli", p.getNombre());
        assertEquals("Póster", p.getCategoria());
        assertEquals(15.5, p.getPrecio());
        assertEquals(10, p.getStock());
    }

    @Test
    public void testSetters() {
        ProductoOtaku p = new ProductoOtaku(0, null, null, 0, 0);
        p.setId(2);
        p.setNombre("Manga Naruto");
        p.setCategoria("Manga");
        p.setPrecio(8.99);
        p.setStock(25);

        assertEquals(2, p.getId());
        assertEquals("Manga Naruto", p.getNombre());
        assertEquals("Manga", p.getCategoria());
        assertEquals(8.99, p.getPrecio());
        assertEquals(25, p.getStock());
    }
}
