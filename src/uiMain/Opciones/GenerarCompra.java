package uiMain.Opciones;

import gestorAplicacion.modelos.*;
import gestorAplicacion.operaciones.Compra;
import uiMain.OpcionesMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase que genera una compra, extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author David Castrillón Vallejo
 */
public class GenerarCompra implements OpcionesMenu {
    Tienda tienda;

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria para generar una compra
     */
    @Override
    public void ejecutar() {
        Date fecha;
        Bodega bodegaSeleccionada;

        // Se pide seleccionar la bodega en la cual se van a guardar los prosuctos que se van a comprar
        System.out.println("Elija la bodega de la cual desea hacer la compra: ");
        System.out.println("1. Bodega 1");
        System.out.println("2. Bodega 2");
        int seleccionBodega;

        do {
            System.out.print("-> ");
            seleccionBodega = Integer.parseInt(scanner.nextLine());
        } while (seleccionBodega < 0 || seleccionBodega > 2);
        bodegaSeleccionada = (seleccionBodega == 1) ? tienda.getBodegas().get(0) : tienda.getBodegas().get(1);

        
        // Se guarda la fecha en la cual se va a hacer la compra
        System.out.println("\nIngrese la fecha en la cual se hace la compra (dd/MM/yyyy): ");
        System.out.print("-> ");
        fecha = generarFecha();

        // Se ingresa la cantidad de categorias diferentes de productos que se van a comprar (Aseo, Licores, Confiteria, ...)
        System.out.println("\nIngrese el numero de categorías diferentes de los productos que va a comprar: ");
        System.out.print("-> ");
        int numCategorias = Integer.parseInt(scanner.nextLine());

        // Se genera la compra de los productos 
        generarCompras(bodegaSeleccionada, numCategorias, fecha);

        System.out.println("\n¡Compra(s) hecha(s) con éxito!");
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

    /**
     * Método encargado de generar una compra desde la interfaz
     * @param bodega Bodega a la cual se le hace la compra
     * @param cantidadCategorias Cantidad de categorías de productos que se van a comprar
     * @param fecha Fecha en la que se hace la compra
     */
    private void generarCompras(Bodega bodega, int cantidadCategorias, Date fecha) {
    	
    	// Se selecciona el tipo de producto del cual se va a hacer la compra
        for (int i = 0; i < cantidadCategorias; i++) {
            System.out.println("\nDe qué tipo de producto desea hacer la compra: ");
            System.out.println("1. Aseo");
            System.out.println("2. Alimentos");
            System.out.println("3. Hogar");
            System.out.println("4. Licores");
            System.out.println("5. Confiteria");
            int categoria;
            do {
                System.out.print("-> ");
                categoria = Integer.parseInt(scanner.nextLine());
            }while(categoria < 0 || categoria > 5);

            // Se guarda el proveedor del tipo de producto seleccionado
            Proveedor proveedor = null;
            switch (categoria){
                case 1:
                    proveedor = bodega.buscarProveedor(TipoProducto.Aseo);
                    break;
                case 2:
                    proveedor =bodega.buscarProveedor(TipoProducto.Alimentos);
                    break;
                case 3:
                    proveedor = bodega.buscarProveedor(TipoProducto.Hogar);
                    break;
                case 4:
                    proveedor = bodega.buscarProveedor(TipoProducto.Licores);
                    break;
                case 5:
                    proveedor = bodega.buscarProveedor(TipoProducto.Confiteria);
                    break;
                default:
                    break;
            }

            // Se pide ingresar cuantos productos se van a comprar del tipo seleccionado
            System.out.println("\n¿Cuantos productos de dicho tipo desea comprar? ");
            System.out.print("-> ");
            int cantidadPorProducto = Integer.parseInt(scanner.nextLine());
            System.out.println("");

            
            ArrayList<Producto> productosCompraProveedor = new ArrayList<>();
            
            // Ciclo para generar y guardar en un array los productos que se van a comprar
            for (int j = 0; j < cantidadPorProducto; j++){
            	
            	// Se pide elegir si el producto es nuevo o ya existe en la bodega
                System.out.println("Elija si el producto es nuevo o ya existe en stock: ");
                System.out.println("1. Nuevo");
                System.out.println("2. En stock");
                int seleccionProducto;
                do {
                    System.out.print("-> ");
                    seleccionProducto = Integer.parseInt(scanner.nextLine());
                    System.out.println("");
                } while (seleccionProducto< 0 || seleccionProducto > 2);

                // Si el producto es nuevo se agrega al array de productos de la bodega, si ya existe se le actualiza su cantidad
                Producto producto = (seleccionProducto == 1) ? generarNuevo(proveedor.getProductoVende(), bodega) : buscarProductoEnStock(bodega, proveedor.getProductoVende());

                // Se muestra la informacion del producto a comprar
                System.out.println("\nInformacion del producto:");
                System.out.println("Nombre: " + producto.getNombre());
                System.out.println("ID: " + producto.getId());
                System.out.println("Tipo: " + producto.getTipoProducto());
                System.out.println("Precio: " + producto.getPrecioVenta() + "\n");

                // Se agrega al array de productos para posteriormente hacer la compra
                productosCompraProveedor.add(producto);
            }
            // se genera la compra, y el método que genera la compra se encarga de disminuir la cantidad o agregar al stock si no existe
            Compra compra = bodega.generarCompra(fecha, productosCompraProveedor);
            System.out.println(compra.generarFactura());
            System.out.println("Compra hecha al proveedor " + proveedor.getNombre() + " de productos tipo " + proveedor.getProductoVende());
            
        }
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
        // se retorna la fecha lista para ser mostrada 
        return fecha;
    }

    /**
     * Buscar un producto en stock del tipo indicado y de la bodega indicada
     * @param bodega Bodega asociada
     * @param tipoSeleccionado Tipo de producto seleccionado
     * @return Instancia del producto encontrado
     */
    private Producto buscarProductoEnStock(Bodega bodega, TipoProducto tipoSeleccionado) {
    	
    	//Se pide ingresar si se quiere ver el stock en la bodega para buscar un producto existente 
        System.out.println("¿Desea ver el stock actual de la bodega?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int seleccionConfirmacion;
        do {
            System.out.print("-> ");
            seleccionConfirmacion = Integer.parseInt(scanner.nextLine());
        } while (seleccionConfirmacion < 0 || seleccionConfirmacion > 2);
        
        
        // Se muestran los productos en la bodega si se escogió que fueran mostrados
        if (seleccionConfirmacion == 1){
            System.out.println("El stock actual en la bodega es: ");
            for (Producto producto : bodega.getProductos()){
                if (producto.getTipoProducto() == tipoSeleccionado) {
                    System.out.println("[Nombre: " + producto.getNombre() + ", ID: " + producto.getId() + ", Cantidad: " + producto.getCantidad() +
                            ", Tipo: " + producto.getTipoProducto() + "]");
                }
            }
        }

        
        // Se pide ingresar el ID del producto que se va a comprar para ser guardado y actualizar sus valores de compra posteriormente 
        System.out.println("\nIngrese el ID del producto que desea comprar: ");
        System.out.print("-> ");
        Producto prod = bodega.buscarProductoPorIDCopia(Integer.parseInt(scanner.nextLine()));
        while (prod == null) {
            System.out.println("\nProducto no encontrado, ingrese una referencia valida: ");
            System.out.print("-> ");
            prod = bodega.buscarProductoPorIDCopia(Integer.parseInt(scanner.nextLine()));
        }
        
        System.out.println("¡Producto Encontrado!");
        
        // Se pide ingresar la cantidad que se va a comprar de dicho producto y se retorna con sus valores actualizados
        System.out.println("\nIngrese la cantidad que desea del producto encontrado: ");
        System.out.print("-> ");
        int cantidad = Integer.parseInt(scanner.nextLine());
        prod.setCantidad(cantidad);
        
        return prod;
    }

    /**
     * Método encargado de generar un nuevo producto para la compra
     * @param tipoProducto Tipo de producto
     * @param bodega Bodega indicada
     * @return Instancia del producto generado
     */
    private Producto generarNuevo(TipoProducto tipoProducto, Bodega bodega){
    	
    	// Variables para guardar los valores del nuevo producto que se va a generar
        String nombre;
        double precioVenta;
        double costoCompra;
        int cantidad;
        int idMaximoStockBodega = bodega.getProductos().get((bodega.getProductos().size())-1).getId();
        int id = idMaximoStockBodega+1;

        // Se piden ingresar los valores correspondientes al producto que se va a generar
        System.out.println("Ingrese los datos correspondiente al nuevo producto solicitado: ");
        System.out.print("Nombre: "); nombre = scanner.nextLine();
        System.out.print("Precio de Venta: "); precioVenta = Double.parseDouble(scanner.nextLine());
        System.out.print("Costo de Compra: "); costoCompra = Double.parseDouble(scanner.nextLine());
        System.out.print("Cantidad: "); cantidad = Integer.parseInt(scanner.nextLine());

        System.out.println("¡Producto Generado!");
        return new Producto(nombre, id, precioVenta, costoCompra, cantidad, tipoProducto);
    }
}
