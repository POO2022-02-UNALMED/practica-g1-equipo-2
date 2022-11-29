from tkinter import *
from tkinter import ttk
from FieldFrame import FieldFrame
from gestorAplicacion.errores.ErrorUsuario import ErrorCampoVacio, ErrorDatoIncorrecto, ErrorUsuario
from gestorAplicacion.modelos.Cliente import Cliente

class AgregarCliente(Frame):

    #atributo de clase para guardar una instacia de la tienda principal futuramente
    _tienda = None

    #metodo para autodestruir este frame 
    def destruir(self):
        self.destroy()

    def __init__(self, master):
        #Se inicializa la clasae padre
        super().__init__(master)
        self._ventana=master

    #limpia el _frame1 (destruye el anterior para borrar todo que había ahí y crea uno nuevo para poner todo)
    def configInicial(self):
        self._ventana._frame1.destroy()
        self._ventana._frame1=self
        self.config(bg="light steel blue")
        self.config(width=960, height=540)
        self.pack(expand=True)

    
    # Se organizan los elementos del frame en el espacio de la ventana 
    def OrganizarEspacio(self):
        
        #texto con el titulo del nombre de la funcionalidad
        titulo=Label(self,text="Agregar Cliente al Registro de la Tienda",font=55,width=66,bg="#3B82BF")
        titulo.place(x=110,y=10)

        #texto co la descripción de la funcionalidad
        descripcion=Label(self,text="Usted agregara la información personal de un nuevo cliente\n (cliente no registrado) a la base de datos de la tienda,\n esto servira para mejorar la experiencia del cliente futuramente.",font=(55,13),width=81,height=5,bg="#3B82BF")
        descripcion.place(x=110,y=50)

        #fielframe para solicitar al usuario que ingrese los datos del nuevo cliente
        self._SolicitudDatos = FieldFrame(self, "Datos", ["Nombre", "Documento", "Email"], "Ingreso de Datos", ["","","gdiv02@gmail.com"])
        self._SolicitudDatos.place(x=330,y=160)
        self._SolicitudDatos.funAceptar(self.botonAceptar)


    # metodo para poner este frame en la ventana en la cual estará contenido
    def VentanaAgregarCliente(self):
        self.configInicial()
        self.OrganizarEspacio()

        
    def botonAceptar(self):
        try:

            #Se obtiene la informacion del cliente ingresada en el fieldframe
            nombre = self._SolicitudDatos.getValue("Nombre")
            documento = self._SolicitudDatos.getValue("Documento")
            email = self._SolicitudDatos.getValue("Email")

            if(nombre.strip(" ") == "" or documento.strip(" ") == "" or email.strip(" ") == ""):
                campo = ""
                if(nombre.strip() == ""):
                    campo = "Nombre"
                elif (documento.strip() == ""):
                    campo = "Documento"
                elif (email.strip() == ""):
                    campo = "Email"

                raise ErrorCampoVacio(campo)

            if(not ErrorDatoIncorrecto.validarDato(nombre, "nombre")):
                raise ErrorDatoIncorrecto("Nombre")
            if(not ErrorDatoIncorrecto.validarDato(documento, "documento")):
                raise ErrorDatoIncorrecto("Documento")
            if(not ErrorDatoIncorrecto.validarDato(email, "email")):
                raise ErrorDatoIncorrecto("Email")

            # se miesta un texto de confirmación con la informacion del cliente agregado
            txt = f"¡ Cliente agregado con Exito !\n Nombre: {nombre}  Documento: {documento} Email: {email}"
            resultado=Label(self,text=txt,font=(55,13),width=81,bg="#3B82BF")
            resultado.place(x=110,y=480)

            #se crea la instancia de cliente y se agrega al registro de la tienda
            cliente = Cliente(nombre,documento,email)
            AgregarCliente._tienda.agregarCliente(cliente)

        except ErrorUsuario as e:
            print(e)
            e.mostrarError()

            return

    @classmethod
    def setTienda(cls, tienda):
        cls._tienda = tienda
