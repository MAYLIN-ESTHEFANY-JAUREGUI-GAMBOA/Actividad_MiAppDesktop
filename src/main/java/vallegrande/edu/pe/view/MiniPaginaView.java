// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.view;

// Importa el controlador de usuarios para manejar la lógica
import vallegrande.edu.pe.controller.EstudianteController;
import vallegrande.edu.pe.controller.UsuarioController;

// Importa clases de Swing y AWT necesarias para la interfaz gráfica
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

// Clase que representa la ventana principal tipo "mini página web" del instituto
public class MiniPaginaView extends JFrame {

    // Controlador de usuarios para gestionar acciones relacionadas con usuarios
    private final UsuarioController controller;

    // Constructor que recibe el controlador de usuarios
    public MiniPaginaView(UsuarioController controller) {
        this.controller = controller;   // Asigna el controlador recibido
        initUI();                        // Inicializa la interfaz gráfica
    }



    // Método que construye toda la interfaz de usuario
    private void initUI() {
        // Configuración básica de la ventana
        setTitle("Instituto Valle Grande - Portal Principal"); // Título de la ventana
        setSize(980, 600);                                     // Tamaño inicial de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Cierra la aplicación al cerrar la ventana
        setLocationRelativeTo(null);                          // Centra la ventana en la pantalla
        setLayout(new BorderLayout());                        // Layout principal con zonas norte, sur, este, oeste y centro

        // ===============================
        // Encabezado con pequeño logotipo y título
        // ===============================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(18, 33, 44)); // mismo color del fondo principal (sin azul)
        header.setPreferredSize(new Dimension(getWidth(), 110));
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        // "Logotipo" simple (escudo) con texto del instituto a la izquierda
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brandPanel.setOpaque(false);
        JLabel logo = crearLogoBadge();
        JLabel brand = new JLabel("<html><div style='color:#e0ecf2; font-weight:800; font-size:14px; line-height:1.05;'>" +
                "Instituto<br>Valle Grande" +
                "</div></html>");
        brand.setForeground(new Color(224, 236, 242));
        brand.setFont(new Font("SansSerif", Font.BOLD, 14));
        brandPanel.add(logo);
        brandPanel.add(brand);
        header.add(brandPanel, BorderLayout.WEST);

        // Solo el logo y marca en el header, sin título principal

        add(header, BorderLayout.NORTH);

        // ===============================
        // Panel central
        // ===============================
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(18, 33, 44)); // fondo base oscuro para contraste sutil
        centerPanel.setBorder(new EmptyBorder(24, 24, 24, 24));

        // -------------------------------
        // Título principal y texto informativo
        // -------------------------------
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        
        // Título principal en la parte superior
        JLabel mainTitle = new JLabel("Bienvenido al Portal del Instituto Valle Grande", SwingConstants.CENTER);
        mainTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        mainTitle.setForeground(new Color(235, 245, 250));
        mainTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Texto informativo debajo del título
        JLabel infoLabel = new JLabel("<html><div style='text-align:center;'>" +
                "<div style='font-size:18px; font-weight:700; color:#dbe7ee;'>Gestión Académica y Administrativa</div>" +
                "<div style='margin-top:8px; color:#c4d2da;'>Administre usuarios, docentes y estudiantes desde un mismo lugar</div>" +
                "<div style='color:#c4d2da;'>Sistema institucional moderno y seguro</div>" +
                "</div></html>", SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        infoPanel.add(mainTitle, BorderLayout.NORTH);
        infoPanel.add(infoLabel, BorderLayout.CENTER);

        // -------------------------------
        // Panel de botones tipo menú
        // -------------------------------
        JPanel menuPanel = new JPanel(new GridLayout(1, 3, 24, 24));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(new EmptyBorder(28, 64, 0, 64));

        // Botones tipo "tarjeta"
        JButton btnUsuarios = crearBotonMenu(
                "<html><div style='text-align:center;'><div style='font-size:42px; margin-bottom:6px;'>\uD83D\uDC64</div><div>Administrar Usuarios</div></div></html>",
                new Color(39, 63, 78));
        btnUsuarios.addActionListener(e -> new UsuarioCrudView(controller).setVisible(true));

        JButton btnDocentes = crearBotonMenu(
                "<html><div style='text-align:center;'><div style='font-size:42px; margin-bottom:6px;'>\uD83D\uDC68\u200D\uD83C\uDF93</div><div>Administrar Docentes</div></div></html>",
                new Color(39, 63, 78));

        JButton btnEstudiantes = crearBotonMenu(
                "<html><div style='text-align:center;'><div style='font-size:42px; margin-bottom:6px;'>\uD83C\uDF93</div><div>Administrar Estudiantes</div></div></html>",
                new Color(32, 122, 102));
        btnEstudiantes.addActionListener(e -> new EstudianteCrudView(new EstudianteController()).setVisible(true));


        // Añade los botones al panel de menú
        menuPanel.add(btnUsuarios);
        menuPanel.add(btnDocentes);
        menuPanel.add(btnEstudiantes);

        // Añade los subpaneles al panel central
        centerPanel.add(infoPanel, BorderLayout.NORTH);        // Texto informativo en la parte superior
        centerPanel.add(menuPanel, BorderLayout.CENTER);       // Botones al centro
        add(centerPanel, BorderLayout.CENTER);                // Añade el panel central a la ventana

        // ===============================
        // Footer
        // ===============================
        JPanel footer = new JPanel();
        footer.setBackground(new Color(18, 33, 44));
        JLabel lblFooter = new JLabel("© 2025 Instituto Valle Grande - Todos los derechos reservados");
        lblFooter.setForeground(new Color(160, 176, 186));
        lblFooter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footer.add(lblFooter);
        add(footer, BorderLayout.SOUTH);
    }

    // Método auxiliar para crear botones tipo menú con estilo y esquinas redondeadas
    private JButton crearBotonMenu(String texto, Color colorFondo) {
        CardButton btn = new CardButton(texto, 24, colorFondo);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.CENTER);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(new Color(230, 239, 245));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(300, 150));
        btn.setBorder(new EmptyBorder(18, 18, 18, 18));
        return btn;
    }

    // Pequeño badge que simula un logotipo (escudo con "V")
    private JLabel crearLogoBadge() {
        JLabel badge = new JLabel("V", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                g2.setColor(new Color(41, 104, 127));
                g2.fillRoundRect(0, 0, w, h, 16, 16);
                g2.setColor(new Color(117, 187, 206));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, w-2, h-2, 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setPreferredSize(new Dimension(36, 36));
        badge.setMinimumSize(new Dimension(36, 36));
        badge.setForeground(new Color(224, 236, 242));
        badge.setFont(new Font("SansSerif", Font.BOLD, 18));
        badge.setBorder(new EmptyBorder(2, 2, 2, 2));
        return badge;
    }

    // Botón personalizado que pinta un rectángulo redondeado (esquinas circulares)
    private static class CardButton extends JButton {
        private final int arc;
        private final Color bg;

        public CardButton(String text, int arc, Color background) {
            super(text);
            this.arc = arc;
            this.bg = background;
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false); // lo pintamos manualmente para respetar las esquinas redondeadas
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            // sombra suave
            for (int i = 6; i >= 1; i--) {
                int alpha = 12 - i; // 6..1 -> 6..11 aprox
                g2.setColor(new Color(0, 0, 0, 10 + alpha));
                g2.fillRoundRect(2 + i, 3 + i, w - 1 - (i * 2), h - 1 - (i * 2), arc + i, arc + i);
            }
            // fondo
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, w - 1, h - 1, arc, arc);
            // borde sutil
            g2.setColor(new Color(0, 0, 0, 60));
            g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}

