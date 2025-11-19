package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.DBConnection;
import dao.mysql.interfaces.*;
import entities.Rol;
import entities.Usuario;

public class UsuarioDAOMySQL implements UsuarioDAO {
	
	private Connection conexion;

	DBConnection conexionJDBC = DBConnection.getInstance();
	
	public UsuarioDAOMySQL() {
		conexion = DBConnection.getInstance().getConnection();
	}

	@Override
	public int insert(Usuario u) {
		try {
		    String sql = "INSERT INTO usuario(nombre_usuario, dni, password, rol) VALUES(?, ?, ?, ?)";
		    
		    // Prepare statement with option to return generated keys
		    PreparedStatement pst = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    
		    pst.setString(1, u.getNombre_usuario());
		    pst.setString(2, u.getDni());
		    pst.setString(3, PasswordUtils.hashPassword(u.getPassword()));
		    pst.setString(4, u.getRol().toString());

		    int resul = pst.executeUpdate();
		    
		    if (resul > 0) {
		        System.out.println("> OK. Usuario insertado correctamente.");
		        
		        // Recuperar ID
		        try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                int id = generatedKeys.getInt(1);
		                u.setId_usuario(id);
		                System.out.println("> OK. ID recuperado correctamente: " + id);
		            } else {
		                System.out.println("> WARNING: No se pudo recuperar el ID del usuario.");
		            }
		        }

		    } else {
		        System.out.println("> NOK. Usuario no insertado.");
		        return -1;
		    }

		} catch (SQLException e) {
		    System.out.println("> ERROR SQL: " + e.getMessage());
		    return -1;
		}

		return 0;

	}

	@Override
	public int update(Usuario u) {
		String nombre = u.getNombre_usuario();
		String dni = u.getDni();
		String password = u.getPassword();
		int id = u.getId_usuario();
		String rol = u.getRol().toString().toUpperCase();
		String sqlUpdate = "UPDATE usuario SET nombre_usuario = '" + nombre + "', dni= '" + dni + "', password= '" + password + "', rol= '" + rol + "' WHERE id_usuario = " + id + ";";
		try {
			PreparedStatement pst = conexion.prepareStatement(sqlUpdate);
			int resul = pst.executeUpdate();
			if (resul > 0) {
				System.out.println("> OK. Usuario con id" + id + "actualizada correctamente.");
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
	public int delete(String dni) {
		try {
			String sqlDelete = "DELETE FROM usuario WHERE dni = ?;";
			PreparedStatement pst = conexion.prepareStatement(sqlDelete);

			pst.setString(1, dni); // Posicion 1, valor 1

			int resul = pst.executeUpdate();
			
			if (resul > 0) {
				System.out.println("> OK. Usuario con dni" + dni + "eliminada correctamente.");
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
	public ArrayList<Usuario> findAll() {
		Statement stmt = null;
		ResultSet resultado = null;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		
		try {
			stmt = conexion.createStatement();
			String sql = "SELECT * FROM usuario;";
			resultado = stmt.executeQuery(sql);
		
			while (resultado.next()) {

				int id = resultado.getInt("id_usuario");
				
				String nombre = resultado.getString("nombre_usuario");
				
				String password = resultado.getString("password");
				
				String rolString = resultado.getString("rol");
				
				Rol rol = Rol.valueOf(rolString.toUpperCase());
				
				String dni = resultado.getString("dni");
				
				Usuario u = new Usuario(nombre, dni, password, rol.toString());
				
				u.setId_usuario(id);
				
				usuarios.add(u);
			}
			return usuarios;
			
		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			
			return null;
		}
		
	}

	@Override
	public Usuario findByDni(String dni) {
	    String query = "SELECT * FROM usuario WHERE dni = ?";
	    
	    try (PreparedStatement stmt = conexion.prepareStatement(query)) {

	        stmt.setString(1, dni);

	        try (ResultSet resultado = stmt.executeQuery()) {

	            if (!resultado.next()) {
	                System.out.println("> Usuario no encontrado.");
	                return null;
	            }

	            int id = resultado.getInt("id_usuario");
	            String nombre = resultado.getString("nombre_usuario");
	            String password = resultado.getString("password");
	            String rolString = resultado.getString("rol");

	            Rol rol = Rol.valueOf(rolString.toUpperCase());

	            Usuario u = new Usuario(nombre, dni, password, rol.toString());
	            u.setId_usuario(id);

	            return u;
	        }
	    } catch (SQLException e) {
	        System.out.println("> Error al buscar usuario por DNI.");
	        System.out.println("> Detalles: " + e.getMessage());
	        return null;
	    }
	}

	@Override
	public boolean login(String dni, String contrasenia) {
		ResultSet resultado = null;
		
		try {
			String sqlPassword = "SELECT password FROM usuario WHERE dni = ?;";
			PreparedStatement pst = conexion.prepareStatement(sqlPassword);

			pst.setString(1, dni); // Posicion 1, valor 1

			resultado = pst.executeQuery();
			
			if (!resultado.next()) {         
	            return false;                 
	        }
			
			String password = resultado.getString("password");
			
			if (PasswordUtils.verifyPassword(contrasenia, password)){
				System.out.println("> Contraseña correcta");
				return true;
			} else {
				System.out.println("> Contraseña incorrecta");
				return false;
			}
			
		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			
			return false;
		}
		
	}

	public Usuario findUsuarioLogueado(String dni) {
		
		return null;
	}



}
