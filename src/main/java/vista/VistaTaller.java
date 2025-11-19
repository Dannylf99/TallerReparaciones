package vista;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import controlador.ControladorTaller;
import dao.mysql.*;
import entities.*;

import java.util.Scanner;

public class VistaTaller {

    private Scanner sc = new Scanner(System.in);
    private ControladorTaller controlador = ControladorTaller.getInstance();
    private Usuario u = null;

    public VistaTaller() {
        controlador = ControladorTaller.getInstance();
    }

    /*
     * Menú del invitado
     */
    
    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("======= INVITADO =======");
            System.out.println("1. Ver reparaciones finalizadas");
            System.out.println("2. Iniciar sesión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            String input = sc.nextLine();
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida");
                opcion = -1;
                continue;
            }

            switch (opcion) {

            case 1:
                controlador.verReparacionesFinalizadas();
                break;

            case 2:
                login();
                break;

            case 0:
                System.out.println("Saliendo...");
                break;

            default:
                System.out.println("Opción no válida");
            }
        }
    }

    private void login() {
        System.out.print("Usuario: ");
        String user = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        u = controlador.login(user, pass);

        if (u != null) {
            System.out.println("Bienvenido " + u.getNombre_usuario());
            menuPorRol(u);
        } else {
            System.out.println("Credenciales incorrectas");
        }
    }

    private void menuPorRol(Usuario u) {

        if (u.getRol().equalsIgnoreCase("MECANICO")) {
            menuMecanico();
        } else if (u.getRol().equalsIgnoreCase("ADMINISTRADOR")) {
            menuAdmin();
        } else {
            System.out.println("Rol no reconocido");
            iniciar();
        }
    }

    private void menuMecanico() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("===== MENÚ MECÁNICO =====");
            System.out.println("1. Registrar nueva reparación");
            System.out.println("2. Cambiar estado de reparación");
            System.out.println("0. Cerrar sesión");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

            case 1:
                registrarReparacion(u);
                break;

            case 2:
                cambiarEstadoReparacion();
                break;

            case 0:
                return;
            default:
                System.out.println("Opción incorrecta");
            }
        }
    }

    private void registrarReparacion(Usuario u) {
        System.out.print("Matrícula del vehículo: ");
        String matricula = sc.nextLine();

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        System.out.print("Coste estimado: ");
        double coste = sc.nextDouble();
        sc.nextLine();

        System.out.print("Fecha (AAAA-MM-DD): ");
        String fecha = sc.nextLine();
        
        String dni = u.getDni();

        controlador.registrarReparacion(matricula, descripcion, coste, fecha, dni);
    }

    private void cambiarEstadoReparacion() {
        System.out.print("Matricula: ");
        String id = sc.nextLine();
        String estado = "";
        boolean correcto = false;

        while(!correcto) {
            System.out.println("> Nuevo estado:");
            System.out.println("1. En reparación");
            System.out.println("2. Finalizada");
            System.out.print("> Introduce el estado que deseas: ");
            int estadoNum = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            if (estadoNum == 1) {
                estado = "EN REPARACIÓN";
                correcto = true;
            } else if(estadoNum == 2) {
                estado = "FINALIZADO";
                correcto = true;
            } else {
                System.out.println("> La opción introducida no es correcta.");
            }
        }

        controlador.cambiarEstado(id, estado);
    }


    private void menuAdmin() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("===== MENÚ ADMINISTRADOR =====");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Gestionar vehículos");
            System.out.println("3. Ver estadísticas");
            System.out.println("0. Cerrar sesión");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

            case 1:
                menuClientes();
                break;

            case 2:
                menuVehiculos();
                break;

            case 3:
                controlador.mostrarEstadisticas();
                break;

            case 0:
                return;
            default:
                System.out.println("Opción incorrecta");
            }
        }
    }

    private void menuClientes() {
        System.out.println("1. Alta cliente");
        System.out.println("2. Baja cliente");
        System.out.println("3. Modificar cliente");

        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {

        case 1:
            controlador.altaCliente();
            break;

        case 2:
            controlador.bajaCliente();
            break;

        case 3:
            controlador.modificarCliente();
            break;
        }
    }

    private void menuVehiculos() {
        System.out.println("1. Alta vehículo");
        System.out.println("2. Baja vehículo");
        System.out.println("3. Modificar vehículo");

        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {

        case 1:
            controlador.altaVehiculo();
            break;

        case 2:
            controlador.bajaVehiculo();
            break;

        case 3:
            controlador.modificarVehiculo();
            break;
        }
    }


        public static void main(String[] args) {

            // Inicializamos DAOs (pueden ser objetos reales o mocks)
            VehiculoDAOMySQL daoVehiculo = new VehiculoDAOMySQL();
            ClienteDAOMySQL daoCliente = new ClienteDAOMySQL();
            UsuarioDAOMySQL daoUsuario = new UsuarioDAOMySQL();
            ReparacionDAOMySQL daoReparacion = new ReparacionDAOMySQL();
            Usuario inicioAdmin = new Usuario("Gonzalo","12345678Z","gonzalo","ADMINISTRADOR");
            Usuario inicioMecanico = new Usuario("Gonzalo","12345678A","gonzalo","MECANICO");
            daoUsuario.insert(inicioAdmin);
            daoUsuario.insert(inicioMecanico);

            // Inicializamos el controlador singleton
            ControladorTaller.init(daoVehiculo, daoCliente, daoUsuario, daoReparacion);

            // Ahora sí podemos usar la vista
            VistaTaller vista = new VistaTaller();
            System.out.println("\n>(ADMIN)Usuario creado:\n>DNI: 12345678Z, Contraseña: gonzalo\n");
            System.out.println("\n>(MECANICO)Usuario creado:\n>DNI: 12345678A, Contraseña: gonzalo\n");
            vista.iniciar();
        }
    }



