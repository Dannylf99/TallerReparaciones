package dao.mysql;


import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dao.MySQLDAOFactory;
import entities.Vehiculo;

class VehiculoDAOMySQLTest {

	@Test
	void test() {
		

		        MySQLDAOFactory factory = new MySQLDAOFactory();
		        VehiculoDAOMySQL vehiculoDAO = (VehiculoDAOMySQL) factory.getVehiculoDAO();


		        Vehiculo v1 = new Vehiculo("1235ABC", "Toyota", 20); // cliente_id = 1 (existente)
		        vehiculoDAO.insert(v1);
		        System.out.println("Vehículo creado: " + v1.getMatricula() + "  ID asignado --> " + v1.getId_vehiculo());


		        v1.setMarca("Toyota Corolla");
		        vehiculoDAO.update(v1);
		        System.out.println("Vehículo actualizado: " + v1.getMatricula() + " Marca: " + v1.getMarca());


		        Vehiculo v2 = vehiculoDAO.findByMatricula("1235ABC");
		        if (v2 != null) {
		            System.out.println("Vehículo encontrado: " + v2.getMatricula() + " Marca: " + v2.getMarca());
		        }


		        ArrayList<Vehiculo> listaVehiculos = vehiculoDAO.findAll();
		        System.out.println("\n=== LISTA DE VEHÍCULOS ===");
		        for (Vehiculo v : listaVehiculos) {
		            System.out.println(v);
		        }


		        vehiculoDAO.delete("1235ABC");
		        System.out.println("Vehículo borrado con matrícula: 1234ABC");
		    }
	

}
