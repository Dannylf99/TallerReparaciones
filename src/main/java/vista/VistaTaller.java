package vista;

import java.util.Scanner;

import controlador.ControladorTaller;
import dao.mysql.*;
import entities.*;

public class VistaTaller {

    private Scanner sc = new Scanner(System.in);
    private ControladorTaller controlador = ControladorTaller.getInstance();
    private Usuario u = null;

    public VistaTaller() {
        controlador = ControladorTaller.getInstance();
    }

    /**
    * Método principal del menú del invitado. Permite iniciar sesión o ver
    * reparaciones finalizadas.
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

    /**
     * Método encargado de autenticar al usuario solicitando credenciales.
     * Si el login es exitoso, redirige al menú correspondiente al rol.
     */
     private void login() {
     	//Se le piden las credenciales.
         System.out.print("Usuario: ");
         String user = sc.nextLine();
         System.out.print("Contraseña: ");
         String pass = sc.nextLine();

         //Guardamos el usuario si cumple el login
         u = controlador.login(user, pass);

         //Si no es nulo, se le imprime y se le pasa a la ventana de su menú, dependiendo del rol
         if (u != null) {
         	//Se le imprime un mensaje de bienvenida y se le manda al rol
             System.out.println("Bienvenido " + u.getNombre_usuario());
             menuPorRol(u);
         } else {
         	//Se indica que no ha mentido las credenciales correctas y se vuelve al menú de invitado.
             System.out.println("Credenciales incorrectas");
         }
     }


    /**
     * Redirige al menú según el rol del usuario autenticado.
     *
     * @param u usuario autenticado
     */
    
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

    /**
    * Menú específico para el rol MECÁNICO. Permite registrar reparaciones
    * y cambiar sus estados.
    */
    
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
    
    /**
     * Permite al mecánico registrar una nueva reparación solicitando datos
     * por consola y delegando la inserción al controlador.
     *
     * @param u persona que está registrando la reparación
     */
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

    /**
     * Permite cambiar el estado de una reparación seleccionada por matrícula.
     * Solicita el nuevo estado y delega el cambio al controlador.
     */
     
     private void cambiarEstadoReparacion() {
     	//Se indica la matrícula a la que pertenece.
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
            sc.nextLine(); 

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
            System.out.println("3. Registrar reparación");
            System.out.println("4. Cambiar estado de reparación");
            System.out.println("5. Consultar estadísticas");
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
                registrarReparacion(u);
                break;

            case 4:
                cambiarEstadoReparacion();
                break;

            case 5:
                controlador.mostrarEstadisticas();
                break;

            case 0:
                return;
                
            default:
                System.out.println("Opción incorrecta");
            }

        }
    }

    /**
     * Muestra el menú de gestión de clientes y delega las operaciones CRUD
     * al controlador.
     */
     
     private void menuClientes() {
     	//Se muestran las opciones para hacer un CRUD de los clientes.
         System.out.println("1. Alta cliente");
         System.out.println("2. Baja cliente");
         System.out.println("3. Modificar cliente");
         System.out.println("0. Cerrar sesión");

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
             
         case 0:
         	break;
         	
         default:
         	 System.out.println("Opción incorrecta");
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

    /**
     * Método main que inicializa los DAOs, crea usuarios base,
     * configura el controlador y lanza la vista principal del sistema.
     *
     * @param args argumentos del programa
     */
         public static void main(String[] args) {

             // Inicializamos DAO
             VehiculoDAOMySQL daoVehiculo = new VehiculoDAOMySQL();
             ClienteDAOMySQL daoCliente = new ClienteDAOMySQL();
             UsuarioDAOMySQL daoUsuario = new UsuarioDAOMySQL();
             ReparacionDAOMySQL daoReparacion = new ReparacionDAOMySQL();
             
             /* 
              * Creo los dos usuarios para que puedas acceder a las cuentas, ya que desde el inicio,
              * ya que si no no podrías acceder de manera "normal". Estos se unen a los ejemplos ya creados
              * en la base de datos.
              */
             Usuario inicioAdmin = new Usuario("Gonzalo","12345678Z","gonzalo","ADMINISTRADOR");
             Usuario inicioMecanico = new Usuario("Gonzalo","12345678A","gonzalo","MECANICO");
             
             
             //Inserto tus usuarios
             daoUsuario.insert(inicioAdmin);
             daoUsuario.insert(inicioMecanico);

             // Inicializamos el controlador singleton.
             ControladorTaller.init(daoVehiculo, daoCliente, daoUsuario, daoReparacion);

             // Ahora sí podemos usar la vista
             VistaTaller vista = new VistaTaller();
             //Imprimimos tus datos para que los puedas utilizar, pero solo al inicio.
             System.out.println("\n>(ADMIN)Usuario creado:\n>DNI: 12345678Z, Contraseña: gonzalo\n");
             System.out.println("\n>(MECANICO)Usuario creado:\n>DNI: 12345678A, Contraseña: gonzalo\n");
             //Iniciamos el menú de invitado
             vista.iniciar();
         }
    }



