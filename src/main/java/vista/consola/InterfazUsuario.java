/**
 * Una pequeña interfaz para un mejor manejo entre el Main de la consola y el Main de la interfaz gráfica.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package vista.consola;

import java.util.List;

import model.Cliente;
import model.ProductoOtaku;

public interface InterfazUsuario {
	void mostrarMenuPrincipal();

	ProductoOtaku pedirDatosNuevoProducto();

	int pedirIdProducto();

	String pedirNombreProducto();

	void mostrarListaProductos(List<ProductoOtaku> productos);

	void mostrarMensaje(String mensaje);

	int leerOpcionMenu();

	String pedirNombreParaSugerencia();

	void mostrarSubmenuClientes();

	Cliente pedirDatosCliente();

	int pedirIdCliente();

}
