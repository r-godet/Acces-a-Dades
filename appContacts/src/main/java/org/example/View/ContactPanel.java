package org.example.View;
import jdk.jfr.internal.util.UserSyntaxException;
import org.example.Controller.Controller;
import org.example.Entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ContactPanel extends JPanel {

    boolean name = false;
    final StandardServiceRegistry registro = new StandardServiceRegistryBuilder().configure().build();
    final SessionFactory sessionFactory = new MetadataSources(registro).buildMetadata().buildSessionFactory();
    final Session session = sessionFactory.openSession();
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JList<User> contactList;

    public ContactPanel() {
        setLayout(new BorderLayout());

        contactList = new JList<>();
        add(new JScrollPane(contactList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Añadir");
        editButton = new JButton("Editar");
        deleteButton = new JButton("Eliminar");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User newContact = User.user();
                if (newContact != null) {
                    Controller.agregarContacto(session);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User selectedContact = contactList.getSelectedValue();
                if (selectedContact != null) {
                    User updatedContact = User.editAndReturnContact(selectedContact);
                    if (updatedContact != null) {
                        DatabaseManager.updateContact(updatedContact);
                        updateContactList();
                    }
                } else {
                    JOptionPane.showMessageDialog(ContactPanel.this, "Por favor, seleccione un contacto para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User selectedContact = contactList.getSelectedValue();
                if (selectedContact != null) {
                    int confirm = JOptionPane.showConfirmDialog(ContactPanel.this, "¿Está seguro de que desea eliminar este contacto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        DatabaseManager.deleteContact(selectedContact);
                        updateContactList();
                    }
                } else {
                    JOptionPane.showMessageDialog(ContactPanel.this, "Por favor, seleccione un contacto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
}

