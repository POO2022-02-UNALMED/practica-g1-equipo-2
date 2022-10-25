package uiMain.Opciones;

import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Tienda;
import gestorAplicacion.operaciones.Operacion;
import gestorAplicacion.operaciones.Venta;
import gestorAplicacion.modelos.Cliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uiMain.*;

 /**
 * Clase que genera una venta a un cliente, extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Andres Esteban Monsalve Vasquez
 */
public class GenerarVenta implements OpcionesMenu {

    private Tienda tienda;

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria para generar una venta desde la tienda a un cliente
     */
    @Override
    public void ejecutar() {
        ArrayList<Producto> productosVenta;
        Cliente cliente;
        Date fecha;

        System.out.println("Ingrese el numero de productos que va a vender: ");
        System.out.print("-> ");
        productosVenta = generarProductos(Integer.parseInt(scanner.nextLine()));

        System.out.println("Ingrese el cliente al cual se le hace la venta: ");
        cliente = buscarCliente();

        System.out.println("Ingrese la fecha en la cual se hace la venta (dd/MM/yyyy): ");
        fecha = generarFecha();

        System.out.println("\n¿Desea confirmar la venta?");
        System.out.println("1. Sí");
        System.out.println("2. No");
        int seleccionConfirmacionVenta;

        do {
            System.out.print("-> ");
            seleccionConfirmacionVenta = Integer.parseInt(scanner.nextLine());
        } while (seleccionConfirmacionVenta < 0 || seleccionConfirmacionVenta > 2);

        if (seleccionConfirmacionVenta == 2) return;


        //desencadenante de la sobrecarga de métodos en la clase tienda
        Operacion ventaGenerada = (productosVenta.size() == 1) ? tienda.generarVenta(fecha, cliente, productosVenta.get(0)) :
                tienda.generarVenta(fecha, cliente, productosVenta);


        Tienda.ofertaDia(productosVenta, fecha);
        double fidelidadCliente = cliente.calcularFidelidad(tienda.getVentas().size());
        if (fidelidadCliente >= 0.1 && fidelidadCliente < 0.2) {
            ((Venta)ventaGenerada).calcularIngresos(0.05);
        } else if (fidelidadCliente >= 0.2) {
            ((Venta)ventaGenerada).calcularIngresos(0.1);
        }

        // ligadura dinámica
        System.out.println(ventaGenerada.generarFactura());
    }

    /**
     * Método encargado de pedir los productos correspondientes a la venta a generar
     * @param cantidad Número de productos a vender
     * @return Lista de los productos asociados a la venta
     */
    private ArrayList<Producto> generarProductos(int cantidad) {
        ArrayList<Producto> productosVenta = new ArrayList<>();
        mostrarStock();

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
                System.out.println("Precio: " + prod.getPrecioVenta());

                System.out.println("\nIngrese las unidades: ");
                System.out.print("-> ");
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
            System.out.println("\n¡Productos agregados con exito al carrito de compras!\n");
        }
        return productosVenta;
    }

    /**
     * Método para buscar producto por código en el stock de la tienda; se enlaza con el método de la tienda que se encarga de buscarlo.
     * @return Instancia del producto
     */
    private Producto buscarProductoPorCodigo() {
        System.out.println("\nIngresar código del producto que se va a vender: ");
        System.out.print("-> ");
        int id = Integer.parseInt(scanner.nextLine());
        return tienda.buscarProductoPorIDCopia(id);
    }

    /**
     * Método para buscar producto por nombre en el stock de la tienda; se enlaza con el método de la tienda que se encarga de buscarlo.
     * @return Instancia del producto
     */
    private Producto buscarProductoPorNombre() {
        System.out.println("\nIngresar el nombre del producto que se va a vender: ");
        System.out.print("-> ");
        String nombre = scanner.nextLine();
        return tienda.buscarProductoPorNombreCopia(nombre);
    }

    /**
     * Método para pedir la información y buscar al cliente en la base de datos de la tienda; sigue pidiendo por datos adecuados, si la instancia
     * devuelta representando al cliente es un null.
     * @return Instancia del cliente
     */
    private Cliente buscarCliente() {
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

        System.out.println("");
        return clien;
    }

    /**
     * Método encargado de buscar al cliente por medio de su identificación; se enlaza con el método de la clase tienda.
     * @return Instancia del cliente.
     */
    private Cliente buscarClientePorIdentificacion() {
        System.out.print("\nIngresar la identificacion del cliente al que se le hizo la venta: ");
        String identificacion = scanner.nextLine();
        return tienda.buscarClientePorIdentificacion(identificacion);
    }

    /**
     * Método encargado de buscar al cliente por medio de su nombre; se enlaza con el método de la clase tienda.
     * @return Instancia del cliente.
     */
    private Cliente buscarClientePorNombre() {
        System.out.print("\nIngresar el nombre del cliente al que se le hizo la venta: ");
        String nombre = scanner.nextLine();
        return tienda.buscarClientePorNombre(nombre);
    }

    /**
     * Método usado para pedir al usuario y generar una fecha en el tipo de dato adecuado
     * @return Fecha en el tipo de dato Date
     */
    private Date generarFecha() {
        String stringFecha = scanner.nextLine();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date fecha = new Date();
        try {
            fecha = simpleDateFormat.parse(stringFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //fecha.setYear(fecha.getYear() - 1900);
        System.out.println("\n¡Venta generada con exito!");
        return fecha;
    }

    /**
     * Método encargado de mostrar el stock de la tienda
     */
    private void mostrarStock(){
        System.out.println("\n¿Desea ver el stock actual de la Tienda?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int seleccionConfirmacion;
        do {
            System.out.print("-> ");
            seleccionConfirmacion = Integer.parseInt(scanner.nextLine());
        } while (seleccionConfirmacion < 0 || seleccionConfirmacion > 2);

        if (seleccionConfirmacion == 1){
            System.out.println("\nEl stock actual en la Tienda es: ");
            for (Producto producto : tienda.getProductos()){
                System.out.println("[Nombre: " + producto.getNombre() + ", ID: " + producto.getId() + ", Cantidad: " + producto.getCantidad() +
                        ", Tipo: " + producto.getTipoProducto() + "]");
            }
        }
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
