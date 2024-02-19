package org.example.Entities;
import java.sql.SQLOutput;
import java.util.Scanner;
public class User {
    static Scanner lector = new Scanner(System.in);
    static String nombre;
    static String apellido;
    static String numero;
    static String foto;

    public static void main(String[] args){

    }

    public User(String nombre, String apellido, String numero, String foto){
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.foto = foto;
    }
}
