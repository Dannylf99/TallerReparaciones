package dao.mysql;

import java.util.ArrayList;

import dao.DBConnection;
import dao.mysql.interfaces.*;

public class ReparacionDAOMySQL implements ReparacionDAO {

	DBConnection conexionJDBC = DBConnection.getInstance();

	@Override
	public int insert(ReparacionDAO r) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ReparacionDAO r) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String matricula) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<ReparacionDAO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReparacionDAO findByDni(String matricula) {
		// TODO Auto-generated method stub
		return null;
	}

}
