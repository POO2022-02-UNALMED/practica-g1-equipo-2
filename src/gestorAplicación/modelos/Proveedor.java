package gestorAplicacion.modelos;

import gestorAplicacion.operaciones.Compra;
import gestorAplicacion.operaciones.Venta;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Proveedor extends Persona implements Serializable {
    private static final long serialVersionUID = 5L;

    private ArrayList<Compra> historico = new ArrayList<Compra>();


    public Proveedor(String nombre, String documento, String email) {
        super(nombre, documento, email);
    }


    public void agregarCompra(Compra compra) {
        historico.add(compra);
    }


    public double calcularFidelidad(int numeroVentasTienda) {
        return historico.size() / numeroVentasTienda;
    }


}
