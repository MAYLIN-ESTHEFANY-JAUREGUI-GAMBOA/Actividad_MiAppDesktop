// Paquete del repositorio en memoria
package vallegrande.edu.pe.model;


import java.io.BufferedReader;       // para leer archivo
import java.io.BufferedWriter;       // para escribir archivo
import java.io.IOException;          // excepción IO
import java.nio.charset.StandardCharsets; // codificación
import java.nio.file.Files;          // utilidades de ficheros
import java.nio.file.Path;           // representación de rutas
import java.nio.file.Paths;          // creación de rutas
import java.util.ArrayList;          // implementación de lista
import java.util.List;               // interfaz List


// Repositorio que guarda estudiantes en memoria y persiste en CSV sencillo
public class InMemoryEstudianteRepository {


    // Lista que contiene los estudiantes en memoria
    private final List<Estudiante> estudiantes = new ArrayList<>();


    // Ruta al archivo donde se guardarán los datos (carpeta "data/estudiantes.csv")
    private final Path filePath = Paths.get("data", "estudiantes.csv");


    // Delimitador simple para el CSV (evitamos coma para reducir problemas)
    private static final String DEL = ";";


    // Constructor: inicializa la lista y carga datos desde disco si existen
    public InMemoryEstudianteRepository() {
        // Carga desde archivo al crear el repositorio
        loadFromFile();
        // Si por alguna razón la lista está vacía, agregamos ejemplos iniciales
        if (estudiantes.isEmpty()) {
            estudiantes.add(new Estudiante("Juan", "juan@correo.com", "Programación"));
            estudiantes.add(new Estudiante("Ana", "ana@correo.com", "Redes"));
            // Guardamos los datos iniciales en disco
            saveToFile();
        }
    }


    // Retorna una copia de la lista de estudiantes (para evitar modificaciones externas directas)
    public List<Estudiante> findAll() {
        return new ArrayList<>(estudiantes);
    }


    // Agrega un estudiante a la lista
    public void add(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }


    // Actualiza un estudiante en la posición indicada
    public void update(int index, Estudiante estudiante) {
        if (index >= 0 && index < estudiantes.size()) {
            estudiantes.set(index, estudiante);
        }
    }


    // Elimina un estudiante por índice
    public void remove(int index) {
        if (index >= 0 && index < estudiantes.size()) {
            estudiantes.remove(index);
        }
    }


    // Guarda la lista de estudiantes en un archivo CSV simple
    public void saveToFile() {
        try {
            // Asegura que la carpeta exista
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            // Escribe el archivo usando un BufferedWriter
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                // Itera cada estudiante y escribe una línea: nombre;correo;curso
                for (Estudiante e : estudiantes) {
                    String line = escape(e.getNombre()) + DEL + escape(e.getCorreo()) + DEL + escape(e.getCurso());
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException ex) {
            // Si hay error, lo imprimimos en consola (para depuración)
            System.err.println("Error guardando estudiantes: " + ex.getMessage());
        }
    }


    // Carga los estudiantes desde el archivo CSV, si existe
    private void loadFromFile() {
        // Si el archivo no existe, no hacemos nada
        if (!Files.exists(filePath)) {
            return;
        }
        // Intentamos leer el archivo
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line; // variable para cada línea
            while ((line = reader.readLine()) != null) {
                // Saltar líneas vacías
                if (line.trim().isEmpty()) continue;
                // Partimos la línea por el delimitador
                String[] parts = line.split(DEL, -1);
                // Si la línea tiene al menos 3 partes, creamos el estudiante
                if (parts.length >= 3) {
                    String nombre = unescape(parts[0]);
                    String correo = unescape(parts[1]);
                    String curso = unescape(parts[2]);
                    estudiantes.add(new Estudiante(nombre, correo, curso));
                }
            }
        } catch (IOException ex) {
            // En caso de error lo informamos en consola
            System.err.println("Error cargando estudiantes: " + ex.getMessage());
        }
    }


    // Escapa secuencias simples (reemplaza saltos de línea por espacio para la exportación)
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\n", " ").replace("\r", " ");
    }


    // Reversa de escape (por si requiere transformaciones futuras)
    private String unescape(String s) {
        return s == null ? "" : s;
    }
}

