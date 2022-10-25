package gestorAplicacion.operaciones;
import java.text.SimpleDateFormat;
import java.util.Date;

import gestorAplicacion.modelos.Cliente;
import gestorAplicacion.modelos.Producto;

import java.util.ArrayList;

/**
 * Clase venta que hereda de la clase Operación y representa lo correspondiente a una venta generada en la tienda.
 * Para la correcta representación de la Venta, se necesitan los parámetros: cliente, total, descuento.
 * @author Juan Esteban Cadavid Arango
 */
public class Venta extends Operacion {
    private static final long serialVersionUID = 6L;

    // Atributos
    private Cliente cliente;
    private double total;
    private double descuento;


    // Constructor

    /**
     * Constructor para generar instancias de la respectiva venta realizada por la tienda
     *
     * @param fecha     Fecha de la venta
     * @param productos Productos en la venta
     * @param cliente   Cliente asociado a la venta
     */
    public Venta(Date fecha, ArrayList<Producto> productos, Cliente cliente) {
        super(fecha, productos);
        this.cliente = cliente;
    }

    // Métodos

    /**
     * Metodo para calcular los ingresos de la tienda al generar una venta
     *
     * @param descuentoGeneral Descuento general necesario de aplicar al valor total
     */
    public void calcularIngresos(double descuentoGeneral) {
        //Metodo para calcular los ingresos de la tienda al generar una venta

        // Se mira el precio de los productos vendidos y se le resta el descuento en caso de haberlo
        descuento = descuentoGeneral;
        for (Producto producto : getProductos()) {
            total += producto.getCantidad() * (producto.getPrecioVenta() * (1 - producto.getDescuento()));
        }
        total *= (1 - descuentoGeneral);
    }

    /**
     * Sobrescritura de método abstracto para generar la factura correspondiente a la venta realizada
     *
     * @return Cadena de texto correspondiente al formato de la venta
     */
    @Override
    public String generarFactura() {
        //Metodo para generar la factura a la hora de realizar una venta

        //Se da formato a la fecha que se mostrará en la factura
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(getFecha());


        // Se construye la factura dando un formato y resaltando datos importantes cono el cliente, los productos y sus descuentos, la fecha y el precio total
        StringBuilder factura = new StringBuilder();
        factura.append("\n============================================================\n");
        factura.append("\t\t\t\tFactura De Venta\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\tCliente\n");
        factura.append("------------------------------------------------------------\n");
        factura.append("\t\t\tNombre: " + cliente.getNombre() + "\n");
        factura.append("\t\t\tID: " + cliente.getDocumento() + "\n");
        factura.append("\t\t\tEmail: " + cliente.getEmail() + "\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\tProductos\n");
        factura.append("------------------------------------------------------------\n");
        for (Producto producto : getProductos()) {
            factura.append("Nombre: " + producto.getNombre() + " | ID: " + producto.getId() + " | Precio: "
                    + producto.getPrecioVenta()
                    + " | Cantidad: " + producto.getCantidad() + "\n");
            factura.append("------------------------------------------------------------\n");
        }
        factura.append("============================================================\n");
        factura.append("\t\t\t\tDescuentos por producto\n");
        factura.append("------------------------------------------------------------\n");
        for (Producto producto : getProductos()) {
            if (producto.getDescuento() != 0.0) {
                factura.append("\t\tNombre: " + producto.getNombre() + " | Descuento: " + producto.getDescuento() * 100
                        + "%\n");
                factura.append("------------------------------------------------------------\n");
            }
        }
        factura.append("============================================================\n");
        factura.append("\t\t\t\tDescuento General: " + descuento * 100 + "%\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\tTOTAL: " + total + "$\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\tFecha: " + date + "\n");
        factura.append("============================================================\n");
        factura.append("\t\t\t\t¡MUCHAS GRACIAS POR SU COMPRA!\n");
        factura.append("============================================================\n\n");

        return factura.toString();
    }


    // Getters & Setters

    /**
     * Getter del cliente asociado a la venta
     *
     * @return Cliente asociado a la venta
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Setter del cliente asociado a la venta
     * @param cliente Cliente asociado a la venta
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Getter del total calculado asociado a la venta
     * @return Total asociado a la venta
     */
    public double getTotal() {
        return total;
    }

    /**
     * Setter del total calculado asociado a la venta
     * @param total Total asociado a la venta
     */
    public void setTotal(Double total) {
        this.total = total;
    }
}
