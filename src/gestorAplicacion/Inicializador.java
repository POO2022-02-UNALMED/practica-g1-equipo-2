package gestorAplicacion;

import baseDatos.PlantillaTienda;
import gestorAplicacion.modelos.Tienda;
import uiMain.Interfaz;

import baseDatos.InstanciadorBD;

public class Inicializador {
    public static void main(String[] args) {

        Tienda tiendaPrincipal = null;
        // Se desserializa e instancia la plantilla de la tienda para ejecutar el
        // programa
        // Ademas se inicia el menú de opciones que compone el programa
        try {
            tiendaPrincipal = InstanciadorBD.getTienda();

            if (tiendaPrincipal == null) {
                tiendaPrincipal = PlantillaTienda.generarTienda();
            }

            Interfaz.inicializarMenu(tiendaPrincipal);
        } catch (Exception e) {
            System.out.println("Se ha producido un error desconocido durante la ejecución del programa.");
            e.printStackTrace();
        }

        InstanciadorBD.setTienda(tiendaPrincipal);
    }

}
