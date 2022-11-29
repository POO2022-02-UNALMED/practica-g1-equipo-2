from gestorAplicacion.modelos.Establecimiento import Establecimiento
from gestorAplicacion.operaciones.Venta import Venta 
from gestorAplicacion.modelos.TipoProducto import TipoProducto
from gestorAplicacion.modelos.Cliente import Cliente
from gestorAplicacion.modelos.Producto import Producto
from gestorAplicacion.modelos.Bodega import Bodega
import datetime as dt
from random import random as rd

class Tienda(Establecimiento):

    _descuentos = {TipoProducto.Alimentos: 0, TipoProducto.Aseo: 1,
                 TipoProducto.Hogar: 2, TipoProducto.Confiteria: 3, TipoProducto.Licores: 4}

    
    def __init__(self, tamanoAlmacenamiento=0, minProducto=0, fechahoy=None):
        super().__init__()
        self._fechahoy = fechahoy
        self._ventas = []
        self._clientes = []
        self._bodegas = []

    
    def getFechaHoy(self):
        return self._fechahoy
    
    def setFechaHoy(self, fecha):
        self._fechahoy = fecha

    def getVentas(self):
        return self._ventas
    
    def setVentas(self, ventas):
        self._ventas = ventas

    def getClientes(self):
        return self._clientes
    
    def setClientes(self, clientes):
        self._clientes = clientes

    def getBodegas(self):
        return self._bodegas
    
    def setBodegas(self, bodegas):
        self._bodegas = bodegas

    def agregarVenta(self, venta):
        self._ventas.append(venta)

    def agregarCliente(self, cliente):
        self._clientes.append(cliente)

    def generarVenta(self, fecha, cliente, productos):
        
        nuevaVenta = Venta(fecha, productos, cliente)

        self._ventas.append(nuevaVenta)
        cliente.agregarOperacion(nuevaVenta)

        for prodVenta in productos:
            productoEnStock = self.buscarProductoPorIDOriginal(prodVenta.getId())
            cantidadProductoEnStock = productoEnStock.getCantidad()
            cantidadProductoEnVenta = prodVenta.getCantidad()

            if (cantidadProductoEnStock-cantidadProductoEnVenta) < 0:
                self.pedirTraslado(fecha, productoEnStock, abs(cantidadProductoEnStock-cantidadProductoEnVenta))

            productoEnStock.setCantidad(productoEnStock.getCantidad()-cantidadProductoEnVenta)

        return nuevaVenta
    

    def productoStockBajo(self, producto):
        if producto.getCantidad() < self.getMinProducto():
            return True
        else:
            return False
        
    def cantidadEnBodegas(self, producto):
        cantidadTotal = 0
        idProdcuto = producto.getId()
        for bodega in self._bodegas:
            for producto4 in bodega.getProductos():
                if producto4.getId() == idProdcuto:
                    cantidadTotal += producto4.getCantidad()
        
        return cantidadTotal
        
    def pedirTraslado(self, fecha, producto, cantidad):
        
        prodVerificar = producto
        bodegaIndicada = None
        idProducto = producto.getId()
        
        for bodg in self._bodegas:
            if bodegaIndicada == None:
                bodegaIndicada = bodg
            elif bodg.buscarProductoPorIDOriginal(idProducto) != None and bodegaIndicada.buscarProductoPorIDOriginal(idProducto) != None:
                if bodg.buscarProductoPorIDOriginal(idProducto).getCantidad() > bodegaIndicada.buscarProductoPorIDOriginal(idProducto).getCantidad():
                    bodegaIndicada = bodg
            else:
                bodegaIndicada = bodg if bodg.buscarProductoPorIDOriginal(idProducto) != None else bodegaIndicada

        if self.buscarProductoPorIDOriginal(prodVerificar.getId()) != None:
            bodegaIndicada.trasladoTienda(fecha, idProducto, cantidad)
            producto.setCantidad(producto.getCantidad() + cantidad)
        else:
            bodegaIndicada.trasladoTienda(fecha, idProducto, cantidad)
            self.agregarProducto(producto)

    
    def ofertaDia(self, productosVenta, fecha):
        tipoDescuento = None
        for key, value in Tienda._descuentos.items():
            if value == fecha.weekday():
                tipoDescuento = key

        for prodVent in productosVenta:
            if prodVent.getTipoProducto() == tipoDescuento:
                descuento = round(rd() * 0.05 * 100.0) / 100.0
                prodVent.setDescuento(descuento)

    def agregarCliente(self, cliente):
        self._clientes.append(cliente)

    def buscarClientePorIdentificacion(self, documento):
        for cliente in self._clientes:
            if cliente.getDocumento() == documento:
                return cliente



    def buscarClientePorNombre(self, nombre):
        for cliente in self._clientes:
            if cliente.getNombre() == nombre:
                return cliente

