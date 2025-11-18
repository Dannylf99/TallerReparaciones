package vista;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import controlador.ControladorTaller;
import dao.mysql.*;
import entities.*;

public class VistaTaller {

    private ControladorTaller controlador;
    private Scanner sc = new Scanner(System.in);

    public VistaTaller() {
        VehiculoDAOMySQL vehiculoDAO = new VehiculoDAOMySQL();
        ClienteDAOMySQL clienteDAO = new ClienteDAOMySQL();
        UsuarioDAOMySQL usuarioDAO = new UsuarioDAOMySQL();
        ReparacionDAOMySQL reparacionDAO = new ReparacionDAOMySQL();

        controlador = new ControladorTaller(vehiculoDAO, clienteDAO, usuarioDAO, reparacionDAO);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== MENÚ TALLER =====");
            System.out.println("1. Gestión de Vehículos");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Usuarios");
            System.out.println("4. Gestión de Reparaciones");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    menuVehiculos();
                    break;
                case 2:
                    menuClientes();
                    break;
                case 3:
                    menuUsuarios();
                    break;
                case 4:
                    menuReparaciones();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private void menuVehiculos() {
        System.out.println("\n--- Gestión Vehículos ---");
        System.out.println("1. Crear vehículo");
        System.out.println("2. Buscar vehículo por matrícula");
        System.out.println("3. Listar todos los vehículos");
        System.out.println("4. Actualizar vehículo");
        System.out.println("5. Borrar vehículo");
        System.out.print("Seleccione una opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Matrícula: ");
                String matricula = sc.nextLine();
                System.out.print("Marca: ");
                String marca = sc.nextLine();
                System.out.print("ID Cliente: ");
                int clienteId = sc.nextInt();
                sc.nextLine();
                boolean creado = controlador.crearVehiculo(matricula, marca, clienteId);
                System.out.println(creado ? "Vehículo creado." : "Error al crear vehículo.");
                break;
            case 2:
                System.out.print("Matrícula: ");
                matricula = sc.nextLine();
                Vehiculo v = controlador.buscarVehiculo(matricula);
                System.out.println(v != null ? v : "Vehículo no encontrado.");
                break;
            case 3:
            	ArrayList<Vehiculo> listaVehiculos = controlador.listarVehiculos();
                if (listaVehiculos.isEmpty()) {
                    System.out.println("No hay vehículos registrados.");
                } else {
                    System.out.println("\n--- Lista de Vehículos ---");
                    for (Vehiculo v1 : listaVehiculos) {
                        System.out.println("ID: " + v1.getId_vehiculo() +
                                           ", Matrícula: " + v1.getMatricula() +
                                           ", Marca: " + v1.getMarca() +
                                           ", ID Cliente: " + v1.getCliente_id());
                    }
                }
                break;
            case 4:
                System.out.print("Matrícula a actualizar: ");
                matricula = sc.nextLine();
                v = controlador.buscarVehiculo(matricula);
                if (v != null) {
                    System.out.print("Nueva marca: ");
                    marca = sc.nextLine();
                    System.out.print("Nuevo ID Cliente: ");
                    clienteId = sc.nextInt();
                    sc.nextLine();
                    v.setMarca(marca);
                    v.setCliente_id(clienteId);
                    boolean actualizado = controlador.actualizarVehiculo(v);
                    System.out.println(actualizado ? "Vehículo actualizado." : "Error al actualizar.");
                } else {
                    System.out.println("Vehículo no encontrado.");
                }
                break;
            case 5:
                System.out.print("Matrícula a borrar: ");
                matricula = sc.nextLine();
                boolean borrado = controlador.borrarVehiculo(matricula);
                System.out.println(borrado ? "Vehículo eliminado." : "Error al eliminar.");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void menuClientes() {
        System.out.println("\n--- Gestión Clientes ---");
        System.out.println("1. Crear cliente");
        System.out.println("2. Buscar cliente por DNI");
        System.out.println("3. Listar todos los clientes");
        System.out.println("4. Actualizar cliente");
        System.out.println("5. Borrar cliente");
        System.out.print("Seleccione una opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("DNI: ");
                String dni = sc.nextLine();
                System.out.print("Teléfono: ");
                int telefono = sc.nextInt();
                sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                boolean creado = controlador.crearCliente(nombre, dni, telefono, email);
                System.out.println(creado ? "Cliente creado." : "Error al crear cliente.");
                break;
            case 2:
                System.out.print("DNI: ");
                dni = sc.nextLine();
                Cliente c = controlador.buscarCliente(dni);
                System.out.println(c != null ? c : "Cliente no encontrado.");
                break;
            case 3:
            	 ArrayList<Cliente> listaClientes = controlador.listarClientes();
                 if (listaClientes.isEmpty()) {
                     System.out.println("No hay clientes registrados.");
                 } else {
                     System.out.println("\n--- Lista de Clientes ---");
                     for (Cliente c1 : listaClientes) {
                         System.out.println("ID: " + c1.getId_Cliente() +
                                            ", Nombre: " + c1.getNombre() +
                                            ", DNI: " + c1.getDni() +
                                            ", Teléfono: " + c1.getTelefono() +
                                            ", Email: " + c1.getEmail());
                     }
                 }
                 break;
            case 4:
                System.out.print("DNI del cliente a actualizar: ");
                dni = sc.nextLine();
                c = controlador.buscarCliente(dni);
                if (c != null) {
                    System.out.print("Nuevo nombre: ");
                    nombre = sc.nextLine();
                    System.out.print("Nuevo teléfono: ");
                    telefono = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo email: ");
                    email = sc.nextLine();
                    c.setNombre(nombre);
                    c.setTelefono(telefono);
                    c.setEmail(email);
                    boolean actualizado = controlador.actualizarCliente(c);
                    System.out.println(actualizado ? "Cliente actualizado." : "Error al actualizar.");
                } else {
                    System.out.println("Cliente no encontrado.");
                }
                break;
            case 5:
                System.out.print("DNI a borrar: ");
                dni = sc.nextLine();
                boolean borrado = controlador.borrarCliente(dni);
                System.out.println(borrado ? "Cliente eliminado." : "Error al eliminar.");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void menuUsuarios() {
        System.out.println("\n--- Gestión Usuarios ---");
        System.out.println("1. Crear usuario");
        System.out.println("2. Buscar usuario por DNI");
        System.out.println("3. Listar todos los usuarios");
        System.out.println("4. Actualizar usuario");
        System.out.println("5. Borrar usuario");
        System.out.println("6. Login usuario");
        System.out.print("Seleccione una opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("DNI: ");
                String dni = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                System.out.print("Rol: ");
                String rol = sc.nextLine();
                boolean creado = controlador.crearUsuario(nombre, dni, password, rol);
                System.out.println(creado ? "Usuario creado." : "Error al crear usuario.");
                break;
            case 2:
                System.out.print("DNI: ");
                dni = sc.nextLine();
                Usuario u = controlador.buscarUsuario(dni);
                System.out.println(u != null ? u : "Usuario no encontrado.");
                break;
            case 3:
            	 ArrayList<Usuario> listaUsuarios = controlador.listarUsuarios();
                 if (listaUsuarios.isEmpty()) {
                     System.out.println("No hay usuarios registrados.");
                 } else {
                     System.out.println("\n--- Lista de Usuarios ---");
                     for (Usuario u1 : listaUsuarios) {
                         System.out.println("ID: " + u1.getId_usuario() +
                                            ", Nombre: " + u1.getNombre_usuario() +
                                            ", DNI: " + u1.getDni() +
                                            ", Rol: " + u1.getRol());
                     }
                 }
                 break;
            case 4:
                System.out.print("DNI del usuario a actualizar: ");
                dni = sc.nextLine();
                u = controlador.buscarUsuario(dni);
                if (u != null) {
                    System.out.print("Nuevo nombre: ");
                    nombre = sc.nextLine();
                    System.out.print("Nueva password: ");
                    password = sc.nextLine();
                    System.out.print("Nuevo rol: ");
                    rol = sc.nextLine();
                    u.setNombre_usuario(nombre);
                    u.setPassword(password);
                    u.setRol(rol);
                    boolean actualizado = controlador.actualizarUsuario(u);
                    System.out.println(actualizado ? "Usuario actualizado." : "Error al actualizar.");
                } else {
                    System.out.println("Usuario no encontrado.");
                }
                break;
            case 5:
                System.out.print("DNI a borrar: ");
                dni = sc.nextLine();
                boolean borrado = controlador.borrarUsuario(dni);
                System.out.println(borrado ? "Usuario eliminado." : "Error al eliminar.");
                break;
            case 6:
                System.out.print("DNI: ");
                dni = sc.nextLine();
                System.out.print("Password: ");
                password = sc.nextLine();
                boolean loginOk = controlador.loginUsuario(dni, password);
                System.out.println(loginOk ? "Login correcto." : "Login incorrecto.");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void menuReparaciones() {
        System.out.println("\n--- Gestión Reparaciones ---");
        System.out.println("1. Crear reparación");
        System.out.println("2. Buscar reparación por matrícula");
        System.out.println("3. Listar todas las reparaciones");
        System.out.println("4. Actualizar reparación");
        System.out.println("5. Borrar reparación por matrícula");
        System.out.print("Seleccione una opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Descripción: ");
                String descripcion = sc.nextLine();
                System.out.print("Fecha entrada (yyyy-mm-dd): ");
                Date fechaEntrada = Date.valueOf(sc.nextLine());
                System.out.print("Coste estimado: ");
                double coste = sc.nextDouble();
                sc.nextLine();
                System.out.print("Estado: ");
                String estado = sc.nextLine();
                System.out.print("ID Vehículo: ");
                int vehiculoId = sc.nextInt();
                sc.nextLine();
                System.out.print("ID Usuario: ");
                int usuarioId = sc.nextInt();
                sc.nextLine();
                boolean creado = controlador.crearReparacion(descripcion, fechaEntrada, coste, estado, vehiculoId, usuarioId);
                System.out.println(creado ? "Reparación creada." : "Error al crear reparación.");
                break;
            case 2:
                System.out.print("Matrícula del vehículo: ");
                String matricula = sc.nextLine();
                Reparacion r = controlador.buscarReparacionPorMatricula(matricula);
                System.out.println(r != null ? r : "Reparación no encontrada.");
                break;
            case 3:
            	 ArrayList<Reparacion> listaReparaciones = controlador.listarReparaciones();
                 if (listaReparaciones.isEmpty()) {
                     System.out.println("No hay reparaciones registradas.");
                 } else {
                     System.out.println("\n--- Lista de Reparaciones ---");
                     for (Reparacion r1 : listaReparaciones) {
                         System.out.println("ID: " + r1.getId_reparacion() +
                                            ", Descripción: " + r1.getDescripcion() +
                                            ", Fecha Entrada: " + r1.getFecha_entrada() +
                                            ", Coste Estimado: " + r1.getCoste_estimado() +
                                            ", Estado: " + r1.getEstado() +
                                            ", ID Vehículo: " + r1.getVehiculo_id() +
                                            ", ID Usuario: " + r1.getUsuario_id());
                     }
                 }
                 break;
            case 4:
                System.out.print("Matrícula del vehículo de la reparación a actualizar: ");
                matricula = sc.nextLine();
                r = controlador.buscarReparacionPorMatricula(matricula);
                if (r != null) {
                    System.out.print("Nueva descripción: ");
                    descripcion = sc.nextLine();
                    System.out.print("Nuevo coste estimado: ");
                    coste = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Nuevo estado: ");
                    estado = sc.nextLine();
                    r.setDescripcion(descripcion);
                    r.setCoste_estimado(coste);
                    r.setEstado(estado);
                    boolean actualizado = controlador.actualizarReparacion(r);
                    System.out.println(actualizado ? "Reparación actualizada." : "Error al actualizar.");
                } else {
                    System.out.println("Reparación no encontrada.");
                }
                break;
            case 5:
                System.out.print("Matrícula del vehículo de la reparación a borrar: ");
                matricula = sc.nextLine();
                boolean borrado = controlador.borrarReparacion(matricula);
                System.out.println(borrado ? "Reparación eliminada." : "Error al eliminar.");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    public static void main(String[] args) {
        VistaTaller vista = new VistaTaller();
        vista.mostrarMenu();
    }
}

