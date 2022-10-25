package gestorAplicacion.operaciones;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Proveedor;

/**
 * Clase compra hereda de la clase abstracta Operación y representa lo correspondiente a cada compra generada de la bodega a los proveedores.
 * Los atributos importantes para su adecuada representación son: proveedor, costoTransporte, total
 * @author Juan Esteban Cadavid Arango
 */
public class Compra extends Operacion {
    private static final long serialVersionUID = 7L;

    
    private Proveedor proveedor;
    private double costoTransporte;
    private double total;

    //Constructor de la clase con los atributos de su padre (Establecimiento)

    /**
     * Constructor con parámetros para generar instancias de la clase
     * @param fecha Fecha en la que se hace la compra
     * @param productos Productos comprados
     * @param proveedor Proveedor al cual se le compra
     */
    public Compra(Date fecha, ArrayList<Producto> productos, Proveedor proveedor) {
        super(fecha, productos);
        this.proveedor = proveedor;
    }


    // Getter & Setter

    /**
     * Getter del proveedor asociado a la compra
     * @return Retorna la instancia del Proveedor
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * Setter del proveedor asociado a la compra
     * @param proveedor Instancia del proveedor
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Getter del costo del transporte de la compra (del proveedor a la bodega)
     * @return Costo total del transporte
     */
    public double getCostoTransporte() {
        return costoTransporte;
    }

    /**
     * Setter del costo total del transporte de la Operación
     * @param costoTransporte
     */
    public void setCostoTransporte(double costoTransporte) {
        this.costoTransporte = costoTransporte;
    }


    // Métodos

    /**
     * Método encargado de calcular el costo del transporte de los productos (del proveedor a la bodega) al ser comprados
     */
    public void costoTransporte() {
    	
    	// Se calcula el costo del transporte de los productos al ser comprados
    	
    	//Entre mas productos se compren mayor será el costo de transporte (aumenta cada 20 productos)
        double costoTrans = 20000;
        for (Producto prodCompra : getProductos()) {
            int cantProd = prodCompra.getCantidad();
            for (int i = 1; i < 1000; i += 20) {
                if (cantProd <= i) {
                    costoTrans += 50000;
                    break;
                }
            }
        }
        this.costoTransporte = costoTrans;
    }

    /**
     * Sobrescritura de método abstracto para generar la factura correspondiente a la compra realizada
     * @return Devuelve una cadena de texto con el formato a imprimir por la interfaz
     */
    @Override
    public String generarFactura() {
    	//Metodo para generar la factura de una compra al ser realizada
    	
    	//Se da formato a la fecha de la compra para ser usada en la factura
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(this.getFecha());

        
        // Se construye la factura danto formato y mostando información importante referente a la compra
        // Se muestra el proveedor, los productos comprados, el costro del transporte, su precio total y la fecha
        StringBuilder factura = new StringBuilder();
        factura.append("\n============================================================\n");
        factura.append("\t\t\t\tFactura De Compra\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\tProveedor\n");
        factura.append("------------------------------------------------------------\n");
        factura.append("\t\t\tNombre: " + proveedor.getNombre() + "\n");
        factura.append("\t\t\tID: " + proveedor.getDocumento() + "\n");
        factura.append("\t\t\tEmail: " + proveedor.getEmail() + "\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\tProducto\n");
        factura.append("------------------------------------------------------------\n");
        for (Producto producto : getProductos()) {
            factura.append("Nombre: " + producto.getNombre() + " | ID: " + producto.getId() + " | Costo: "
                    + producto.getCostoCompra()
                    + " | Cantidad: " + producto.getCantidad() + "\n");
        }
        factura.append("============================================================\n");
        factura.append("\t\t\t\tCosto del Transporte: " + this.costoTransporte + "$\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\tTOTAL: " + this.total + "$\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\tFecha: " + date + "\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\t¡MUCHAS GRACIAS POR SU COMPRA!\n");
        factura.append("============================================================\n");

        return factura.toString();
    }

    /**
     * Método encargado de calcular los egresos asociados a la compra
     */
    public void calcularEgresos() {
    	
    	// Se calculan los egresos totales al realizar una compra
    	
    	// El costo del transporte sumado al precio de los productos comprados
        for (Producto prodCompra : getProductos()) {
            this.total = costoTransporte + (prodCompra.getCostoCompra() * prodCompra.getCantidad());
        }
    }

    /**
     * Getter del total asociado a la compra
     * @return Total asociado a la compra
     */
    public double getTotal() {
        return total;
    }

}
