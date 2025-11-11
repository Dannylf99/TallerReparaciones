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

	@Override
	public int insert(Usuario u) {
		try {
			String sql = "INSERT INTO usuario(nombre_usuario, dni, password, rol) VALUES(?, ?, ?, ?)";
			PreparedStatement pst = conexion.prepareStatement(sql);

			pst.setString(1, u.getNombre_usuario()); // Posicion 1, valor nombre
			pst.setString(2, u.getDni()); // Posición 2, valor dni
			pst.setString(3, u.getPassword()); // Posición 3, valor password
			pst.setString(4, u.getRol().toString()); // Posición 4, valor rol;

			int resul = pst.executeUpdate();
			if (resul > 0) {
				System.out.println("> OK. Usuario insertado correctamente.");
			} else {
				System.out.println("> NOK. Persona no insertada.");
				return -1;
			}

		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
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
		String sqlUpdate = "UPDATE usuario SET nombre_usuario = '" + nombre + "', dni= '" + dni + "', password= '" + password + "', rol= '" + rol + "' WHERE id = " + id + ";";
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
		// TODO Auto-generated method stub
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
				
				Usuario u = new Usuario(id,nombre,dni,password,rol);
				
				usuarios.add(u);
			}
			
		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			
			return null;
		}
		
		return usuarios;
	}

	@Override
	public Usuario findByDni(String dni) {
		Statement stmt = null;
		ResultSet resultado = null;
		
		try {
			stmt = conexion.createStatement();
			String find = "SELECT * FROM usuario WHERE dni = '" + dni + "';" ;
			
			resultado = stmt.executeQuery(find);
		
			int id = resultado.getInt("id_usuario");
			
			String nombre = resultado.getString("nombre_usuario");
			
			String password = resultado.getString("password");
			
			String rolString = resultado.getString("rol");
			
			Rol rol = Rol.valueOf(rolString.toUpperCase());
			
			Usuario u = new Usuario(id,nombre, dni, password, rol);
		
			return u;
			
		} catch (SQLException e) {
			System.out.println("> NOK. Usuario no encontrado.");
			System.out.println("> NOK:" + e.getMessage());
			return null;
		}
	}


}
