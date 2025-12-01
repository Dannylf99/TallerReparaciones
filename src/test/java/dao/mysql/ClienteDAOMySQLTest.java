package dao.mysql;


import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import entities.Cliente;

class ClienteDAOMySQLTest {

	@Test
	void test() {
		ClienteDAOMySQL clienteDAO = new ClienteDAOMySQL();

	        Cliente c1 = new Cliente("Juan Perez", "11111111g", "juan@email.com", 662777888);

	        // INSERT
	        clienteDAO.insert(c1);
	        System.out.println("Cliente creado: " + c1.getNombre() + " DNI: " + c1.getDni() 
	                           + "  ID asignado --> " + c1.getId_Cliente());

	        // UPDATE
	        c1.setTelefono(600600600);
	        clienteDAO.update(c1);

	        // FIND BY DNI
	        Cliente c2 = clienteDAO.findByDni("11111111g");
	        System.out.println("Cliente encontrado: " + c2);

	        // LISTAR TODOS
	        ArrayList<Cliente> listaClientes = clienteDAO.findAll();
	        for (Cliente c : listaClientes) {
	            System.out.println(c);
	        }

	        // DELETE
	        clienteDAO.delete("11111111g");


	}

}
