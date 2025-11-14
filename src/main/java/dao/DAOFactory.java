package dao;

import dao.mysql.interfaces.*;

public interface DAOFactory {
	public UsuarioDAO getUsuarioDAO();
	public ClienteDAO getClienteDAO();
	public ReparacionDAO getReparacionDAO();
	public VehiculoDAO getVehiculoDAO();
}
