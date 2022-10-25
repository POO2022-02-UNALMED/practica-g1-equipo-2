package gestorAplicacion.modelos;
import gestorAplicacion.operaciones.Operacion;

/**
 * Clase cliente que extiende la clase abstracta Persona, y representa la información correspondiente a un cliente de la tienda
 * Más allá de los atributos heredados por la clase Persona, esta clase no contiene más.
 * @author Juan David Restrepo Montoya
 */
public class Cliente extends Persona {
    private static final long serialVersionUID = 4L;

    // Constructor por defecto

    /**
     * Constructor por defecto para generar instancias de cliente
     */
    public Cliente() {
        super();
    }

    //Constructor de la clase con los atributos de su padre (Persona)

    /**
     * Constructor con parámetros para generar intancias de clientes específicos
     * @param nombre Nombre del cliente
     * @param documento Documento del cliente
     * @param email Email del cliente
     */
    public Cliente(String nombre, String documento, String email) {
        super(nombre, documento, email);
    }
    
    //Metodo para calcular la fidelidad del cliente, se mira cuantas compras ha hecho en el total de ventas hechas por la tienda

    /**
     * Metodo para calcular la fidelidad del cliente, se mira cuantas compras ha hecho en el total de ventas hechas por la tienda.
     * Usado por lo general en conexión con la interfaz.
     * @param numeroVentasTienda Número del total de ventas realizadas en la tienda.
     * @return Proporción de ventas hechas al cliente sobre el total de ventas totales de la tienda
     */
    public double calcularFidelidad(double numeroVentasTienda) {
        return (numeroVentasTienda == 0) ? 0.0 : getHistorico().size()/numeroVentasTienda;
    }
}
