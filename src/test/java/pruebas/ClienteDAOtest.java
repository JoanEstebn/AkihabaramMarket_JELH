package pruebas;

import org.junit.jupiter.api.*;

import dao.ClienteDAO;
import model.Cliente;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ClienteDAOtest {

    @BeforeAll
    public static void setup() {
        new ClienteDAO();
    }

    @Test
    public void testAgregarYObtenerCliente() {
        Cliente cliente = new Cliente(0, "Test Cliente", "testcliente@example.com", "123456789", null);
        ClienteDAO.agregarCliente(cliente);
        List<Cliente> clientes = ClienteDAO.obtenerTodosLosClientes();
        assertTrue(clientes.stream().anyMatch(c -> "testcliente@example.com".equals(c.getEmail())));
    }

    @Test
    public void testActualizarCliente() {
        List<Cliente> clientes = ClienteDAO.obtenerTodosLosClientes();
        if (!clientes.isEmpty()) {
            Cliente cliente = clientes.get(0);
            cliente.setTelefono("999999999");
            boolean actualizado = ClienteDAO.actualizarCliente(cliente);
            assertTrue(actualizado);
        }
    }

    @Test
    public void testEliminarCliente() {
        Cliente cliente = new Cliente(0, "Eliminar Cliente", "delete@example.com", "000000000", null);
        ClienteDAO.agregarCliente(cliente);
        List<Cliente> clientes = ClienteDAO.obtenerTodosLosClientes();
        int lastId = clientes.get(clientes.size() - 1).getId();
        assertTrue(ClienteDAO.eliminarCliente(lastId));
    }
}
