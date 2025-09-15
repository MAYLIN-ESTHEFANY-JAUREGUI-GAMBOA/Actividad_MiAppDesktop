// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.model;

// Importa las clases necesarias para usar listas dinámicas
import java.util.ArrayList;
import java.util.List;

// Definición de la clase InMemoryUsuarioRepository que simula un repositorio en memoria
public class InMemoryUsuarioRepository {

    // Lista privada que almacena los usuarios en memoria
    private final List<Usuario> usuarios = new ArrayList<>();

    // Constructor de la clase
    public InMemoryUsuarioRepository() {
        // Agrega usuarios de ejemplo al repositorio
        usuarios.add(new Usuario("Ana López", "ana@instituto.edu", "Estudiante"));
        usuarios.add(new Usuario("Carlos Pérez", "carlos@instituto.edu", "Docente"));
    }

    // Método que retorna todos los usuarios almacenados
    public List<Usuario> findAll() {
        return usuarios;
    }

    // Método para agregar un nuevo usuario al repositorio
    public void add(Usuario usuario) {
        usuarios.add(usuario);
    }

    // Método para eliminar un usuario del repositorio según su índice
    public void remove(int index) {
        usuarios.remove(index);
    }
}

