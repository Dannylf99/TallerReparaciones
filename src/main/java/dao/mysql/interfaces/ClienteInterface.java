package dao.mysql.interfaces;

import java.util.ArrayList;

import entities.Cliente;

public interface ClienteInterface {
	int insert(Cliente c);

	int update(Cliente c);

	int delete(String dni);

	ArrayList<ClienteInterface> findAll();

	ClienteInterface findByDni(String dni);
}
