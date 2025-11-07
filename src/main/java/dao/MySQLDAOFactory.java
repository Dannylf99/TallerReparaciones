package dao;

import java.util.Scanner;

public class MySQLDAOFactory implements DAOFactory {

	@Override
	public void insertarCliente() {

		Scanner sc = new Scanner(System.in);

		System.out.println("> Dame nombre: ");
		String nombre = sc.nextLine();

		System.out.println("> Dame e-mail: ");
		String email = sc.nextLine();

		System.out.println("> Dame telefono: ");
		String telefono = sc.nextLine();

		sc.close();

	}

	@Override
	public void insertarUsuario() {

		Scanner sc = new Scanner(System.in);

		System.out.println("> Dame nombre de usuario: ");
		String nombre = sc.nextLine();

		System.out.println("> Dame dni: ");
		int dni = sc.nextInt();
		sc.nextLine();

		System.out.println("> Dame e-mail: ");
		String email = sc.nextLine();

		System.out.println("> Dame telefono: ");
		String telefono = sc.nextLine();

		System.out.println("> Dime el rol que va a tener en la organización: ");
		String telefonoUsuario = sc.nextLine();

		sc.close();

	}

	@Override
	public void insertarVehiculo() {

		Scanner sc = new Scanner(System.in);

		System.out.println("> Dame matrícula: ");
		String matricula = sc.nextLine();

		System.out.println("> Dame dni del cliente: ");
		int dni = sc.nextInt();
		sc.nextLine();

		System.out.println("> Dame marca: ");
		String marca = sc.nextLine();

		sc.close();

	}

	@Override
	public void insertarReparacion() {
		Scanner sc = new Scanner(System.in);

		System.out.println("> Dame una descripción de la reparación: ");
		String matricula = sc.nextLine();

		System.out.println("> Dame dni del cliente: ");
		int dni = sc.nextInt();
		sc.nextLine();

		System.out.println("> Dame marca: ");
		String marca = sc.nextLine();

		sc.close();

	}

}
