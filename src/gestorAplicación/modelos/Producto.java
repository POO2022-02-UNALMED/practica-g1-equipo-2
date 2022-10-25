package gestorAplicacion.modelos;

import java.io.Serializable;


/**
 * Clase que representa los diversos productos que entran al stock de la tienda.
 * Tiene como atributos destacables: nombre, id, precio de venta, costo de compra, cantidad, tipo de producto y descuento.
 * Implementa interfaces necesarias para el correcto funcionamiento de ciertas funcionalidades de la aplicacion.
 * @author David Castrillón Vallejo
 */
public class Producto implements Cloneable, Serializable {
    private static final long serialVersionUID = 3L;

    
    /* La clase Producto contiene la informacion relacionada con cada producto instanciado en la tienda y en las bodegas
     Como su precio de venta y de compra, la cantidad, el tipo de producto, su id y su nombre
     */

    // Atributos
    private String nombre;
    private int id;
    private double precioVenta;
    private double costoCompra;
    private int cantidad;
    private TipoProducto tipoProducto;
    private double descuento;

    // Constructor

    /**
     * Constructor para la generación de instancias tipo Producto
     * @param nombre Nombre del producto
     * @param id ID representativo del producto en el stock tanto de la tienda como de la bodega
     * @param precioVenta Precio al cual se desea vender el producto
     * @param costoCompra Precio al cual se compró el producto al proveedor
     * @param cantidad Cantidad de unidades disponibles (dependiendo del stock en el que esté, es decir, bodega o tienda)
     * @param tipoProducto Tipo de producto, es decir la categoría a la que pertenece (ASEO, HOGAR, ALIMENTOS, LICORES, CONFITERIA)
     */
    public Producto(String nombre, int id, double precioVenta, double costoCompra, int cantidad,
            TipoProducto tipoProducto) {
        this.nombre = nombre;
        this.id = id;
        this.precioVenta = precioVenta;
        this.costoCompra = costoCompra;
        this.cantidad = cantidad;
        this.tipoProducto = tipoProducto;
    }


    /**
     * Método implementado dado la interfaz Cloneable, usado para clonar el objeto cuando sea necesario, por ejemplo, cuando se va a hacer una venta
     * @return Retorna el objeto clonado
     * @throws CloneNotSupportedException
     */
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Getters & Setters

    /**
     * Getter del nombre del producto
     * @return Nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del nombre del producto
     * @param nombre Nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter del ID del producto
     * @return El ID del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Setter del ID del producto
     * @param id ID del producto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter del precio de venta del producto
     * @return El precio de venta del producto
     */
    public double getPrecioVenta() {
        return precioVenta;
    }

    /**
     * Setter del precio de venta del producto
     * @param precioVenta Precio de venta del producto
     */
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    /**
     * Getter del costo de compra del producto
     * @return El costo de compra del producto
     */
    public double getCostoCompra() {
        return costoCompra;
    }

    /**
     * Setter del costo de compra del producto
     * @param costoCompra Costo de compra del producto
     */
    public void setCostoCompra(double costoCompra) {
        this.costoCompra = costoCompra;
    }

    /**
     * Getter de la cantidad del producto
     * @return La cantidad del producto
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Setter de la cantidad del producto
     * @param cantidad Nombre del producto
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Getter del tipo del producto
     * @return El tipo del producto
     */
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    /**
     * Setter del tipo del producto
     * @param tipoProducto Tipo del producto
     */
    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    /**
     * Getter del descuento del producto
     * @return EL descuento del producto
     */
    public double getDescuento() {
        return descuento;
    }

    /**
     * Setter del descuento del producto
     * @param descuento Descuento del producto
     */
    void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
