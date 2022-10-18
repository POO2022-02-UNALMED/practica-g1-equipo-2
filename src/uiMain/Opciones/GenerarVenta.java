package uiMain.Opciones;

import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Tienda;
import gestorAplicacion.operaciones.Venta;
import gestorAplicacion.modelos.Cliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uiMain.*;

public class GenerarVenta implements OpcionesMenu {

    private Tienda tienda;

    @Override
    public void ejecutar() {
        ArrayList<Producto> productosVenta;
        Cliente cliente;
        Date fecha;

        System.out.print("Ingrese el numero categorías diferentes de los productos que va a vender: ");
        productosVenta = generarProductos(Integer.parseInt(scanner.nextLine()));

        System.out.println("Ingrese el cliente al cual se le hace la venta: ");
        cliente = buscarCliente();

        System.out.println("Ingrese la fecha en la cual se hace la venta (dd/MM/yyyy): ");
        fecha = generarFecha();

        Venta ventaGenerada = tienda.generarVenta(fecha, cliente, productosVenta);
        tienda.ofertaDia(productosVenta, fecha);
        double fidelidadCliente = cliente.calcularFidelidad(tienda.getVentas().size());
        if (fidelidadCliente >= 0.1 && fidelidadCliente < 0.2) {
            ventaGenerada.calcularIngresos(0.05);
        } else if (fidelidadCliente >= 0.2) {
            ventaGenerada.calcularIngresos(0.1);
        }

        System.out.println(ventaGenerada.generarFactura());
    }

    public ArrayList<Producto> generarProductos(int cantidad) {
        ArrayList<Producto> productosVenta = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            int seleccionBusquedaProducto;
            int cantidadProductoVenta;
            int seleccionConfirmacion;
            Producto prod;

            do {
                System.out.println("\nBuscar producto por: ");
                System.out.println("1. Código");
                System.out.println("2. Nombre");

                do {
                    System.out.print("-> ");
                    seleccionBusquedaProducto = Integer.parseInt(scanner.nextLine());
                } while (seleccionBusquedaProducto < 0 || seleccionBusquedaProducto > 2);

                prod = (seleccionBusquedaProducto == 1) ? buscarProductoPorCodigo() : buscarProductoPorNombre();

                while (prod == null) {
                    System.out.println("Producto no encontrado, ingrese una referencia valida");
                    prod = (seleccionBusquedaProducto == 1) ? buscarProductoPorCodigo() : buscarProductoPorNombre();
                }

                System.out.println("\nInformacion del producto:");
                System.out.println("Nombre: " + prod.getNombre());
                System.out.println("ID: " + prod.getId());
                System.out.println("Tipo: " + prod.getTipoProducto());
                System.out.println("Precio: \n" + prod.getPrecioVenta());

                System.out.print("Ingrese las unidades (máximo " + prod.getCantidad() + "): ");
                cantidadProductoVenta = Integer.parseInt(scanner.nextLine());
                prod.setCantidad(cantidadProductoVenta);

                System.out.println("\n¿Desea confirmar su seleccion?");
                System.out.println("1. Sí");
                System.out.println("2. No");

                do {
                    System.out.print("-> ");
                    seleccionConfirmacion = Integer.parseInt(scanner.nextLine());
                } while (seleccionConfirmacion < 0 || seleccionConfirmacion > 2);
            } while (seleccionConfirmacion == 2);

            productosVenta.add(prod);
            System.out.println("\n¡Producto agregado con exito al carrito de compras!\n");
        }
        return productosVenta;
    }

    public Producto buscarProductoPorCodigo() {
        System.out.print("\nIngresar código del producto que se va a vender: ");
        int id = Integer.parseInt(scanner.nextLine());
        return tienda.buscarProductoPorID(id);
    }

    public Producto buscarProductoPorNombre() {
        System.out.print("\nIngresar el nombre del producto que se va a vender: ");
        String nombre = scanner.nextLine();
        return tienda.buscarProductoPorNombre(nombre);
    }

    public Cliente buscarCliente() {
        int seleccionBusquedaCliente;
        Cliente clien;
        int seleccionConfirmacion;

        do {
            System.out.println("\nBuscar cliente por: ");
            System.out.println("1. Identificacion");
            System.out.println("2. Nombre");

            do {
                System.out.print("-> ");
                seleccionBusquedaCliente = Integer.parseInt(scanner.nextLine());
            } while (seleccionBusquedaCliente < 0 || seleccionBusquedaCliente > 2);

            clien = (seleccionBusquedaCliente == 1) ? buscarClientePorIdentificacion() : buscarClientePorNombre();

            while (clien == null) {
                System.out.println("Cliente no encontrado, ingrese una referencia valida");
                clien = (seleccionBusquedaCliente == 1) ? buscarClientePorIdentificacion() : buscarClientePorNombre();
            }
            System.out.println("\nInformacion del cliente:");
            System.out.println("Nombre: " + clien.getNombre());
            System.out.println("Identificacion: " + clien.getDocumento());
            System.out.println("Email: " + clien.getEmail());

            System.out.println("\n¿Desea confirmar su seleccion?");
            System.out.println("1. Sí");
            System.out.println("2. No");

            do {
                System.out.print("-> ");
                seleccionConfirmacion = Integer.parseInt(scanner.nextLine());
            } while (seleccionConfirmacion < 0 || seleccionConfirmacion > 2);
        } while (seleccionConfirmacion == 2);

        // System.out.println("\n¡Venta generada con exito!\n");
        return clien;
    }

    public Cliente buscarClientePorIdentificacion() {
        System.out.print("\nIngresar la identificacion del cliente al que se le hizo la venta: ");
        String identificacion = scanner.nextLine();
        return tienda.buscarClientePorIdentificacion(identificacion);
    }

    public Cliente buscarClientePorNombre() {
        System.out.print("\nIngresar el nombre del cliente al que se le hizo la venta: ");
        String nombre = scanner.nextLine();
        return tienda.buscarClientePorNombre(nombre);
    }

    public Date generarFecha() {
        String stringFecha = scanner.nextLine();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date fecha = new Date();
        try {
            fecha = simpleDateFormat.parse(stringFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fecha.setYear(fecha.getYear() - 1900);
        System.out.println("\n¡Venta generada con exito!\n");
        return fecha;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}
