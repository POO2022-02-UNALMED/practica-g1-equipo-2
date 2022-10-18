package gestorAplicacion.modelos;

import java.io.Serializable;

import gestorAplicacion.operaciones.Venta;

public class Cliente extends Persona implements Serializable {
    private static final long serialVersionUID = 4L;

    public Cliente() {
        super();
    }

    public Cliente(String nombre, String documento, String email) {
        super(nombre, documento, email);
    }

    public double calcularFidelidad(int numeroVentasTienda) {
        return getHistorico().size() / numeroVentasTienda;
    }

    public void agregarCompra(Venta compra) {
        getHistorico().add(compra);
    }
}
