package org.example.Controller;
import org.example.Entities.User;
import java.util.ArrayList;
import java.util.Scanner;
import org.example.Controller.Controller;
public class Controller {

   static ArrayList<User> users = new ArrayList<>();
   static Scanner scan = new Scanner(System.in);

    public static void Controller() {
        System.out.println("Nombre: ");
        String nombre = scan.nextLine();

        System.out.println("Apellido: ");
        String apellido = scan.nextLine();

        System.out.println("Telefono: ");
        String telefono = scan.nextLine();

        System.out.println("Añadir una foto");
        String foto = scan.nextLine();

        Controller contacto = new contacto(nombre, apellido, telefono, foto);
        controller.add(contacto);
    }
    public static void main(String[] args)
    {
        while (true) {
            System.out.println("Contactos");
            System.out.println("1. Agregar Contacto");
            System.out.println("2. Ver lista de contactos");
            System.out.println("3. Salir");
            int options = scan.nextInt();
            scan.nextLine();

            switch (opcions) {
                case 1:
                    agregarContacto();
                    break;
                case 2:
                    verContactos();
                    break;
                case 3:
                    System.out.println("Gracias por usar la aplicación. ");
                    return;
                default:
                    System.out.println("Opción inválida. Vuleve a intertarlo.");
            }
        }
    }
}
