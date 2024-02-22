package org.example.Controller;
import org.example.Entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Controller {


   static ArrayList<User> users = new ArrayList<>();
   static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        boolean name = false;
        final StandardServiceRegistry registro = new StandardServiceRegistryBuilder().configure().build();
        final SessionFactory sessionFactory = new MetadataSources(registro).buildMetadata().buildSessionFactory();
        final Session session = sessionFactory.openSession();

        while (true) {
            System.out.println();
            System.out.print("CONTACTOS\n------------\n");
            System.out.print("Iniciar Sesion o Registrate (I/R): ");
            String respuesta = scan.nextLine();
            if(respuesta.equals("I")){
                System.out.print("Introduce tu usuario: ");
                System.out.print("Introduce tu contraseña: ");
            }
            else if(respuesta.equals("R")){
                System.out.print("Introduce tu usuario: ");
                String nombre1 = scan.nextLine();
                session.beginTransaction();
                Query<User> queryUsers = session.createQuery("SELECT nombre FROM User", User.class);
                session.getTransaction().commit();
                
               /*while(!name){
                     if(!nombre1.equals(queryUsers)){
                        System.out.println("Este nombre ya esta en uso, porfavor introduzca otro.");
                        return;
                    }else
                    {
                        name = true; //El nombre de usuario esta disponible y se le aplica
                    }
                }*/
               
                System.out.print("Introduce tu contraseña: ");
                String contra1 = scan.nextLine();
                System.out.print("Confirma tu contraseña: ");
                String contra2 = scan.nextLine();
                if(contra1==contra2){
                    System.out.print("Contraseña valida");
                }
            }
            System.out.println("1. Agregar Contacto");
            System.out.println("2. Ver lista de contactos");
            System.out.println("3. Cerrar sesion");
            int options = scan.nextInt();
            scan.nextLine();

            switch (options) {
                case 1:
                    agregarContacto(session);
                    break;
                case 2:
                    verContacto(session);
                    break;
                case 3:
                    System.out.println("Cerrar programa");
                    return;
                default:
                    System.out.println("Opción inválida. Vuleve a intertarlo.");
            }
        }
    }
    public static void verContacto(Session session)
    {
        Query<User> queryUsers = session.createQuery("FROM User", User.class);
        List<User> users = queryUsers.getResultList();
        System.out.println(users);

        System.out.println("Quieres editar un contacto? (S/N): ");
        String editar = scan.nextLine();
        if (editar.equals("N")){
            System.out.print("Volveras al menu en breves!\n");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else if(editar.equals("S")){
            System.out.print("Que contacto quieres editar? (introduce el nombre): ");
        }

    }

    public static void agregarContacto(Session session){
        System.out.print("Ingrese el nombre del contacto: ");
        String nombre = scan.nextLine();

        System.out.print("Ingrese el apellido del contacto: ");
        String apellido = scan.nextLine();

        System.out.print("Ingrese el número de teléfono: ");
        String telefono = scan.nextLine();

        session.beginTransaction();
        User contacto = new User(nombre, apellido, telefono);
        session.save(contacto);
       session.getTransaction().commit();

        System.out.println("Contacto de "+nombre+" agregado\n");
        System.out.print("Volveras al menu en breves!\n");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
