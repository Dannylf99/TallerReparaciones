package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.DBConnection;
import dao.mysql.interfaces.*;
import entities.Vehiculo;

public class VehiculoDAOMySQL implements VehiculoDAO {

	private Connection conexion;

	DBConnection conexionJDBC = DBConnection.getInstance();
	
	public VehiculoDAOMySQL() {
		conexion = DBConnection.getInstance().getConnection();
	}


	@Override
	public int insert(Vehiculo v) {
		int rc = 0;
		try {
			String sql = "INSERT INTO vehiculo(matricula, marca, modelo, cliente_id) VALUES(?, ?, ?, ?)";
			PreparedStatement pst = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, v.getMatricula()); // Posicion 1, valor matricula
			pst.setString(2, v.getMarca()); // Posición 2, valor marca
			pst.setString(3, v.getModelo()); // Posicion 3, valor modelo
			pst.setInt(4, v.getCliente_id()); // Posición 4, valor cliente_id

			int resul = pst.executeUpdate();
			if (resul > 0) {
			} else {
				System.out.println("> NOK. Vehículo no insertado.");
				return -1;
			}
			
			try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int id = generatedKeys.getInt(1);
	                v.setId_vehiculo(id);
	            } else {
	                System.out.println("> WARNING: No se pudo recuperar el ID del vehículo.");
	            }
	        }

		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			return -1;
		}
		return rc;
	}

	@Override
	public int update(Vehiculo v) {
		
		String matricula = v.getMatricula();
		String marca = v.getMarca();
		String modelo = v.getModelo();
		int id_cliente= v.getCliente_id();
		int id = v.getId_vehiculo();
		
		String sqlUpdate = "UPDATE vehiculo SET matricula= '" + matricula + "', marca= '" + marca + "', modelo = '" + modelo + "', cliente_id= '" + id_cliente + "' WHERE id_vehiculo = " + id + ";";
		
		try {
			PreparedStatement pst = conexion.prepareStatement(sqlUpdate);
			
			int resul = pst.executeUpdate();
			
			if (resul > 0) {
				System.out.println("> OK. Vehículo con id" + id + "actualizado correctamente.");
			} else {
				System.out.println("> NOK. Vehículo no encontrado.");
				return -1;
			}
			
		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			return -1;
		}
		return 0;
	}

	@Override
	public int delete(String matricula) {
		try {
			String sqlDelete = "DELETE FROM vehiculo WHERE matricula= ?;";
			PreparedStatement pst = conexion.prepareStatement(sqlDelete);

			pst.setString(1, matricula); // Posicion 1, valor 1

			int resul = pst.executeUpdate();
			
			if (resul > 0) {
				System.out.println("> OK. Vehículo con matricula" + matricula + "eliminada correctamente.");
			} else {
				System.out.println("> NOK. Usuario no encontrado.");
				return -1;
			}

		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			return -1;
		}
		return 0;
	}

	@Override
	public ArrayList<Vehiculo> findAll() {
		Statement stmt = null;
		ResultSet resultado = null;
		ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		
		try {
			stmt = conexion.createStatement();
			String sql = "SELECT * FROM vehiculo;";
			resultado = stmt.executeQuery(sql);
		
			while (resultado.next()) {

				int id = resultado.getInt("id_vehiculo");
				
				String matricula = resultado.getString("matricula");
				
				String marca = resultado.getString("marca");
				
				String modelo = resultado.getString("modelo");
				
				int idCliente = resultado.getInt("cliente_id");
				
				Vehiculo v = new Vehiculo(matricula,marca,modelo,idCliente);
				v.setId_vehiculo(id);
				
				vehiculos.add(v);
			}
			
		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			
			return null;
		}
		
		return vehiculos;
	}

	@Override
	public Vehiculo findByMatricula(String matricula) {
	    String sql = "SELECT * FROM vehiculo WHERE matricula = ?";

	    try (PreparedStatement pst = conexion.prepareStatement(sql)) {
	        pst.setString(1, matricula);

	        try (ResultSet rs = pst.executeQuery()) {

	            if (!rs.next()) {
	                System.out.println("> Vehículo no encontrado para matrícula: " + matricula);
	                return null;
	            }

	            int id = rs.getInt("id_vehiculo");
	            String marca = rs.getString("marca");
	            String modelo = rs.getString("modelo");
	            int idCliente = rs.getInt("cliente_id");

	            Vehiculo v = new Vehiculo(matricula, marca, modelo, idCliente);
	            v.setId_vehiculo(id);

	            return v;
	        }

	    } catch (SQLException e) {
	        System.out.println("> NOK. Error al buscar vehículo.");
	        System.out.println("> Detalles: " + e.getMessage());
	        return null;
	    }
	}

}

