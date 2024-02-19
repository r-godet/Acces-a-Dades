package org.example.Entities;
import jakarta.persistence.*;

import java.sql.SQLOutput;
import java.util.Scanner;
@Entity
@Table(name = "Users")

public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        int id;
        @Column(length = 20, unique = true)
        String nombre;
        @Column(length = 20)
        String apellido;
        @Column(length = 100)
        String contrasenya;
        @Column(length = 9)
        String telefono;



    public User() {
    }

    @Override
    public String toString() {
        return "Nombre: "+ nombre+ "\nApellido: "+ apellido+ "\nTelefono: "+ telefono+"\n";
    }

    public User(String nombre, String apellido, String telefono, String constrasenya){
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.contrasenya = constrasenya;
    }
}
