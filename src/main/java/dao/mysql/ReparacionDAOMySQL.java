package dao.mysql;

import java.util.ArrayList;

import dao.DBConnection;
import dao.mysql.interfaces.*;

public class ReparacionDAOMySQL implements ReparacionInterface {

	DBConnection conexionJDBC = DBConnection.getInstance();

	@Override
	public int insert(ReparacionInterface r) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ReparacionInterface r) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String matricula) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<ReparacionInterface> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReparacionInterface findByDni(String matricula) {
		// TODO Auto-generated method stub
		return null;
	}

}
