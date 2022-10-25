package uiMain.Opciones;

import gestorAplicacion.operaciones.Operacion;
import uiMain.OpcionesMenu;
import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Tienda;
import gestorAplicacion.modelos.Cliente;
import gestorAplicacion.operaciones.Compra;
import gestorAplicacion.operaciones.Venta;
import gestorAplicacion.modelos.Bodega;
import gestorAplicacion.modelos.Proveedor;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase que muestra el balance de la tienda, extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Andres Esteban Monsalve Vasquez
  */
public class BalanceTienda implements OpcionesMenu {
    Tienda tienda;

    /**
     * Función para ejecutar el código desde la interfaz, en esta se llaman todos los métodos encargados de
     generar el balance tanto de la tienda, como de la bodega
     */
    @Override
    /* Función para ejecutar el código desde la interfaz, en esta se llaman todos los métodos encargados de
    generar el balance tanto de la tienda, como de la bodega
     */
    public void ejecutar() {

        System.out.println("BALANCE DE INVENTARIO\n\n");

        cantidadProductos(tienda);
        disponibilidadInventario(tienda);
        ingresosTienda(tienda);
        egresosTotales(tienda);
        productosMasVendidos(tienda);
        clienteMasVentas(tienda);
        proveedorMasCompras(tienda);

    }

    /**
     * Getter de la tienda asociada a la opción
     * @return Instancia de la tienda
     */
    public Tienda getTienda() {
        return tienda;
    }

    /**
     * Setter de la tienda asociada a la opción
     * @param tienda Tienda sobre la cual se opera
     */
    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }


    // Calcula cantidad de productos disponibles en la tienda y en ambas bodegas

    /**
     * Método encargado de calcular la cantidad de productos disponibles en la tienda y en ambas bodegas
     * @param tienda Tienda asociada
     */
    private void cantidadProductos(Tienda tienda) {
        int cantidadTienda = 0;
        int cantidadBodega1 = 0;
        int cantidadBodega2 = 0;

        // Itera sobre la tienda y cada bodega y suma la cantidad de productos disponibles. Al final muestra una cifra.
        for (Producto producto : tienda.getProductos()) {
            cantidadTienda += producto.getCantidad();
        }

        for (Producto producto : tienda.getBodegas().get(0).getProductos()) { // Itera sobre cada bodega para obtener la
                                                                              // cantidad de productos
            cantidadBodega1 += producto.getCantidad();
        }

        for (Producto producto : tienda.getBodegas().get(1).getProductos()) { // Itera sobre cada bodega para obtener la
                                                                              // cantidad de productos
            cantidadBodega2 += producto.getCantidad();
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("\n\t\tPRODUCTOS\n");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Cantidad de Productos en Tienda: " + cantidadTienda +
                "\nCantidad de Productos en Bodega 1: " + cantidadBodega1 +
                "\nCantidad de Productos en Bodega 2: " + cantidadBodega2);
        System.out.println("-----------------------------------------------------------------------------");
    }

   /*
   El método disponibilidadInventario se encarga de mostrar los productos disponibles tanto en tienda como en las bodegas
   y con estos muestra el nombre junto a la cantidad disponible.
    */

    /**
     * El método disponibilidadInventario se encarga de mostrar los productos disponibles tanto en tienda como en las bodegas
     * y con estos muestra el nombre junto a la cantidad disponible.
     * @param tienda Tienda asociada
     */
    private void disponibilidadInventario(Tienda tienda) {
        HashMap<String, Integer> productosDisponiblesBodega1 = new HashMap<>();
        HashMap<String, Integer> productosDisponiblesBodega2 = new HashMap<>();
        HashMap<String, Integer> productosDisponiblesTienda = new HashMap<>();
        // Itera sobre los productos de las bodegas y de la tienda, los incluye en un
        // hashmap de forma que sea Producto:cantidad
        for (Producto producto : tienda.getBodegas().get(0).getProductos()) {
            productosDisponiblesBodega1.put(producto.getNombre(), producto.getCantidad());
        }

        for (Producto producto : tienda.getBodegas().get(1).getProductos()) {
            productosDisponiblesBodega2.put(producto.getNombre(), producto.getCantidad());
        }

        for (Producto producto : tienda.getProductos()) {
            productosDisponiblesTienda.put(producto.getNombre(), producto.getCantidad());
        }

        // Imprime los productos junto a sus cantidades

        System.out.println("\nLos productos disponibles en la bodega 1 con su respectiva cantidad son: \n");

        for (String producto : productosDisponiblesBodega1.keySet()) {
            System.out.println(producto + " | " + productosDisponiblesBodega1.get(producto) + " unidades");
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("\nLos productos disponibles en la bodega 2 con su respectiva cantidad son: \n");

        for (String producto : productosDisponiblesBodega2.keySet()) {
            System.out.println(producto + " | " + productosDisponiblesBodega2.get(producto) + " unidades");
        }
        System.out.println("-----------------------------------------------------------------------------");

        System.out.println("\nLos productos disponibles en la tienda con su respectiva cantidad son: \n");

        for (String producto : productosDisponiblesTienda.keySet()) {
            System.out.println(producto + " | " + productosDisponiblesTienda.get(producto) + " unidades");
        }
    }

    // Calcula ingresos de la tienda, al sumar el valor de cada venta realizada.

    /**
     * Calcula ingresos de la tienda, al sumar el valor de cada venta realizada.
     * @param tienda Tienda asociada
     */
    private void ingresosTienda(Tienda tienda) {
        double ingresosTotales = 0;

        //ligadura dinámica
        for (Operacion venta : tienda.getVentas()) {
            ingresosTotales = ingresosTotales + venta.getTotal();
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("\n\t\tINGRESOS Y EGRESOS\n");
        System.out.println("-----------------------------------------------------------------------------");

        System.out.println("\nLos ingresos de la tienda al momento son: " + formatearNumeroMoneda(ingresosTotales));
        System.out.println("\n-----------------------------------------------------------------------------");
    }

    // Calcula los egresos, entre estos el costo del transporte y los costos de compra de productos a los proveedores.

    /**
     * Calcula los egresos, entre estos el costo del transporte y los costos de compra de productos a los proveedores.
     * @param tienda
     */
    private void egresosTotales(Tienda tienda) {

        Bodega bodega1 = tienda.getBodegas().get(0);
        Bodega bodega2 = tienda.getBodegas().get(1);

        double totalEgresos = 0;

        //ligadura dinámica
        for (Operacion comp : bodega1.getCompras()) {

            totalEgresos += comp.getTotal();
        }

        //ligadura dinámica
        for (Operacion comp : bodega2.getCompras()) {

            totalEgresos += comp.getTotal();
        }
        // Se imprime los egresos usando el método formatearNumeroMoneda de forma que sea correcto el formato del número
        System.out.println("\nLos egresos de la tienda al momento son: " + formatearNumeroMoneda(totalEgresos));
        System.out.println("\n-----------------------------------------------------------------------------");
    }

    // Muestra los 3 productos más vendidos en la tienda, así como la cantidad de ventas que se han hecho donde se incluya dicho producto.

    /**
     * Muestra los 3 productos más vendidos en la tienda, así como la cantidad de ventas que se han hecho donde se incluya dicho producto.
     * @param tienda Tienda asociada
     */
    private void productosMasVendidos(Tienda tienda) {
        HashMap<String, Integer> ventaProductos = new HashMap<>(); // Se crea un hashmap para almacenar el nombre del producto junto a la cantidad de ventas

        //ligadura dinámica
        for (Operacion venta : tienda.getVentas()) { // Se itera sobre cada venta, con el finde verificar los productos que se han vendido.
            for (Producto producto : venta.getProductos()) {
                /*
                Se itera sobre los productos y en caso de que ya esté en el hashmap, se le suma uno, en caso contrario se crea la instancia en el hashmap junto a un 1.
                 */
                if (ventaProductos.containsKey(producto.getNombre())) {
                    ventaProductos.put(producto.getNombre(), ventaProductos.get(producto.getNombre()) + 1);
                } else {
                    ventaProductos.put(producto.getNombre(), 1);
                }
            }

        }

        // Se encarga de obtener los 3 productos con valor más alto y los añade a una lista, de forma que sea más fácil imprimirlos.
        List<String> keys = ventaProductos.entrySet()
                .stream().sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed())
                .limit(3).map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("\n\t\t\tRANKING\n");
        System.out.println("-----------------------------------------------------------------------------");

        System.out.println("\nLos 3 productos más vendidos son: \n");
        for (String llave : keys) {
            System.out.println(llave + " | " + ventaProductos.get(llave)); // Se imprime el nombre del producto junto a la cantidad de veces que ha sido vendido.
        }
    }

    // Provee el top 3 de clientes que más compras han hecho

    /**
     * Provee el top 3 de clientes que más compras han hecho
     * @param tienda Tienda asociada
     */
    private void clienteMasVentas(Tienda tienda) {
        HashMap<String, Integer> clienteVentas = new HashMap<>();

        /* Itera sobre los clientes de la tienda, y se añade a un hashmap el nombre del cliente junto a la cantidad de compras que se han hecho.
        Esto se hace usando el método getHistorico, el cual almacena todas las compras que ha hecho cada cliente.
         */
        for (Cliente cliente : tienda.getClientes()) {
            clienteVentas.put(cliente.getNombre(), cliente.getHistorico().size());
        }

        // Al igual que en el método anterior, las siguientes líneas son para obtener los 3 clientes con el mayor número en una lista.
        List<String> keys = clienteVentas.entrySet()
                .stream().sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("\nLos clientes con más compras son: \n");
        for (String llave : keys) {
            System.out.println(llave + " | " + clienteVentas.get(llave));
        }
        System.out.println("-----------------------------------------------------------------------------");
    }

    // El método proveedorMasCompras funciona similar al método anterior y se encarga de dar los 3 proveedores a los que más compras se le han hecho.

    /**
     * El método proveedorMasCompras funciona similar al método anterior y se encarga de dar los 3 proveedores a los que más compras se le han hecho.
     * @param tienda Tienda asociada
     */
    private void proveedorMasCompras(Tienda tienda) {
        HashMap<String, Integer> proveedorCompras = new HashMap<>();

        for (Bodega bodega : tienda.getBodegas()) {
            for (Proveedor proveedor : bodega.getProveedores()) {
                proveedorCompras.put(proveedor.getNombre(), proveedor.getHistoricoProveedor().size());
            }
        }

        List<String> keys = proveedorCompras.entrySet()
                .stream().sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("\nLos proveedores a los que más se le ha comprado son: \n");
        for (String llave : keys) {
            System.out.println(llave + " | " + proveedorCompras.get(llave));
        }
        System.out.println("-----------------------------------------------------------------------------");
    }

    /**
     * Método para mostrar en mejor formato el número de los totales
     * @param numero Número a dar formato
     * @return Cadena de texto con formato
     */
    private String formatearNumeroMoneda(double numero) {
        Locale colombia = new Locale("es", "CO");

        NumberFormat pesos = NumberFormat.getCurrencyInstance(colombia);

        return pesos.format(numero);
    }

}
