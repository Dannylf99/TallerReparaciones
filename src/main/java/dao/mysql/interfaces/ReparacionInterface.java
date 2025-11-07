package dao.mysql.interfaces;

import java.util.ArrayList;

public interface ReparacionInterface {

	int insert(ReparacionInterface r);

	int update(ReparacionInterface r);

	int delete(String matricula);

	ArrayList<ReparacionInterface> findAll();

	ReparacionInterface findByDni(String matricula);
}
