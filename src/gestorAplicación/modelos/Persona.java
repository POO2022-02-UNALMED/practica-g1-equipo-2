package gestorAplicacion.modelos;

import gestorAplicacion.operaciones.Operacion;

import java.util.ArrayList;

public abstract class Persona {

	private String nombre;
	private String documento;
	private String email;
	private ArrayList<Operacion> historico = new ArrayList<Operacion>();

	protected Persona() {
	}

	protected Persona(String nombre, String documento, String email) {
		this.nombre = nombre;
		this.documento = documento;
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Operacion> getHistorico() {
		return historico;
	}

	public void setHistorico(Operacion operacion) {
		this.historico.add(operacion);
	}

}
