package dao.mysql.interfaces;

import java.util.ArrayList;

public interface ReparacionDAO {

	int insert(ReparacionDAO r);

	int update(ReparacionDAO r);

	int delete(String matricula);

	ArrayList<ReparacionDAO> findAll();

	ReparacionDAO findByDni(String matricula);
}
