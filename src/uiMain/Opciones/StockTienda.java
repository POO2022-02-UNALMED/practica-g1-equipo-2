package uiMain.Opciones;
import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Tienda;
import uiMain.OpcionesMenu;

/**
 * Clase que para mostrar el stock de la tienda que extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Juan Esteban Cadavid Arango
 */
public class StockTienda implements OpcionesMenu {
    Tienda tienda;

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria para ver el stock de la tienda
     */
    @Override
    public void ejecutar() {
    	
    	// Se muetra dando formato el stock de productos de la tienda con datos como nombre, Id, cantidad y tipo de producto
        System.out.println("El stock actual en la tienda es: ");
        for (Producto producto : tienda.getProductos()){
            System.out.println("[Nombre: " + producto.getNombre() + ", ID: " + producto.getId() + ", Cantidad: " + producto.getCantidad() +
            ", Tipo: " + producto.getTipoProducto() + "]");
        }
        System.out.println("");
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
