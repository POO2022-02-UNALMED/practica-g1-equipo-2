import copy

class Establecimiento:
    
    # Constructor
    def __init__(self, tamanoAlmacenamiento=2000, minProducto=10):
        self._tamanoAlmacenamiento = tamanoAlmacenamiento
        self._minProducto = minProducto
        self._productos = []

    # Getter y Setter
    def getTamanoAlmacenamiento(self):
        return self._tamanoAlmacenamiento

    def setTamanoAlmacenamiento(self, tamanoAlmacenamiento):
        self._tamanoAlmacenamiento = tamanoAlmacenamiento

    def getMinProducto(self):
        return self._minProducto

    def setMinProducto(self, minProducto):
        self._minProducto = minProducto

    def getProductos(self):
        return self._productos

    def setProductos(self, productos):
        self._productos = productos


    # Metodos
    def verDisponibilidad(self):
        cantidadOcupada = 0

        for producto in self._productos:
            cantidadOcupada += producto.getCantidad()

        return self._tamanoAlmacenamiento - cantidadOcupada

    def agregarProducto(self, producto):
        self._productos.append(producto)

    def buscarProductoPorIDCopia(self, id):
        for i in self._productos:
            if i.getId() == id:
                return copy.deepcopy(i)
        return None

    def buscarProductoPorIDOriginal(self, id):
        for i in self._productos:
            if i.getId() == id:
                return i

        return None

    def buscarProductoPorNombreCopia(self, nombre):
        for i in self._productos:
            if i.getNombre() == nombre:
                return copy.deepcopy(i)

        return None

    def buscarProductoPorNombreOriginal(self, nombre):
        for i in self._productos:
            if i.getNombre() == nombre:
                return i

        return None
