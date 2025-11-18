package entities;

public class Usuario {

	private int id_usuario;
	private String nombre_usuario;
	private String password;
	String rol;
	private String dni;

	public Usuario(String nombre_usuario, String dni, String password, String rol) { //Rol rol) {
		this.nombre_usuario = nombre_usuario;
		this.password = password;
		this.dni = dni;
		this.rol = rol;

	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public /*Rol*/String getRol() {
		return rol;
	}

	public void setRol(String rol)  { //Rol rol) {

		this.rol = rol;
	}

}
