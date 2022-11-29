class Persona:
    def __init__(self, nombre="Cristiano Ronaldo", documento="777777777", email="elbicho@gmail.com"):
        self._nombre = nombre
        self._documento = documento
        self._email = email
        self._historico = []

    def getNombre(self):
        return self._nombre

    def setNombre(self, nombre):
        self._nombre = nombre

    def getDocumento(self):
        return self._documento

    def setDocumento(self, documento):
        self._documento = documento

    def getEmail(self):
        return self._email

    def setEmail(self, email):
        self._email = email

    def getHistorico(self):
        return self._historico

    def setHistorico(self, historico):
        self._historico = historico

    def agregarOperacion(self, operacion):
        self._historico.append(operacion)
