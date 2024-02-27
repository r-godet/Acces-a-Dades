package org.example.View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginPanel() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Usuario:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(loginButton);

        registerButton = new JButton("Registrarse");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean loginSuccess = DatabaseManager.login(username, password);
                if (loginSuccess) {
                    // Cambia al panel de contactos
                    MainFrame.switchToContactPanel(username);
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Inicio de sesión fallido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean registerSuccess = DatabaseManager.register(username, password);
                if (registerSuccess) {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Registro exitoso. Por favor, inicie sesión.", "Registro", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "El registro falló. Es posible que el usuario ya exista.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
}
