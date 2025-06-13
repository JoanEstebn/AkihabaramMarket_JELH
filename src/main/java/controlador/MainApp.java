/**
 * La página principal para ejecutar todo menos la interfaz gráfica.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package controlador;

import java.util.List;
import dao.ClienteDAO;
import dao.ProductoDAO;
import model.Cliente;
import model.ProductoOtaku;
import prueba.datos.SetupDatos;
import vista.consola.*;
import service.LlmService;

public class MainApp {

	public static void main(String[] args) {

		// Carga productos iniciales si no existen
		SetupDatos.inicializarInventario();

		// Inicializa la vista, DAO de productos y servicio de IA
		InterfazUsuario vista = new InterfazConsola();
		ProductoDAO dao = new ProductoDAO();
		LlmService ia = new LlmService();

		boolean salir = false;

		// Bucle principal del menú
		while (!salir) {
			vista.mostrarMenuPrincipal();
			int opcion = vista.leerOpcionMenu();

			switch (opcion) {
			case 1: // Añadir nuevo producto
				ProductoOtaku nuevo = vista.pedirDatosNuevoProducto();
				ProductoDAO.agregarProducto(nuevo);
				break;

			case 2: // Consultar producto por ID
				int idConsulta = vista.pedirIdProducto();
				ProductoOtaku encontrado = dao.obtenerProductoPorId(idConsulta);
				if (encontrado != null) {
					vista.mostrarMensaje("Producto encontrado:");
					vista.mostrarMensaje(encontrado.toString());
				} else {
					vista.mostrarMensaje("No se encontró ningún producto.");
				}
				break;

			case 3: // Mostrar todos los productos
				List<ProductoOtaku> lista = dao.obtenerTodosLosProductos();
				vista.mostrarListaProductos(lista);
				break;

			case 4: // Actualizar producto por ID
				int idActualizar = vista.pedirIdProducto();
				ProductoOtaku existente = dao.obtenerProductoPorId(idActualizar);
				if (existente != null) {
					vista.mostrarMensaje("Introduce los nuevos datos del producto:");
					ProductoOtaku actualizado = vista.pedirDatosNuevoProducto();
					actualizado.setId(idActualizar);
					boolean exito = dao.actualizarProducto(actualizado);
					vista.mostrarMensaje(exito ? "Producto actualizado correctamente." : "No se actualizó el producto.");
				} else {
					vista.mostrarMensaje("Producto no encontrado.");
				}
				break;

			case 5: // Eliminar producto
				int idEliminar = vista.pedirIdProducto();
				boolean eliminado = dao.eliminarProducto(idEliminar);
				vista.mostrarMensaje(eliminado ? "Producto eliminado." : "No se encontró el producto para eliminar.");
				break;

			case 6: // Buscar productos por nombre
				String nombreBusqueda = vista.pedirNombreProducto();
				List<ProductoOtaku> resultados = dao.buscarProductosPorNombre(nombreBusqueda);
				vista.mostrarListaProductos(resultados);
				break;

			case 7: // Descripción IA para producto
				int idDesc = vista.pedirIdProducto();
				ProductoOtaku prod = dao.obtenerProductoPorId(idDesc);
				if (prod != null) {
					String prompt = "Genera una descripción de marketing breve y divertida para el producto: "
							+ prod.getNombre() + " de la categoría " + prod.getCategoria() + ".";
					String descripcion = ia.enviarPrompt(prompt);
					vista.mostrarMensaje("\n Descripción:\n" + descripcion);
				} else {
					vista.mostrarMensaje("Producto no encontrado.");
				}
				break;

			case 8: // Sugerencia de categoría con IA
				String nombreNuevo = vista.pedirNombreParaSugerencia();
				String promptCat = "Para un producto otaku llamado '" + nombreNuevo
						+ "', sugiere una categoría adecuada de esta lista: "
						+ "Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
				String categoriaSugerida = ia.enviarPrompt(promptCat);
				vista.mostrarMensaje("\nCategoría sugerida:\n" + categoriaSugerida);
				break;

			case 9: // Submenú de clientes
				int opcionCliente;
				do {
					vista.mostrarSubmenuClientes();
					opcionCliente = vista.leerOpcionMenu();

					switch (opcionCliente) {
					case 1: // Añadir cliente
						Cliente nuevoCliente = vista.pedirDatosCliente();
						ClienteDAO.agregarCliente(nuevoCliente);
						break;

					case 2: // Consultar cliente por ID
						int idBuscar = vista.pedirIdProducto(); // Reutiliza método para pedir ID
						Cliente c = ClienteDAO.obtenerClientePorId(idBuscar);
						if (c != null) {
							vista.mostrarMensaje(c.toString());
						} else {
							vista.mostrarMensaje("Cliente no encontrado.");
						}
						break;

					case 3: // Ver todos los clientes
						List<Cliente> listaClientes = ClienteDAO.obtenerTodosLosClientes();
						if (listaClientes.isEmpty()) {
							vista.mostrarMensaje("No hay clientes registrados.");
						} else {
							for (Cliente cli : listaClientes) {
								vista.mostrarMensaje(cli.toString());
							}
						}
						break;

					case 4: // Actualizar cliente
						int idActualizar1 = vista.pedirIdCliente();
						Cliente clienteExistente = ClienteDAO.obtenerClientePorId(idActualizar1);
						if (clienteExistente != null) {
							vista.mostrarMensaje("Introduce los nuevos datos:");
							Cliente datosActualizados = vista.pedirDatosCliente();
							datosActualizados.setId(idActualizar1);
							boolean actualizado = ClienteDAO.actualizarCliente(datosActualizados);
							vista.mostrarMensaje(actualizado ? "Cliente actualizado correctamente." : "No se pudo actualizar.");
						} else {
							vista.mostrarMensaje("Cliente no encontrado.");
						}
						break;

					case 5: // Eliminar cliente
						int idEliminar1 = vista.pedirIdCliente();
						boolean eliminado1 = ClienteDAO.eliminarCliente(idEliminar1);
						vista.mostrarMensaje(eliminado1 ? "Cliente eliminado correctamente." : "Cliente no encontrado.");
						break;

					case 0:
						vista.mostrarMensaje("Volviendo al menú principal...");
						break;

					default:
						vista.mostrarMensaje("Opción no válida.");
					}

				} while (opcionCliente != 0);
				break;

			case 0: // Salir de la aplicación
				salir = true;
				vista.mostrarMensaje("Gracias por usar Akihabara Market. Un placer.");
				break;

			default:
				vista.mostrarMensaje("Opción no válida. Inténtalo de nuevo.");
				break;
			}
		}
	}
}
