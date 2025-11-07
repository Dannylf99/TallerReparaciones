package dao.mysql.interfaces;

import java.util.ArrayList;

public interface UsuarioInterface {

	int insert(UsuarioInterface u);

	int update(UsuarioInterface u);

	int delete(String dni);

	ArrayList<UsuarioInterface> findAll();

	UsuarioInterface findByDni(String dni);

}
