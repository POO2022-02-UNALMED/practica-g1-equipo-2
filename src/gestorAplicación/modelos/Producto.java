package gestorAplicacion.modelos;

import java.io.Serializable;

public class Producto implements Cloneable, Serializable {
    private static final long serialVersionUID = 3L;

    private String nombre;
    private int id;
    private double precioVenta;
    private double costoCompra;
    private int cantidad;
    private TipoProducto tipoProducto;
    private double descuento;

    public Producto(String nombre, int id, double precioVenta, double costoCompra, int cantidad,
            TipoProducto tipoProducto) {
        this.nombre = nombre;
        this.id = id;
        this.precioVenta = precioVenta;
        this.costoCompra = costoCompra;
        this.cantidad = cantidad;
        this.tipoProducto = tipoProducto;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getCostoCompra() {
        return costoCompra;
    }

    public void setCostoCompra(double costoCompra) {
        this.costoCompra = costoCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
