from gestorAplicacion.operaciones.Operacion import Operacion
import datetime as dt
from gestorAplicacion.modelos.Producto import Producto
from gestorAplicacion.modelos.Proveedor import Proveedor

class Compra(Operacion):

    def __init__(self, fecha, productos, proveedor):
        super().__init__(fecha, productos)
        self._proveedor = proveedor
        self._costoTransporte = 0
        self._total = 0

    def getProveedor(self):
        return self._proveedor
    
    def setProveedor(self, proveedor):
        self._proveedor = proveedor

    def getCostoTransporte(self):
        return self._costoTransporte
    
    def setCostoTransporte(self, costoTransporte):
        self._costoTransporte = costoTransporte

    def costoTransporte(self):

        costoTrans = 20000

        for prodCompra in self.getProductos():
            cantProd = prodCompra.getCantidad()
            for i in range(1, 1000, 20):
                if cantProd <= i:
                    costoTrans += 50000
                    break
        
        
        self._costoTransporte = costoTrans

    def generarFactura(self):

        date = self._fecha

        factura = "\n============================================================\n"
        factura += "\t\t\t\tFactura De Compra\n"
        factura += "\n============================================================\n"
        factura += "\t\t\t\tProveedor\n"
        factura += "------------------------------------------------------------\n"
        factura += "\t\t\tNombre: " + self._proveedor.getNombre() + "\n"
        factura += "\t\t\tID: " + self._proveedor.getDocumento() + "\n"
        factura += "\t\t\tEmail: " + self._proveedor.getEmail() + "\n"
        factura += "============================================================\n"
        factura += "\t\t\t\tProductos\n"
        factura += "------------------------------------------------------------\n"
        for producto in self.getProductos():
            factura += "Nombre: " + producto.getNombre() + " | ID: " + producto.getId() + " | Costo: " + producto.getCostoCompra() + " | Cantidad: " + producto.getCantidad() + "\n"
            factura += "------------------------------------------------------------\n"

        factura += "============================================================\n"
        factura += "\t\t\t\tCosto del Transporte: " + self._costoTransporte + "%\n"
        factura += "============================================================\n"
        factura += "\t\t\t\tTOTAL: " + self._total + "$\n"
        factura += "============================================================\n"
        factura += "\t\t\t\tFecha: " + date + "\n"
        factura += "============================================================\n"
        factura += "\t\t\t\t¡MUCHAS GRACIAS POR SU COMPRA!\n"
        factura += "============================================================\n"

        return factura
    
    def calcularEgresos(self):

        self._total += self._costoTransporte

        for prodCompra in self.getProductos():

            self._total += prodCompra.getCostoCompra() * prodCompra.getCantidad()

    def getTotal(self):
        return self._total

        