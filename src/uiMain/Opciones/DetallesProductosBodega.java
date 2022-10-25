package uiMain.Opciones;

import uiMain.OpcionesMenu;
import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Tienda;

/**
 * Clase que muestra los detalles de los productos en la bodega, extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Juan Esteban Cadavid Arango
 */
public class DetallesProductosBodega implements OpcionesMenu {

    private Tienda tienda;

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria ver los detalles de los productos en las bodegas
     */
    @Override
    public void ejecutar() {
        productosBodega(tienda);
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

    /**
     * Método para formatear el stock en las bodegas
     * @param tienda Tienda asociada
     */
    private void productosBodega(Tienda tienda) {
    	//Se muestra el stock de productos que hay en cada bodega dandole formato a la impresion de estos productos 
    	
        int totalProductosBod1 = 0;
        int totalProductosBod2 = 0;
        System.out.println("-----------------------------------------------------");
        System.out.println("\n\t\t\tSTOCK EN BODEGA\n");
        System.out.println("-----------------------------------------------------");

        // Se itera sobre el array de productos de la bodega 1 y se muestra con formato cada uno de ellos
        System.out.println("\n\t\t\t\tBODEGA 1 \n\n");
        for (Producto producto : tienda.getBodegas().get(0).getProductos()) {
            System.out.println("[" + producto.getNombre() + " | ID: " + producto.getId() + " | tipo: "
                    + producto.getTipoProducto() + " | " + producto.getCantidad() + " unidades]\n");
            totalProductosBod1 += producto.getCantidad();
        }
        
        //Se muestra el total de productos que hay en la bodega 1
        System.out.println("-----------------------------------------------------");
        System.out.println("\nEn la bodega 1 hay un total de " + totalProductosBod1 + " productos\n");
        System.out.println("-----------------------------------------------------");

     // Se itera sobre el array de productos de la bodega 2 y se muestra con formato cada uno de ellos
        System.out.println("\n\t\t\t\tBODEGA 2 \n\n");
        for (Producto producto : tienda.getBodegas().get(1).getProductos()) {

            System.out.println("[" + producto.getNombre() + " | ID: " + producto.getId() + " | tipo: "
                    + producto.getTipoProducto() + " | " + producto.getCantidad() + " unidades]\n");

            totalProductosBod2 += producto.getCantidad();

        }
        
        //Se muestra el total de productos que hay en la bodega 2
        System.out.println("-----------------------------------------------------");
        System.out.println("\nEn la bodega 2 hay un total de " + totalProductosBod2 + " productos\n");
        System.out.println("-----------------------------------------------------");

        int totalProductosBodega = totalProductosBod1 + totalProductosBod2;
        System.out.println("\nHay un total de " + totalProductosBodega + " productos en ambas bodegas\n");
    }

}
