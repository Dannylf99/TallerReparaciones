package dao.mysql;

import java.util.ArrayList;

import dao.DBConnection;
import dao.mysql.interfaces.*;

public class VehiculoDAOMySQL implements VehiculoInterface {

	DBConnection conexionJDBC = DBConnection.getInstance();

	@Override
	public int insert(VehiculoInterface v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(VehiculoInterface v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String id_reparacion) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<VehiculoInterface> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VehiculoInterface findById(String id_reparacion) {
		// TODO Auto-generated method stub
		return null;
	}
}
