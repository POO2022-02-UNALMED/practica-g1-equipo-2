from gestorAplicacion.errores.ErrorAplicacion import ErrorAplicacion
from baseDatos.plantillaTienda import PlantillaTienda
from tkinter import messagebox

class ErrorInterno(ErrorAplicacion):
    """
    Representa un error en el funcionamiento interno de la aplicación; es decir, un error
    que no se ha producido por interacción del usuario, sino por un fallo o
    comportamiento inesperado del programa.
    Aporta la segunda parte del mensaje que se muestra por consola.
    """

    
    def __init__(self, mensaje):
        super().__init__("  - Se ha producido un error interno en la aplicación: \n" + mensaje)
        self._mensaje = mensaje


"""
Las siguientes clases son errores específicos que se pueden producir en la aplicación.
Heredan de ErrorInterno y aportan la línea final del mensaje que se muestra por consola.
Algunas de ellas cuentan con implementaciones específicas de métodos.
"""


class ErrorTiendaNoEncontrada(ErrorInterno):
    """
    Se produce cuando no se encuentra el archivo serializado de la tienda en la ruta especificada.
    Para estos casos, posee el método generarTienda que permite crear una nueva tienda
    utilizando los datos de la plantilla.
    """


    def __init__(self):
        super().__init__("      - No se encontró una tienda en la base de datos")

    def generarTienda(self):
        messagebox.showinfo("Información", "Parece que no hay una tienda establecidada... \n" +
        "Se generará una nueva tienda a partir de la plantilla")

        return PlantillaTienda.generarTienda()



class ErrorObjetoInexistente(ErrorInterno):
    """
    Se produce cuando se intenta acceder a un objeto que no existe en la tienda actual.
    """


    def __init__(self, mensaje):
        super().__init__(f"     - {mensaje}")
        self._mensaje = mensaje


class ErrorCantidadInsuficiente(ErrorInterno):
    """
    Se produce cuando se intenta hacer una operación con un producto
    con una cantidad inferior al mínimo requerido.
    Por ejemplo, si intenta vender o comprar 0 unidades de un producto, o si intenta ingresar
    menos de 57 unidades en una venta al por mayor.
    """


    def __init__(self, cantidadMinima):
        super().__init__("      - La cantidad de unidades ingresada es menor a la cantidad mínima. \n" + 
        f"       La cantidad mínima requerida es de {cantidadMinima} unidades")
        self._cantidadMinima = cantidadMinima
