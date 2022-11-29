from abc import ABC, abstractmethod


class Operacion(ABC):
    def __init__(self, fecha, productos):
        self._fecha = fecha
        self._productos = productos

    def getFecha(self):
        return self._fecha

    def setFecha(self, fecha):
        self._fecha = fecha

    def getProductos(self):
        return self._productos

    def setProductos(self, productos):
        self._productos = productos

    @abstractmethod
    def generarFactura(self):
        pass

    @abstractmethod
    def getTotal():
        pass
