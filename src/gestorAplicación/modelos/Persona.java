package gestorAplicacion.modelos;
import gestorAplicacion.operaciones.Operacion;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase abstracta encargada de representar lo relacionado a una entidad humana en el sistema.
 * Tiene como atributos importantes para su representación: nombre, documento, email, e histórico de operaciones.
 * @author Juan David Restrepo Montoya
 */
public abstract class Persona implements Serializable {
	// Atributos
	private String nombre;
	private String documento;
	private String email;
	private ArrayList<Operacion> historico = new ArrayList<Operacion>();

	// Constructores

	/**
	 * Constructor por defecto para generar una persona
	 */
	protected Persona() {
		this("Cristiano Ronaldo", "777777777", "elbicho@gmail.com");
	}

	/**
	 * Constructor con parámetros para generar una persona específica
	 * @param nombre Nombre de la persona
	 * @param documento Documento de la persona
	 * @param email Email de la persona
	 */
	protected Persona(String nombre, String documento, String email) {
		this.nombre = nombre;
		this.documento = documento;
		this.email = email;
	}

	// Getter & Setter

	/**
	 * Getter del nombre de la persona
	 * @return Nombre de la persona
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setter del nombre de la persona
	 * @param nombre Nombre de la persona
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Getter del documento de la persona
	 * @return Documento de la persona
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * Setter del documento de la persona
	 * @param documento Documento de la persona
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * Getter del email de la persona
	 * @return Email de la persona
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter del email de la persona
	 * @param email Email de la persona
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter del histórico de operaciones de la persona
	 * @return Histórico de operaciones de la persona
	 */
	public ArrayList<Operacion> getHistorico() {
		return historico;
	}

	/**
	 * Setter del histórico de operaciones de la persona
	 * @param historico Nombre de la persona
	 */
	public void setHistorico(ArrayList<Operacion> historico) {
		this.historico = historico;
	}

	/**
	 * Método para agregar una operación al historial de operaciones de la persona
	 * @param operacion Operacion hecha por la persona (venta generada por la tienda al cliente, o compra generada de la bodega al proveedor)
	 */
	public void agregarOperacion(Operacion operacion){
		historico.add(operacion);
	}

}
