package gestorAplicacion.operaciones;

import java.util.ArrayList;
import java.util.Date;
import gestorAplicacion.modelos.Producto;

public abstract class Operacion {

	private Date fecha;
	private ArrayList<Producto> productos;

	protected Operacion(Date fecha, ArrayList<Producto> productos) {
		this.fecha = fecha;
	}

	protected Operacion(Date fecha) {
		this.fecha = fecha;
	}

	public abstract String generarFactura();

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Producto producto) {
		this.productos.add(producto);
	}

}
