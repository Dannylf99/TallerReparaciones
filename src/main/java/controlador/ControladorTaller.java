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
        boolean ok = usuarioDAO.login(dni, pass);
        if (!ok) return null;

        return usuarioDAO.findByDni(dni);
    }

    public void registrarReparacion(String matricula, String desc, double coste, String fecha, String dni) {
        Vehiculo v = vehiculoDAO.findByMatricula(matricula);

        if (v == null) {
            System.out.println("Vehículo no encontrado");
            return;
        }

        Usuario u = usuarioDAO.findByDni(dni);
        if (u == null) {
            System.out.println("Error: no hay usuario logueado.");
            return;
        }

        Reparacion r = new Reparacion(desc, Date.valueOf(fecha), coste, "EN REPARACIÓN",
                v.getId_vehiculo(), u.getId_usuario());

        reparacionDAO.insert(r);
        System.out.println("Reparación registrada.");
    }

    public void cambiarEstado(String matricula, String estado) {
    	ArrayList<Reparacion> lista = reparacionDAO.findByMatricula(matricula);
    	
    	
    	System.out.println("> Seleccione la reparación que desea cambiar, introduciendo el id que se indica:");
    	
    	for (Reparacion reparacion : lista) {
    		System.out.println("> Id: " + reparacion.getId_reparacion() + ". Descripción: " + reparacion.getDescripcion() + 
    				". Fecha entrada: " + reparacion.getFecha_entrada() + ". Coste estimado: " + reparacion.getCoste_estimado());
    	}
    	
    	System.out.println("> Seleccione el id:");
    	Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		
		/*
		 Se comprueba que el id está dentro de las reparaciones, si no la r
		 será null y no se podrá realizar.
		 */
		Reparacion r = null;
		
		for(Reparacion reparacion : lista) {
			if(reparacion.getId_reparacion() == id) {
				r = reparacionDAO.findById(id);
				break;
				}
		}
    	
        
        if (r == null) {
            System.out.println("Reparación no encontrada.");
            return;
        }

        r.setEstado(estado);

        reparacionDAO.update(r);
        System.out.println("Estado actualizado.");
    }

    public void altaCliente() {
        System.out.println("Alta de cliente no implementada en este snippet.");
    }

    public void bajaCliente() {
        System.out.println("Baja de cliente no implementada en este snippet.");
    }

    public void modificarCliente() {
        System.out.println("Modificar cliente no implementado en este snippet.");
    }

    public void altaVehiculo() {
        System.out.println("Alta de vehículo no implementada.");
    }

    public void bajaVehiculo() {
        System.out.println("Baja de vehículo no implementada.");
    }

    public void modificarVehiculo() {
        System.out.println("Modificar vehículo no implementado.");
    }

    public void mostrarEstadisticas() {
        int total = reparacionDAO.findAll().size();
        int finalizadas = reparacionDAO.countFinalizadas();

        System.out.println("Total reparaciones: " + total);
        System.out.println("Finalizadas: " + finalizadas);
    }
}
