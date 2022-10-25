package uiMain.Opciones;
import gestorAplicacion.modelos.Cliente;
import gestorAplicacion.modelos.Tienda;
import uiMain.OpcionesMenu;

/**
 * Clase para ver los clientes de la base de datos de la tienda, que extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Juan Esteban Cadavid Arango
 */
public class ClientesTienda implements OpcionesMenu {

    private Tienda tienda;

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria para mostrar los clientes de la base de datos de la tienda
     */
    @Override
    public void ejecutar() {
    	//Se itera por el array de clientes de la tienda para darle formato y mostrar cada uno de los clientes
    	
        System.out.println("Los clientes registrados actualmente en la tienda son: ");
        for (Cliente cliente : tienda.getClientes()){
            System.out.println("[Nombre: " + cliente.getNombre() + ", Documento: " + cliente.getDocumento() +
                    ", Email: " + cliente.getEmail() + "]");
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
