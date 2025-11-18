package controlador;

import java.sql.Date;
import java.util.ArrayList;

import dao.mysql.ClienteDAOMySQL;
import dao.mysql.ReparacionDAOMySQL;
import dao.mysql.UsuarioDAOMySQL;
import dao.mysql.VehiculoDAOMySQL;
import entities.Cliente;
import entities.Reparacion;
import entities.Usuario;
import entities.Vehiculo;

public class ControladorTaller {
	
	public ControladorTaller(VehiculoDAOMySQL daoVehiculo, ClienteDAOMySQL daoCliente,
                             UsuarioDAOMySQL daoUsuario, ReparacionDAOMySQL daoReparacion) {
        this.vehiculoDAO = daoVehiculo;
        this.clienteDAO = daoCliente;
        this.usuarioDAO = daoUsuario;
        this.reparacionDAO = daoReparacion;
    }
	
    // Vehículo
	
    private VehiculoDAOMySQL vehiculoDAO;

    public boolean crearVehiculo(String matricula, String marca, int clienteId) {
        Vehiculo v = new Vehiculo(matricula, marca, clienteId);
        return vehiculoDAO.insert(v) == 0;
    }

    public Vehiculo buscarVehiculo(String matricula) {
        return vehiculoDAO.findByMatricula(matricula);
    }

    public ArrayList<Vehiculo> listarVehiculos() {
        return vehiculoDAO.findAll();
    }

    public boolean actualizarVehiculo(Vehiculo v) {
        return vehiculoDAO.update(v) == 0;
    }

    public boolean borrarVehiculo(String matricula) {
        return vehiculoDAO.delete(matricula) == 0;
    }

    // Cliente
    private ClienteDAOMySQL clienteDAO;

    public boolean crearCliente(String nombre, String dni, int telefono, String email) {
        Cliente c = new Cliente(nombre, dni, email, telefono);
        return clienteDAO.insert(c) != -1;
    }

    public Cliente buscarCliente(String dni) {
        return clienteDAO.findByDni(dni);
    }

    public ArrayList<Cliente> listarClientes() {
        return clienteDAO.findAll();
    }

    public boolean actualizarCliente(Cliente c) {
        return clienteDAO.update(c) == 0;
    }

    public boolean borrarCliente(String dni) {
        return clienteDAO.delete(dni) == 0;
    }

    // Usuario
    private UsuarioDAOMySQL usuarioDAO;

    public boolean crearUsuario(String nombre, String dni, String password, String rol) {
        Usuario u = new Usuario(nombre, dni, password, rol);
        return usuarioDAO.insert(u) == 0;
    }

    public Usuario buscarUsuario(String dni) {
        return usuarioDAO.findByDni(dni);
    }

    public ArrayList<Usuario> listarUsuarios() {
        return usuarioDAO.findAll();
    }

    public boolean actualizarUsuario(Usuario u) {
        return usuarioDAO.update(u) == 0;
    }

    public boolean borrarUsuario(String dni) {
        return usuarioDAO.delete(dni) == 0;
    }

    public boolean loginUsuario(String dni, String contrasenia) {
        return usuarioDAO.login(dni, contrasenia);
    }

    // Reparación
    private ReparacionDAOMySQL reparacionDAO;

    public boolean crearReparacion(String descripcion, Date fechaEntrada, double costeEstimado,
                                   String estado, int vehiculoId, int usuarioId) {
        Reparacion r = new Reparacion(descripcion, fechaEntrada, costeEstimado, estado, vehiculoId, usuarioId);
        return reparacionDAO.insert(r) == 0;
    }

    public Reparacion buscarReparacionPorMatricula(String matricula) {
        return reparacionDAO.findByMatricula(matricula);
    }

    public ArrayList<Reparacion> listarReparaciones() {
        return reparacionDAO.findAll();
    }

    public boolean actualizarReparacion(Reparacion r) {
        return reparacionDAO.update(r) == 0;
    }

    public boolean borrarReparacion(String matricula) {
        return reparacionDAO.delete(matricula) == 0;
    }
}
