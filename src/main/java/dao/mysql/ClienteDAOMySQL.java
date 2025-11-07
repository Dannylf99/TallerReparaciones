package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import dao.mysql.interfaces.*;
import entities.Cliente;
import dao.DBConnection;

public class ClienteDAOMySQL implements ClienteInterface {

	private Connection conexion;

	DBConnection conexionJDBC = DBConnection.getInstance();

	public ClienteDAOMySQL() {
		conexion = DBConnection.getInstance().getConnection();
	}

	@Override
	public int insert(Cliente c) {
		try {
			String sql = "INSERT INTO cliente(nombre, dni, telefono, email) VALUES(?, ?, ?, ?)";
			PreparedStatement pst = conexion.prepareStatement(sql);

			pst.setString(1, c.getNombre()); // Posicion 1, valor nombre
			pst.setString(2, c.getDni()); // Posición 2, valor dni
			pst.setInt(3, c.getTelefono()); // Posición 3, valor teléfono
			pst.setString(4, c.getEmail()); // Posición 4, valor email>;

			int resul = pst.executeUpdate();
			System.out.print("resultado de la insercción " + resul);

		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			return -1;
		}
		return 0;
	}

	@Override
	public int update(Cliente c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String dni) {
		try {
			String sqlDelete = "DELETE FROM cliente WHERE dni = ?;";
			PreparedStatement pst = conexion.prepareStatement(sqlDelete);

			pst.setString(1, dni); // Posicion 1, valor 1

			int resul = pst.executeUpdate();

			if (resul > 0) {
				System.out.println("> OK. Persona con id 1 eliminada correctamente.");
			} else {
				System.out.println("> NOK. Persona no encontrada.");
				return -1;
			}

		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			return -1;
		}
		return 0;
	}

	@Override
	public ArrayList<ClienteInterface> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClienteInterface findByDni(String dni) {
		// TODO Auto-generated method stub
		return null;
	}

}
