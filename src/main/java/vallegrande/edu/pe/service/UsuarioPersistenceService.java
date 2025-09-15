package vallegrande.edu.pe.service;

import vallegrande.edu.pe.model.Usuario;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para manejar la persistencia de usuarios en archivo CSV
 */
public class UsuarioPersistenceService {
    
    private static final String ARCHIVO_USUARIOS = "usuarios.csv";
    private static final String SEPARADOR = ",";
    private static final String HEADER = "Nombre,Correo,Rol";
    
    /**
     * Guarda la lista de usuarios en un archivo CSV
     * @param usuarios Lista de usuarios a guardar
     */
    public void guardarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            // Escribir encabezado
            writer.println(HEADER);
            
            // Escribir cada usuario
            for (Usuario usuario : usuarios) {
                String linea = escaparCSV(usuario.getNombre()) + SEPARADOR +
                              escaparCSV(usuario.getCorreo()) + SEPARADOR +
                              escaparCSV(usuario.getRol());
                writer.println(linea);
            }
            
            System.out.println("Usuarios guardados exitosamente en " + ARCHIVO_USUARIOS);
            
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
    
    /**
     * Carga la lista de usuarios desde un archivo CSV
     * @return Lista de usuarios cargados desde el archivo
     */
    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        
        // Verificar si el archivo existe
        if (!Files.exists(Paths.get(ARCHIVO_USUARIOS))) {
            System.out.println("Archivo " + ARCHIVO_USUARIOS + " no existe. Iniciando con datos por defecto.");
            return usuarios; // Retorna lista vacía
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            boolean primeraLinea = true;
            
            while ((linea = reader.readLine()) != null) {
                // Saltar el encabezado
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                
                // Procesar línea de datos
                String[] campos = parsearLineaCSV(linea);
                if (campos.length >= 3) {
                    String nombre = campos[0].trim();
                    String correo = campos[1].trim();
                    String rol = campos[2].trim();
                    
                    usuarios.add(new Usuario(nombre, correo, rol));
                }
            }
            
            System.out.println("Usuarios cargados exitosamente desde " + ARCHIVO_USUARIOS + ". Total: " + usuarios.size());
            
        } catch (IOException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    /**
     * Escapa caracteres especiales para formato CSV
     * @param texto Texto a escapar
     * @return Texto escapado para CSV
     */
    private String escaparCSV(String texto) {
        if (texto == null) return "";
        
        // Si contiene coma, comilla o salto de línea, envolver en comillas
        if (texto.contains(",") || texto.contains("\"") || texto.contains("\n")) {
            // Escapar comillas duplicándolas
            texto = texto.replace("\"", "\"\"");
            return "\"" + texto + "\"";
        }
        
        return texto;
    }
    
    /**
     * Parsea una línea CSV considerando comillas y comas dentro de campos
     * @param linea Línea CSV a parsear
     * @return Array de campos
     */
    private String[] parsearLineaCSV(String linea) {
        List<String> campos = new ArrayList<>();
        StringBuilder campoActual = new StringBuilder();
        boolean dentroComillas = false;
        
        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);
            
            if (c == '"') {
                if (dentroComillas && i + 1 < linea.length() && linea.charAt(i + 1) == '"') {
                    // Comilla escapada
                    campoActual.append('"');
                    i++; // Saltar la siguiente comilla
                } else {
                    // Cambiar estado de comillas
                    dentroComillas = !dentroComillas;
                }
            } else if (c == ',' && !dentroComillas) {
                // Separador de campo
                campos.add(campoActual.toString());
                campoActual = new StringBuilder();
            } else {
                campoActual.append(c);
            }
        }
        
        // Agregar el último campo
        campos.add(campoActual.toString());
        
        return campos.toArray(new String[0]);
    }
}
