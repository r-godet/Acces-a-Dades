package org.example.View;
public class MainFrame extends JFrame {
    private LoginPanel loginPanel;

    public MainFrame() {
        setTitle("Aplicación de Contactos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        loginPanel = new LoginPanel();
        add(loginPanel);
        
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

