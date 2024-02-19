package org.example.Controller;
import org.example.Entities.User;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Controller {

   static ArrayList<User> users = new ArrayList<>();
   static Scanner scan = new Scanner(System.in);
    public static void verContacto()
    {
        System.out.println("Lista de Contactos: ");
        for(int i = 0; i <= users.toArray().length; i++){
            System.out.println(users.toString());
        }
    }

    public static void agregarContacto(){
            System.out.print("Ingrese el nombre del contacto: ");
            String nombre = scan.nextLine();

            System.out.print("Ingrese el apellido del contacto: ");
            String apellido = scan.nextLine();

            System.out.print("Ingrese el número de teléfono: ");
            String telefono = scan.nextLine();

            System.out.print("Quieres añadir una foto: ");
            String foto = scan.nextLine();

            User contacto = new User(nombre, apellido, telefono, foto);
            users.add(contacto);

            System.out.println("Contacto de "+nombre+" agregado\n");
            System.out.println("Volveras al menu en breves!");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

            switch (options) {
                case 1:
                    agregarContacto();
                    break;
                case 2:
                    verContacto();
                    break;
                case 3:
                    System.out.println("Cerrar programa");
                    return;
                default:
                    System.out.println("Opción inválida. Vuleve a intertarlo.");
            }
        }
    }
}
