package dao.mysql.interfaces;

import java.util.ArrayList;

import entities.Reparacion;

public interface ReparacionDAO {

	int insert(Reparacion r);

	int update(Reparacion r);

	int delete(String matricula);

	ArrayList<Reparacion> findAll();

	Reparacion findByMatricula(String matricula);
}
