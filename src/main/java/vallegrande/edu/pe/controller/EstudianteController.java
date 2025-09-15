// Paquete del controlador
package vallegrande.edu.pe.controller;


// Importa el modelo Estudiante y el repositorio en memoria
import vallegrande.edu.pe.model.Estudiante;
import vallegrande.edu.pe.model.InMemoryEstudianteRepository;


import java.util.List; // para devolver listas


// Controlador que actúa como capa intermedia entre la vista y el repositorio
public class EstudianteController {


    // Repositorio interno (en memoria + persistencia simple)
    private final InMemoryEstudianteRepository repository;


    // Constructor que crea el repositorio
    public EstudianteController() {
        this.repository = new InMemoryEstudianteRepository(); // inicializa y carga datos desde archivo
    }


    // Retorna la lista completa de estudiantes
    public List<Estudiante> listarEstudiantes() {
        return repository.findAll();
    }


    // Agrega un estudiante a partir de tres cadenas (nombre, correo, curso)
    public void addEstudiante(String nombre, String correo, String curso) {
        // Crea el objeto y lo añade al repositorio
        repository.add(new Estudiante(nombre, correo, curso));
        // Después de modificar, guarda en disco para persistencia
        repository.saveToFile();
    }


    // Actualiza un estudiante existente por índice
    public void updateEstudiante(int index, Estudiante estudiante) {
        repository.update(index, estudiante); // actualiza en memoria
        repository.saveToFile();              // guarda cambios
    }


    // Elimina un estudiante por índice
    public void deleteEstudiante(int index) {
        repository.remove(index); // remueve en memoria
        repository.saveToFile();  // guarda cambios
    }


    // Comprueba si existe un estudiante con el mismo correo (evitar duplicados)
    public boolean existeCorreo(String correo) {
        // Recorre la lista y compara por correo (ignora mayúsculas)
        for (Estudiante e : repository.findAll()) {
            if (e.getCorreo().equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
    }
}
