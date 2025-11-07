package entities;

public class Usuario {

	private int id_usuario;
	private String nombre_usuario;
	private String password;
	private Rol rol;

	public Usuario(int id_usuario, String nombre_usuario, String password, Rol rol) {
		super();
		this.id_usuario = id_usuario;
		this.nombre_usuario = nombre_usuario;
		this.password = password;
		if (rol == Rol.Administrador | rol == Rol.Invitado | rol == Rol.Mecanico) {
			this.rol = rol;
		} else {
			System.out.println("El rol indicado no es válido, se le ha asignado el rol de Invitado.");
			this.rol = Rol.Invitado;
		}
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		if (rol == Rol.Administrador | rol == Rol.Invitado | rol == Rol.Mecanico) {
			this.rol = rol;
		} else {
			System.out.println("El rol indicado no es válido, se le ha asignado el rol de Invitado.");
			this.rol = Rol.Invitado;
		}
	}

}
