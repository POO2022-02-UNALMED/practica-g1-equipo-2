package gestorAplicacion.modelos;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import gestorAplicacion.operaciones.Compra;
import gestorAplicacion.operaciones.Operacion;

/**
 * Clase bodega que extiende de la clase abstracta Establecimiento, y representa la información correspondiente a las bodegas asociadas a la tienda
 * Tiene como atributos importantes para representar su información: Proveedores asociados, Compras asociadas, ID de la bodega.
 * @author Juan David Restrepo Montoya
 */
public class Bodega extends Establecimiento {
    private static final long serialVersionUID = 2L;

    // Atributos
    private ArrayList<Proveedor> proveedores = new ArrayList<>(); // Array con los proveedores a los cuales se
                                                                          // les pueden comprar productos
    private ArrayList<Operacion> compras = new ArrayList<>(); // Array con el historial de compras hechas a todos los
                                                                 // proveedores
    private int id;


    // Constructor

    /**
     * Constructor con parámetros asociados para generar instancias de la bodegas asociadas a la tienda, presentes en el sistema.0
     * @param tamanoAlmacenamiento Tamaño sugerido de almacenamiento para la bodega
     * @param minProducto Mínimo de unidades de cada producto en stock.
     * @param id ID de la bodega
     * @param proveedores Proveedores de la bodega.
     */
    public Bodega(int tamanoAlmacenamiento, int minProducto, int id, ArrayList<Proveedor> proveedores) {
        super(tamanoAlmacenamiento, minProducto);
        this.id = id;
        this.proveedores = proveedores;
    }

    // Getter & Setter

    /**
     * Getter del ID de la bodega
     * @return ID de la bodega
     */
    public int getId() {
        return id;
    }

    /**
     * Setter del ID de la bodega
     * @param id ID de la bodega
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter de los proveedores de la bodega
     * @return proveedores de la bodega
     */
    public ArrayList<Proveedor> getProveedores() {
        return proveedores;
    }

    /**
     * Setter de los proveedores de la bodega
     * @param proveedores Proveedores de la bodega
     */
    public void setProveedores(ArrayList<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    /**
     * Getter de las compras a los proveedores de la bodega
     * @return compras a los proveedores de la bodega
     */
    public ArrayList<Operacion> getCompras() {
        return compras;
    }

    /**
     * Setter de las compras de la bodega
     * @param compras Compras de la bodega
     */
    public void setCompras(ArrayList<Operacion> compras) {
        this.compras = compras;
    }

    // Métodos

    /**
     * Método para agregar un proveedor asociado a la bodega
     * @param proveedor Proveedor a agregar
     */
    public void agregarProveedor(Proveedor proveedor) {
        this.proveedores.add(proveedor);
    }

    // Metodo para agregar una compra hecha al array con el historial de compras

    /**
     * Metodo para agregar una compra hecha al array con el historial de compras
     * @param nuevaCompra Nueva compra realizada
     */
    private void agregarCompra(Compra nuevaCompra) {
        this.compras.add(nuevaCompra);
    }

    /**
     * Método para buscar el un proveedor dado el tipo de producto que vende
     * @param prodCompra Tipo de producto que vende el proveedor
     * @return Referencia a la instancia del proveedor
     */
    public Proveedor buscarProveedor(TipoProducto prodCompra) {
        // Se busca un proveedor en el Array de proveedores que corresponda al tipo de
        // producto que se pasa como parametro
        Iterator<Proveedor> itProv = proveedores.iterator();
        while (itProv.hasNext()) {
            Proveedor tempProv = itProv.next();
            if (tempProv.getProductoVende().equals(prodCompra)) {
                return tempProv;
            }
        }
        return null;
    }

    /**
     * Método para generar un despacho de un producto al por mayor, usado principalmente desde la funcionalidad asociada desde la interfaz.
     * @param fecha Fecha ddel despacho al por mayor
     * @param producto Producto a despachar
     */
    public void despachoPorMayor(Date fecha, Producto producto) {

        // Metodo para restar a los productos de la bodega las cantidades respectivas
        // cuadno se hacen ventas al por mayor

        // Se guarda el producto que se pasa como argumento (el que se va a vender)
        Producto enStock = this.buscarProductoPorIDOriginal(producto.getId());

        // Se guarda una copia del mismo aunque con la "cantidad" actualizada en un
        // array de productos (aunque este array solo contendrá un elemento)
        ArrayList<Producto> prodCompra = new ArrayList<>();
        Producto prodCompCopia = this.buscarProductoPorIDCopia(producto.getId());
        prodCompCopia.setCantidad(Math.abs(enStock.getCantidad() - producto.getCantidad()));
        prodCompra.add(prodCompCopia);

        // Condicional para tomar el caso en el que el producto no tenga suficiente
        // cantidad para realizar el pedido de venta
        // en ese caso se compra el exedente al respectivo proveedor
        if ((enStock.getCantidad() - producto.getCantidad()) < 0) {

            generarCompra(fecha, prodCompra);
            enStock.setCantidad(enStock.getCantidad() - producto.getCantidad());

        } else {

            enStock.setCantidad(enStock.getCantidad() - producto.getCantidad());

        }
    }

    /**
     * Método encargado de generar una compra de los productos solicitados a los proveedores.
     * @param fecha Fecha de la compra
     * @param productos Productos solicitados
     * @return Instancia de la compra realizada
     */
    public Compra generarCompra(Date fecha, ArrayList<Producto> productos) {

        // Metodo para realizar una compra de productos a un proveedor

        // Se guarda el proveedor al cual se le va a realizar la compra (cada compra se
        // hace a un proveedor con productos de un mismo tipo)
        Proveedor proveedorProd = buscarProveedor(productos.get(0).getTipoProducto());

        // Se crea la instancia de el proceso de compra, se calcula el costo de
        // transporte y los egresos totales
        Compra compraActual = new Compra(fecha, productos, proveedorProd);
        compraActual.costoTransporte();
        compraActual.calcularEgresos();

        // Se agrega la compra al historial de compras y tambien al hisotrial de su
        // respectivo proveedor
        compras.add(compraActual);
        proveedorProd.agregarOperacion(compraActual);

        // Se actualizan las cantidades de los productos comprados o se agregan en caso
        // de ser productos nuevos
        for (Producto prod : productos) {
            Producto prodStock = buscarProductoPorIDOriginal(prod.getId());
            if (prodStock != null) {
                prodStock.setCantidad(prodStock.getCantidad() + prod.getCantidad());
            } else {
                agregarProducto(prod);
            }
        }

        return compraActual;
    }

    /**
     * Método encargado de generar un traslado del stock de la bodega, hacia el stock de la tienda
     * @param fecha Fecha en la que se genera el traslado
     * @param idProducto ID del producto que se solicita en traslado
     * @param cantidad Cantidad de unidades solicitadas del producto
     */
    public void trasladoTienda(Date fecha, int idProducto, int cantidad) {

        // Metodo para hacer un traslado de productos a la tienda

        // Se busca el producto a ser trasladado
        Producto prod = buscarProductoPorIDOriginal(idProducto);

        // Condicional para verificar que la cantidad solicitada en el traslado sea
        // suficiente
        // En caso de no serlo, se genera una compra de la cantidad faltante
        if ((prod.getCantidad() - cantidad) < 0) {
            ArrayList<Producto> productoIndividual = new ArrayList<>();
            Producto individual = buscarProductoPorIDCopia(idProducto);
            individual.setCantidad(Math.abs(prod.getCantidad() - cantidad));
            productoIndividual.add(individual);
            generarCompra(fecha, productoIndividual);
        }
        // Se actualiza la cantidad del producto trasladado en la bodega
        prod.setCantidad(prod.getCantidad() - cantidad);
    }

}
