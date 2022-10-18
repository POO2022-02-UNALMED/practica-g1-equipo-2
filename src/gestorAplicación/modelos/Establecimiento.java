package gestorAplicacion.modelos;

import java.util.ArrayList;
import java.util.Iterator;

public class Establecimiento {

	private int tamanoAlmacenamiento;
	private int minProducto;
	private ArrayList<Producto> productos = new ArrayList<Producto>();

	public Establecimiento() {
	}

	public Establecimiento(int tamanoAlmacenamiento, int minProducto) {
		this.tamanoAlmacenamiento = tamanoAlmacenamiento;
		this.minProducto = minProducto;
	}

	public int verDisponibilidad() {
		return 1;
	}

	public int getTamanoAlmacenamiento() {
		return tamanoAlmacenamiento;
	}

	public void setTamanoAlmacenamiento(int tamanoAlmacenamiento) {
		this.tamanoAlmacenamiento = tamanoAlmacenamiento;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Producto producto) {
		this.productos.add(producto);
	}

	public int getMinProducto() {
		return minProducto;
	}

	public void setMinProducto(int minProducto) {
		this.minProducto = minProducto;
	}

	public Producto buscarProductoPorID(int id) {
		Iterator<Producto> itProd = productos.iterator();
		while (itProd.hasNext()) {
			Producto tempProd = itProd.next();
			if (tempProd.getId() == id) {
				try {
					return (Producto) tempProd.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public Producto buscarProductoPorNombre(String nombre) {
		Iterator<Producto> itProd = productos.iterator();
		while (itProd.hasNext()) {
			Producto tempProd = itProd.next();
			if (tempProd.getNombre().equals(nombre)) {
				try {
					return (Producto) tempProd.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
