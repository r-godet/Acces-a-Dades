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
        @Column(length = 100)
        String contrasenya;

    public Owner() {
    }

    @Override
    public String toString() {
        return "Nombre usuario: "+ usuario;
    }

    public Owner(String usuario, String constrasenya){
        this.usuario = usuario;
        this.contrasenya = constrasenya;
    }
}
