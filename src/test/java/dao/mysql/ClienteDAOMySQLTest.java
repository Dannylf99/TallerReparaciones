package dao.mysql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dao.DBConnection;
import entities.Cliente;

class ClienteDAOMySQLTest {

	@Test
	void test() {
		DBConnection conexionJDBC = DBConnection.getInstance();

		ClienteDAOMySQL cliente = new ClienteDAOMySQL();

		assertEquals(cliente.delete("12345678x"), 0);

	}

}
