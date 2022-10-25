package gestorAplicacion.modelos;

import java.util.*;

import gestorAplicacion.operaciones.Operacion;
import gestorAplicacion.operaciones.Venta;

/**
 * Clase representa la tienda y sus respectivos estados.
 * Esta clase tiene como atributos los siguientes datos para el manejo del
 * sistema: descuentos (asociados al dia de la semana), ventas, clientes y
 * bodegas asociadas.
 * Extiende de la clase abstracta Establecimiento.
 * 
 * @author David Castrillón Vallejo
 */
public class Tienda extends Establecimiento {
    private static final long serialVersionUID = 1L;

    // atributos que contienen descuentos, ventas, clientes, bodegas... que son las
    // partes que componen la tienda
    private static HashMap<Integer, TipoProducto> descuentos;
    private ArrayList<Operacion> ventas = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private ArrayList<Bodega> bodegas = new ArrayList<Bodega>();
    private Date fechaHoy;

    // Días en los que se aplica descuentos a los productos que se venden en la
    // tienda
    // (1: lunes, 2: martes, 3: miercoles, 4: jueves, 5: viernes)
    static {
        descuentos = new HashMap<>();
        descuentos.put(1, TipoProducto.Alimentos);
        descuentos.put(2, TipoProducto.Aseo);
        descuentos.put(3, TipoProducto.Hogar);
        descuentos.put(4, TipoProducto.Confiteria);
        descuentos.put(5, TipoProducto.Licores);
    }

    /**
     * Constructor por defecto para generar instancias de la tienda
     */
    public Tienda() {
        super();
    }

    // Constructor de la tienda

    /**
     * Constructor con los parámetros necesarios para crear una tienda específica
     * 
     * @param tamanoAlmacenamiento Tamaño sugerido para tener en stock (no es un
     *                             limitante)
     * @param minProducto          Minimo de cada producto para generar alertas en
     *                             caso de tener stock bajo
     * @param fechaHoy             Fecha actual en la tienda
     */
    public Tienda(int tamanoAlmacenamiento, int minProducto, Date fechaHoy) {
        super(tamanoAlmacenamiento, minProducto);
        this.fechaHoy = fechaHoy;
    }

    // Getter & Setters

    /**
     * Getter de la fecha actual
     * 
     * @return Fecha actual
     */
    public Date getFechaHoy() {
        return fechaHoy;
    }

    /**
     * Setter de la fecha actual
     * 
     * @param fechaHoy Fecha actual
     */
    public void setFechaHoy(Date fechaHoy) {
        this.fechaHoy = fechaHoy;
    }

    /**
     * Getter de las ventas asociadas a la tienda
     * 
     * @return Ventas asociadas a la tienda
     */
    public ArrayList<Operacion> getVentas() {
        return ventas;
    }

    /**
     * Setter de las ventas asociadas a la tienda
     * 
     * @param ventas Ventas asociadas a la tienda
     */
    public void setVentas(ArrayList<Operacion> ventas) {
        this.ventas = ventas;
    }

    /**
     * Getter de los clientes de la tienda
     * 
     * @return Clientes de la tienda
     */
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    /**
     * Setter de los clientes de la tienda
     * 
     * @param clientes Clientes de la tienda
     */
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * Getter de las bodegas asociadas a la tienda
     * 
     * @return Bodegas asociadas a la tienda
     */
    public ArrayList<Bodega> getBodegas() {
        return bodegas;
    }

    /**
     * Setter de las bodegas asociadas a la tienda
     * 
     * @param bodegas Bodegas asociadas a la tienda
     */
    public void setBodegas(ArrayList<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    // Métodos

    // agregara una venta al ArrayList de las ventas}

    /**
     * Método para agregar una venta al historial de ventas de la tienda
     * 
     * @param venta Venta a agregar
     */
    public void agregarVenta(Venta venta) {
        ventas.add(venta);
    }

    // calcula la suma total de cantidad por producto y se la resta al
    // tamanoAlmacenamiento.

    /**
     * Método para generar una venta de varios productos. Se hacen comprobaciones
     * importantes en base al stock de cada producto en la tienda, si no hay stock
     * suficiente se pide un traslado a las bodegas del número faltante necesario
     * para realizar la venta
     * Usado principalmente por la opción de la interfaz "GenerarVenta".
     * 
     * @param fecha     En la cual se realiza la venta
     * @param cliente   Referencia del cliente al que se le hace la venta
     * @param productos Lista de productos asociados a la venta
     * @return Retorna instancia de tipo Operacion para enlazar con la interfaz
     */
    public Operacion generarVenta(Date fecha, Cliente cliente, ArrayList<Producto> productos) {
        // Metodo para generar una venta desde la tienda

        // Se crea una nueva instancia de venta con sus respectivos argumentos
        // solicitados
        Operacion nuevaVenta = new Venta(fecha, productos, cliente);

        // Se agrega la venta al historial de ventas de la tienda y al historial de
        // compras del cliente al cual se le hizo la venta
        ventas.add(nuevaVenta);
        cliente.agregarOperacion(nuevaVenta);

        // Ciclo para actualizar las cantidades de los productos vendidos en la tienda
        // En caso de no tener suficiente cantidad de determinado producto para hacer la
        // venta, éste es traido desde alguna bodega
        for (Producto productoVenta : productos) {
            Producto productoEnStock = buscarProductoPorIDOriginal(productoVenta.getId());
            int cantidadProductoEnStock = productoEnStock.getCantidad();
            int cantidadProductoEnVenta = productoVenta.getCantidad();
            if ((cantidadProductoEnStock - cantidadProductoEnVenta) < 0) {
                pedirTraslado(fecha, productoEnStock, Math.abs(cantidadProductoEnStock - cantidadProductoEnVenta));
            }
            productoEnStock.setCantidad(productoEnStock.getCantidad() - cantidadProductoEnVenta);
        }

        return nuevaVenta;
    }

    // sobrecarga de método en caso que sólo sea un producto

    /**
     * Método para generar una venta de un solo producto. Se hacen comprobaciones
     * importantes en base al stock de cada producto en la tienda, si no hay stock
     * suficiente se pide un traslado a las bodegas del número faltante necesario
     * para realizar la venta
     * Usado principalmente por la opción de la interfaz "GenerarVenta".
     * 
     * @param fecha    En la cual se realiza la venta
     * @param cliente  Referencia del cliente al que se le hace la venta
     * @param producto Producto asociado a la venta
     * @return Retorna instancia de tipo Operacion para enlazar con la interfaz
     */
    public Operacion generarVenta(Date fecha, Cliente cliente, Producto producto) {
        // Metodo para generar una venta desde la tienda
        ArrayList<Producto> productoUnico = new ArrayList<>(Arrays.asList(producto));

        // Se crea una nueva instancia de venta con sus respectivos argumentos
        // solicitados
        Operacion nuevaVenta = new Venta(fecha, productoUnico, cliente);

        // Se agrega la venta al historial de ventas de la tienda y al historial de
        // compras del cliente al cual se le hizo la venta
        ventas.add(nuevaVenta);
        cliente.agregarOperacion(nuevaVenta);

        // En caso de no tener suficiente cantidad de determinado producto para hacer la
        // venta, éste es traido desde alguna bodega
        Producto productoEnStock = buscarProductoPorIDOriginal(producto.getId());
        int cantidadProductoEnStock = productoEnStock.getCantidad();
        int cantidadProductoEnVenta = producto.getCantidad();
        if ((cantidadProductoEnStock - cantidadProductoEnVenta) < 0) {
            pedirTraslado(fecha, productoEnStock, Math.abs(cantidadProductoEnStock - cantidadProductoEnVenta));
        }
        productoEnStock.setCantidad(productoEnStock.getCantidad() - cantidadProductoEnVenta);

        return nuevaVenta;
    }

    // verifica si el producto ingresado como argumento tiene stock bajo y retorna
    // true o false
    // Este metodo es usado en GenerarAlertaStockBajo para verificar el stock de
    // cada producto
    /**
     * Método que verifica si el producto indicado en el stock de la tienda tiene
     * stock bajo.
     * Usado principalmente ligado a la opción de la interfaz "alerta stock bajo"
     * 
     * @param producto Producto sobre el cual se realiza la comprobación
     * @return Resultado de la comprobación (true ó false)
     */
    public boolean prouctoStockBajo(Producto producto) {
        if (producto.getCantidad() < getMinProducto()) {
            return true;
        } else {
            return false;
        }
    }

    // verifica y retorna la cantidad disponibles de un producto en especifico en
    // las Bodegas(La suma)
    // Este metodo es usado en GenerarAlertaStockBajo para mostrar la suma de las
    // cantidades disponibles en las bodegas por producto
    /**
     * Verifica la cantidad disponible de un producto en específico en las bodegas
     * (la suma de unidades disponibles)
     * 
     * @param producto Producto para verificar
     * @return Cantidad en total disponible de producto en las bodegas
     */
    public int cantidadEnBodegas(Producto producto) {
        int cantidadTotal = 0;
        int idProducto = producto.getId();
        for (Bodega bodega : bodegas) {
            for (Producto producto4 : bodega.getProductos()) {
                if (producto4.getId() == idProducto) {
                    cantidadTotal += producto4.getCantidad();
                }
            }
        }
        return cantidadTotal;
    }

    /**
     * Método usado para pedir un traslado (actualizar stock) de cierta cantidad de
     * unidades de un producto desde la bodega que tengas más cantidad del mismo.
     * Si el producto no está en el stock de la tienda, se agrega al stock la
     * referencia de este con su respectiva cantidad solicitada.
     * 
     * @param fecha    Fecha del traslado
     * @param producto Producto del cual se solicita un traslado
     * @param cantidad Cantidad de unidades solicitadas
     */
    public void pedirTraslado(Date fecha, Producto producto, int cantidad) {
        // Metodo para pedir a alguna bodega hacer el traslado de determinado producto a
        // la tienda

        // Variables de control
        Producto prodVerificar = producto;
        Bodega bodegaIndicada = null;
        int idProducto = producto.getId();

        // Ciclo para buscar en cual bodega se encuentra el producto, si se encuentra en
        // las dos, se mirará en cual hay mayor disponibilidad
        for (Bodega bodg : bodegas) {
            if (bodegaIndicada == null) {
                bodegaIndicada = bodg;
            } else if (bodg.buscarProductoPorIDOriginal(idProducto) != null
                    && bodegaIndicada.buscarProductoPorIDOriginal(idProducto) != null) {
                if (bodg.buscarProductoPorIDOriginal(idProducto).getCantidad() > bodegaIndicada
                        .buscarProductoPorIDOriginal(idProducto).getCantidad())
                    bodegaIndicada = bodg;
            } else
                bodegaIndicada = (bodg.buscarProductoPorIDOriginal(idProducto) != null) ? bodg : bodegaIndicada;
        }

        // Se actualiza la cantidad en tienda del producto trasladado o se agrega al
        // array de productos en caso de ser nuevo
        if (this.buscarProductoPorIDOriginal(prodVerificar.getId()) != null) {
            bodegaIndicada.trasladoTienda(fecha, idProducto, cantidad);
            producto.setCantidad(producto.getCantidad() + cantidad);
        } else {
            bodegaIndicada.trasladoTienda(fecha, idProducto, cantidad);
            this.agregarProducto(producto);
        }
    }

    /**
     * Método estático encargado de verificar si en la fecha indicada se aplica
     * descuento a los productos de una lista. En caso de cumplirse la condicion
     * relacionada con el día y el tipo de categoría del producto, al atributo
     * "descuento" del objeto tipo "Producto" se le asocia un valor aleatorio (como
     * máximo del 5%).
     * 
     * @param productosVenta Productos sobre los cuales se verificará
     * @param fecha          Fecha en la cual se verifica
     */
    public static void ofertaDia(ArrayList<Producto> productosVenta, Date fecha) {
        // Dependiendo el dia de la semana, ciertos productos tendran un descuento
        // (diferentes productos en diferentes dias)
        Iterator<Producto> itProd = productosVenta.iterator();
        while (itProd.hasNext()) {
            Producto tempProd = itProd.next();
            if (tempProd.getTipoProducto() == descuentos.get(fecha.getDay())) {
                double descuento = Math.round(Math.random() * 0.05 * 100.0) / 100.0;
                tempProd.setDescuento(descuento);
            }
        }
    }

    /**
     * Método para agregar un cliente
     * 
     * @param cliente Cliente a agregar
     */
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Método encargado de buscar un cliente por medio de su identificación en la
     * base de datos de la tienda
     * 
     * @param documento Documento del cliente a buscar
     * @return La referencia a la instancia del cliente, o null en caso de no
     *         encontarlo
     */
    public Cliente buscarClientePorIdentificacion(String documento) {
        // Se itera en el array de los clientes para buscar el que coincide con el
        // documento o identificacion pasada como argumento al metodo
        Iterator<Cliente> itClien = clientes.iterator();
        while (itClien.hasNext()) {
            Cliente tempClien = itClien.next();
            if (tempClien.getDocumento().equals(documento)) {
                return tempClien;
            }
        }
        return null;
    }

    /**
     * Método encargado de buscar un cliente por medio de su nombre en la base de
     * datos de la tienda
     * 
     * @param nombre del cliente a buscar
     * @return La referencia a la instancia del cliente, o null en caso de no
     *         encontarlo
     */
    public Cliente buscarClientePorNombre(String nombre) {
        // Se itera en el array de los clientes para buscar el que coincide con el
        // nombre pasado como argumento al metodo
        Iterator<Cliente> itClien = clientes.iterator();
        while (itClien.hasNext()) {
            Cliente tempClien = itClien.next();
            if (tempClien.getNombre().equals(nombre)) {
                return tempClien;
            }
        }
        return null;
    }

}
