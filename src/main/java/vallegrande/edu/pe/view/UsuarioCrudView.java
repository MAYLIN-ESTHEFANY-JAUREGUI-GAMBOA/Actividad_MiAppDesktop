// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.view;


// Importa el controlador de usuarios y la clase Usuario
import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.model.Usuario;


// Importa clases de Swing y AWT necesarias para la interfaz gráfica y tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


// Clase que representa la ventana CRUD de usuarios
public class UsuarioCrudView extends JFrame {


    // Controlador de usuarios para manejar la lógica
    private final UsuarioController controller;


    // Modelo de tabla para mostrar los datos de los usuarios
    private final DefaultTableModel tableModel;
    
    // Campo de texto para búsqueda
    private JTextField searchField;


    // Constructor que recibe el controlador
    public UsuarioCrudView(UsuarioController controller) {
        this.controller = controller;  // Asigna el controlador recibido


        // Configuración básica de la ventana
        setTitle("Gestión de Usuarios");                  // Título de la ventana
        setSize(700, 400);                                // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Al cerrar, solo se cierra esta ventana
        setLocationRelativeTo(null);                       // Centra la ventana en la pantalla


        // Modelo de tabla con columnas: Nombre, Correo, Rol
        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Correo", "Rol"}, 0);


        // Tabla que mostrará los usuarios
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);                             // Altura de cada fila
        table.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Fuente de las celdas

        // Panel superior con campo de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel searchLabel = new JLabel("🔍 Buscar por nombre o correo:");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(300, 30));
        
        // Listener para filtrar en tiempo real mientras se escribe
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarUsuarios();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarUsuarios();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarUsuarios();
            }
        });
        
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);              // Añade la tabla con scroll al centro


        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("➕ Agregar");      // Botón para agregar usuario
        JButton editButton = new JButton("✏️ Editar");      // Botón para editar usuario
        JButton deleteButton = new JButton("🗑 Eliminar");  // Botón para eliminar usuario


        // Acción del botón Agregar
        addButton.addActionListener(e -> {
            // Solicita los datos del nuevo usuario mediante diálogos
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            String correo = JOptionPane.showInputDialog(this, "Correo:");
            String rol = JOptionPane.showInputDialog(this, "Rol (Administrador/Docente/Estudiante):");
            // Si los datos no son nulos y pasan las validaciones, agrega el usuario y recarga la tabla
            if (nombre != null && correo != null && rol != null) {
                if (validarDatosUsuario(nombre, correo, rol)) {
                    controller.addUsuario(new Usuario(nombre.trim(), correo.trim(), rol.trim()));
                    cargarUsuarios();
                    JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        // Acción del botón Editar
        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();                     // Obtiene la fila seleccionada
            if (row >= 0) {                                       // Verifica que haya una fila seleccionada
                // Obtiene los datos actuales del usuario seleccionado
                Usuario usuarioActual = controller.getUsuarios().get(row);
                
                // Solicita los nuevos datos mediante diálogos, mostrando los valores actuales
                String nombre = JOptionPane.showInputDialog(this, "Nombre:", usuarioActual.getNombre());
                String correo = JOptionPane.showInputDialog(this, "Correo:", usuarioActual.getCorreo());
                String rol = JOptionPane.showInputDialog(this, "Rol (Administrador/Docente/Estudiante):", usuarioActual.getRol());
                
                // Si los datos no son nulos y pasan las validaciones, actualiza el usuario y recarga la tabla
                if (nombre != null && correo != null && rol != null) {
                    if (validarDatosUsuario(nombre, correo, rol)) {
                        controller.updateUsuario(row, new Usuario(nombre.trim(), correo.trim(), rol.trim()));
                        cargarUsuarios();
                        JOptionPane.showMessageDialog(this, "Usuario editado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario para editar.", "Ningún usuario seleccionado", JOptionPane.WARNING_MESSAGE);
            }
        });


        // Acción del botón Eliminar
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();                 // Obtiene la fila seleccionada
            if (row >= 0) {                                   // Verifica que haya una fila seleccionada
                controller.deleteUsuario(row);               // Elimina el usuario correspondiente
                cargarUsuarios();                             // Recarga la tabla
            }
        });


        // Añade los botones al panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);               // Coloca el panel de botones al sur


        // Carga inicialmente los usuarios en la tabla
        cargarUsuarios();
    }


    // Método que carga todos los usuarios del controlador en la tabla
    private void cargarUsuarios() {
        tableModel.setRowCount(0);                          // Limpia la tabla
        for (Usuario u : controller.getUsuarios()) {        // Recorre cada usuario
            tableModel.addRow(new Object[]{u.getNombre(), u.getCorreo(), u.getRol()}); // Agrega fila
        }
    }

    // Método para filtrar usuarios según el texto de búsqueda
    private void filtrarUsuarios() {
        String textoBusqueda = searchField.getText().toLowerCase().trim(); // Obtiene el texto de búsqueda en minúsculas
        tableModel.setRowCount(0);                          // Limpia la tabla
        
        for (Usuario u : controller.getUsuarios()) {        // Recorre cada usuario
            // Verifica si el nombre o correo contienen el texto de búsqueda
            boolean coincideNombre = u.getNombre().toLowerCase().contains(textoBusqueda);
            boolean coincideCorreo = u.getCorreo().toLowerCase().contains(textoBusqueda);
            
            // Si hay coincidencia en nombre o correo, agrega la fila
            if (coincideNombre || coincideCorreo) {
                tableModel.addRow(new Object[]{u.getNombre(), u.getCorreo(), u.getRol()});
            }
        }
    }

    // Método para validar que los campos no estén vacíos
    private boolean validarCamposVacios(String nombre, String correo, String rol) {
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Nombre' no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (correo == null || correo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Correo' no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (rol == null || rol.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Rol' no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Método para validar el formato básico del correo electrónico
    private boolean validarFormatoCorreo(String correo) {
        if (!correo.contains("@") || !correo.contains(".")) {
            JOptionPane.showMessageDialog(this, "El correo debe tener un formato válido (debe contener '@' y '.').", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Validación adicional: @ no puede estar al inicio o final, y debe haber al menos un carácter antes del punto después del @
        int atIndex = correo.indexOf("@");
        int lastDotIndex = correo.lastIndexOf(".");
        if (atIndex == 0 || atIndex == correo.length() - 1 || lastDotIndex <= atIndex + 1 || lastDotIndex == correo.length() - 1) {
            JOptionPane.showMessageDialog(this, "El correo debe tener un formato válido (ej: usuario@dominio.com).", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Método para validar el rol
    private boolean validarRol(String rol) {
        String rolLower = rol.trim().toLowerCase();
        if (!rolLower.equals("administrador") && !rolLower.equals("docente") && !rolLower.equals("estudiante")) {
            JOptionPane.showMessageDialog(this, "El rol debe ser: Administrador, Docente o Estudiante.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Método completo de validación
    private boolean validarDatosUsuario(String nombre, String correo, String rol) {
        return validarCamposVacios(nombre, correo, rol) && 
               validarFormatoCorreo(correo) && 
               validarRol(rol);
    }
}




