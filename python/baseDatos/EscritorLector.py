import pickle

class EscritorLector:

    #metodo para serializar en un archivo el objeto que se pase, en la direccion especificada
    def serializador(objeto, direccion):
        try:
            picklefile = open(direccion, "wb")
            pickle.dump(objeto,picklefile)
            picklefile.close()
        except:
            print("error de serializacion")   


    #metodo para deserializar el archivo de la direccion especificada
    def deserializador(direccion):
        try:
            picklefile = open(direccion, "rb")
        except:
            return None

        objeto = pickle.load(picklefile)
        picklefile.close()
        return objeto 


