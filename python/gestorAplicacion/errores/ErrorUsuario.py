from gestorAplicacion.errores.ErrorAplicacion import ErrorAplicacion
import re

class ErrorUsuario(ErrorAplicacion):
    """
    Representa un error en el funcionamiento de la aplicación por parte del usuario; es decir, un error
    que se ha producido por una interacción del usuario con la interfaz de la aplicación, ya sea por
    introducir datos incorrectos, o por no introducirlos, o por no seguir las instrucciones planteadas.
    Aporta la segunda parte del mensaje que se muestra por consola.
    """


    def __init__(self, mensaje):
        super().__init__("  - Se ha producido un error por interacción del usuario: \n" + mensaje)
        self._mensaje = mensaje


"""
Las siguientes clases son errores específicos que se pueden producir en la aplicación.
Heredan de ErrorUsuario y aportan la línea final del mensaje que se muestra por consola.
Algunas de ellas cuentan con implementaciones específicas de métodos.
"""


class ErrorCampoVacio(ErrorUsuario):
    """
    Se produce cuando el usuario no introduce ningún valor en un campo de texto requerido.
    """


    def __init__(self, campo):
        super().__init__("      - El campo " + campo + " no puede estar vacio")
    


class ErrorCarritoVacio(ErrorUsuario):
    """
    Se produce cuando el usuario intenta realizar una venta sin haber añadido ningún producto al carrito.
    """


    def __init__(self):
        self._mensaje = "       - No se puede generar una venta sin haber agregado productos al carrito de compras"
        super().__init__(self._mensaje)



class ErrorDatoIncorrecto(ErrorUsuario):
    """
    Se produce cuando el usuario introduce un valor incorrecto en un campo de texto.
    """


    def __init__(self, campo):
        super().__init__("      - El valor introducido en el campo " + campo + " no es correcto")

    @staticmethod
    def validarDato(dato, tipoDato):
        tipoDato = tipoDato.lower().strip(" ")
        dato = dato.strip(" ")

        if(tipoDato == "id"):
            try:
                int(dato)
                return True
            except ValueError:
                return False
        elif(tipoDato == "nombre"):
            return dato.replace(" ", "").isalpha()
        elif(tipoDato == "cantidad"):
            return dato.isnumeric()
        elif(tipoDato == "email"):
            regex = re.search(r"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$", dato)
            return True if regex else False
        elif(tipoDato == "documento"):
            regex = re.search(r"^\d{7,10}$", dato)
            return True if regex else False
        else:
            return False
    