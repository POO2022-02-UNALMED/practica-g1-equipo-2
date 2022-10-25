package gestorAplicacion.modelos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase abstracta encargada de representar las entidades asociadas a los establecimientos presentes en el sistema.
 * Tiene como atributos necesarios: tamaño de almacenamiento (sugerido), minimo de unidades por cada producto, lista de productos asociados.
 * @author Juan David Restrepo Montoya
 */
public abstract class Establecimiento implements Serializable {
	// Atributos
	private int tamanoAlmacenamiento;
	private int minProducto;
	private ArrayList<Producto> productos = new ArrayList<Producto>();

	//Constructor de la clase

	/**
	 * Constructor por defecto de la clase para inicializar atributos con valores predeterminados.
	 * tamaño sugerido de almacenamiento = 2000.
	 * minimo de unidades por producto = 10.
	 */
	protected Establecimiento() {
		this(2000,10);
	}

	/**
	 * Constructor con parámetros de la clase, para inicializar los atributos específicamente.
	 * @param tamanoAlmacenamiento Tamaño sugerido de almacenamiento en el establecimiento
	 * @param minProducto Minimo de unidades por producto en el stock
	 */
	protected Establecimiento(int tamanoAlmacenamiento, int minProducto) {
		this.tamanoAlmacenamiento = tamanoAlmacenamiento;
		this.minProducto = minProducto;
	}

	/**
	 * Método para ver la cantidad ocupada por el stock en el establecimiento, y ver disponibilidad sugerida de acuerdo al tamaño de almacenamiento sugerido.
	 * @return Cantidad disponible de unidades sugeridas
	 */
	public int verDisponibilidad() {
		int cantidadOcupada = 0;

		//Ciclo para obtener la cantidad total de productos que hay en la tienda
		for (Producto producto : getProductos()) {
			cantidadOcupada += producto.getCantidad();
		}
		// Se retorna el tamaño de la tienda menos la cantidad de productos que hay
		return (getTamanoAlmacenamiento() - cantidadOcupada);
	}


	// Getter & Setter

	/**
	 * Getter del tamaño de almacenamiento sugerido del establecimiento
	 * @return Tamaño sugerido de almacenamiento
	 */
	public int getTamanoAlmacenamiento() {
		return tamanoAlmacenamiento;
	}

	/**
	 * Setter del tamaño sugerido del almacenamiento del establecimiento
	 * @param tamanoAlmacenamiento Tamaño sugerido de almacenamiento
	 */
	public void setTamanoAlmacenamiento(int tamanoAlmacenamiento) {
		this.tamanoAlmacenamiento = tamanoAlmacenamiento;
	}

	/**
	 * Getter de los productos en el stock del establecimiento
	 * @return Stock de productos en el establecimiento
	 */
	public ArrayList<Producto> getProductos() {
		return productos;
	}

	/**
	 * Setter de los productos en el stock del establecimiento
	 * @param productos en el stock del establecimiento
	 */
	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	/**
	 * Getter del mínimo número de unidades por cada producto en el stock del establecimiento
	 * @return Mínimo de stock por cada producto
	 */
	public int getMinProducto() {
		return minProducto;
	}

	/**
	 * Setter del número de unidades mínimas de cada producto en el stock del establecimiento
	 * @param minProducto Mínimo número de unidades en el almacenamiento
	 */
	public void setMinProducto(int minProducto) {
		this.minProducto = minProducto;
	}

	/**
	 * Método para agregar un producto al stock en el establecimiento
	 * @param producto Producto a agregar
	 */
	public void agregarProducto(Producto producto){
		this.productos.add(producto);
	}


	// Métodos

	/**
	 * Método para buscar un producto por ID en el stock y devolver una copia del mismo
	 * @param id ID del producto
	 * @return Referencia a una copia del producto
	 */
	public Producto buscarProductoPorIDCopia(int id) {
		// Se busca en el array de productos el que coincida con el id pasado como argumento y se retorna una copia del producto
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


	/**
	 * Método para buscar un producto por nombre en el stock y devolver una copia del mismo
	 * @param nombre Nombre del producto
	 * @return Referencia a una copia del producto
	 */
	public Producto buscarProductoPorNombreCopia(String nombre) {
		
		// Se busca en el array de productos el que coincida con el nombre pasado como argumento y se retorna una copia del producto
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

	/**
	 * Método para buscar un producto por ID en el stock y devolver su referencia original
	 * @param id ID del producto
	 * @return Referencia original del producto
	 */
	public Producto buscarProductoPorIDOriginal(int id) {
		// Se busca en el array de productos el que coincida con el id pasado como argumento y se retorna la instancia del producto
		Iterator<Producto> itProd = productos.iterator();
		while (itProd.hasNext()) {
			Producto tempProd = itProd.next();
			if (tempProd.getId() == id) {

				return tempProd;
			}
		}
		return null;
	}

	/**
	 * Método para buscar un producto por nombre en el stock y devolver su referencia original
	 * @param nombre Nombre del producto
	 * @return Referencia original del producto
	 */
	public Producto buscarProductoPorNombreOriginal(String nombre) {
		
		// Se busca en el array de productos el que coincida con el nombre pasado como argumento y se retorna la instancia del producto
		Iterator<Producto> itProd = productos.iterator();
		while (itProd.hasNext()) {
			Producto tempProd = itProd.next();
			if (tempProd.getNombre().equals(nombre)) {
				return tempProd;
			}
		}
		return null;
	}
}
