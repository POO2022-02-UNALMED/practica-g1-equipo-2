package gestorAplicacion.modelos;
import gestorAplicacion.operaciones.Compra;

import java.util.ArrayList;

/**
 * Clase "Proveedor" que hereda de la clase "Persona" representa los posibles surtidores que se encargarán de vender los productos.
 * Cada proveedor vende un tipo de producto específico.
 * La clase Proveedor servirá para surtir las bodegas en caso de necesitarlo por falta de productos.
 * Contiene un historico de compras realizadas para ser usado al generar el Balance de la tienda
 * @author David Castrillón Vallejo
 */
public class Proveedor extends Persona {
    private static final long serialVersionUID = 5L;

    // Atributos
    private final TipoProducto productoVende;
    private ArrayList<Compra> historicoProveedor = new ArrayList<Compra>();

    // Constructor

    /**
     *Construye un objeto tipo Proveedor
     * @param nombre Nombre del proveedor
     * @param documento Documento del proveedor
     * @param email Email del proveedor
     * @param prodQueVende Producto
     */
    public Proveedor(String nombre, String documento, String email, TipoProducto prodQueVende) {
        super(nombre, documento, email);
        this.productoVende = prodQueVende;
    }


    // Getter & Setter

    /**
     * Getter del histórico de ventas del proveedor
     * @return Un ArrayList de las compras que se le hicieron al proveedor (las ventas que hizo)
     */
    public ArrayList<Compra> getHistoricoProveedor() {
        return historicoProveedor;
    }

    /**
     * Setter del histórico de ventas del proveedor
     * @param historicoProveedor ArrayList del histórico de compras que se le hicieron al proveedor (las ventas que hizo)
     */
    public void setHistoricoProveedor(ArrayList<Compra> historicoProveedor) {
        this.historicoProveedor = historicoProveedor;
    }

    /**
     * Getter del TipoProducto que vende el proveedor
     * @return el TipoProducto que vende el proveedor
     */
    public TipoProducto getProductoVende() {
        return productoVende;
    }
}
