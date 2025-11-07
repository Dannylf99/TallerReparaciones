package dao.mysql.interfaces;

import java.util.ArrayList;

public interface VehiculoInterface {

	int insert(VehiculoInterface v);

	int update(VehiculoInterface v);

	int delete(String id_reparacion);

	ArrayList<VehiculoInterface> findAll();

	VehiculoInterface findById(String id_reparacion);

}
