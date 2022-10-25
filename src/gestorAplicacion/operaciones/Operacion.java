package gestorAplicacion.operaciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import gestorAplicacion.modelos.Producto;

/**
 * Clase abstracta encargada de representar las operaciones disponibles en el sistema
 * Para la correcta especificación de una operación se tienen los parámetros: fecha y productos.
 * @author Juan Esteban Cadavid Arango
 */
public abstract class Operacion implements Serializable {

	private Date fecha;
	private ArrayList<Producto> productos;


	// Constructores

	/**
	 * Constructor con parámetros para inicializar los valores de la operación
	 * @param fecha Fecha de la operación
	 * @param productos Productos asociados a la operación
	 */
	protected Operacion(Date fecha, ArrayList<Producto> productos) {
		this.fecha = fecha;
		this.productos = productos;
	}


	// Método abstracto

	/**
	 * Método abstracto para definir la necesidad de implementar la generación de facturas de cada operación asociada
	 * @return Cadena de texto con un formato adecuado
	 */
	public abstract String generarFactura();

	// Getter & Setter

	/**
	 * Getter de la fecha de la operación
	 * @return Fecha de la operación
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Setter de la fecha de la operación
	 * @param fecha Fecha de la operación
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Getter de los productos asociados a la operación
	 * @return
	 */
	public ArrayList<Producto> getProductos() {
		return productos;
	}

	/**
	 * Setter de los productos asociados a la operación
	 * @param producto
	 */
	public void setProductos(Producto producto) {
		this.productos.add(producto);
	}

	/**
	 * Getter abstracto que define la necesidad de tener un total
	 * @return Total de la operación
	 */
	public abstract double getTotal();
}
