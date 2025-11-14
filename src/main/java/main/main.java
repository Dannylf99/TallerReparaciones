package main;

import java.sql.Connection;

import org.omg.PortableInterceptor.ClientRequestInfo;

import controlador.*;
import dao.DBConnection;
import dao.MySQLDAOFactory;
import vista.*;
import entities.*;
import dao.mysql.*;

public class main {
	DBConnection conexionJDBC = DBConnection.getInstance();
	
	ClienteDAOMySQL cliente = new ClienteDAOMySQL();
	
	public main( String[] args ) {
//	MySQLDAOFactory factory = new MySQLDAOFactory();
//		
//	UsuarioDAOMySQL usuario = (UsuarioDAOMySQL) factory.getUsuarioDAO();
//			
//	Usuario usuario1 = new Usuario("Gonzalo","23242323x","petaca",Rol.Mecanico);
//			
//	usuario.insert(usuario1);
//				
//	System.out.println("Usuario: " + usuario1.getNombre_usuario() + "con dni " + usuario1.getDni() + "creado. \n>ID asignado: " + usuario1.getId_usuario());
	
	}
	
	
}
