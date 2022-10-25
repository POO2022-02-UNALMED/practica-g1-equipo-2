package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Esta clase se encarga de la serialización y deserialización de objetos
 * en un archivo determinado.
 * 
 * @author koe255
 * 
 */
public class EscritorLector {

    /**
     * Serializa un objeto que implemente la interfaz <b>
     * Serializable </b>.
     * 
     * @param objeto  El objeto que se desea serializar.
     * @param archivo Objeto de tipo File que indica el archivo objetivo para
     *                guardar los datos.
     */
    public static void serializar(Serializable objeto, File archivo) {
        try (FileOutputStream fileOut = new FileOutputStream(archivo)) {
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(objeto);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializa un objeto de tipo genérico a partir de un archivo determinado.
     * 
     * @param archivo El archivo de origen de los datos.
     * @return El objeto deserializado del tipo especificado.
     * @throws IOException
     * @throws FileNotFoundException si el archivo proporcionado no existe.
     *                               Esto puede ser normal en caso de ser la primera
     *                               ejecición del programa.
     *                               Por tal motivo se implementa un handle para
     *                               ignorar esta excepción.
     */
    public static <T> T deserializar(File archivo) throws IOException {
        try {
            FileInputStream fileIn = new FileInputStream(archivo);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            @SuppressWarnings("unchecked")
            /*
             * Es seguro ignorar el cast check porque este archivo no se usa para guardar
             * ningún otro tipo de objeto.
             */
            T objeto = (T) in.readObject();
            in.close();
            fileIn.close();

            return objeto;
        } catch (ClassNotFoundException e) {

            return null;

        } catch (IOException e) {
            if (e instanceof InvalidClassException) {
                throw e;
            }

            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();

                return null;
            }

            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
