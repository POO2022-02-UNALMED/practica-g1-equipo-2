package uiMain.Opciones;
import uiMain.OpcionesMenu;
import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Tienda;
import gestorAplicacion.modelos.Cliente;
import gestorAplicacion.operaciones.Venta;
import gestorAplicacion.modelos.Bodega;
import gestorAplicacion.modelos.Proveedor;

import java.util.*;
import java.util.stream.Collectors;

public class BalanceTienda implements OpcionesMenu {
    @Override
    public void ejecutar() {

        System.out.println("El balance de la tienda es: \n\n");





    }

// Calcula cantidad de productos disponibles en la tienda y en ambas bodegas
    public void cantidadProductos(Tienda tienda) {
        int cantidadTienda = tienda.getProductos().size();
        ArrayList<Integer> cantidadBodegas = new ArrayList<Integer>();

        for (Bodega bodega: tienda.getBodegas()) { // Itera sobre cada bodega para obtener la cantidad de productos
            cantidadBodegas.add(bodega.getProductos().size());
        }

        System.out.println("Cantidad de Productos en Tienda: " + cantidadTienda +
                         "\nCantidad de Productos en Bodega 1: " + cantidadBodegas.get(0) +
                         "\nCantidad de Productos en Bodega 2: " + cantidadBodegas.get(1));
    }


// Muestra los productos disponibles con su respectiva cantidad
    public void disponibilidadInventario(Tienda tienda) {
        HashMap<String, Integer> productosDisponiblesBodega1 = new HashMap<>();
        HashMap<String, Integer> productosDisponiblesBodega2 = new HashMap<>();
        HashMap<String, Integer> productosDisponiblesTienda = new HashMap<>();
// Itera sobre los productos de las bodegas y de la tienda, los incluye en un hasmap de forma que sea Producto : cantidad
        for (Producto producto: tienda.getBodegas().get(0).getProductos()) {
            productosDisponiblesBodega1.put(producto.getNombre(), producto.getCantidad());
        }

        for (Producto producto: tienda.getBodegas().get(1).getProductos()) {
            productosDisponiblesBodega2.put(producto.getNombre(), producto.getCantidad());
        }

        for (Producto producto: tienda.getProductos()) {
            productosDisponiblesTienda.put(producto.getNombre(), producto.getCantidad());
        }

// Imprime los productos junto a sus cantidades
        System.out.println("Los productos disponibles en la bodega 1 con su respectiva cantidad son: \n");
        for (String producto : productosDisponiblesBodega1.keySet()) {
            System.out.println(producto + productosDisponiblesBodega1.get(producto) + "unidades");
        }

        System.out.println("Los productos disponibles en la bodega 2 con su respectiva cantidad son: \n");
        for (String producto : productosDisponiblesBodega2.keySet()) {
            System.out.println(producto + productosDisponiblesBodega2.get(producto) + "unidades");
        }


        System.out.println("Los productos disponibles en la tienda con su respectiva cantidad son: \n");
        for (String producto : productosDisponiblesTienda.keySet()) {
            System.out.println(producto + productosDisponiblesTienda.get(producto) + "unidades");
        }
    }


// Calcula ingresos
    public void ingresosTienda(Tienda tienda) {
        double ingresosTotales = 0;

        for (Venta venta : tienda.getVentas()){
            ingresosTotales = ingresosTotales + venta.getTotal();
        }

        System.out.println("Los ingresos de la tienda al momento son: $"+ingresosTotales);
    }


    // TODO Egresos totales
    public void egresosTotales(Tienda tienda) {

    }

    public void productosMasVendidos(Tienda tienda) {
        HashMap<String, Integer> ventaProductos = new HashMap<>();



        for (Venta venta: tienda.getVentas()) {
            for (Producto producto : venta.getProductos()) {
                if(ventaProductos.containsKey(producto.getNombre())) {
                    ventaProductos.put(producto.getNombre(), ventaProductos.get(producto.getNombre())+1);
                }
                else{ ventaProductos.put(producto.getNombre(), 1); }
            }

        }

        List<String> keys = ventaProductos.entrySet()
                .stream().sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()).limit(3).map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("Los 3 productos más vendidos son: \n");
        System.out.println(Arrays.toString(keys.toArray()));

    }



    public void clienteMasVentas(Tienda tienda) {
        HashMap<String, Integer> clienteVentas = new HashMap<>();

        for (Cliente cliente: tienda.getClientes()) {
            clienteVentas.put(cliente.getNombre(), cliente.getHistorico().size());
        }

        List<String> keys = clienteVentas.entrySet()
                        .stream().sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()).limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("Los clientes con más compras son: \n");

        for (String llave : keys) {
            System.out.println(llave + clienteVentas.get(llave));
        }
    }

    //TODO Proveedor al que más se le ha comprado


}
