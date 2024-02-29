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


        /*AQUI ESTA EL MAIN*/

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
                System.out.print("\nIntroduce tu usuario: ");
                String usuario = scan.nextLine();

                System.out.print("Introduce tu contraseña: ");
                String contrasenya = scan.nextLine();

                session.beginTransaction();

                Query<Owner> query = session.createQuery("FROM Owner WHERE usuario = :username", Owner.class);
                query.setParameter("username", usuario);
                Owner owner = query.uniqueResult();

                session.getTransaction().commit();

                if(owner != null && owner.getPassword().equals(contrasenya)) {
                    boolean inSesion = true;
                    while(inSesion){
                        System.out.println();
                        System.out.println("Inicio de session correcto.");
                        System.out.println("1. Agregar Contacto");
                        System.out.println("2. Ver lista de contactos");
                        System.out.println("3. Cerrar Contactos");
                        System.out.print("Que opcion quieres elegir? ");
                        int options = scan.nextInt();
                        scan.nextLine();

                        switch (options) {
                            case 1:
                                agregarContacto(session, owner);
                                break;
                            case 2:
                                verContacto(session, owner);
                                break;
                            case 3:
                                System.out.println("Cerrar Contactos");
                                inSesion = false;
                                return;
                            default:
                                System.out.println("\nOpción inválida. Vuleve a intertarlo.");
                        }
                    }
                } else {
                    System.out.println("\nNombre de usuario o contraseña incorrecta.\n");
                }
            }
            else if(respuesta.equals("R")){
                agregarOwner(session);
            }
        }
    }

    public static void agregarContacto(Session session, Owner currentOwner) {
            System.out.print("\nIngrese el nombre del contacto: ");
            String nombre = scan.nextLine();

            System.out.print("Ingrese el apellido del contacto: ");
            String apellido = scan.nextLine();

            System.out.print("Ingrese el número de teléfono: ");
            String telefono = scan.nextLine();

            session.beginTransaction();

            User nuevoContacto = new User();
            nuevoContacto.setNombre(nombre);
            nuevoContacto.setApellido(apellido);
            nuevoContacto.setTelefono(telefono);
            nuevoContacto.setOwner(currentOwner);

            session.save(nuevoContacto);

            session.getTransaction().commit();

            System.out.println("\nContacto agregado exitosamente.");
    }
    public static void agregarOwner(Session session)
    {
        System.out.print("\nIntroduce tu usuario: ");
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

            System.out.println("\nUsuario Creado");
            System.out.println("En breves volveras al menu de inicio\n");
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        else{
            System.out.println("\nContraseña invalida");
            System.out.println("En breves volveras al menu de inicio");
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        return;
    }

    public static void verContacto(Session session, Owner currentOwner) {
        int ownerId = currentOwner.getId();
        session.beginTransaction();
        List<User> contactos = session.createQuery("FROM User WHERE owner.id = :ownerId", User.class)
                .setParameter("ownerId", ownerId)
                .getResultList();
        session.getTransaction().commit();

        if (contactos.isEmpty()) {
            System.out.println("\nNo hay contactos para mostrar.");
            return;
        }

        System.out.println("\nContactos:");
        for (int i = 0; i < contactos.size(); i++) {
            User contacto = contactos.get(i);
            System.out.println((i + 1) + ". " + contacto.getNombre() + " " + contacto.getApellido() + " | Teléfono: " + contacto.getTelefono());
        }

        System.out.print("\n¿Desea editar o eliminar algún contacto? (presiona E para editar, presiona D para eliminar o presiona M para volver al menu): ");
        String respuesta = scan.nextLine();

        if (respuesta.equalsIgnoreCase("E")) {
            editarContacto(session, contactos);
        } else if (respuesta.equalsIgnoreCase("D")) {
            eliminarContacto(session, contactos);
        }
    }

    public static void editarContacto(Session session, List<User> contactos) {
        System.out.print("\nIngrese el número del contacto que desea editar: ");
        int index = scan.nextInt();
        scan.nextLine(); // Consume newline character

        if (index >= 1 && index <= contactos.size()) {
            User contacto = contactos.get(index - 1);

            System.out.println("\nEditar contacto: " + contacto.getNombre() + " " + contacto.getApellido());

            System.out.println("\n¿Qué parte del contacto desea editar?");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido");
            System.out.println("3. Teléfono");
            System.out.println("4. Editar todo");
            System.out.print("Que opcion quieres elegir? ");
            int opcion = scan.nextInt();
            scan.nextLine(); // Consume newline character

            switch (opcion) {
                case 1:
                    System.out.print("\nIngrese el nuevo nombre: ");
                    String nuevoNombre = scan.nextLine();
                    contacto.setNombre(nuevoNombre);
                    break;
                case 2:
                    System.out.print("\nIngrese el nuevo apellido: ");
                    String nuevoApellido = scan.nextLine();
                    contacto.setApellido(nuevoApellido);
                    break;
                case 3:
                    System.out.print("\nIngrese el nuevo número de teléfono: ");
                    String nuevoTelefono = scan.nextLine();
                    contacto.setTelefono(nuevoTelefono);
                    break;
                case 4:
                    System.out.print("\nIngrese el nuevo nombre: ");
                    nuevoNombre = scan.nextLine();
                    contacto.setNombre(nuevoNombre);

                    System.out.print("Ingrese el nuevo apellido: ");
                    nuevoApellido = scan.nextLine();
                    contacto.setApellido(nuevoApellido);

                    System.out.print("Ingrese el nuevo número de teléfono: ");
                    nuevoTelefono = scan.nextLine();
                    contacto.setTelefono(nuevoTelefono);
                    break;
                default:
                    System.out.println("\nOpción inválida.\n");
                    return;
            }

            session.beginTransaction();
            session.update(contacto);
            session.getTransaction().commit();

            System.out.println("\nContacto actualizado exitosamente.");
        } else {
            System.out.println("\nNúmero de contacto inválido.");
        }
    }

    public static void eliminarContacto(Session session, List<User> contactos) {
        System.out.print("Ingrese el número del contacto que desea eliminar: ");
        int index = scan.nextInt();
        scan.nextLine(); // Consume newline character

        if (index >= 1 && index <= contactos.size()) {
            User contacto = contactos.get(index - 1);

            session.beginTransaction();
            session.delete(contacto);
            session.getTransaction().commit();

            System.out.println("\nContacto eliminado exitosamente.");
        } else {
            System.out.println("\nNúmero de contacto inválido.");
        }
    }

}
