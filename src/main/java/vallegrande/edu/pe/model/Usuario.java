// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.model;

// Definición de la clase Usuario que representa a un usuario del sistema
public class Usuario {

    // Atributo que almacena el nombre completo del usuario
    private String nombre;

    // Atributo que almacena el correo electrónico del usuario
    private String correo;

    // Atributo que almacena el rol del usuario (Administrador, Docente, Estudiante, etc.)
    private String rol;

    // Constructor de la clase Usuario que inicializa todos los atributos
    public Usuario(String nombre, String correo, String rol) {
        this.nombre = nombre;  // Asigna el nombre recibido al atributo
        this.correo = correo;  // Asigna el correo recibido al atributo
        this.rol = rol;        // Asigna el rol recibido al atributo
    }

    // ===========================
    // Getters y Setters
    // ===========================

    // Retorna el nombre del usuario
    public String getNombre() {
        return nombre;
    }

    // Establece un nuevo nombre para el usuario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Retorna el correo del usuario
    public String getCorreo() {
        return correo;
    }

    // Establece un nuevo correo para el usuario
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // Retorna el rol del usuario
    public String getRol() {
        return rol;
    }

    // Establece un nuevo rol para el usuario
    public void setRol(String rol) {
        this.rol = rol;
    }
}

