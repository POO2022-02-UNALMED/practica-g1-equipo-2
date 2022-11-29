from gestorAplicacion.modelos.Establecimiento import Establecimiento
from gestorAplicacion.modelos.TipoProducto import TipoProducto
from gestorAplicacion.operaciones.Compra import Compra
from gestorAplicacion.operaciones.Venta import Venta


class Bodega(Establecimiento):
    # Constructor
    def __init__(self, tamanoAlmacenamiento, minProducto, id, proveedores):
        super().__init__(tamanoAlmacenamiento, minProducto)
        self._id = id
        self._proveedores = proveedores
        self._compras = []

    # Getter y Setter
    def getId(self):
        return self._id

    def setId(self, id):
        self._id = id

    def getProveedores(self):
        return self._proveedores

    def setProveedores(self, proveedores):
        self._proveedores = proveedores

    def getCompras(self):
        return self._compras

    def setCompras(self, compras):
        self._compras = compras
        
    # Metodos

    def agregarCompra(self, compra):
        self._compras.append(compra)

    def agregarProveedor(self, proveedor):
        self._proveedores.append(proveedor)

    def buscarProveedor(self, tipoProducto):
        for proveedor in self._proveedores:
            if proveedor.getProductoVende() == tipoProducto:
                return proveedor
        return None

    def despachoPorMayor(self, fecha, producto):

        enStock = super().buscarProductoPorIDOriginal(producto.getId())

        prodCompra = []

        prodComCopia = super().buscarProductoPorIDCopia(producto.getId())
        prodComCopia.setCantidad(
            abs(enStock.getCantidad() - producto.getCantidad()))

        prodCompra.append(prodComCopia)

        if ((enStock.getCantidad() - producto.getCantidad()) < 0):
            self.generarCompra(fecha, prodCompra) # /////cambié productos por prodCompra

        enStock.setCantidad(enStock.getCantidad() - producto.getCantidad())

    def generarCompra(self, fecha, productos):
        proveedor = self.buscarProveedor(productos[0].getTipoProducto())
        compraActual = Compra(fecha, productos, proveedor)

        compraActual.costoTransporte()
        compraActual.calcularEgresos()

        self.agregarCompra(compraActual)
        proveedor.agregarOperacion(compraActual)

        for producto in productos:
            prodStock = super().buscarProductoPorIDOriginal(producto.getId())

            if (prodStock != None):
                prodStock.setCantidad(prodStock.getCantidad() +
                                      producto.getCantidad())
            else:
                super().agregarProducto(producto)

        return compraActual

    def trasladoTienda(self, fecha, idProducto, cantidad):
        prod = super().buscarProductoPorIDOriginal(idProducto)

        if ((prod.getCantidad() - cantidad) < 0):
            productoIndividual = []
            individual = super().buscarProductoPorIDCopia(idProducto)
            individual.setCantidad(abs(prod.getCantidad() - cantidad))
            productoIndividual.append(individual)
            self.generarCompra(fecha, productoIndividual)

        prod.setCantidad(prod.getCantidad() - cantidad)
