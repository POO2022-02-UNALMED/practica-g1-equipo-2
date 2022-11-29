import datetime as dt
from gestorAplicacion.operaciones.Operacion import Operacion
from gestorAplicacion.modelos.Cliente import Cliente
from gestorAplicacion.modelos.Producto import Producto


class Venta(Operacion):
    
    def __init__(self, fecha, productos, cliente):
        super().__init__(fecha, productos)
        self._cliente = cliente
        self._total = 0
        self._descuento = 0

    
    def calcularIngresos(self, descuentoGeneral):
        self._descuento = descuentoGeneral

        for producto in self.getProductos():
            self._total += producto.getCantidad()* (producto.getPrecioVenta() * (1 - producto.getDescuento()))

        self._total *= (1-descuentoGeneral)

    
    def generarFactura(self):

        date = self._fecha

        factura = "\n======================================\n"
        factura += "\tFactura De Venta\n"
        factura += "\tCliente\n"
        factura += "-------------------------------------------------------------------------------\n"
        factura += "\tNombre: " + self._cliente.getNombre() + "\n"
        factura += "\tID: " + self._cliente.getDocumento() + "\n"
        factura += "\tEmail: " + self._cliente.getEmail() + "\n"
        factura += "======================================\n"
        factura += "\tProductos\n"
        factura += "-------------------------------------------------------------------------------\n"
        for producto in self.getProductos():
            factura += "\tNombre: " + producto.getNombre() + " | ID: " + str(producto.getId()) + " | Precio: " + str(producto.getPrecioVenta()) + " | Cantidad: " + str(producto.getCantidad()) + "\n"
            factura += "-------------------------------------------------------------------------------\n"

        factura += "===========================\n"
        factura += "\tDescuentos por producto\n"
        factura += "-------------------------------------------------------------------------------\n"

        for producto in self.getProductos():
            if producto.getDescuento() != 0.0:
                factura += "\t\tNombre: " + producto.getNombre() + " | Descuento: " + str(producto.getDescuento() * 100) + "%\n"
                factura += "-------------------------------------------------------------------------------\n"

        factura += "======================================\n"
        factura += "\tDescuento General: " + str(self._descuento * 100) + "%\n"
        factura += "======================================\n"
        factura += "\tTOTAL: " + str(self._total) + "$\n"
        factura += "======================================\n"
        factura += "\tFecha: " + date.strftime("%d/%m/%Y") + "\n"
        factura += "======================================\n"
        factura += "\t¡MUCHAS GRACIAS POR SU COMPRA!\n"
        factura += "======================================\n"

        return factura
    
    def getCliente(self):
        return self._cliente

    def setCliente(self, cliente):
        self._cliente = cliente
    
    def getTotal(self):
        return self._total

    def setTotal(self, total):
        self._total = total