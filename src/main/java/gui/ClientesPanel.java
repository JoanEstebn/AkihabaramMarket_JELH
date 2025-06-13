/**
 * Panel para la pantalla de clientes del .
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import model.Cliente;
import dao.ClienteDAO;
import service.LlmService;

public class ClientesPanel extends JPanel {
    // DAO para operaciones con la base de datos
    private ClienteDAO clienteDAO;
    // Servicio de IA para generar mensajes con LLM
    private LlmService ia;

    // Componentes de la interfaz
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTextField campoNombre, campoEmail, campoTelefono;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnMensajeIA;

    // Constructor de la clase
    public ClientesPanel() {
        clienteDAO = new ClienteDAO(); // Inicializa acceso a datos
        ia = new LlmService(); // Inicializa servicio IA
        setLayout(new BorderLayout()); // Usa BorderLayout como diseño principal

        // Define la tabla con encabezados
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Teléfono", "Fecha Registro"}, 0);
        tablaClientes = new JTable(modeloTabla);
        add(new JScrollPane(tablaClientes), BorderLayout.CENTER); // Añade la tabla al centro

        // Panel para el formulario de entrada y botones
        JPanel formulario = new JPanel(new GridLayout(2, 4));

        // Campos de texto para datos del cliente
        campoNombre = new JTextField();
        campoEmail = new JTextField();
        campoTelefono = new JTextField();

        // Añade etiquetas y campos al formulario
        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Email:"));
        formulario.add(campoEmail);
        formulario.add(new JLabel("Teléfono:"));
        formulario.add(campoTelefono);

        // Botones de acción
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnMensajeIA = new JButton("Mensaje Bienvenida IA");

        // Añade botones al formulario
        formulario.add(btnAgregar);
        formulario.add(btnActualizar);
        formulario.add(btnEliminar);
        formulario.add(btnMensajeIA);

        // Añade el formulario a la parte inferior del panel
        add(formulario, BorderLayout.SOUTH);

        // Define acciones para cada botón
        btnAgregar.addActionListener(e -> agregarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnMensajeIA.addActionListener(e -> generarMensajeIA());

        // Cuando se hace clic en una fila de la tabla, se rellenan los campos con los datos del cliente
        tablaClientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaClientes.getSelectedRow();
                campoNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                campoEmail.setText(modeloTabla.getValueAt(fila, 2).toString());
                campoTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
            }
        });

        // Carga inicial de clientes desde la base de datos
        cargarClientes();
    }

    // Carga todos los clientes de la base de datos en la tabla
    private void cargarClientes() {
        modeloTabla.setRowCount(0); // Limpia la tabla
        List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
        for (Cliente c : clientes) {
            modeloTabla.addRow(new Object[]{
                c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(), c.getFechaRegistro()
            });
        }
    }

    // Añade un nuevo cliente a la base de datos
    private void agregarCliente() {
        String nombre = campoNombre.getText().trim();
        String email = campoEmail.getText().trim();
        String telefono = campoTelefono.getText().trim();

        if (nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y email son obligatorios.");
            return;
        }

        clienteDAO.agregarCliente(new Cliente(nombre, email, telefono));
        cargarClientes(); // Recarga la tabla
        limpiarCampos();
    }

    // Actualiza un cliente seleccionado
    private void actualizarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para actualizar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = campoNombre.getText().trim();
        String email = campoEmail.getText().trim();
        String telefono = campoTelefono.getText().trim();

        ClienteDAO.actualizarCliente(new Cliente(id, nombre, email, telefono, null));
        cargarClientes();
        limpiarCampos();
    }

    // Elimina el cliente seleccionado
    private void eliminarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para eliminar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        clienteDAO.eliminarCliente(id);
        cargarClientes();
        limpiarCampos();
    }

    // Genera un mensaje de bienvenida usando el modelo LLM
    private void generarMensajeIA() {
        String nombre = campoNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduce un nombre para generar el mensaje.");
            return;
        }

        String prompt = "Genera un mensaje de bienvenida para un nuevo cliente llamado " + nombre + ".";
        String respuesta = ia.enviarPrompt(prompt);
        JOptionPane.showMessageDialog(this, "Mensaje generado:\n\n" + respuesta);
    }

    // Limpia los campos del formulario
    private void limpiarCampos() {
        campoNombre.setText("");
        campoEmail.setText("");
        campoTelefono.setText("");
    }
}
