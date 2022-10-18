package gestorAplicacion.modelos;

import java.util.ArrayList;
import java.io.Serializable;

import gestorAplicacion.operaciones.Compra;

public class Bodega extends Establecimiento implements Serializable {
    private static final long serialVersionUID = 2L;

    public ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
    private ArrayList<Producto> productos = new ArrayList<Producto>();
    private int id;
    private int tamanoAlmacenamiento;
    private int minProducto;

    public Bodega(int tamanoAlmacenamiento, int minProducto, int id, ArrayList<Proveedor> proveedores) {
        super(tamanoAlmacenamiento, minProducto);
        this.id = id;
        this.proveedores = proveedores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Proveedor> getProveedores() {
        return proveedores;
    }

    @Override
    public int getTamanoAlmacenamiento() {
        return tamanoAlmacenamiento;
    }

    @Override
    public void setTamanoAlmacenamiento(int tamanoAlmacenamiento) {
        this.tamanoAlmacenamiento = tamanoAlmacenamiento;
    }

    @Override
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public int getMinProducto() {
        return minProducto;
    }

    @Override
    public void setMinProducto(int minProducto) {
        this.minProducto = minProducto;
    }

    public int verDisponibilidad() {
        return 0;
    }

    public void generarCompra(Compra compra) {
    }

    // Revisar si necesita recibir proveedor o nada
    public void agregarProveedor(Proveedor proveedor) {
    }

    public void despachoPorMayor(Producto producto) {

        Producto enStock = this.buscarProductoPorID(producto.getId());
        if ((enStock.getCantidad() - producto.getCantidad()) < 0) {

            // compra();
            enStock.setCantidad(enStock.getCantidad() - producto.getCantidad());
            System.out.println("El producto" + producto.getNombre() + " tardará un poco mas en ser despachado.");

        } else {

            enStock.setCantidad(enStock.getCantidad() - producto.getCantidad());

        }
    }
}
