/**
 * Interfaz de consola que nos servirá para llamar a los datos desde el MainApp.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package vista.consola;

import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.ProductoOtaku;

// Clase que implementa la interfaz de usuario por consola
public class InterfazConsola implements InterfazUsuario {

	private final Scanner scanner = new Scanner(System.in);

	// Muestra el menú principal de opciones
	@Override
	public void mostrarMenuPrincipal() {
		System.out.println("\n====== AKIHABARA MARKET ======\n");
		System.out.println("| 1. Añadir nuevo producto");
		System.out.println("| 2. Consultar producto por ID");
		System.out.println("| 3. Ver todos los productos");
		System.out.println("| 4. Actualizar producto");
		System.out.println("| 5. Eliminar producto");
		System.out.println("| 6. Buscar producto por nombre");
		System.out.println("| 7. Generar descripción con IA");
		System.out.println("| 8. Sugerir categoría con IA");
		System.out.println("| 9. Gestión de Clientes");
		System.out.println("| 0. Salir");
		System.out.print("Elige una opción: ");
	}

	// Muestra el submenú específico para gestión de clientes
	public void mostrarSubmenuClientes() {
		System.out.println("\nGestión de Clientes\n");
		System.out.println("| 1. Añadir nuevo cliente");
		System.out.println("| 2. Consultar cliente por ID");
		System.out.println("| 3. Ver todos los clientes");
		System.out.println("| 4. Actualizar cliente");
		System.out.println("| 5. Eliminar cliente");
		System.out.println("| 0. Volver");
		System.out.print("Opción: ");
	}

	// Solicita y valida los datos de un cliente (nombre, email, teléfono)
	public Cliente pedirDatosCliente() {
		String nombre;
		String email;
		String telefono;

		// Validar que el nombre no esté vacío
		do {
			System.out.print("Nombre: ");
			nombre = scanner.nextLine().trim();
			if (nombre.isEmpty()) {
				System.out.println("El nombre no puede estar vacío.");
			}
		} while (nombre.isEmpty());

		// Validar el email con expresión regular básica
		do {
			System.out.print("Email: ");
			email = scanner.nextLine().trim();
			if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
				System.out.println("El email es inválido.");
			}
		} while (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"));

		// Validar formato del teléfono
		do {
			System.out.print("Teléfono: ");
			telefono = scanner.nextLine().trim();
			if (!telefono.matches("[\\d\\-+() ]{7,}")) {
				System.out.println("El teléfono es inválido.");
			}
		} while (!telefono.matches("[\\d\\-+() ]{7,}"));

		return new Cliente(nombre, email, telefono);
	}

	// Solicita los datos de un nuevo producto (nombre, categoría, precio, stock)
	@Override
	public ProductoOtaku pedirDatosNuevoProducto() {
		System.out.print("Nombre: ");
		String nombre = scanner.nextLine().trim();

		System.out.print("Categoría (ej. Figura, Manga, Póster...): ");
		String categoria = scanner.nextLine().trim();

		System.out.print("Precio: ");
		double precio = leerDouble();

		System.out.print("Stock: ");
		int stock = leerEntero();

		return new ProductoOtaku(0, nombre, categoria, precio, stock);
	}

	// Solicita un ID de producto
	@Override
	public int pedirIdProducto() {
		System.out.print("Introduce el ID del producto: ");
		return leerEntero();
	}

	// Solicita un ID de cliente
	@Override
	public int pedirIdCliente() {
		System.out.print("Introduce el ID del cliente: ");
		return leerEntero();
	}

	// Solicita un nombre (o parte) de producto para búsqueda
	@Override
	public String pedirNombreProducto() {
		System.out.print("Introduce el nombre o parte del nombre a buscar: ");
		return scanner.nextLine().trim();
	}

	// Muestra una lista de productos en consola
	@Override
	public void mostrarListaProductos(List<ProductoOtaku> productos) {
		if (productos.isEmpty()) {
			System.out.println("No hay productos para mostrar.");
		} else {
			System.out.println("\n--- INVENTARIO ---");
			productos.forEach(System.out::println);
		}
	}

	// Muestra un mensaje cualquiera en consola
	@Override
	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}

	// Lee la opción seleccionada del menú (entero)
	@Override
	public int leerOpcionMenu() {
		return leerEntero();
	}

	// Lee un número entero de forma segura
	private int leerEntero() {
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.print("Número inválido. Inténtalo de nuevo: ");
			}
		}
	}

	// Lee un número decimal de forma segura (permite coma o punto)
	private double leerDouble() {
		while (true) {
			try {
				return Double.parseDouble(scanner.nextLine().replace(",", ".").trim());
			} catch (NumberFormatException e) {
				System.out.print("Número inválido. Inténtalo de nuevo: ");
			}
		}
	}

	// Solicita un nombre para generar sugerencias de categoría con IA
	public String pedirNombreParaSugerencia() {
		System.out.print("Introduce el nombre del producto para sugerir una categoría en español: ");
		return scanner.nextLine().trim();
	}
}
