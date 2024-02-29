package org.example.Entities;
import jakarta.persistence.*;

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
        @Column(length = 9)
        String telefono;

        @ManyToOne
        @JoinColumn(name = "owner_id")
        private Owner owner;



    public User() {
    }

    @Override
    public String toString() {
        return "Nombre: "+ nombre+ "\nApellido: "+ apellido+ "\nTelefono: "+ telefono+"\n";
    }

    public User(String nombre, String apellido, String telefono){
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
