from tkinter import messagebox

class ErrorAplicacion(Exception):
    """
    Esta clase representa un error de la aplicación. Se utiliza como clase base
    a partir de la cual heredan las demás clases de error.
    Aporta la primera parte del mensaje que se muestra por consola, así
    como el método mostrarError, que muestra el mensaje de error al usuario
    a través de la interfaz.
    """


    def __init__(self, mensaje):
        self.mensaje = mensaje

    def __str__(self):
        return "=> Manejo de errores de la aplicacion: \n" + self.mensaje

    def mostrarError(self):
        messagebox.showerror("Error", self._mensaje)

