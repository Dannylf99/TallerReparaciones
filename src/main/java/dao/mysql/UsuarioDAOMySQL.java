package dao.mysql;

import java.util.ArrayList;

import dao.DBConnection;
import dao.mysql.interfaces.*;

public class UsuarioDAOMySQL implements UsuarioInterface {

	DBConnection conexionJDBC = DBConnection.getInstance();

	@Override
	public int insert(UsuarioInterface u) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(UsuarioInterface u) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String dni) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<UsuarioInterface> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioInterface findByDni(String dni) {
		// TODO Auto-generated method stub
		return null;
	};
}
