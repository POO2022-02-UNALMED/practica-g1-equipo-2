package uiMain.Opciones;

import uiMain.OpcionesMenu;
import gestorAplicacion.modelos.Tienda;
import gestorAplicacion.modelos.Proveedor;

/**
 * Clase que muestra un resumen de los proveedores de las bodegas, extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Andres Esteban Monsalve Vasquez
 */
public class ResumenProveedores implements OpcionesMenu {
    private Tienda tienda;

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria para mostrar con formato los proveedores
     */
    @Override
    public void ejecutar() {

        // Muestra los datos de cada uno de los proveedores asociados a las bodegas,
        // datos como:
        // Nombre, documento, Email y tipo de producto que provee

        System.out.println("Los proveedores registrados son:");
        System.out.println("───────────────────────────────────────────────────────────");
        for (Proveedor proveedor : (tienda.getBodegas()).get(1).getProveedores()) {
            System.out.println(" Nombre: " + proveedor.getNombre() + ",  Documento: " + proveedor.getDocumento() +
                    ",  Email: " + proveedor.getEmail() + "  Tipo de producto: " + proveedor.getProductoVende());
        }
        System.out.println("___________________________________________________________");
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