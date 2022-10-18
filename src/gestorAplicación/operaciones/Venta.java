package gestorAplicacion.operaciones;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import gestorAplicacion.modelos.Cliente;
import gestorAplicacion.modelos.Producto;

import java.util.ArrayList;

public class Venta extends Operacion implements Serializable {
    private static final long serialVersionUID = 6L;

    private Cliente cliente;
    private double total;
    private double descuento;

    public Venta(Date fecha, ArrayList<Producto> productos, Cliente cliente) {
        super(fecha, productos);
        this.cliente = cliente;
    }


    // TODO aca que significa el descuento general? Revisar ingresos, si se hace asi este metodo?
    public void calcularIngresos(double descuentoGeneral) {
        descuento = descuentoGeneral;
        for (Producto producto : getProductos()) {
            total += producto.getCantidad() * (producto.getPrecioVenta() * (1 - producto.getDescuento()));
        }
        total *= (1 - descuentoGeneral);
    }

    @Override
    public String generarFactura() {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(getFecha());

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
