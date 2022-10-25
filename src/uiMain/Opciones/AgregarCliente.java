package uiMain.Opciones;
import uiMain.OpcionesMenu;
import gestorAplicacion.modelos.Cliente;
import gestorAplicacion.modelos.Tienda;

/**
 * Clase que extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Juan Esteban Cadavid Arango
 */
public class AgregarCliente implements OpcionesMenu {

    Tienda tienda;

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria para agregar un cliente a la base de datos de la tienda
     */
    @Override
    public void ejecutar() {
    	//Se agrega un cliente al array de clientes de la tienda en caso de ser un cliente nuevo 
    	
    	//Se solicita ingresar los datos del cliente como nombre, documento, email, ...
        System.out.println("Ingrese los datos correspondientes al nuevo cliente a agregar: ");
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el documento: ");
        String documento = scanner.nextLine();
        System.out.print("Ingrese el email: ");
        String email = scanner.nextLine();
        System.out.println("\n¡Cliente agregado con éxito!\n");
        tienda.agregarCliente(new Cliente(nombre, documento, email));
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
