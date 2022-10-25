package baseDatos;

import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;

import gestorAplicacion.modelos.Tienda;

/**
 * Esta clase se encarga de actuar como una capa de abstracción para el proceso
 * de serialización y deserialización de los datos que se realiza al inicio de
 * cada
 * ejecución del programa.
 * Es la única clase que debería implementar a
 * {@link baseDatos.EscritorLector}.
 * 
 * @author koe255
 */
public class InstanciadorBD {
    public static File rutaTienda = new File("src/baseDatos/temp/tienda.ser");
    public static File rutaBodegas = new File("src/baseDatos/temp/bodegas.ser");

    /**
     * Obtiene y retorna los datos deserializados de la tienda.
     * 
     * @return Instancia de la tienda.
     */
    public static Tienda getTienda() {
        try {
            return EscritorLector.deserializar(rutaTienda);
        } catch (IOException e) {
            if (e instanceof InvalidClassException) {
                System.out.println("La clase Tienda ha cambiado. "
                        + "Se procederá a crear una nueva tienda.");

                return PlantillaTienda.generarTienda();
            }

            return null;
        }
    }

    /**
     * Serializa los datos de la tienda en la ruta estática predefinida.
     * 
     * @param tienda La instancia de Tienda que se desea serializar.
     */
    public static void setTienda(Tienda tienda) {
        EscritorLector.serializar(tienda, rutaTienda);
    }
}
