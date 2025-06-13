/**
 * Panel para la interfaz gráfica de la tienda.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import model.ProductoOtaku;
import dao.ProductoDAO;
import service.LlmService;

public class ProductosPanel extends JPanel {
    // Acceso a la base de datos de productos
    private ProductoDAO productoDAO;
    // Servicio de IA para generar descripciones o sugerencias
    private LlmService ia;

    // Componentes de la tabla
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    // Campos del formulario
    private JTextField campoNombre, campoCategoria, campoPrecio, campoStock;

    // Botones para acciones
    private JButton btnAgregar, btnActualizar, btnEliminar, btnDescripcionIA, btnSugerirCategoria;

    // Constructor del panel
    public ProductosPanel() {
        productoDAO = new ProductoDAO(); // DAO para interactuar con MySQL
        ia = new LlmService(); // Servicio de lenguaje natural
        setLayout(new BorderLayout()); // Diseño principal

        // Inicializa la tabla y modelo
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}, 0
        );
        tablaProductos = new JTable(modeloTabla);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        // Panel con formulario para añadir/editar productos
        JPanel formulario = new JPanel(new GridLayout(2, 5));

        campoNombre = new JTextField();
        campoCategoria = new JTextField();
        campoPrecio = new JTextField();
        campoStock = new JTextField();

        // Añadir campos y etiquetas al formulario
        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Categoría:"));
        formulario.add(campoCategoria);
        formulario.add(new JLabel("Precio:"));
        formulario.add(campoPrecio);
        formulario.add(new JLabel("Stock:"));
        formulario.add(campoStock);

        // Inicialización de botones
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnDescripcionIA = new JButton("Generar Descripción IA");
        btnSugerirCategoria = new JButton("Sugerir Categoría IA");

        // Añadir botones al formulario
        formulario.add(btnAgregar);
        formulario.add(btnActualizar);
        formulario.add(btnEliminar);
        formulario.add(btnDescripcionIA);
        formulario.add(btnSugerirCategoria);

        // Añadir formulario al sur del panel principal
        add(formulario, BorderLayout.SOUTH);

        // === Acciones de los botones ===

        // Agrega un nuevo producto
        btnAgregar.addActionListener(e -> agregarProducto());

        // Actualiza el producto seleccionado
        btnActualizar.addActionListener(e -> actualizarProducto());

        // Elimina el producto seleccionado
        btnEliminar.addActionListener(e -> eliminarProducto());

        // Usa IA para generar descripción de producto
        btnDescripcionIA.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            String categoria = campoCategoria.getText().trim();

            if (nombre.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y categoría son necesarios.");
                return;
            }

            String prompt = "Genera una descripción atractiva para el producto otaku: \"" + nombre + 
                            "\" de la categoría \"" + categoria + "\".";
            String respuesta = ia.enviarPrompt(prompt);
            JOptionPane.showMessageDialog(this, "Descripción generada:\n\n" + respuesta);
        });

        // Usa IA para sugerir una categoría basada en el nombre
        btnSugerirCategoria.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce el nombre del producto.");
                return;
            }

            String prompt = "Para un producto otaku llamado \"" + nombre + 
                            "\", sugiere una categoría adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
            String respuesta = ia.enviarPrompt(prompt);
            campoCategoria.setText(respuesta);
            JOptionPane.showMessageDialog(this, "Categoría sugerida:\n" + respuesta);
        });

        // Al hacer clic en la tabla, carga los datos del producto en los campos
        tablaProductos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaProductos.getSelectedRow();
                campoNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                campoCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
                campoPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());
                campoStock.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });

        // Carga inicial de datos al iniciar el panel
        cargarProductos();
    }

    // Carga todos los productos desde la base de datos
    private void cargarProductos() {
        modeloTabla.setRowCount(0); // Limpia tabla
        List<ProductoOtaku> productos = productoDAO.obtenerTodosLosProductos();
        for (ProductoOtaku p : productos) {
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()
            });
        }
    }

    // Agrega un nuevo producto a la base de datos
    private void agregarProducto() {
        try {
            String nombre = campoNombre.getText().trim();
            String categoria = campoCategoria.getText().trim();
            double precio = Double.parseDouble(campoPrecio.getText().trim());
            int stock = Integer.parseInt(campoStock.getText().trim());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }

            ProductoDAO.agregarProducto(new ProductoOtaku(0, nombre, categoria, precio, stock));
            cargarProductos();
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser valores numéricos.");
        }
    }

    // Actualiza el producto seleccionado
    private void actualizarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para actualizar.");
            return;
        }

        try {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            String nombre = campoNombre.getText().trim();
            String categoria = campoCategoria.getText().trim();
            double precio = Double.parseDouble(campoPrecio.getText().trim());
            int stock = Integer.parseInt(campoStock.getText().trim());

            productoDAO.actualizarProducto(new ProductoOtaku(id, nombre, categoria, precio, stock));
            cargarProductos();
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser numéricos.");
        }
    }

    // Elimina el producto seleccionado
    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        productoDAO.eliminarProducto(id);
        cargarProductos();
        limpiarCampos();
    }

    // Limpia los campos del formulario
    private void limpiarCampos() {
        campoNombre.setText("");
        campoCategoria.setText("");
        campoPrecio.setText("");
        campoStock.setText("");
    }
}
