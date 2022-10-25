package uiMain.Opciones;

import uiMain.OpcionesMenu;
import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Tienda;
import java.util.Date;
import java.util.*;

/**
 * Clase que genera una venta a un cliente, extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Juan José Nanclares Cárdenas
 */
public class GenerarAlertaStockBajo implements OpcionesMenu {

    private Tienda tienda;

    // Verifica cuantos y cuales productos tiene Stock bajo, muestra la suma de las
    // cantidades disponibles en las bodegas
    // ademas si el usuario asi lo quiere Realiza el traslado desde bodeja para que
    // los productos se surtan y cumplan con el requerimiento de Stock.

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria para generar la alerta de stock bajo.
     * Verifica cuantos y cuales productos tiene Stock bajo, muestra la suma de las cantidades disponibles en las bodegas
     * ademas si el usuario asi lo quiere Realiza el traslado desde bodeja para que los productos se surtan y cumplan con el requerimiento de Stock.
     */
    public void ejecutar() {
        int cantidadProBajos = 0; // cantidad de productos con stock bajo
        ArrayList<Producto> listaProBajos = new ArrayList<Producto>(); // lista con los productos que tiene stock bajo
        for (Producto producto : tienda.getProductos()) {
            if (tienda.prouctoStockBajo(producto)) {
                cantidadProBajos += 1;
                listaProBajos.add(producto);
            }
        }
        if (cantidadProBajos == 0) {
            System.out.println("No hay productos con Stock inferior al establecido: " + tienda.getMinProducto());
        } else {// si hay al menos un producto con Stock bajo en la tienda
            System.out.println(
                    "¡Hay " + cantidadProBajos + " productos con Stock bajo en la Tienda! Estos poductos son: ");
            System.out.println("───────────────────────────────────────────────────────────");

            for (Producto producto : listaProBajos) {
                System.out.println(
                        producto.getNombre() + " ID: " + producto.getId() + " Cantidad: " + producto.getCantidad());
            }
            int desicion = 1;
            System.out.println("La cantidad en total en las Bodegas de estos productos es: \n"); // Muestra la cantidad
                                                                                                 // total(suma las
                                                                                                 // cantidades)
                                                                                                 // disponible en las
                                                                                                 // bodegas
            for (Producto producto2 : listaProBajos) {
                if (tienda.cantidadEnBodegas(producto2) > 0) {
                    System.out.println("Cantidad total en bodegas de " + producto2.getNombre() + " es de : "
                            + tienda.cantidadEnBodegas(producto2));
                } else {
                    System.out.println("La cantidad disponible de " + producto2.getNombre()
                            + " es 0, Si desea surtir se hará la compra directamente al proveedor");// si no hay
                                                                                                    // cantidad para
                                                                                                    // surtir el minimo
                                                                                                    // en la tienda
                }
            }
            System.out.println("───────────────────────────────────────────────────────────");
            System.out.println(
                    "\n¿Quiere hacer el traslado de los productos con disponiblidad en las bodegas para surtir los productos en la Tienda?"
                            + "\n" + " 1. Sí" + "\n" + " 2. No");
            desicion = scanner.nextInt();
            if (desicion == 1) {// Si el usuario quiere se hace el traslado llamando al metodo de tienda pedir
                                // Traslado
                for (Producto producto2 : listaProBajos) {
                    int cantidadFaltante = (Math.abs(producto2.getCantidad() - tienda.getMinProducto()));

                    Date fecha = tienda.getFechaHoy();// se usa la Fecha alctual, atributo fechaHoy de la tienda

                    tienda.pedirTraslado(fecha, producto2, cantidadFaltante);
                }
                System.out.println("───────────────────────────────────────────────────────────");
                System.out.println("El traslado se realizó con éxito.");
            } else {// si no se quiere hacer el traslado
                System.out.println("───────────────────────────────────────────────────────────");
                System.out.println("El traslado no se realizó.");

            }
        }
    }

    // Retorna la cantidad de productos con Stock bajo, se implementa para ver el
    // numero de alertas en la interfaz

    /**
     * Retorna la cantidad de productos con Stock bajo, se implementa para ver el numero de alertas en la interfaz
     * @return Número de alertas
     */
    public int generarAlertas() {
        int cantidadProBajos = 0; // cantidad de productos con stock bajo
        for (Producto producto : tienda.getProductos()) {
            if (tienda.prouctoStockBajo(producto)) {
                cantidadProBajos += 1;
            }
        }
        return cantidadProBajos;
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
}
