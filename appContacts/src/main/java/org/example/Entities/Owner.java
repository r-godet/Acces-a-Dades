package org.example.Entities;
import jakarta.persistence.*;

@Entity
@Table(name = "Owner")

public class Owner {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        int id;
        @Column(length = 20, unique = true)
        String usuario;
        @Column(length = 20)
        String contrasenya;

    public Owner() {
    }

    public Owner(String usuario, String constrasenya){
        this.usuario = usuario;
        this.contrasenya = constrasenya;
    }
}
