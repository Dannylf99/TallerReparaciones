package dao.mysql.interfaces;

import java.util.ArrayList;

import entities.Vehiculo;

public interface VehiculoDAO {

	int insert(Vehiculo v);

	int update(Vehiculo v);

	int delete(String id_reparacion);

	ArrayList<Vehiculo> findAll();

	Vehiculo findByMatricula(String matricula);

}
