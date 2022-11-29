from gestorAplicacion.modelos.TipoProducto import TipoProducto


class Producto:
    def __init__(self, nombre, id, precioVenta, costoCompra, cantidad, tipoProducto):
        self._nombre = nombre
        self._id = id
        self._precioVenta = precioVenta
        self._costoCompra = costoCompra
        self._cantidad = cantidad
        self._tipoProducto = tipoProducto
        self._descuento = 0

    def getNombre(self):
        return self._nombre

    def setNombre(self, nombre):
        self._nombre = nombre

    def getId(self):
        return self._id

    def setId(self, id):
        self._id = id

    def getPrecioVenta(self):
        return self._precioVenta

    def setPrecioVenta(self, precioVenta):
        self._precioVenta = precioVenta

    def getCostoCompra(self):
        return self._costoCompra

    def setCostoCompra(self, costoCompra):
        self._costoCompra = costoCompra

    def getCantidad(self):
        return self._cantidad

    def setCantidad(self, cantidad):
        self._cantidad = cantidad

    def getTipoProducto(self):
        return self._tipoProducto

    def setTipoProducto(self, tipoProducto):
        self._tipoProducto = tipoProducto

    def getDescuento(self):
        return self._descuento

    def setDescuento(self, descuento):
        self._descuento = descuento
