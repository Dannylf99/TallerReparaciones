package dao.mysql.interfaces;

import java.util.ArrayList;

import entities.Usuario;

public interface UsuarioDAO {
	
	boolean login(String usuario, String contrasenia);

	int insert(Usuario u);

	int update(Usuario u);

	int delete(String dni);

	ArrayList<Usuario> findAll();

	Usuario findByDni(String dni);

}
