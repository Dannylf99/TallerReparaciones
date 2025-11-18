 package dao.mysql;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dao.MySQLDAOFactory;

import entities.Reparacion;
import entities.Usuario;
import entities.Vehiculo;

class ReparacionDAOMySQLTest {

	@Test
	void test() {
		 MySQLDAOFactory factory = new MySQLDAOFactory();
	        ReparacionDAOMySQL reparacionDAO = (ReparacionDAOMySQL) factory.getReparacionDAO();
	        VehiculoDAOMySQL cochesDAO = (VehiculoDAOMySQL) factory.getVehiculoDAO();
	        UsuarioDAOMySQL usuariosDAO = (UsuarioDAOMySQL) factory.getUsuarioDAO();
	        
	        Usuario usuario1 = new Usuario("Pepito","14567896A","petaca", "ADMINISTRADOR");
	        usuariosDAO.insert(usuario1);
	        int usuariosId = usuario1.getId_usuario();
	        System.out.println(usuariosId);
			
	        
	        Vehiculo v = new Vehiculo("1234PIP", "Toyota", 1); 
	        cochesDAO.insert(v);
	        int vehiculoId = v.getId_vehiculo();
	        System.out.println(vehiculoId);

	        Reparacion r1 = new Reparacion(
	                "Cambio de aceite",
	                Date.valueOf("2025-11-18"),
	                100.0,
	                "EN REPARACIÓN",
	                vehiculoId,
	                usuariosId
	        );

	        int insertResult = reparacionDAO.insert(r1);
	        assertEquals(0, insertResult);
	        assertTrue(r1.getId_reparacion() > 0);

	        r1.setEstado("REPARADO");
	        r1.setCoste_estimado(120.0);
	        int updateResult = reparacionDAO.update(r1);
	        assertEquals(0, updateResult);
	       

	        Reparacion r2 = reparacionDAO.findByMatricula("1234PIP");
	        int nVehiculo = r2.getVehiculo_id();
	        if (r2 != null) {
	            assertEquals("1234PIP", r2.getVehiculo_id() == nVehiculo ? "1234PIP" : "");
	        }

	        ArrayList<Reparacion> listaReparaciones = reparacionDAO.findAll();
	        assertNotNull(listaReparaciones);
	        assertTrue(listaReparaciones.size() > 0);
	        System.out.println(listaReparaciones);
	}

}
