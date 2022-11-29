from baseDatos.EscritorLector import EscritorLector
from baseDatos.plantillaTienda import PlantillaTienda
from gestorAplicacion.errores.ErrorInterno import ErrorTiendaNoEncontrada
import pathlib
import os

class instanciadorBD:

    #generamos la ruta del archivo sobre el que se serializará la tienda
    rutaTienda = os.path.join(pathlib.Path(__file__).parent.absolute(),"tienda.txt")
    print(rutaTienda)

    #metodo para tratar de obtener la tienda serializada
    def getTienda():
        try:
            #se trata de deserializar el archivo de la ruta especificada
            tienda = EscritorLector.deserializador(instanciadorBD.rutaTienda)
            #si no hay ninguna tienda se lanza una excepcion
            if (tienda == None):
                raise ErrorTiendaNoEncontrada
            return tienda
        
        except ErrorTiendaNoEncontrada as e:
            print(e)
            return e.generarTienda()

    #metodo para serializarr la tienda en la ruta especificada
    def setTienda(tienda):
        EscritorLector.serializador(tienda, instanciadorBD.rutaTienda)
        

