// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.controller;

// Importa la clase Usuario del paquete model
import vallegrande.edu.pe.model.Usuario;
import vallegrande.edu.pe.service.UsuarioPersistenceService;

// Importa las clases necesarias para usar listas dinámicas
import java.util.ArrayList;
import java.util.List;

// Definición de la clase UsuarioController que maneja la lógica de usuarios
public class UsuarioController {

    // Lista privada que almacena todos los usuarios
    private final List<Usuario> usuarios;
    
    // Servicio de persistencia para guardar/cargar datos
    private final UsuarioPersistenceService persistenceService;

    // Constructor de la clase
    public UsuarioController() {
        // Inicializa el servicio de persistencia
        persistenceService = new UsuarioPersistenceService();
        
        // Intenta cargar usuarios desde archivo
        usuarios = new ArrayList<>(persistenceService.cargarUsuarios());
        
        // Si no hay usuarios cargados, agrega datos de ejemplo
        if (usuarios.isEmpty()) {
            usuarios.add(new Usuario("Valery Chumpitaz", "valery@correo.com", "Administrador"));
            usuarios.add(new Usuario("Juan Pérez", "juan@correo.com", "Docente"));
            usuarios.add(new Usuario("María López", "maria@correo.com", "Estudiante"));
            // Guarda los datos de ejemplo
            persistenceService.guardarUsuarios(usuarios);
        }
    }

    // Método que retorna la lista completa de usuarios
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    // Método para agregar un nuevo usuario a la lista
    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        persistenceService.guardarUsuarios(usuarios); // Guarda automáticamente
    }

    // Método para eliminar un usuario de la lista según su índice
    public void deleteUsuario(int index) {
        // Verifica que el índice sea válido antes de eliminar
        if (index >= 0 && index < usuarios.size()) {
            usuarios.remove(index);
            persistenceService.guardarUsuarios(usuarios); // Guarda automáticamente
        }
    }

    // Método para actualizar un usuario existente en la lista según su índice
    public void updateUsuario(int index, Usuario usuario) {
        // Verifica que el índice sea válido antes de actualizar
        if (index >= 0 && index < usuarios.size()) {
            usuarios.set(index, usuario);
            persistenceService.guardarUsuarios(usuarios); // Guarda automáticamente
        }
    }
}

