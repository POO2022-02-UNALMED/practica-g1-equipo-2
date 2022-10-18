package gestorAplicacion.operaciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Proveedor;

public class Compra extends Operacion implements Serializable {
    private static final long serialVersionUID = 7L;

    Date fechaCompra;
    private ArrayList<Producto> productos = new ArrayList<Producto>();
    private Proveedor proveedor;
    private double costoTransporte;

    private double totalEgresos;

    protected Compra(Date fecha, Proveedor proveedor) {
        super(fecha);
        this.proveedor = proveedor;
    }

    @Override
    public Date getFecha() {
        return fechaCompra;
    }
    public void setFecha(Date fecha) {
        this.fechaCompra = fecha;
    }

    @Override
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public double getCostoTransporte() {
        return costoTransporte;
    }

    public void setCostoTransporte(double costoTransporte) {
        this.costoTransporte = costoTransporte;
    }

    @Override
    public String generarFactura() {
        return null;
    }


    public void calcularEgresos() {

    }

}
