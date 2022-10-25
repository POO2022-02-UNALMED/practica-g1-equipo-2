package baseDatos;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import gestorAplicacion.modelos.*;

/**
 * Esta clase se encarga de generar una instancia de la clase Tienda
 * con bodegas, productos, clientes y proveedores predefinidos.
 * Los productos predefinidos en las bodegas y en la tienda se procesan como
 * compras
 * para ser tenidos en cuenta al momento de generar el balance de la tienda.
 * 
 * generarTienda() Genera la instancia plantilla de la clase Tienda
 */
public class PlantillaTienda {
        public static Date fecha = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        /**
         * Genera la instancia plantilla de la clase Tienda
         * 
         * @return Tienda plantilla
         */
        public static Tienda generarTienda() {
                Tienda tienda = new Tienda();

                tienda.setTamanoAlmacenamiento(1000);
                tienda.setMinProducto(10);
                tienda.setFechaHoy(fecha);

                ArrayList<Bodega> bodegas = generarBodegas();

                tienda.setBodegas(bodegas);
                tienda.setClientes(generarClientes());

                ArrayList<Producto> alimentos = new ArrayList<>();

                alimentos.add(new Producto("Leche", 0, 3000, 1500, 6, TipoProducto.Alimentos));
                alimentos.add(new Producto("Galletas", 1, 6000, 4400, 15, TipoProducto.Alimentos));
                alimentos.add(new Producto("Pan", 2, 3000, 1700, 25, TipoProducto.Alimentos));
                alimentos.add(new Producto("Aceite", 3, 7000, 5000, 11, TipoProducto.Alimentos));
                alimentos.add(new Producto("Frijoles", 4, 2000, 500, 19, TipoProducto.Alimentos));
                alimentos.add(new Producto("Arepas", 5, 4300, 2000, 40, TipoProducto.Alimentos));
                alimentos.add(new Producto("Café", 6, 6500, 4700, 25, TipoProducto.Alimentos));
                alimentos.add(new Producto("Arroz", 7, 5600, 3400, 16, TipoProducto.Alimentos));
                alimentos.add(new Producto("Queso", 8, 4000, 2000, 18, TipoProducto.Alimentos));
                alimentos.add(new Producto("Sal", 9, 3600, 5000, 27, TipoProducto.Alimentos));

                ArrayList<Producto> aseo = new ArrayList<>();

                aseo.add(new Producto("Jabón Rey", 10, 6200, 4100, 30, TipoProducto.Aseo));
                aseo.add(new Producto("Detergente en Polvo", 11, 8000, 6500, 4, TipoProducto.Aseo));
                aseo.add(new Producto("Blanqueador", 12, 8700, 5800, 12, TipoProducto.Aseo));
                aseo.add(new Producto("Papel Higienico", 13, 5400, 3100, 24, TipoProducto.Aseo));
                aseo.add(new Producto("Suavizante", 14, 12000, 8000, 13, TipoProducto.Aseo));
                aseo.add(new Producto("Lavaloza", 15, 7200, 4100, 32, TipoProducto.Aseo));
                aseo.add(new Producto("Toalla de Cocina", 16, 5700, 2400, 1, TipoProducto.Aseo));
                aseo.add(new Producto("Lavaloza liquido", 43, 8200, 6400, 15, TipoProducto.Aseo));
                aseo.add(new Producto("Esponja", 44, 5600, 3700, 10, TipoProducto.Aseo));
                aseo.add(new Producto("Limpia pisos", 45, 9000, 6850, 20, TipoProducto.Aseo));

                ArrayList<Producto> licores = new ArrayList<>();

                licores.add(new Producto("Ron", 17, 33500, 25000, 30, TipoProducto.Licores));
                licores.add(new Producto("Cerveza", 18, 10500, 8000, 40, TipoProducto.Licores));
                licores.add(new Producto("Whisky", 19, 121900, 10000, 13, TipoProducto.Licores));
                licores.add(new Producto("Tequila", 20, 65000, 47000, 10, TipoProducto.Licores));
                licores.add(new Producto("Amaretto", 21, 144000, 12000, 20, TipoProducto.Licores));
                licores.add(new Producto("Vodka", 22, 43600, 28100, 19, TipoProducto.Licores));
                licores.add(new Producto("Aguardiente", 23, 27000, 19700, 3, TipoProducto.Licores));
                licores.add(new Producto("Vino", 40, 34000, 15000, 23, TipoProducto.Licores));
                licores.add(new Producto("Crema de Whisky", 41, 48900, 34000, 17, TipoProducto.Licores));
                licores.add(new Producto("Coñac", 42, 67000, 19700, 34, TipoProducto.Licores));

                ArrayList<Producto> confiteria = new ArrayList<>();

                confiteria.add(new Producto("Gomitas", 24, 3600, 2600, 33, TipoProducto.Confiteria));
                confiteria.add(new Producto("Arequipe", 25, 6300, 3600, 21, TipoProducto.Confiteria));
                confiteria.add(new Producto("Barrilete", 26, 2500, 1100, 17, TipoProducto.Confiteria));
                confiteria.add(new Producto("Chocolatina", 27, 1500, 700, 14, TipoProducto.Confiteria));
                confiteria.add(new Producto("Panelita", 28, 2000, 500, 20, TipoProducto.Confiteria));
                confiteria.add(new Producto("ChocoRamo", 29, 5000, 2000, 19, TipoProducto.Confiteria));
                confiteria.add(new Producto("Chicle", 30, 2500, 1650, 20, TipoProducto.Confiteria));
                confiteria.add(new Producto("Menta", 31, 800, 350, 35, TipoProducto.Confiteria));
                confiteria.add(new Producto("Gelatina", 32, 3650, 2350, 18, TipoProducto.Confiteria));
                confiteria.add(new Producto("Bocadillo", 33, 6500, 4000, 23, TipoProducto.Confiteria));

                ArrayList<Producto> hogar = new ArrayList<>();

                hogar.add(new Producto("Plato", 34, 15000, 1000, 14, TipoProducto.Hogar));
                hogar.add(new Producto("Jarra", 35, 10000, 7500, 10, TipoProducto.Hogar));
                hogar.add(new Producto("Cuchillo", 36, 8500, 5600, 20, TipoProducto.Hogar));
                hogar.add(new Producto("Cuchara", 37, 8700, 5700, 30, TipoProducto.Hogar));
                hogar.add(new Producto("Cafetera", 38, 57000, 43500, 9, TipoProducto.Hogar));
                hogar.add(new Producto("Vasos", 39, 12000, 7600, 19, TipoProducto.Hogar));
                hogar.add(new Producto("Abrelatas", 46, 23000, 15600, 14, TipoProducto.Hogar));
                hogar.add(new Producto("Pinzas", 47, 16700, 11300, 12, TipoProducto.Hogar));
                hogar.add(new Producto("Rallador", 48, 17800, 14650, 15, TipoProducto.Hogar));
                hogar.add(new Producto("Pelador de verduras", 49, 9600, 6400, 19, TipoProducto.Hogar));

                ArrayList<Producto> todos = new ArrayList<>() {
                        {
                                addAll(alimentos);
                                addAll(aseo);
                                addAll(licores);
                                addAll(confiteria);
                                addAll(hogar);
                        }
                };

                ArrayList<ArrayList<Producto>> todosPorTipo = new ArrayList<>() {
                        {
                                add(alimentos);
                                add(aseo);
                                add(licores);
                                add(confiteria);
                                add(hogar);
                        }
                };

                // Procesa los productos predefinidos como compras a la
                // bodega uno.
                for (ArrayList<Producto> productos : todosPorTipo) {
                        (bodegas.get(0)).generarCompra(fecha, productos);

                }

                // Procesa los productos predefinidos de la tienda como traslados de la bodega 1
                // a la tienda.
                for (Producto producto : todos) {
                        tienda.pedirTraslado(fecha, producto, producto.getCantidad());
                }

                return tienda;
        }

        /**
         * Genera las bodegas para ser integradas a la instancia plantilla de la tienda.
         * Los proveedores, productos y compras de cada bodega son instanciados
         * y asignadas en este método.
         * 
         * @return ArrayList de bodegas.
         */

        private static ArrayList<Bodega> generarBodegas() {
                Bodega bodega1 = new Bodega(1000, 10, 1, new ArrayList<>());
                Bodega bodega2 = new Bodega(1000, 10, 2, new ArrayList<>());

                ArrayList<Producto> alimentos = new ArrayList<>();

                alimentos.add(new Producto("Leche", 0, 3000, 1500, 6, TipoProducto.Alimentos));
                alimentos.add(new Producto("Galletas", 1, 6000, 4400, 15, TipoProducto.Alimentos));
                alimentos.add(new Producto("Pan", 2, 3000, 1700, 25, TipoProducto.Alimentos));
                alimentos.add(new Producto("Aceite", 3, 7000, 5000, 11, TipoProducto.Alimentos));
                alimentos.add(new Producto("Frijoles", 4, 2000, 500, 19, TipoProducto.Alimentos));
                alimentos.add(new Producto("Arepas", 5, 4300, 2000, 40, TipoProducto.Alimentos));
                alimentos.add(new Producto("Café", 6, 6500, 4700, 25, TipoProducto.Alimentos));
                alimentos.add(new Producto("Arroz", 7, 5600, 3400, 16, TipoProducto.Alimentos));
                alimentos.add(new Producto("Queso", 8, 4000, 2000, 18, TipoProducto.Alimentos));
                alimentos.add(new Producto("Sal", 9, 3600, 5000, 27, TipoProducto.Alimentos));

                ArrayList<Producto> aseo = new ArrayList<>();

                aseo.add(new Producto("Jabón Rey", 10, 6200, 4100, 30, TipoProducto.Aseo));
                aseo.add(new Producto("Detergente en Polvo", 11, 8000, 6500, 4, TipoProducto.Aseo));
                aseo.add(new Producto("Blanqueador", 12, 8700, 5800, 12, TipoProducto.Aseo));
                aseo.add(new Producto("Papel Higienico", 13, 5400, 3100, 24, TipoProducto.Aseo));
                aseo.add(new Producto("Suavizante", 14, 12000, 8000, 13, TipoProducto.Aseo));
                aseo.add(new Producto("Lavaloza", 15, 7200, 4100, 32, TipoProducto.Aseo));
                aseo.add(new Producto("Toalla de Cocina", 16, 5700, 2400, 1, TipoProducto.Aseo));
                aseo.add(new Producto("Lavaloza liquido", 43, 8200, 6400, 15, TipoProducto.Aseo));
                aseo.add(new Producto("Esponja", 44, 5600, 3700, 10, TipoProducto.Aseo));
                aseo.add(new Producto("Limpia pisos", 45, 9000, 6850, 20, TipoProducto.Aseo));

                ArrayList<Producto> licores = new ArrayList<>();

                licores.add(new Producto("Ron", 17, 33500, 25000, 30, TipoProducto.Licores));
                licores.add(new Producto("Cerveza", 18, 10500, 8000, 40, TipoProducto.Licores));
                licores.add(new Producto("Whisky", 19, 121900, 10000, 13, TipoProducto.Licores));
                licores.add(new Producto("Tequila", 20, 65000, 47000, 10, TipoProducto.Licores));
                licores.add(new Producto("Amaretto", 21, 144000, 12000, 20, TipoProducto.Licores));
                licores.add(new Producto("Vodka", 22, 43600, 28100, 19, TipoProducto.Licores));
                licores.add(new Producto("Aguardiente", 23, 27000, 19700, 3, TipoProducto.Licores));
                licores.add(new Producto("Vino", 40, 34000, 15000, 23, TipoProducto.Licores));
                licores.add(new Producto("Crema de Whisky", 41, 48900, 34000, 17, TipoProducto.Licores));
                licores.add(new Producto("Coñac", 42, 67000, 19700, 34, TipoProducto.Licores));

                ArrayList<Producto> confiteria = new ArrayList<>();

                confiteria.add(new Producto("Gomitas", 24, 3600, 2600, 33, TipoProducto.Confiteria));
                confiteria.add(new Producto("Arequipe", 25, 6300, 3600, 21, TipoProducto.Confiteria));
                confiteria.add(new Producto("Barrilete", 26, 2500, 1100, 17, TipoProducto.Confiteria));
                confiteria.add(new Producto("Chocolatina", 27, 1500, 700, 14, TipoProducto.Confiteria));
                confiteria.add(new Producto("Panelita", 28, 2000, 500, 20, TipoProducto.Confiteria));
                confiteria.add(new Producto("ChocoRamo", 29, 5000, 2000, 19, TipoProducto.Confiteria));
                confiteria.add(new Producto("Chicle", 30, 2500, 1650, 20, TipoProducto.Confiteria));
                confiteria.add(new Producto("Menta", 31, 800, 350, 35, TipoProducto.Confiteria));
                confiteria.add(new Producto("Gelatina", 32, 3650, 2350, 18, TipoProducto.Confiteria));
                confiteria.add(new Producto("Bocadillo", 33, 6500, 4000, 23, TipoProducto.Confiteria));

                ArrayList<Producto> hogar = new ArrayList<>();

                hogar.add(new Producto("Plato", 34, 15000, 1000, 14, TipoProducto.Hogar));
                hogar.add(new Producto("Jarra", 35, 10000, 7500, 10, TipoProducto.Hogar));
                hogar.add(new Producto("Cuchillo", 36, 8500, 5600, 20, TipoProducto.Hogar));
                hogar.add(new Producto("Cuchara", 37, 8700, 5700, 30, TipoProducto.Hogar));
                hogar.add(new Producto("Cafetera", 38, 57000, 43500, 9, TipoProducto.Hogar));
                hogar.add(new Producto("Vasos", 39, 12000, 7600, 19, TipoProducto.Hogar));
                hogar.add(new Producto("Abrelatas", 46, 23000, 15600, 14, TipoProducto.Hogar));
                hogar.add(new Producto("Pinzas", 47, 16700, 11300, 12, TipoProducto.Hogar));
                hogar.add(new Producto("Rallador", 48, 17800, 14650, 15, TipoProducto.Hogar));
                hogar.add(new Producto("Pelador de verduras", 49, 9600, 6400, 19, TipoProducto.Hogar));

                ArrayList<ArrayList<Producto>> todos = new ArrayList<>() {
                        {
                                add(alimentos);
                                add(aseo);
                                add(licores);
                                add(confiteria);
                                add(hogar);
                        }
                };

                bodega1.setProveedores(generarProveedores());
                bodega2.setProveedores(generarProveedores());

                // Genera las compras de los productos de cada bodega.
                for (ArrayList<Producto> productos : todos) {
                        bodega1.generarCompra(fecha, productos);
                        bodega2.generarCompra(fecha, deepCopyProductos(productos));
                }

                return new ArrayList<Bodega>() {
                        {
                                add(bodega1);
                                add(bodega2);
                        }
                };

        }

        /**
         * Genera y retorna cinco (5) instancias predefinidas de la clase Cliente.
         * 
         * @return ArrayList de Cliente.
         */
        private static ArrayList<Cliente> generarClientes() {
                ArrayList<Cliente> clientes = new ArrayList<>();

                clientes.add(new Cliente("Juan Perez", "1001532054", "juan.perez@gmail.com"));
                clientes.add(new Cliente("Maria Lopez", "1031524055", "marialopez34@yahoo.com"));
                clientes.add(new Cliente("Pedro Martinez", "1201532636", "pedroM032@gmail.com"));
                clientes.add(new Cliente("Sofia Sanchez", "1000105295", "sanchez_maria01@gmail.com"));
                clientes.add(new Cliente("Carlos Ramirez", "1000294012", "carlosRamirezO@hotmail.com"));

                return clientes;
        }

        /**
         * Genera y retorna cinco (5) instancias predefinidas de la clase Proveedor.
         * 
         * @return ArrayList de Proveedor.
         */
        private static ArrayList<Proveedor> generarProveedores() {
                ArrayList<Proveedor> proveedores = new ArrayList<>();

                proveedores.add(new Proveedor("Guillermo Rendón", "49319294", "rendonguillermo_55@gmail.com",
                                TipoProducto.Confiteria));
                proveedores.add(new Proveedor("Juan Camilo Acevedo", "100429443", "juanC.acevedo@gmail.com",
                                TipoProducto.Hogar));
                proveedores.add(new Proveedor("Sandra Rodriguez", "1003431298", "SRodriguezC02@hotmail.com",
                                TipoProducto.Alimentos));
                proveedores.add(new Proveedor("Fabrica de Licores de Antioquia", "9014367751", "mercuriofla@fla.com.co",
                                TipoProducto.Licores));

                proveedores.add(new Proveedor("Fernando Guerra", "1004245923", "fernandoguerra_negocios@hotmail.com",
                                TipoProducto.Aseo));

                return proveedores;
        }

        /**
         * Método de utilidad para hacer una copia profunda de un array de la clase
         * Producto.
         * Se utiliza para facilidad a la hora de instanciar los productos de las
         * bodegas.
         * 
         * @param productos ArrayList de Producto.
         * @return ArrayList de Producto.
         */
        private static ArrayList<Producto> deepCopyProductos(ArrayList<Producto> lista) {
                // deep copy de la lista
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                try {
                        oos = new ObjectOutputStream(bos);
                        oos.writeObject(lista);
                        oos.flush();
                        oos.close();
                        bos.close();
                        byte[] byteData = bos.toByteArray();
                        ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
                        @SuppressWarnings("unchecked")
                        ArrayList<Producto> newProductos = (ArrayList<Producto>) new ObjectInputStream(bais)
                                        .readObject();
                        return newProductos;
                } catch (IOException e) {
                        throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }
}
