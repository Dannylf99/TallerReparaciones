package dao.mysql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dao.MySQLDAOFactory;
import entities.Usuario;

class UsuarioDAOMySQLTest {

	@Test
	void test() {
		MySQLDAOFactory factory = new MySQLDAOFactory();
		
		UsuarioDAOMySQL usuario = (UsuarioDAOMySQL) factory.getUsuarioDAO();
		
		Usuario usuario1 = new Usuario("Gonzalo","23243434P","petaca", "ADMINISTRADOR");
			
		usuario.insert(usuario1);
			

		usuario1.setDni("12345678P");

		usuario.update(usuario1);

		Usuario usuario2 = usuario.findByDni("22222226F");

		System.out.println(usuario2);

		usuario.delete("22222223C");

		ArrayList<Usuario> usuariosPrint = usuario.findAll();
	
		usuariosPrint.toString();


		assertFalse(usuario.login("23243434Q", "petaka"));
		assertTrue(usuario.login("23243434Q", "petaca"));

	}

}
