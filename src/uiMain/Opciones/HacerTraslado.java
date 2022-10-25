package uiMain.Opciones;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gestorAplicacion.modelos.Bodega;
import gestorAplicacion.modelos.Producto;
import gestorAplicacion.modelos.Tienda;
import uiMain.OpcionesMenu;

/**
 * Clase que hace un traslado de bodega a tienda, extiende de la interfaz OpcionesMenu, y representa una de las opciones de la interfaz
 * Para operar se necesita la referencia a la tienda a la cual agregará el cliente
 * @author Juan Esteban Cadavid Arango
 */
public class HacerTraslado implements OpcionesMenu {
    Tienda tienda;

    /**
     * Sobreescritura del método abstracto, con la implementación necesaria para hacer un traslado de productos de una bodega a la tienda
     */
    @Override
    public void ejecutar() {
    	
    	Date fecha;
        
    	System.out.println("Usted realizará un traslado de productos hacia su tienda\n");
    	
        System.out.println("Ingrese la fecha en la cual se hace el traslado (dd/MM/yyyy): ");
        System.out.print("-> ");
        fecha = generarFecha(); // se genera la fecha en la que se va hacer el traslado

        
        // Se selecciona la bodega de la cual se va a sacar el producto para el traslado
    	System.out.println("Elija la bodega de la cual desea hacer el traslado: ");
        System.out.println("1. Bodega 1");
        System.out.println("2. Bodega 2");
    	int seleccionBodega = 0;
        do {
            System.out.print("-> ");
            seleccionBodega = Integer.parseInt(scanner.nextLine());
        } while (seleccionBodega < 0 || seleccionBodega > 2);


        // Se muestra el stock en la bodega seleccionada anteriormente 
        if (seleccionBodega == 1) {

            System.out.println("\nStock de productos en la bodega" + 1 +":");
            System.out.println("------------------------------------------------------------");
            System.out.println("Bodega 1: ");
            Bodega bodega1 = tienda.getBodegas().get(0);
            formatBodega(bodega1);
            System.out.println("------------------------------------------------------------");

            int idProducto = buscarProductoBodega(bodega1); // se busca y se guarda el id del producto que se va a trasladar de la bodega seleccionada

            System.out.println("\nIndique la cantidad que desea trasladar: ");
            System.out.print("-> ");
            int cantidad = Integer.parseInt(scanner.nextLine()); // cantidad que se va a trasladar 

            // Se genera el traslado y se guarda el producto en la tienda (si no existe se crea su instancia)
        	bodega1.trasladoTienda(fecha, idProducto, cantidad);
        	productoNuevoExistente(bodega1, idProducto, cantidad);

        }else {

        	// MISMO PROCEDIMIENTO ANTERIOR PERO SI SE SELECCIONA LA BODEGA 2
        	
            System.out.println("\nStock de productos en la bodega" + 2 +":");
            System.out.println("------------------------------------------------------------");
            System.out.println("\nBodega 2: ");
            Bodega bodega2 = tienda.getBodegas().get(1);
            formatBodega(bodega2);
            System.out.println("------------------------------------------------------------");

            int idProducto = buscarProductoBodega(bodega2);

            System.out.println("\nIndique la cantidad que desea trasladar: ");
            System.out.print("-> ");
            int cantidad = Integer.parseInt(scanner.nextLine());

        	bodega2.trasladoTienda(fecha, idProducto, cantidad);
        	productoNuevoExistente(bodega2, idProducto, cantidad);

        }

        System.out.println("\n¡El traslado se ha hecho de forma existosa!\n");
    }

    /**
     * Método que mira si el producto que se va a trasladar ya existe en la tienda, si es asi se le suma la cantidad trasladada,
     * si no se agrega el producto nuevo al array de productos de la tienda
     * @param bodega Bodega de la cual se hace el traslado
     * @param idProdBodega ID del producto de la bodega
     * @param cantidad Cantidad a ser trasladada
     */
    private void productoNuevoExistente(Bodega bodega, int idProdBodega, int cantidad) {
    	
    	// Se mira si el producto que se va a trasladar ya existe en la tienda, si es asi se le suma la cantidad trasladada, sino se agrega el producto nuevo al array de productos de la tienda
    	Producto prodTienda = tienda.buscarProductoPorIDOriginal(idProdBodega);               	
        if(prodTienda != null) {
            prodTienda.setCantidad(prodTienda.getCantidad() + cantidad);
        } else {
        	tienda.agregarProducto(bodega.buscarProductoPorIDCopia(idProdBodega));
        	Producto prodAgregado = tienda.buscarProductoPorIDOriginal(idProdBodega);
        	prodAgregado.setCantidad(cantidad);
        }
    	
    }

    /**
     * Método usado para imprimir adecuadamente el stock de la bodega
     * @param bodega Bodega sobre la quiere imprimir el stock
     */
    private void formatBodega(Bodega bodega) {
    	
    	// Se da formato a los productos de la bodega seleccionada para posteriormente ser mostrados al usuario
    	
        for (Producto producto : bodega.getProductos()) {
        	System.out.println("[Nombre: " + producto.getNombre() + " | ID: " + producto.getId() + " | Precio: "
                    + producto.getPrecioVenta()
                    + " | Cantidad: " + producto.getCantidad() + "]");
        }
        
    }

    /**
     * Método usado para pedir la fecha al usuario en un formato, y transformarla a un tipo de dato adecuado
     * @return fecha en el tipo de dato Date
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
        System.out.println("");
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

    /**
     * Método encargado de buscar producto en la bodega
     * @param bodega Bodega sobre la cual se busca el producto
     * @return
     */
    private int buscarProductoBodega(Bodega bodega) {
    	
    	//Se pide el ID del producto (estos IDs fueron mostrados anteriormente al seleccionar una bodega)
        System.out.println("\nIndique el ID del producto en la Bodega elegida: ");
        System.out.print("-> ");
        int id = Integer.parseInt(scanner.nextLine());
    	
        // Se busca el producto en la bodega y se retorna el id de éste
    	Producto prod = bodega.buscarProductoPorIDOriginal(id);
        while (prod == null) {
            System.out.println("Producto no encontrado, ingrese una referencia valida");
            System.out.println("\nIndique el ID del producto en la Bodega elegida: ");
            System.out.print("-> ");
            id = Integer.parseInt(scanner.nextLine());
            prod = bodega.buscarProductoPorIDOriginal(id);
        }
        
        return id;
    }
    
}

