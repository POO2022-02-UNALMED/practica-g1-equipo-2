package uiMain;
import gestorAplicacion.modelos.Tienda;

import java.util.Scanner;

/**
 * Interfaz que representa la base para las opciones del menu de la interfaz. Define métodos comunes e importantes para el comportamiento
 * @author Juan José Nanclares Cárdenas
 */
public interface OpcionesMenu {

	// Interface con un metodo y un scanner para ser implementados en las opciones del menu del sistema
    Scanner scanner = new Scanner(System.in);

    /**
     * Método abstracto que define la necesidad de tener un método que permita ejecutar la opción de menu
     */
    public abstract void ejecutar();

    /**
     * Método que define la necesidad de tener un parámetro tienda para asociar en cada una de las opciones de menu
     *
     * @param tienda
     */
    default void setTienda(Tienda tienda) {

    }
}
