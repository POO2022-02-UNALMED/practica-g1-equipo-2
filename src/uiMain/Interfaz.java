package uiMain;
import uiMain.Opciones.AgregarCliente;
import uiMain.Opciones.GenerarVenta;
import gestorAplicacion.modelos.Tienda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Interfaz {

    static ArrayList<OpcionesMenu> opcionesMenu = new ArrayList<>(Arrays.asList(new AgregarCliente(),new GenerarVenta()));
    static Scanner scanner = new Scanner(System.in);

    public static void inicializarMenu(Tienda tienda) {

        System.out.println("\t\tBIENVENIDO AL MANEJO DE INVENTARIO DE LA TIENDA...\n");

        int seleccion = -1;
        while(seleccion != 0) {
            System.out.println("Opciones disponibles: ");
            System.out.println("0. Salir del sistema");
            System.out.println("1. Agregar un cliente");
            System.out.println("2. Hacer un traslado de productos desde una Bodega");
            System.out.println("3. Generar balance de la tienda");
            System.out.println("4. Resumen del stock de la tienda");
            System.out.println("5. Resumen de los clientes de la tienda");
            System.out.println("6. Realizar una venta\n");

            System.out.println("Ingrese el número de la opcion a ejecutar: ");
            while(seleccion < 0 || seleccion > 7){
                seleccion = Integer.parseInt(scanner.nextLine());
            }
            System.out.println("");

            switch (seleccion){
                case 1:
                    ((AgregarCliente) opcionesMenu.get(0)).setTienda(tienda);
                    opcionesMenu.get(0).ejecutar();
                    break;
                case 2:
                    System.out.println("hola desde 2");
                    break;
                case 3:
                    System.out.println("hola desde 3");
                    break;
                case 4:
                    System.out.println("hola desde 4");
                    break;
                case 5:
                    System.out.println("hola desde 5");
                    break;
                case 6:
                    System.out.println("hola desde 6");
                    break;
                case 7:
                    ((GenerarVenta) opcionesMenu.get(0)).setTienda(tienda);
                    opcionesMenu.get(1).ejecutar();
                    break;
                default:
                    System.out.println("melo");
            }
            seleccion = -1;
        }
    }
}
