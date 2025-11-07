package main;

import java.sql.Connection;

import controlador.*;
import dao.DBConnection;
import vista.*;
import entities.*;
import dao.mysql.*;

public class main {
	DBConnection conexionJDBC = DBConnection.getInstance();
	
	ClienteDAOMySQL cliente = new ClienteDAOMySQL();
	
	cliente.
}
