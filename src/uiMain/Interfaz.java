package uiMain;

import uiMain.Opciones.*;
import gestorAplicacion.modelos.Tienda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Clase encargada de mostrar al usuario toda salida por consola, y encargada a su vez de ser el punto de interacción entre el usuario
 * y la capa lógica del programa.
 * Para el funcionamiento de cada funcionalidad, se tiene una lista con la instancia correspondiente a la opción del menu.
 * @author Juan José Nanclares Cárdenas
 */
public class Interfaz {

	// se guardan instancias de las clases en las cuales están contenidas todas las opciones del menu del sistema
    static ArrayList<OpcionesMenu> opcionesMenu = new ArrayList<>(
            Arrays.asList(new AgregarCliente(), new HacerTraslado(), new BalanceTienda(), new StockTienda(), new ClientesTienda(),
                    new GenerarVenta(), new GenerarCompra(), new GenerarAlertaStockBajo(), new ResumenProveedores(), new DetallesProductosBodega(), new GenerarVentaPorMayor()));
    static Scanner scanner = new Scanner(System.in);

    /**
     * Método estático para inicializar el menu, pedir las entradas correspondientes a las opciones e invocar las funcionalidades.
     * @param tienda Tienda asociada
     */
    public static void inicializarMenu(Tienda tienda) {
    	// Imagen decorativa
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║     BIENVENIDO AL MANEJO DE INVENTARIO DE LA TIENDA     ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");

        // Se le dan ciertas opciones al ususario para que elija lo que quiere hacer
        int seleccion = -1;
        while (seleccion != 0) {
            System.out.println("___________________________________________________________");
            System.out.println(" Opciones disponibles: ");
            System.out.println("───────────────────────────────────────────────────────────");
            System.out.println("  0. Salir del sistema");
            System.out.println("  1. Agregar un cliente");
            System.out.println("  2. Hacer un traslado de productos desde una Bodega");
            System.out.println("  3. Generar balance de la tienda");
            System.out.println("  4. Resumen del stock de la tienda");
            System.out.println("  5. Resumen de los clientes de la tienda");
            System.out.println("  6. Realizar una venta");
            System.out.println("  7. Realizar una compra");
            System.out.println("  8. Verificar productos con stock bajo");
            System.out.println("  9. Resumen proveedores bodegas");
            System.out.println(" 10. Mostrar stock bodegas");
            System.out.println(" 11. Generar venta al por mayor");
            System.out.println("___________________________________________________________");

            // Se mostrará una alerta en el menu principal que informará sobre la cantidad de productos con stock bajo (alerta informativa)
            ((GenerarAlertaStockBajo) opcionesMenu.get(7)).setTienda(tienda);
            if ( ((GenerarAlertaStockBajo) opcionesMenu.get(7)).generarAlertas() != 0){
                System.out.println("            ¡Hay "+((GenerarAlertaStockBajo) opcionesMenu.get(7)).generarAlertas()+" alertas por Stock bajo!");
            }
            
            // Se pide al usuario ingresar una opcion a ejecutar (la acción que va a realizar en el sistema)
            System.out.println("───────────────────────────────────────────────────────────");
            System.out.println("Ingrese el número de la opcion a ejecutar: ");
            System.out.print("-> ");
            seleccion = Integer.parseInt(scanner.nextLine());
            System.out.println("───────────────────────────────────────────────────────────");

            while (seleccion < 0 || seleccion > 11) {
                System.out.println("¡Opción inválida! favor ingresar una opción válida");
                System.out.print("-> ");
                seleccion = Integer.parseInt(scanner.nextLine());
            }
            System.out.println("");

            
            // Switch para ejecutar la opcion escogida anteriormente por el ususario (hay opciones entre 0 y 11)
            //en cada case hay ligadura dinámica
            switch (seleccion) {
                case 0:
                    System.out.println("¡Ha salido del sistema!");
                    break;
                case 1:
                    opcionesMenu.get(0).setTienda(tienda);
                    opcionesMenu.get(0).ejecutar();
                    break;
                case 2:
                    opcionesMenu.get(1).setTienda(tienda);
                    opcionesMenu.get(1).ejecutar();
                    break;
                case 3:
                    opcionesMenu.get(2).setTienda(tienda);
                    opcionesMenu.get(2).ejecutar();
                    break;
                case 4:
                    opcionesMenu.get(3).setTienda(tienda);
                    opcionesMenu.get(3).ejecutar();
                    break;
                case 5:
                    opcionesMenu.get(4).setTienda(tienda);
                    opcionesMenu.get(4).ejecutar();
                    break;
                case 6:
                    opcionesMenu.get(5).setTienda(tienda);
                    opcionesMenu.get(5).ejecutar();
                    break;
                case 7:
                    opcionesMenu.get(6).setTienda(tienda);
                    opcionesMenu.get(6).ejecutar();
                    break;
                case 8:
                    //((GenerarAlertaStockBajo) opcionesMenu.get(7)).setTienda(tienda);
                    opcionesMenu.get(7).ejecutar();
                    break;
                case 9:
                    opcionesMenu.get(8).setTienda(tienda);
                    opcionesMenu.get(8).ejecutar();
                    break;
                case 10:
                    opcionesMenu.get(9).setTienda(tienda);
                    opcionesMenu.get(9).ejecutar();
                    break;
                case 11:
                    opcionesMenu.get(10).setTienda(tienda);
                    opcionesMenu.get(10).ejecutar();
                    break;
                default:
                    System.out.println("Y");
            }
            if (seleccion == 0) {
                break;
            }else {
                seleccion = -1;
            }

        }
    }
}
