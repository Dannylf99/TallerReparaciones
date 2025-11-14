package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import dao.mysql.interfaces.*;
import entities.Cliente;
import dao.DBConnection;

public class ClienteDAOMySQL implements ClienteDAO {

	private Connection conexion;

	DBConnection conexionJDBC = DBConnection.getInstance();

	public ClienteDAOMySQL() {
		conexion = DBConnection.getInstance().getConnection();
	}

	@Override
	public int insert(Cliente c) {
	    String sql = "INSERT INTO cliente(nombre, dni, telefono, email) VALUES(?, ?, ?, ?)";
	    
	    try (PreparedStatement pst = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        pst.setString(1, c.getNombre());
	        pst.setString(2, c.getDni());
	        pst.setInt(3, c.getTelefono());
	        pst.setString(4, c.getEmail());
	        
	        int resul = pst.executeUpdate();
	        
	        if (resul > 0) {
	            System.out.println("> OK. Cliente insertado correctamente.");
	            
	            // Recuperar ID generado
	            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    int id = generatedKeys.getInt(1);
	                    c.setId_Cliente(id);
	                    System.out.println("> OK. ID del cliente recuperado: " + id);
	                    return id; 
	                } else {
	                    System.out.println("> WARNING: No se pudo recuperar el ID del cliente.");
	                    return -1;
	                }
	            }
	            
	        } else {
	            System.out.println("> NOK. Cliente no insertado.");
	            return -1;
	        }

	    } catch (SQLException e) {
	        System.out.println("> ERROR SQL: " + e.getMessage());
	        return -1;
	    }
	}


	@Override
	public int update(Cliente c) {
		String nombre = c.getNombre();
		String dni = c.getDni();
		int telefono = c.getTelefono();
		String email = c.getEmail();
		int id = c.getId_Cliente();
		String sqlUpdate = "UPDATE cliente SET nombre = '" + nombre + "', dni= '" + dni + "', telefono= '" + telefono + "', email= '" + email + "' WHERE id = " + id + ";";
		try {
			PreparedStatement pst = conexion.prepareStatement(sqlUpdate);
			int resul = pst.executeUpdate();
			if (resul > 0) {
				System.out.println("> OK. Persona con id" + id + "actualizada correctamente.");
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
	public int delete(String dni) {
		try {
			String sqlDelete = "DELETE FROM cliente WHERE dni = ?;";
			PreparedStatement pst = conexion.prepareStatement(sqlDelete);

			pst.setString(1, dni); // Posicion 1, valor 1

			int resul = pst.executeUpdate();
			
			if (resul > 0) {
				System.out.println("> OK. Persona con dni" + dni + "eliminada correctamente.");
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
	public ArrayList<Cliente> findAll() {
		Statement stmt = null;
		ResultSet resultado = null;
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		
		try {
			stmt = conexion.createStatement();
			String sql = "SELECT * FROM persona;";
			resultado = stmt.executeQuery(sql);
		
			while (resultado.next()) {

				int id = resultado.getInt("id_cliente");
				
				String nombre = resultado.getString("nombre");
				
				String dni = resultado.getString("dni");
				
				int numTelefono = resultado.getInt("telefono");
				
				String email = resultado.getString("email");
				
				Cliente c = new Cliente(id,nombre,dni,email,numTelefono);
				
				clientes.add(c);
			}
			
		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			
			return null;
		}
		
		return clientes;
	}

	@Override
	public Cliente findByDni(String dni) {
		Statement stmt = null;
		ResultSet resultado = null;
		
		try {
			stmt = conexion.createStatement();
			String find = "SELECT * FROM persona WHERE dni = '" + dni + "';" ;
			resultado = stmt.executeQuery(find);
		
			int id = resultado.getInt("id_cliente");
			
			String nombre = resultado.getString("nombre");
			
			int numTelefono = resultado.getInt("telefono");
			
			String email = resultado.getString("email");
			
			Cliente c = new Cliente(id,nombre,dni,email,numTelefono);
		
			return c;
			
		} catch (SQLException e) {
			System.out.println("> NOK. Persona no encontrada.");
			System.out.println("> NOK:" + e.getMessage());
			return null;
		}
	}

}
