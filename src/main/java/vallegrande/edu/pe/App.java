package vallegrande.edu.pe;

import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.view.MiniPaginaView;

import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FlatLaf.setup(new FlatDarkLaf());
                JFrame.setDefaultLookAndFeelDecorated(true);
            } catch (Exception ignored) {}
            UsuarioController controller = new UsuarioController();
            new MiniPaginaView(controller).setVisible(true);
        });
    }
}