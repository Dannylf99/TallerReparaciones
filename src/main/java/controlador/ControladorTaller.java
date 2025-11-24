package controlador;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import dao.mysql.ClienteDAOMySQL;
import dao.mysql.ReparacionDAOMySQL;
import dao.mysql.UsuarioDAOMySQL;
import dao.mysql.VehiculoDAOMySQL;
import entities.Cliente;
import entities.Reparacion;
import entities.Usuario;
import entities.Vehiculo;

public class ControladorTaller {

    private static ControladorTaller instance;
    Scanner sc = new Scanner(System.in);

    public static ControladorTaller getInstance() {
        return instance;
    }

    public static void init(VehiculoDAOMySQL daoVehiculo, ClienteDAOMySQL daoCliente,
                            UsuarioDAOMySQL daoUsuario, ReparacionDAOMySQL daoReparacion) {
        instance = new ControladorTaller(daoVehiculo, daoCliente, daoUsuario, daoReparacion);
    }

    private ControladorTaller(VehiculoDAOMySQL v, ClienteDAOMySQL c,
                              UsuarioDAOMySQL u, ReparacionDAOMySQL r) {
        vehiculoDAO = v;
        clienteDAO = c;
        usuarioDAO = u;
        reparacionDAO = r;
    }

    private VehiculoDAOMySQL vehiculoDAO;
    private ClienteDAOMySQL clienteDAO;
    private UsuarioDAOMySQL usuarioDAO;
    private ReparacionDAOMySQL reparacionDAO;

    public void verReparacionesFinalizadas() {
        ArrayList<Reparacion> lista = reparacionDAO.findFinalizadas();

        if (lista.isEmpty()) {
            System.out.println("No hay reparaciones finalizadas.");
            return;
        }
        
        for (Reparacion r : lista) {
        	System.out.println(r.toString());
        }
    }

    public Usuario login(String dni, String pass) {
    	//Se comprueba que la contraseña es correcta en base al usuario.
        boolean ok = usuarioDAO.login(dni, pass);
        if (!ok) return null;

        //Se devuelve que el usuario se ha logueado.
        return usuarioDAO.findByDni(dni);
    }

    public void registrarReparacion(String matricula, String desc, double coste, String fecha, String dni) {
    	//El busca el vehículo en base a la matrícula
        Vehiculo v = vehiculoDAO.findByMatricula(matricula);
        //Si no se encuentra, se devuelve
        if (v == null) {
            System.out.println("Vehículo no encontrado");
            return;
        }
        //Lo mismo con el usuario
        Usuario u = usuarioDAO.findByDni(dni);
        if (u == null) {
            System.out.println("Error: no hay usuario logueado.");
            return;
        }
        //Se crea una reparación, con el valor por defecto de "EN PREPARACIÓN"
        Reparacion r = new Reparacion(desc, Date.valueOf(fecha), coste, "EN PREPARACIÓN",
                v.getId_vehiculo(), u.getId_usuario());
        //Se inserta y se avisa al cliente
        reparacionDAO.insert(r);
        System.out.println("Reparación registrada.");
    }


    public void cambiarEstado(String matricula, String estado) {
        ArrayList<Reparacion> lista = reparacionDAO.findByMatricula(matricula);

        if (lista.isEmpty()) {
            System.out.println("> No hay reparaciones para esa matrícula.");
            return;
        }

        System.out.println("> Seleccione la reparación que desea cambiar, introduciendo el id que se indica:");

        for (Reparacion reparacion : lista) {
            System.out.println("> Id: " + reparacion.getId_reparacion() + 
                               ". Descripción: " + reparacion.getDescripcion() + 
                               ". Fecha entrada: " + reparacion.getFecha_entrada() + 
                               ". Coste estimado: " + reparacion.getCoste_estimado());
        }

        System.out.println("> Seleccione el id:");
        int id = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        // Buscamos la reparación directamente en la lista
        Reparacion r = null;
        for (Reparacion reparacion : lista) {
            if (reparacion.getId_reparacion() == id) {
                r = reparacion;
                break;
            }
        }

        if (r == null) {
            System.out.println("> Reparación no encontrada.");
            return;
        }

        r.setEstado(estado);
        reparacionDAO.update(r);

        System.out.println("> Estado actualizado.");
    }


    public void altaCliente() {
        String nombre;
        do {
            System.out.print("> Introduce el nombre: ");
            nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) System.out.println("> El nombre no puede estar vacío.");
        } while (nombre.isEmpty());

        String dni;
        do {
            System.out.print("> Introduce el dni: ");
            dni = sc.nextLine().trim();
            if (dni.isEmpty()) System.out.println("> El DNI no puede estar vacío.");
        } while (dni.isEmpty());

        String email;
        do {
            System.out.print("> Introduce el email: ");
            email = sc.nextLine().trim();
            if (email.isEmpty()) System.out.println("> El email no puede estar vacío.");
        } while (email.isEmpty());

        int tel = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print("> Introduce el teléfono: ");
            String telInput = sc.nextLine().trim();
            try {
                tel = Integer.parseInt(telInput);
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("> Teléfono no válido, introduce solo números.");
            }
        }

        Cliente c = new Cliente(nombre, dni, email, tel);
        int resultado = (clienteDAO.insert(c));
        if (resultado == -1) {
        	System.out.println("> No se ha introducido el cliente por el fallo que se indica.");
        } else {
        	System.out.println("> Cliente dado de alta correctamente.");
        }
    }


    public void bajaCliente() {
        System.out.println("> Introduce el dni para dar de baja: ");
        String dni = sc.nextLine();
        clienteDAO.delete(dni);
    }

    public void modificarCliente() {
        System.out.print("> Introduce el DNI del cliente que quieras modificar: ");
        String dni = sc.nextLine();

        Cliente c = clienteDAO.findByDni(dni);
        if (c == null) {
            System.out.println("> Cliente no encontrado.");
            return;
        }

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n> Datos actuales del cliente:");
            System.out.println("1. Nombre: " + c.getNombre());
            System.out.println("2. DNI: " + c.getDni());
            System.out.println("3. Email: " + c.getEmail());
            System.out.println("4. Teléfono: " + c.getTelefono());
            System.out.println("0. Terminar y guardar cambios");
            System.out.print("> Selecciona el campo a modificar: ");

            String input = sc.nextLine();
            int opcion;
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("> Opción no válida");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("> Nuevo nombre: ");
                    c.setNombre(sc.nextLine());
                    break;
                case 2:
                    System.out.print("> Nuevo DNI: ");
                    c.setDni(sc.nextLine());
                    break;
                case 3:
                    System.out.print("> Nuevo email: ");
                    c.setEmail(sc.nextLine());
                    break;
                case 4:
                    System.out.print("> Nuevo teléfono: ");
                    try {
                        c.setTelefono(Integer.parseInt(sc.nextLine()));
                    } catch (NumberFormatException e) {
                        System.out.println("> Teléfono no válido");
                    }
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("> Opción no válida");
            }
        }

        clienteDAO.update(c);
        System.out.println("> Cliente actualizado correctamente.");
    }


    public void altaVehiculo() {
        // Matrícula
        String matricula;
        do {
            System.out.print("> Introduce la matrícula: ");
            matricula = sc.nextLine().trim();
            if (matricula.isEmpty()) System.out.println("> La matrícula no puede estar vacía.");
        } while (matricula.isEmpty());

        // Marca
        String marca;
        do {
            System.out.print("> Introduce la marca: ");
            marca = sc.nextLine().trim();
            if (marca.isEmpty()) System.out.println("> La marca no puede estar vacía.");
        } while (marca.isEmpty());

        // Modelo
        String modelo;
        do {
            System.out.print("> Introduce el modelo: ");
            modelo = sc.nextLine().trim();
            if (modelo.isEmpty()) System.out.println("> El modelo no puede estar vacío.");
        } while (modelo.isEmpty());

        // DNI del cliente
        String dni;
        Cliente cliente = null;
        do {
            System.out.print("> Introduce el DNI del cliente: ");
            dni = sc.nextLine().trim();
            if (dni.isEmpty()) {
                System.out.println("> El DNI no puede estar vacío.");
                continue;
            }
            // Se le busca por el dni
            cliente = clienteDAO.findByDni(dni);
            if (cliente == null) {
                System.out.println("> Cliente no encontrado, introduce un DNI válido.");
            }
        } while (cliente == null); //Hasta que el cliente no esté bien no se sale.

        // Creamos el vehículo y lo asociamos al cliente
        Vehiculo v = new Vehiculo(matricula, marca, modelo, cliente.getId_Cliente());
        int resultado = vehiculoDAO.insert(v);
        //Se avisa al usuario de que se ha incluido.
        if (resultado == -1) {
        	System.out.println("> No se ha introducido el vehículo por el fallo que se indica.");
        } else {
        	System.out.println("> Vehículo dado de alta correctamente y asociado al cliente " + cliente.getNombre());
        }
    }

    public void bajaVehiculo() {
        String matricula;

        do {
            System.out.print("> Introduce la matrícula del vehículo que deseas dar de baja: ");
            matricula = sc.nextLine().trim();

            if (matricula.isEmpty()) {
                System.out.println("> La matrícula no puede estar vacía.");
            }
        } while (matricula.isEmpty());

        // Verificar que el vehículo exista antes de borrarlo
        Vehiculo v = vehiculoDAO.findByMatricula(matricula);
        if (v == null) {
            System.out.println("> No existe ningún vehículo con esa matrícula.");
            return;
        }

        vehiculoDAO.delete(matricula);
        System.out.println("> Vehículo dado de baja correctamente.");
    }


    public void modificarVehiculo() {
        System.out.print("> Introduce la matrícula del vehículo que quieras modificar: ");
        String matricula = sc.nextLine();

        Vehiculo v = vehiculoDAO.findByMatricula(matricula);
        if (v == null) {
            System.out.println("> Vehículo no encontrado.");
            return;
        }
        
        Cliente c = clienteDAO.findById(v.getCliente_id());

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n> Datos actuales del vehículo:");
            System.out.println("1. Matrícula: " + v.getMatricula());
            System.out.println("2. Marca: " + v.getMarca());
            System.out.println("3. Modelo: " + v.getModelo());
            System.out.println("4. DNI del dueño actual: " + c.getDni());
            System.out.println("0. Terminar y guardar cambios");
            System.out.print("> Selecciona el campo a modificar: ");

            String input = sc.nextLine();
            int opcion;

            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("> Opción no válida");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("> Nueva matrícula: ");
                    String nuevaMatricula = sc.nextLine().trim();
                    if (!nuevaMatricula.isEmpty()) {
                        v.setMatricula(nuevaMatricula);
                    } else {
                        System.out.println("> La matrícula no puede estar vacía.");
                    }
                    break;

                case 2:
                    System.out.print("> Nueva marca: ");
                    String nuevaMarca = sc.nextLine().trim();
                    if (!nuevaMarca.isEmpty()) {
                        v.setMarca(nuevaMarca);
                    } else {
                        System.out.println("> La marca no puede estar vacía.");
                    }
                    break;

                case 3:
                    System.out.print("> Nuevo modelo: ");
                    String nuevoModelo = sc.nextLine().trim();
                    if (!nuevoModelo.isEmpty()) {
                        v.setModelo(nuevoModelo);
                    } else {
                        System.out.println("> El modelo no puede estar vacío.");
                    }
                    break;

                case 4:
                    System.out.print("> DNI del nuevo dueño: ");
                    String dniNuevo = sc.nextLine().trim();

                    if (dniNuevo.isEmpty()) {
                        System.out.println("> El DNI no puede estar vacío.");
                        break;
                    }

                    Cliente nuevoCliente = clienteDAO.findByDni(dniNuevo);
                    if (nuevoCliente == null) {
                        System.out.println("> Cliente no encontrado.");
                    } else {
                        v.setCliente_id(nuevoCliente.getId_Cliente());
                        System.out.println("> Dueño actualizado correctamente.");
                    }
                    break;

                case 0:
                    continuar = false;
                    break;

                default:
                    System.out.println("> Opción no válida");
            }
        }

        vehiculoDAO.update(v);
        System.out.println("> Vehículo actualizado correctamente.");
    }



    public void mostrarEstadisticas() {
        System.out.println("\n===== ESTADÍSTICAS DE REPARACIONES =====");
        System.out.println("1. Filtrar por estado");
        System.out.println("2. Filtrar por coste medio");
        System.out.println("0. Salir");
        System.out.print("> Seleccione una opción: ");

        int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("> Opción no válida.");
            return;
        }

        switch (opcion) {
            case 1:
                ArrayList<Reparacion> reparaciones = reparacionDAO.findAll();

                ArrayList<Reparacion> enPreparacion = new ArrayList<>();
                ArrayList<Reparacion> enReparacion = new ArrayList<>();
                ArrayList<Reparacion> finalizado = new ArrayList<>();

                for (Reparacion r : reparaciones) {
                    String estado = r.getEstado().toUpperCase();

                    switch (estado) {
                        case "EN PREPARACIÓN":
                            enPreparacion.add(r);
                            break;

                        case "EN REPARACIÓN":
                            enReparacion.add(r);
                            break;

                        case "FINALIZADO":
                            finalizado.add(r);
                            break;

                        default:
                            System.out.println("> Fallo en el estado de la reparación con ID: " + r.getId_reparacion());
                    }
                }

                double costePreparacion = reparacionDAO.filtrarPorCosteMedio(1);
                double costeReparacion = reparacionDAO.filtrarPorCosteMedio(2);
                double costeFinalizado = reparacionDAO.filtrarPorCosteMedio(3);

                System.out.println("\n--- REPARACIONES POR ESTADO ---");

                System.out.println("\nEN PREPARACIÓN (" + enPreparacion.size() + "), Coste medio: " + costePreparacion + "€:");
                for (Reparacion r : enPreparacion) {
                    System.out.println(r);
                }

                System.out.println("\nEN REPARACIÓN (" + enReparacion.size() + "), Coste medio: " + costeReparacion + "€:");
                for (Reparacion r : enReparacion) {
                    System.out.println(r);
                }

                System.out.println("\nFINALIZADO (" + finalizado.size() + "), Coste medio: " + costeFinalizado + "€:");
                for (Reparacion r : finalizado) {
                    System.out.println(r);
                }
                break;

            case 2:
                double costeMedio = reparacionDAO.filtrarPorCosteMedio(0);
                double costeUno = reparacionDAO.filtrarPorCosteMedio(1);
                double costeDos = reparacionDAO.filtrarPorCosteMedio(2);
                double costeTres = reparacionDAO.filtrarPorCosteMedio(3);

                System.out.println("> Coste medio general: " + costeMedio + "€");
                System.out.println("> Coste medio (EN PREPARACIÓN): " + costeUno + "€");
                System.out.println("> Coste medio (EN REPARACIÓN): " + costeDos + "€");
                System.out.println("> Coste medio (FINALIZADO): " + costeTres + "€");
                break;

            case 0:
                return;

            default:
                System.out.println("> Opción no válida.");
        }
    }

    
}
