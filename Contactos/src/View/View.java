package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class View {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton afegirBt;
    private JButton llistaBt;
    private JList list;
    Connection conn;

    public View() {
        llistaBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });
    }

    public void connect() {
        try{
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", "root", "123456");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
