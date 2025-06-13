package pruebas;

import org.junit.jupiter.api.Test;

import model.Cliente;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    public void testCrearCliente() {
        Cliente c = new Cliente(1, "Neus", "neus@example.com", "612345678", null);
        assertEquals("Neus", c.getNombre());
        assertEquals("neus@example.com", c.getEmail());
        assertEquals("612345678", c.getTelefono());
    }

    @Test
    public void testSetters() {
        Cliente c = new Cliente(0, null, null, null, null);
        c.setId(2);
        c.setNombre("Joan");
        c.setEmail("joan@example.com");
        c.setTelefono("698765432");

        assertEquals(2, c.getId());
        assertEquals("Joan", c.getNombre());
        assertEquals("joan@example.com", c.getEmail());
        assertEquals("698765432", c.getTelefono());
    }
}
