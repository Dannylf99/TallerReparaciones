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
		String sqlUpdate = "UPDATE cliente SET nombre = ?, dni= ?, telefono= ?, email= ? WHERE id_cliente = ?";
		

		try {
			PreparedStatement pst = conexion.prepareStatement(sqlUpdate);

			pst.setString(1, c.getNombre());
			pst.setString(2, c.getDni());
			pst.setInt(3, c.getTelefono());
			pst.setString(4, c.getEmail());
			pst.setInt(5, c.getId_Cliente());
			int resul = pst.executeUpdate();
			if (resul > 0) {
				System.out.println("> OK. Persona con id" + c.getId_Cliente() + "actualizada correctamente.");
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
				System.out.println("> OK. Cliente con dni" + dni + "eliminada correctamente.");
			} else {
				System.out.println("> NOK. Cliente no encontrado.");
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
			String sql = "SELECT * FROM cliente;";
			resultado = stmt.executeQuery(sql);
		
			while (resultado.next()) {

				int id = resultado.getInt("id_cliente");
				
				String nombre = resultado.getString("nombre");
				
				String dni = resultado.getString("dni");
				
				int numTelefono = resultado.getInt("telefono");
				
				String email = resultado.getString("email");
				
				Cliente c = new Cliente(nombre,dni,email,numTelefono);
				
				c.setId_Cliente(id);
				
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
		String sql = "SELECT * FROM cliente WHERE dni = ?";
		try {
		PreparedStatement pst = conexion.prepareStatement(sql);
		pst.setString(1, dni);
		ResultSet resultado = pst.executeQuery();

		if (!resultado.next()) {
		    System.out.println("> Cliente no encontrado.");
		    return null;
		}
 
		int id = resultado.getInt("id_cliente");
		String nombre = resultado.getString("nombre");
		int telefono = resultado.getInt("telefono");
		String email = resultado.getString("email");

		Cliente c = new Cliente(nombre, dni, email, telefono);
		
		c.setId_Cliente(id);
		return c;
			
		} catch (SQLException e) {
			System.out.println("> NOK. Persona no encontrada.");
			System.out.println("> NOK:" + e.getMessage());
			return null;
		}
	}

	@Override
	public Cliente findById(int id) {

		String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

	    try (PreparedStatement pst = conexion.prepareStatement(sql)) {
	        pst.setInt(1, id);
	        ResultSet rs = pst.executeQuery();

	        if (!rs.next()) {
	            return null;
	        }

	        Cliente c = new Cliente(
	            rs.getString("nombre"),
	            rs.getString("dni"),
	            rs.getString("email"),
	            rs.getInt("telefono")
	        );
	        c.setId_Cliente(id);

	        return c;

	    } catch (SQLException e) {
	        System.out.println("> ERROR: " + e.getMessage());
	        return null;
	    }
		
	}


}
