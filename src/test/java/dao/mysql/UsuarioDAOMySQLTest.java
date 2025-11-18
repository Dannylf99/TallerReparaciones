package dao.mysql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



import org.junit.jupiter.api.Test;

import dao.MySQLDAOFactory;
import entities.Usuario;

class UsuarioDAOMySQLTest {

	@Test
	void test() {
		MySQLDAOFactory factory = new MySQLDAOFactory();
		
		UsuarioDAOMySQL usuario = (UsuarioDAOMySQL) factory.getUsuarioDAO();
		
		Usuario usuario1 = new Usuario("Gonzalo","23243434z","petaca", "ADMINISTRADOR");
			
		usuario.insert(usuario1);
			
//		System.out.println("Usuario: " + usuario1.getNombre_usuario() + " con DNI: " + usuario1.getDni()
//				+ "creado. \n>ID asignado: " + usuario1.getId_usuario());
//
//		usuario1.setDni("12345678x");
//
//		usuario.update(usuario1);
//
//		Usuario usuario2 = usuario.findByDni("22222221A");
//
//		System.out.println(usuario2);
//
//		usuario.delete("22222221A");
//
//		ArrayList<Usuario> usuariosPrint = usuario.findAll();
//
//		for (int i = 0; i < usuariosPrint.size(); i++) {
//			System.out.println(usuariosPrint.get(i));
//		}

		assertFalse(usuario.login("23243434z", "petaka"));
		assertTrue(usuario.login("23243434z", "petaca"));

	}

}
