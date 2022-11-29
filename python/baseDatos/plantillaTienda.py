# Creacion de la plantilla inicial de la tienda
from gestorAplicacion.modelos.Bodega import Bodega
from gestorAplicacion.modelos.Cliente import Cliente 
from gestorAplicacion.modelos.Establecimiento import Establecimiento
from gestorAplicacion.modelos.Persona import Persona
from gestorAplicacion.modelos.Producto import Producto
from gestorAplicacion.modelos.Proveedor import Proveedor
from gestorAplicacion.modelos.Tienda import Tienda
from gestorAplicacion.modelos.TipoProducto import TipoProducto

from datetime import date
import copy


class PlantillaTienda:
    # Variable de clase para guardar la fecha actual
    fecha = date.today()
    
    # Generacion de la tienda
    def generarTienda():
        # Se instancia una tienda sin argumentos
        tienda = Tienda()
        
        # Ponemos el tamaño, mínima cantidad de productos y la fecha actual
        tienda.setTamanoAlmacenamiento(1000);
        tienda.setMinProducto(10);
        tienda.setFechaHoy(PlantillaTienda.fecha);

        # Generamos las bodegas
        bodegas = PlantillaTienda.generarBodegas()

        # Añadimos las bodegas previamente generadas a la tienda
        tienda.setBodegas(bodegas)
        
        # Generamos clientes y los añadimos a la tienda
        tienda.setClientes(PlantillaTienda.generarClientes())

        # Instanciamos productos y los añadimos a la lista de tipo alimento
        alimentos = []

        alimentos.append(Producto("Leche", 0, 3000, 1500, 6, TipoProducto.Alimentos));
        alimentos.append(Producto("Galletas", 1, 6000, 4400, 15, TipoProducto.Alimentos));
        alimentos.append(Producto("Pan", 2, 3000, 1700, 25, TipoProducto.Alimentos));
        alimentos.append(Producto("Aceite", 3, 7000, 5000, 11, TipoProducto.Alimentos));
        alimentos.append(Producto("Frijoles", 4, 2000, 500, 19, TipoProducto.Alimentos));
        alimentos.append(Producto("Arepas", 5, 4300, 2000, 40, TipoProducto.Alimentos));
        alimentos.append(Producto("Café", 6, 6500, 4700, 25, TipoProducto.Alimentos));
        alimentos.append(Producto("Arroz", 7, 5600, 3400, 16, TipoProducto.Alimentos));
        alimentos.append(Producto("Queso", 8, 4000, 2000, 18, TipoProducto.Alimentos));
        alimentos.append(Producto("Sal", 9, 3600, 5000, 27, TipoProducto.Alimentos));

        # Instanciamos productos y los añadimos a la lista de tipo aseo
        aseo = []

        aseo.append(Producto("Jabón Rey", 10, 6200, 4100, 30, TipoProducto.Aseo));
        aseo.append(Producto("Detergente en Polvo", 11, 8000, 6500, 4, TipoProducto.Aseo));
        aseo.append(Producto("Blanqueador", 12, 8700, 5800, 12, TipoProducto.Aseo));
        aseo.append(Producto("Papel Higienico", 13, 5400, 3100, 24, TipoProducto.Aseo));
        aseo.append(Producto("Suavizante", 14, 12000, 8000, 13, TipoProducto.Aseo));
        aseo.append(Producto("Lavaloza", 15, 7200, 4100, 32, TipoProducto.Aseo));
        aseo.append(Producto("Toalla de Cocina", 16, 5700, 2400, 1, TipoProducto.Aseo));
        aseo.append(Producto("Lavaloza liquido", 43, 8200, 6400, 15, TipoProducto.Aseo));
        aseo.append(Producto("Esponja", 44, 5600, 3700, 10, TipoProducto.Aseo));
        aseo.append(Producto("Limpia pisos", 45, 9000, 6850, 20, TipoProducto.Aseo));

        # Instanciamos productos y los añadimos a la lista de tipo licores
        licores = []

        licores.append(Producto("Ron", 17, 33500, 25000, 30, TipoProducto.Licores));
        licores.append(Producto("Cerveza", 18, 10500, 8000, 40, TipoProducto.Licores));
        licores.append(Producto("Whisky", 19, 121900, 10000, 13, TipoProducto.Licores));
        licores.append(Producto("Tequila", 20, 65000, 47000, 10, TipoProducto.Licores));
        licores.append(Producto("Amaretto", 21, 144000, 12000, 20, TipoProducto.Licores));
        licores.append(Producto("Vodka", 22, 43600, 28100, 19, TipoProducto.Licores));
        licores.append(Producto("Aguardiente", 23, 27000, 19700, 3, TipoProducto.Licores));
        licores.append(Producto("Vino", 40, 34000, 15000, 23, TipoProducto.Licores));
        licores.append(Producto("Crema de Whisky", 41, 48900, 34000, 17, TipoProducto.Licores));
        licores.append(Producto("Coñac", 42, 67000, 19700, 34, TipoProducto.Licores));

        # Instanciamos productos y los añadimos a la lista de tipo confiteria
        confiteria = []

        confiteria.append(Producto("Gomitas", 24, 3600, 2600, 33, TipoProducto.Confiteria));
        confiteria.append(Producto("Arequipe", 25, 6300, 3600, 21, TipoProducto.Confiteria));
        confiteria.append(Producto("Barrilete", 26, 2500, 1100, 17, TipoProducto.Confiteria));
        confiteria.append(Producto("Chocolatina", 27, 1500, 700, 14, TipoProducto.Confiteria));
        confiteria.append(Producto("Panelita", 28, 2000, 500, 20, TipoProducto.Confiteria));
        confiteria.append(Producto("ChocoRamo", 29, 5000, 2000, 19, TipoProducto.Confiteria));
        confiteria.append(Producto("Chicle", 30, 2500, 1650, 20, TipoProducto.Confiteria));
        confiteria.append(Producto("Menta", 31, 800, 350, 35, TipoProducto.Confiteria));
        confiteria.append(Producto("Gelatina", 32, 3650, 2350, 18, TipoProducto.Confiteria));
        confiteria.append(Producto("Bocadillo", 33, 6500, 4000, 23, TipoProducto.Confiteria));

        # Instanciamos productos y los añadimos a la lista de tipo hogar
        hogar = []

        hogar.append(Producto("Plato", 34, 15000, 1000, 14, TipoProducto.Hogar));
        hogar.append(Producto("Jarra", 35, 10000, 7500, 10, TipoProducto.Hogar));
        hogar.append(Producto("Cuchillo", 36, 8500, 5600, 20, TipoProducto.Hogar));
        hogar.append(Producto("Cuchara", 37, 8700, 5700, 30, TipoProducto.Hogar));
        hogar.append(Producto("Cafetera", 38, 57000, 43500, 9, TipoProducto.Hogar));
        hogar.append(Producto("Vasos", 39, 12000, 7600, 19, TipoProducto.Hogar));
        hogar.append(Producto("Abrelatas", 46, 23000, 15600, 14, TipoProducto.Hogar));
        hogar.append(Producto("Pinzas", 47, 16700, 11300, 12, TipoProducto.Hogar));
        hogar.append(Producto("Rallador", 48, 17800, 14650, 15, TipoProducto.Hogar));
        hogar.append(Producto("Pelador de verduras", 49, 9600, 6400, 19, TipoProducto.Hogar));

        # creamos una lista para añadir todos los productos en una sola
        todos = []
        todos.extend(alimentos)
        todos.extend(aseo)
        todos.extend(licores)
        todos.extend(confiteria)
        todos.extend(hogar)
        
        
        # Lista distinta para añadir todos los productos pero por tipo
        todosPorTipo = [alimentos, aseo, licores, confiteria, hogar]

        # Generamos compra a los proveedores de los productos por tipo para las bodegas
        for productos in todosPorTipo:
            bodegas[0].generarCompra(PlantillaTienda.fecha, productos)
            
        # Pedimos traslado de las bodegas a la tienda
        for producto in todos:
            tienda.pedirTraslado(PlantillaTienda.fecha, producto, producto.getCantidad())
            
        
        return tienda # retornamos la tienda generada
    
    
    # Funcion para generar bodega
    def generarBodegas():
        
        # Instanciamos bodegas con atributos iniciales
        bodega1 = Bodega(1000, 10, 1, []);
        bodega2 = Bodega(1000, 10, 2, []);

        # Instanciamos productos y los añadimos a la lista de tipo alimentos

        alimentos = []

        alimentos.append(Producto("Leche", 0, 3000, 1500, 6, TipoProducto.Alimentos));
        alimentos.append(Producto("Galletas", 1, 6000, 4400, 15, TipoProducto.Alimentos));
        alimentos.append(Producto("Pan", 2, 3000, 1700, 25, TipoProducto.Alimentos));
        alimentos.append(Producto("Aceite", 3, 7000, 5000, 11, TipoProducto.Alimentos));
        alimentos.append(Producto("Frijoles", 4, 2000, 500, 19, TipoProducto.Alimentos));
        alimentos.append(Producto("Arepas", 5, 4300, 2000, 40, TipoProducto.Alimentos));
        alimentos.append(Producto("Café", 6, 6500, 4700, 25, TipoProducto.Alimentos));
        alimentos.append(Producto("Arroz", 7, 5600, 3400, 16, TipoProducto.Alimentos));
        alimentos.append(Producto("Queso", 8, 4000, 2000, 18, TipoProducto.Alimentos));
        alimentos.append(Producto("Sal", 9, 3600, 5000, 27, TipoProducto.Alimentos));

        # Instanciamos productos y los añadimos a la lista de tipo aseo
        aseo = []

        aseo.append(Producto("Jabón Rey", 10, 6200, 4100, 30, TipoProducto.Aseo));
        aseo.append(Producto("Detergente en Polvo", 11, 8000, 6500, 4, TipoProducto.Aseo));
        aseo.append(Producto("Blanqueador", 12, 8700, 5800, 12, TipoProducto.Aseo));
        aseo.append(Producto("Papel Higienico", 13, 5400, 3100, 24, TipoProducto.Aseo));
        aseo.append(Producto("Suavizante", 14, 12000, 8000, 13, TipoProducto.Aseo));
        aseo.append(Producto("Lavaloza", 15, 7200, 4100, 32, TipoProducto.Aseo));
        aseo.append(Producto("Toalla de Cocina", 16, 5700, 2400, 1, TipoProducto.Aseo));
        aseo.append(Producto("Lavaloza liquido", 43, 8200, 6400, 15, TipoProducto.Aseo));
        aseo.append(Producto("Esponja", 44, 5600, 3700, 10, TipoProducto.Aseo));
        aseo.append(Producto("Limpia pisos", 45, 9000, 6850, 20, TipoProducto.Aseo));

        # Instanciamos productos y los añadimos a la lista de tipo licores
        licores = []

        licores.append(Producto("Ron", 17, 33500, 25000, 30, TipoProducto.Licores));
        licores.append(Producto("Cerveza", 18, 10500, 8000, 40, TipoProducto.Licores));
        licores.append(Producto("Whisky", 19, 121900, 10000, 13, TipoProducto.Licores));
        licores.append(Producto("Tequila", 20, 65000, 47000, 10, TipoProducto.Licores));
        licores.append(Producto("Amaretto", 21, 144000, 12000, 20, TipoProducto.Licores));
        licores.append(Producto("Vodka", 22, 43600, 28100, 19, TipoProducto.Licores));
        licores.append(Producto("Aguardiente", 23, 27000, 19700, 3, TipoProducto.Licores));
        licores.append(Producto("Vino", 40, 34000, 15000, 23, TipoProducto.Licores));
        licores.append(Producto("Crema de Whisky", 41, 48900, 34000, 17, TipoProducto.Licores));
        licores.append(Producto("Coñac", 42, 67000, 19700, 34, TipoProducto.Licores));

        # Instanciamos productos y los añadimos a la lista de tipo confiteria
        confiteria = []

        confiteria.append(Producto("Gomitas", 24, 3600, 2600, 33, TipoProducto.Confiteria));
        confiteria.append(Producto("Arequipe", 25, 6300, 3600, 21, TipoProducto.Confiteria));
        confiteria.append(Producto("Barrilete", 26, 2500, 1100, 17, TipoProducto.Confiteria));
        confiteria.append(Producto("Chocolatina", 27, 1500, 700, 14, TipoProducto.Confiteria));
        confiteria.append(Producto("Panelita", 28, 2000, 500, 20, TipoProducto.Confiteria));
        confiteria.append(Producto("ChocoRamo", 29, 5000, 2000, 19, TipoProducto.Confiteria));
        confiteria.append(Producto("Chicle", 30, 2500, 1650, 20, TipoProducto.Confiteria));
        confiteria.append(Producto("Menta", 31, 800, 350, 35, TipoProducto.Confiteria));
        confiteria.append(Producto("Gelatina", 32, 3650, 2350, 18, TipoProducto.Confiteria));
        confiteria.append(Producto("Bocadillo", 33, 6500, 4000, 23, TipoProducto.Confiteria));

        # Instanciamos productos y los añadimos a la lista de tipo hogar
        hogar = []

        hogar.append(Producto("Plato", 34, 15000, 1000, 14, TipoProducto.Hogar));
        hogar.append(Producto("Jarra", 35, 10000, 7500, 10, TipoProducto.Hogar));
        hogar.append(Producto("Cuchillo", 36, 8500, 5600, 20, TipoProducto.Hogar));
        hogar.append(Producto("Cuchara", 37, 8700, 5700, 30, TipoProducto.Hogar));
        hogar.append(Producto("Cafetera", 38, 57000, 43500, 9, TipoProducto.Hogar));
        hogar.append(Producto("Vasos", 39, 12000, 7600, 19, TipoProducto.Hogar));
        hogar.append(Producto("Abrelatas", 46, 23000, 15600, 14, TipoProducto.Hogar));
        hogar.append(Producto("Pinzas", 47, 16700, 11300, 12, TipoProducto.Hogar));
        hogar.append(Producto("Rallador", 48, 17800, 14650, 15, TipoProducto.Hogar));
        hogar.append(Producto("Pelador de verduras", 49, 9600, 6400, 19, TipoProducto.Hogar));

        # creamos lista con todos los productos por tipo (lista de listas)
        todos = [alimentos, aseo, licores, confiteria, hogar]

        # Generamos los proveedores y los añadimos a las bodegas 
        bodega1.setProveedores(PlantillaTienda.generarProveedores());
        bodega2.setProveedores(PlantillaTienda.generarProveedores());

        
        # Generamos la compra de los productos 
        for productos in todos:
                bodega1.generarCompra(PlantillaTienda.fecha, productos)
                bodega2.generarCompra(PlantillaTienda.fecha, copy.deepcopy(productos))
        
        # Retornamos bodega 1 y 2
        return [bodega1, bodega2]


    # Funcion para generar los clientes
    def generarClientes():
        # Creamos una lista de clientes
        clientes = []
    
        # Añadimos cada cliente a la lista
        clientes.append(Cliente("Juan Perez", "1001532054", "juan.perez@gmail.com"))
        clientes.append(Cliente("Maria Lopez", "1031524055", "marialopez34@yahoo.com"))
        clientes.append(Cliente("Pedro Martinez", "1201532636", "pedroM032@gmail.com"))
        clientes.append(Cliente("Sofia Sanchez", "1000105295", "sanchez_maria01@gmail.com"))
        clientes.append(Cliente("Carlos Ramirez", "1000294012", "carlosRamirezO@hotmail.com"))

        # Retornamos la lista de clientes
        return clientes
        
    
    # Funcion para generar proveedores
    def generarProveedores():

        # Creamos una lista de proveedores
        proveedores = []
    
        # Añadimos cada proveedor a la lista
        proveedores.append(Proveedor("Guillermo Rendón", "49319294", "rendonguillermo_55@gmail.com", TipoProducto.Confiteria))
        proveedores.append(Proveedor("Juan Camilo Acevedo", "100429443", "juanC.acevedo@gmail.com", TipoProducto.Hogar))
        proveedores.append(Proveedor("Sandra Rodriguez", "1003431298", "SRodriguezC02@hotmail.com", TipoProducto.Alimentos))
        proveedores.append(Proveedor("Fabrica de Licores de Antioquia", "9014367751", "mercuriofla@fla.com.co", TipoProducto.Licores))
        proveedores.append(Proveedor("Fernando Guerra", "1004245923", "fernandoguerra_negocios@hotmail.com", TipoProducto.Aseo))

        # Retornamos la lista de proveedores
        return proveedores

    
    
    
    
    
    
    
    
    
    