package uiMain.Opciones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import gestorAplicacion.modelos.Bodega;
import gestorAplicacion.modelos.Cliente;
import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Tienda;
import gestorAplicacion.operaciones.Venta;
import uiMain.*;

/**
 * Clase que extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Juan José Nanclares Cárdenas
 */
public class GenerarVentaPorMayor implements OpcionesMenu {

	
    private Tienda tienda; // instancia de tienda que será asiganda y usada al iniciar el programa

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria para generar una venta al por mayor
     */
    @Override
    public void ejecutar() {
        ArrayList<Producto> productosVenta = new ArrayList<>();
        Cliente cliente;
        Date fecha;
        Bodega bodega1 = tienda.getBodegas().get(0);
        Bodega bodega2 = tienda.getBodegas().get(1);
        

        System.out.println(" ------ Usted va a realizar un venta de productos al Por Mayor ------ ");

        System.out.println("\nIngrese el numero de productos que va a vender: ");
        System.out.print("-> ");
        productosVenta = generarProductos(Integer.parseInt(scanner.nextLine())); //Metodo para generar y guardar en un Array los productos que se van a vender 

        System.out.println("\nIngrese el cliente al cual se le hace la venta: ");
        cliente = buscarCliente(); //Metodo para buscar el cliente al que se le realizará la venta

        System.out.println("\nIngrese la fecha en la cual se hace la venta (dd/MM/yyyy): ");
        System.out.print("-> ");
        fecha = generarFecha(); //Metodo para ingresar y dar formato a la fecha en la que se hhace la venta
        
        // Ciclo para despachar cada producto en la bodega que tenga mayor cantidad.
        for (Producto prod : productosVenta) {
            if (bodega1.buscarProductoPorIDOriginal(prod.getId()).getCantidad() > bodega2.buscarProductoPorIDOriginal(prod.getId())
                    .getCantidad()) {

                bodega1.despachoPorMayor(fecha, prod);
                System.out.println("\nEl producto \"" + prod.getNombre() + "\" tardará un poco mas en ser despachado.");

            } else {

                bodega2.despachoPorMayor(fecha, prod);
                System.out.println("\nEl producto \"" + prod.getNombre() + "\" tardará un poco mas en ser despachado.");

            }

        }
        
        // Proceso de guardado de la venta, generacion de factura y descuentos respectivos.
        Venta nuevaVenta = new Venta(fecha, productosVenta, cliente);
        tienda.agregarVenta(nuevaVenta);
        cliente.agregarOperacion(nuevaVenta);
        nuevaVenta.calcularIngresos(0.15);
        System.out.println(nuevaVenta.generarFactura());
    }

    /**
     * Método encargado de pedir los productos correspondientes a la venta a generar
     * @param cantidad Número de productos a vender
     * @return Lista de los productos asociados a la venta
     */
    private ArrayList<Producto> generarProductos(int cantidad) {
        ArrayList<Producto> productosVenta = new ArrayList<>();
        
        // ciclo para guardar cada producto seleccionado en un Array
        for (int i = 0; i < cantidad; i++) {
        	
        	//variables para controlar las elecciones del usuario
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

                prod = (seleccionBusquedaProducto == 1) ? buscarProductoPorCodigo() : buscarProductoPorNombre(); // se busca el producto en las bodegas por codigo o nombre (a eleccion del ususario)

                while (prod == null) {
                    System.out.println("Producto no encontrado, ingrese una referencia valida");
                    prod = (seleccionBusquedaProducto == 1) ? buscarProductoPorCodigo() : buscarProductoPorNombre();
                }
                
                // Se muestra la informacion del producto seleccionado en el paso anterior
                System.out.println("\nInformacion del producto:");
                System.out.println("Nombre: " + prod.getNombre());
                System.out.println("ID: " + prod.getId());
                System.out.println("Tipo: " + prod.getTipoProducto());
                System.out.println("Precio: " + prod.getPrecioVenta());

                
                // Se le pide al usuario ingresar las unidades que se van a vender del producto seleccionado
                do {
                    System.out.println("\nIngrese las unidades (Mayor a 57): ");
                    System.out.print("-> ");
                    cantidadProductoVenta = Integer.parseInt(scanner.nextLine());
                } while (cantidadProductoVenta < 57);
                
                prod.setCantidad(cantidadProductoVenta); // se le asigna la cantidad seleccionada al producto

                System.out.println("\n¿Desea confirmar su seleccion?");
                System.out.println("1. Sí");
                System.out.println("2. No");

                do {
                    System.out.print("-> ");
                    seleccionConfirmacion = Integer.parseInt(scanner.nextLine());
                } while (seleccionConfirmacion < 0 || seleccionConfirmacion > 2);
            } while (seleccionConfirmacion == 2);

            // se agrega el producto a un Array
            productosVenta.add(prod);
            System.out.println("\n¡Producto agregado con exito al carrito de compras!");

        }
        // se retorna el array con todos los productos de la venta a realizar
        return productosVenta;
    }


    /**
     * Método para buscar producto por código en el stock de las bodegas.
     * @return Instancia del producto
     */
    private Producto buscarProductoPorCodigo() {
        System.out.println("\nIngresar código del producto que se va a vender: ");
        System.out.print("-> ");
        int id = Integer.parseInt(scanner.nextLine()); // se pide el ID del producto a buscar
        
        Bodega bodega1 = tienda.getBodegas().get(0);
        Bodega bodega2 = tienda.getBodegas().get(1);
        
        
        // Se busca la bodega en la que se encuentre el producto, si se encientra en las dos, se mira donde esté con mayor cantidad
        int cantProdB1 = 0;
        if(bodega1.buscarProductoPorIDOriginal(id) != null) {
        	cantProdB1 = bodega1.buscarProductoPorIDOriginal(id).getCantidad();
        } 
        
        int cantProdB2 = 0;
        if(bodega2.buscarProductoPorIDOriginal(id) != null) {
        	cantProdB2 = bodega2.buscarProductoPorIDOriginal(id).getCantidad();
        } 
        

        // Se retorna una copia del producto para ser guardada y luego asignarle sus respectivos valores de venta.
        if (cantProdB1 > cantProdB2) {
            return bodega1.buscarProductoPorIDCopia(id);
        } else {
            return bodega2.buscarProductoPorIDCopia(id);
        }

    }

    /**
     * Método para buscar producto por nombre en el stock de las bodegas.
     * @return Instancia del producto
     */
    private Producto buscarProductoPorNombre() {
        System.out.println("\nIngresar el nombre del producto que se va a vender: ");
        String nombre = scanner.nextLine(); // Se pide el nombre del producto a buscar
        
        Bodega bodega1 = tienda.getBodegas().get(0);
        Bodega bodega2 = tienda.getBodegas().get(1);
        
        
        // Se busca la bodega en la que se encuentre el producto, si se encientra en las dos, se mira donde esté con mayor cantidad
        int cantProdB1 = 0;
        if(bodega1.buscarProductoPorNombreOriginal(nombre) != null) {
        	cantProdB1 = bodega1.buscarProductoPorNombreOriginal(nombre).getCantidad();
        } 
        
        int cantProdB2 = 0;
        if(bodega2.buscarProductoPorNombreOriginal(nombre) != null) {
        	cantProdB2 = bodega2.buscarProductoPorNombreOriginal(nombre).getCantidad();
        } 

        
     // Se retorna una copia del producto para ser guardada y luego asignarle sus respectivos valores de venta.
        if (cantProdB1 > cantProdB2) {
            return bodega1.buscarProductoPorNombreCopia(nombre);
        } else {
            return bodega2.buscarProductoPorNombreCopia(nombre);
        }

    }

    /**
     * Método para pedir la información y buscar al cliente en la base de datos de la tienda; sigue pidiendo por datos adecuados, si la instancia
     * devuelta representando al cliente es un null.
     * @return Instancia del cliente
     */
    private Cliente buscarCliente() {
    	
    	//Variables para controlar las elecciones del usuario
        int seleccionBusquedaCliente;
        Cliente clien;
        int seleccionConfirmacion;

        // Se dará la opcion de buscar al cliente por su nombre o su Identificacion
        do {
            System.out.println("\nBuscar cliente por: ");
            System.out.println("1. Identificacion");
            System.out.println("2. Nombre");

            do {
                System.out.print("-> ");
                seleccionBusquedaCliente = Integer.parseInt(scanner.nextLine());
            } while (seleccionBusquedaCliente < 0 || seleccionBusquedaCliente > 2);

            
            // Se buscará al cliente por el medio seleccionado (nombre o identificacion)
            clien = (seleccionBusquedaCliente == 1) ? buscarClientePorIdentificacion() : buscarClientePorNombre(); 

            while (clien == null) {
                System.out.println("Cliente no encontrado, ingrese una referencia valida");
                clien = (seleccionBusquedaCliente == 1) ? buscarClientePorIdentificacion() : buscarClientePorNombre();
            }
            
            // Se mostrará la informacion del cliente escogido 
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

        // se retorna el cliente luego de confirmar  la busqueda y seleccion de él
        return clien;
    }

    /**
     * Método encargado de buscar al cliente por medio de su identificación; se enlaza con el método de la clase tienda.
     * @return Instancia del cliente.
     */
    private Cliente buscarClientePorIdentificacion() {
    	// Se pide la identificacion del cliente a buscar y se busca en los clientes guardados en la tienda (si no existe se agrega)
        System.out.println("\nIngresar la identificacion del cliente al que se le hizo la venta: ");
        System.out.print("-> ");
        String identificacion = scanner.nextLine();
        return tienda.buscarClientePorIdentificacion(identificacion);
    }

    /**
     * Método encargado de buscar al cliente por medio de su nombre; se enlaza con el método de la clase tienda.
     * @return Instancia del cliente.
     */
    private Cliente buscarClientePorNombre() {
    	// Se pide el nombre del cliente a buscar y se busca en los clientes guardados en la tienda (si no existe se agrega)
        System.out.println("\nIngresar el nombre del cliente al que se le hizo la venta: ");
        System.out.print("-> ");
        String nombre = scanner.nextLine();
        return tienda.buscarClientePorNombre(nombre);
    }

    /**
     * Método usado para pedir al usuario y generar una fecha en el tipo de dato adecuado
     * @return Fecha en el tipo de dato Date
     */
    private Date generarFecha() {
    	// se pide la fecha en formato "dd/MM/yyyy"
        String stringFecha = scanner.nextLine();
        String pattern = "dd/MM/yyyy";
        
        // se le da formato de impresion a la fecha 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date fecha = new Date();
        try {
            fecha = simpleDateFormat.parse(stringFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //fecha.setYear(fecha.getYear() - 1900);
        // System.out.println("\n¡Venta generada con exito!\n");
        
        // se retorna la fecha lista para ser mostrada 
        return fecha;
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
