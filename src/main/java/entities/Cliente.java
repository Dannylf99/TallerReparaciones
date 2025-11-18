package entities;

public class Cliente {
	private int id_Cliente;
	private String nombre;
	private String email;
	private int telefono;
	private String dni;

	public Cliente(String nombre, String dni, String email, int telefono) {
		this.id_Cliente = 0;
		this.nombre = nombre;
		this.email = email;
		this.dni = dni;
		this.telefono = telefono;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getId_Cliente() {
		return id_Cliente;
	}

	public void setId_Cliente(int id_Cliente) {
		this.id_Cliente = id_Cliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
