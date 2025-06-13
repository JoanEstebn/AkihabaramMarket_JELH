/**
 * El centro de actividades de la interfaz gráfica.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package gui;

import javax.swing.*;

public class AkihabaraGUIjelh extends JFrame {

    public AkihabaraGUIjelh() {
        setTitle("Akihabara Market - Gestión de Inventario");
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear pestañas y añadir paneles
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Productos", new ProductosPanel());
        pestañas.addTab("Clientes", new ClientesPanel());  

        add(pestañas); // agregar al JFrame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AkihabaraGUIjelh().setVisible(true);
        });
    }
}
