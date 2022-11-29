from gestorAplicacion.modelos.TipoProducto import TipoProducto
from gestorAplicacion.modelos.Persona import Persona


class Proveedor(Persona):
    def __init__(self, nombre, documento, email, productoVente):
        super().__init__(nombre, documento, email)
        self._productoVende = productoVente
        self._historicoProveedor = []

    def getProductoVende(self):
        return self._productoVende

    def getHistoricoProveedor(self):
        return self._historicoProveedor

    def setHistoricoProveedor(self, historicoProveedor):
        self._historicoProveedor = historicoProveedor
