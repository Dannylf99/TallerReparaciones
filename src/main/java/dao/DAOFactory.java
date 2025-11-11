package dao;

import dao.mysql.interfaces.UsuarioDAO;

public interface DAOFactory {
	public UsuarioDAO getUsuarioDAO();
}
