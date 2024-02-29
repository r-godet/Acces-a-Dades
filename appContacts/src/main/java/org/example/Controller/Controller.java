package org.example.Controller;

import org.example.Entities.Owner;
import org.example.Entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Controller {
    public static boolean contraGood = false;
   static ArrayList<User> users = new ArrayList<>();
   static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        boolean name = false;
        final StandardServiceRegistry registro = new StandardServiceRegistryBuilder().configure().build();
        final SessionFactory sessionFactory = new MetadataSources(registro).buildMetadata().buildSessionFactory();
        final Session session = sessionFactory.openSession();
        String currentOwner;
        while (true) {
            System.out.println();
            System.out.print("CONTACTOS\n------------\n");
            System.out.print("Iniciar Sesion o Registrate (I/R): ");
            String respuesta = scan.nextLine();
            if(respuesta.equals("I")){
                System.out.print("Introduce tu usuario: ");
                String usuario = scan.nextLine();

                System.out.print("Introduce tu contraseña: ");
                String contrasenya = scan.nextLine();

                session.beginTransaction();

                Query<Owner> query = session.createQuery("FROM Owner WHERE usuario = :username", Owner.class);
                query.setParameter("username", usuario);
                Owner owner = query.uniqueResult();

                session.getTransaction().commit();

                if(owner != null && owner.getPassword().equals(contrasenya)) {
                    System.out.println("Autenticación exitosa.");
                    System.out.println("1. Agregar Contacto");
                    System.out.println("2. Ver lista de contactos");
                    System.out.println("3. Cerrar sesion");
                    int options = scan.nextInt();
                    scan.nextLine();

                    switch (options) {
                        case 1:
                            agregarContacto(session, currentOwner);
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
                } else {
                    System.out.println("Nombre de usuario o contraseña incorrecta.");
                }
            }
            else if(respuesta.equals("R")){
                agregarOwner(session);
            }
        }
    }
    public static void verContacto(Session session, Owner currentOwner) {
        // Asume que Owner tiene un campo 'id' que se usa para filtrar los contactos
        Long ownerId = currentOwner.getId();

        // Inicia una transacción para la operación de lectura (opcional dependiendo de tu configuración)
        session.beginTransaction();

        // Crea una consulta para obtener todos los contactos asociados con el Owner actual
        List<User> contactos = session.createQuery("FROM User WHERE owner.id = :ownerId", User.class)
                .setParameter("ownerId", ownerId)
                .getResultList();

        // Finaliza la transacción si es necesario (opcional)
        session.getTransaction().commit();

        // Verifica si hay contactos para mostrar
        if (contactos.isEmpty()) {
            System.out.println("No hay contactos para mostrar.");
            return;
        }

        // Muestra los contactos
        System.out.println("Contactos:");
        for (User contacto : contactos) {
            System.out.println("- " + contacto.getNombre() + " " + contacto.getApellido() + " | Teléfono: " + contacto.getTelefono());
        }
    }


    public static void agregarContacto(Session session, Owner currentOwner) {
            System.out.print("Ingrese el nombre del contacto: ");
            String nombre = scan.nextLine();

            System.out.print("Ingrese el apellido del contacto: ");
            String apellido = scan.nextLine();

            System.out.print("Ingrese el número de teléfono: ");
            String telefono = scan.nextLine();

            // Inicia una transacción con la base de datos
            session.beginTransaction();

            // Crea un nuevo objeto User (contacto) con los datos ingresados
            User nuevoContacto = new User();
            nuevoContacto.setNombre(nombre);
            nuevoContacto.setApellido(apellido);
            nuevoContacto.setTelefono(telefono);
            nuevoContacto.setOwner(currentOwner); // Asocia el contacto con el Owner actual

            // Guarda el nuevo contacto en la base de datos
            session.save(nuevoContacto);

            // Compromete la transacción
            session.getTransaction().commit();

            System.out.println("Contacto agregado exitosamente.");
    }
    public static void agregarOwner(Session session)
    {
        System.out.print("Introduce tu usuario: ");
        String nombre1 = scan.nextLine();
        session.beginTransaction();
        Query<Owner> queryUsers = session.createQuery("SELECT usuario FROM Owner", Owner.class);
        session.getTransaction().commit();

        System.out.print("Introduce tu contraseña: ");
        String contra1 = scan.nextLine();

        System.out.print("Confirma tu contraseña: ");
        String contra2 = scan.nextLine();

        if(contra1.equals(contra2)){
            System.out.print("Contraseña valida");
            session.beginTransaction();
            Owner owner = new Owner(nombre1, contra1);
            session.save(owner);
            session.getTransaction().commit();

            String currentOwner = nombre1;

            System.out.println("Usuario Creado");
            System.out.println("En breves volveras al menu de inicio");
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        return;
    }
}
