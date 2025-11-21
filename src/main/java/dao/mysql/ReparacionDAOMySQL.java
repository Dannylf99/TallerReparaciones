package dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.DBConnection;
import dao.mysql.interfaces.*;
import entities.Reparacion;

public class ReparacionDAOMySQL implements ReparacionDAO {

	private Connection conexion;

	DBConnection conexionJDBC = DBConnection.getInstance();
	
	public ReparacionDAOMySQL() {
		conexion = DBConnection.getInstance().getConnection();
	}


	@Override
	public int insert(Reparacion r) {
		try {
		    String sql = "INSERT INTO reparacion(descripcion, fecha_entrada, coste_estimado, estado, vehiculo_id, usuario_id) VALUES(?, ?, ?, ?, ?, ?)";
		    
		    // Prepare statement with option to return generated keys
		    PreparedStatement pst = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    
		    pst.setString(1, r.getDescripcion());
		    pst.setDate(2, r.getFecha_entrada());
		    pst.setDouble(3, r.getCoste_estimado());
		    pst.setString(4, r.getEstado());
		    pst.setInt(5, r.getVehiculo_id());
		    pst.setInt(6, r.getUsuario_id());

		    int resul = pst.executeUpdate();
		    
		    if (resul > 0) {
		        System.out.println("> OK. Usuario insertado correctamente.");
		        
		        // Recuperar ID
		        try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                int id = generatedKeys.getInt(1);
		                r.setId_reparacion(id);
		                System.out.println("> OK. ID recuperado correctamente: " + id);
		            } else {
		                System.out.println("> WARNING: No se pudo recuperar el ID de la reparación.");
		            }
		        }

		    } else {
		        System.out.println("> NOK. Reparación no insertada.");
		        return -1;
		    }

		} catch (SQLException e) {
		    System.out.println("> ERROR SQL: " + e.getMessage());
		    return -1;
		}

		return 0;
	}

	@Override
	public int update(Reparacion r) {
		String descripcion = r.getDescripcion();
		Date fecha_entrada = r.getFecha_entrada();
		double coste_estimado = r.getCoste_estimado();
		String estado = r.getEstado();
		int vehiculo_id = r.getVehiculo_id();
		int usuario_id = r.getUsuario_id();
		int id = r.getId_reparacion();

		String sqlUpdate = "UPDATE reparacion SET descripcion= '" + descripcion + "', fecha_entrada= '" + fecha_entrada + "', coste_estimado= '" + coste_estimado 
				+ "', estado= '" + estado + "', vehiculo_id= '" + vehiculo_id + "', usuario_id= '" + usuario_id + "' WHERE id_reparacion = " + id + ";";
		
		try {
			PreparedStatement pst = conexion.prepareStatement(sqlUpdate);
			
			int resul = pst.executeUpdate();
			
			if (resul > 0) {
				System.out.println("> OK. Reparación con id " + id + " actualizada correctamente.");
			} else {
				System.out.println("> NOK. Reparación no encontrada.");
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
			String sqlDelete = "DELETE FROM reparacion WHERE matricula= ?;";
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
	public ArrayList<Reparacion> findAll() {
	    ArrayList<Reparacion> reparaciones = new ArrayList<>();

	    String sql = "SELECT * FROM reparacion";

	    try (Statement stmt = conexion.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            int id = rs.getInt("id_reparacion");
	            String descripcion = rs.getString("descripcion");
	            Date fechaEntrada = rs.getDate("fecha_entrada");
	            double costeEstimado = rs.getDouble("coste_estimado");
	            String estado = rs.getString("estado");
	            int vehiculoId = rs.getInt("vehiculo_id");
	            int usuarioId = rs.getInt("usuario_id");

	            Reparacion r = new Reparacion(descripcion, fechaEntrada, costeEstimado, estado, vehiculoId, usuarioId);
	            r.setId_reparacion(id);

	            reparaciones.add(r);
	        }

	    } catch (SQLException e) {
	        System.out.println("> NOK: Error al listar reparaciones.");
	        System.out.println("> Detalles: " + e.getMessage());
	        return null;
	    }

	    return reparaciones;
	}

	@Override
	public ArrayList<Reparacion> findByMatricula(String matricula) {
	    ArrayList<Reparacion> lista = new ArrayList<>();
	    String sql = "SELECT r.* FROM reparacion r " +
	                 "JOIN vehiculo v ON r.vehiculo_id = v.id_vehiculo " +
	                 "WHERE v.matricula = ?";

	    try {
	        PreparedStatement pst = conexion.prepareStatement(sql);
	        pst.setString(1, matricula);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            Reparacion r = new Reparacion(
	                rs.getString("descripcion"),
	                rs.getDate("fecha_entrada"),
	                rs.getDouble("coste_estimado"),
	                rs.getString("estado"),
	                rs.getInt("vehiculo_id"),
	                rs.getInt("usuario_id")
	            );
	            r.setId_reparacion(rs.getInt("id_reparacion"));
	            lista.add(r);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error en findByMatricula: " + e.getMessage());
	    }

	    return lista;
	}






	public ArrayList<Reparacion> findFinalizadas() {
		ArrayList<Reparacion> reparaciones = findAll();
		ArrayList<Reparacion> finalizadas = new ArrayList<>(); 
		for (Reparacion reparacion : reparaciones) {
			if(reparacion.getEstado().equalsIgnoreCase("FINALIZADO")) {
				finalizadas.add(reparacion);
			}
		}
		return finalizadas;
	}


	public int countFinalizadas() {
		ArrayList<Reparacion> finalizadas = findFinalizadas(); 
		return finalizadas.size();
	}


	public Reparacion findById(int id) {
		try {
			String sqlDelete = "SELECT * FROM reparacion WHERE matricula= ?;";
			PreparedStatement pst = conexion.prepareStatement(sqlDelete);

			pst.setInt(1, id); 
			
			 try (ResultSet rs = pst.executeQuery()) {
		            if (!rs.next()) {
		                System.out.println("> Reparación no encontrada para id: " + id);
		                return null;
		            }

		            String descripcion = rs.getString("descripcion");
		            Date fechaEntrada = rs.getDate("fecha_entrada");
		            double costeEstimado = rs.getDouble("coste_estimado");
		            String estado = rs.getString("estado");
		            int vehiculoId = rs.getInt("vehiculo_id");
		            int usuarioId = rs.getInt("usuario_id");

		            Reparacion r = new Reparacion(descripcion, fechaEntrada, costeEstimado, estado, vehiculoId, usuarioId);
		            r.setId_reparacion(id);
		            
		            return r;
		        }
			
			

		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			return null;
		}
	}


	public double filtrarPorCosteMedio(int opcion) {
		
		if (opcion == 0) {
		try {
			double media = 0;
			String sqlCoste = "SELECT AVG(coste_estimado) AS media FROM reparacion;";
			PreparedStatement pst = conexion.prepareStatement(sqlCoste);

			ResultSet rs = pst.executeQuery();

			
			if (rs.next()) {
			    media = rs.getDouble("media");
			}
		        
			media = Math.round(media * 100.0) / 100.0;
		    return media;
		        	

		} catch (SQLException e) {
			System.out.println("> NOK:" + e.getMessage());
			return -1;
		}
		} else if(opcion == 1){
			try {
				double media = 0;
				String sqlCoste = "SELECT AVG(coste_estimado) AS media FROM reparacion WHERE estado = 'EN PREPARACIÓN';";
				PreparedStatement pst = conexion.prepareStatement(sqlCoste);

				ResultSet rs = pst.executeQuery();

				
				if (rs.next()) {
				    media = rs.getDouble("media");
				}
			         
				media = Math.round(media * 100.0) / 100.0;
			    return media;
			        	

			} catch (SQLException e) {
				System.out.println("> NOK:" + e.getMessage());
				return -1;
			}

		} else if(opcion == 2) {
			try {
				double media = 0;
				String sqlCoste = "SELECT AVG(coste_estimado) AS media FROM reparacion WHERE estado = 'EN REPARACIÓN';";
				PreparedStatement pst = conexion.prepareStatement(sqlCoste);

				ResultSet rs = pst.executeQuery();

				
				if (rs.next()) {
				    media = rs.getDouble("media");
				}
			    
				media = Math.round(media * 100.0) / 100.0;
			    return media;
			        	

			} catch (SQLException e) {
				System.out.println("> NOK:" + e.getMessage());
				return -1;
			}
			
		} else if(opcion == 3) {
			try {
				double media = 0;
				String sqlCoste = "SELECT AVG(coste_estimado) AS media FROM reparacion WHERE estado = 'FINALIZADO';";
				PreparedStatement pst = conexion.prepareStatement(sqlCoste);

				ResultSet rs = pst.executeQuery();

				
				if (rs.next()) {
				    media = rs.getDouble("media");
				}
			     
				media = Math.round(media * 100.0) / 100.0;
			    return media;
			        	

			} catch (SQLException e) {
				System.out.println("> NOK:" + e.getMessage());
				return -1;
			} 
		} else {
			System.out.println("> Opción incorrecta.");
			return -1;
		}
		
	}
	
}
