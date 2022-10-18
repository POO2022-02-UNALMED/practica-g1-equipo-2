package gestorAplicacion.modelos;

import java.io.Serializable;
import java.util.*;

import gestorAplicacion.operaciones.Venta;

public class Tienda extends Establecimiento implements Serializable {
    private static final long serialVersionUID = 1L;

    private static HashMap<Integer, TipoProducto> descuentos;
    private ArrayList<Producto> productos = new ArrayList<>();
    private ArrayList<Venta> ventas = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private ArrayList<Bodega> bodegas = new ArrayList<Bodega>();
    private int tamanoAlmacenamiento;
    private int minProducto;
    private Date fechaHoy;

    static {
        descuentos = new HashMap<>();
        descuentos.put(1, TipoProducto.Alimentos);
        descuentos.put(2, TipoProducto.Aseo);
        descuentos.put(3, TipoProducto.Hogar);
        descuentos.put(4, TipoProducto.Confiteria);
        descuentos.put(5, TipoProducto.Licores);
    }

    public Tienda() {
        super();
    }

    public Tienda(int tamanoAlmacenamiento, int minProducto, Date fechaHoy) {
        super(tamanoAlmacenamiento, minProducto);
        this.fechaHoy = fechaHoy;
    }

    // cacula la suma total de cantidad por producto y se la resta al
    // tamanoAlmacenamiento.
    public int verDisponibilidad() {
        int cantidadOcupada = 0;
        for (Producto producto : productos) {
            cantidadOcupada = producto.getCantidad();
        }
        return (tamanoAlmacenamiento - cantidadOcupada);
    }

    // como se genera la venta desde la clase Tienda??
    public Venta generarVenta(Date fecha, Cliente cliente, ArrayList<Producto> productos) {
        Venta nuevaVenta = new Venta(fecha, productos, cliente);
        ventas.add(nuevaVenta);
        cliente.agregarCompra(nuevaVenta);

        for (Producto productoVenta : productos) {
            Producto productoEnStock = buscarProductoPorID(productoVenta.getId());
            int cantidadProductoEnStock = productoEnStock.getCantidad();
            int cantidadProductoEnVenta = productoVenta.getCantidad();
            if ((cantidadProductoEnStock - cantidadProductoEnVenta) < 0) {
                pedirTraslado(productoEnStock, Math.abs(cantidadProductoEnStock-cantidadProductoEnVenta));
            }
            productoEnStock.setCantidad(cantidadProductoEnStock - cantidadProductoEnVenta);
        }

        return nuevaVenta;
    }


    // Seria bueno que retorne un Array con los productos con stock bajo??
    public void alertaStockBajo() {
        ArrayList<Producto> productosBajoStock = new ArrayList<Producto>();
        for (Producto producto : productos) {
            if (producto.getCantidad() < minProducto) {
                productosBajoStock.add(producto);
            }
        }
    }

    public void pedirTraslado(Producto producto, int cantidad) {
        /*
        HashMap<Bodega, Integer> bodegaIndicada = new HashMap<>();
        bodegaIndicada.put(null, 0);
        
        for (Bodega bodg : bodegas){
            Producto productoBodega = bodg.buscarProductoPorID(producto.getId());
            if (productoBodega != null && productoBodega.getCantidad() > (int) bodegaIndicada.values().toArray()[0]) bodegaIndicada.put(bodg,productoBodega.getCantidad());
        }
        */

        Bodega bodegaIndicada = null;
        int idProducto = producto.getId();
        
        for(Bodega bodg : bodegas){
            if (bodegaIndicada == null) bodegaIndicada = bodg;
            else if (bodg.buscarProductoPorID(idProducto).getCantidad() > bodegaIndicada.buscarProductoPorID(idProducto).getCantidad()) bodegaIndicada = bodg;
        }

        bodegaIndicada.trasladoTienda(idProducto, cantidad);
        producto.setCantidad(producto.getCantidad()+cantidad);
    }

    public void ofertaDia(ArrayList<Producto> productosVenta, Date fecha) {
        Iterator<Producto> itProd = productosVenta.iterator();
        while (itProd.hasNext()) {
            Producto tempProd = itProd.next();
            if (tempProd.getTipoProducto() == descuentos.get(fecha.getDate())) {
                double descuento = Math.random() * 0.05;
                tempProd.setDescuento(descuento);
            }
        }
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void agregarBodega(Bodega bodega) {
        bodegas.add(bodega);
    }

    public Cliente buscarClientePorIdentificacion(String documento) {
        Iterator<Cliente> itClien = clientes.iterator();
        while (itClien.hasNext()) {
            Cliente tempClien = itClien.next();
            if (tempClien.getDocumento().equals(documento)) {
                return tempClien;
            }
        }
        return null;
    }

    public Cliente buscarClientePorNombre(String nombre) {
        Iterator<Cliente> itClien = clientes.iterator();
        while (itClien.hasNext()) {
            Cliente tempClien = itClien.next();
            if (tempClien.getNombre().equals(nombre)) {
                return tempClien;
            }
        }
        return null;
    }

    @Override
    public int getMinProducto() {
        // TODO Auto-generated method stub
        return super.getMinProducto();
    }

    @Override
    public void setMinProducto(int minProducto) {
        // TODO Auto-generated method stub
        super.setMinProducto(minProducto);
    }

    @Override
    public int getTamanoAlmacenamiento() {
        // TODO Auto-generated method stub
        return super.getTamanoAlmacenamiento();
    }

    public Date getFechaHoy() {
        return fechaHoy;
    }

    public void setFechaHoy(Date fechaHoy) {
        this.fechaHoy = fechaHoy;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(Venta venta) {
        this.ventas.add(venta);
    }

    @Override
    public ArrayList<Producto> getProductos() {
        // TODO Auto-generated method stub
        return super.getProductos();
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(ArrayList<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

}
