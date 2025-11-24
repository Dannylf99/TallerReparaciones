package dao.mysql.interfaces;

import java.util.ArrayList;

import entities.Cliente;

public interface ClienteDAO {
	int insert(Cliente c);

	int update(Cliente c);

	int delete(String dni);

	ArrayList<Cliente> findAll();

	Cliente findByDni(String dni);
	
	Cliente findById(int id);
}
