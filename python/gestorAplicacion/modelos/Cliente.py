from gestorAplicacion.modelos.Persona import Persona


class Cliente(Persona):
    def __init__(self, nombre, documento, email):
        super().__init__(nombre, documento, email)

    def calcularFidelidad(self, numeroVentasTienda):
        return 0 if (numeroVentasTienda == 0) else len(super().getHistorico())/numeroVentasTienda
