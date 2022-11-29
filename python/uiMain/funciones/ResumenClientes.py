from tkinter import *
from tkinter import ttk

class ResumenClientes(Frame):

    #atributo de clase para guardar una instacia de la tienda principal futuramente
    _tienda = None
    
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

        #Atributo para guardar la informacion que se mostrara en el frame
        self._listaClientes = ResumenClientes._tienda.getClientes()
        
        #texto con el titulo del nombre de la funcionalidad
        titulo=Label(self,text="Resumen de Clientes Actualmente Registrados",font=55,width=52,bg="#3B82BF")
        titulo.place(x=180,y=50)

        #texto co la descripción de la funcionalidad
        contenedor=Frame(self,bg="light steel blue",width=580,height=310,relief="ridge",bd=1)
        contenedor.pack()
        contenedor.place(x=180,y=80)

        #tabla para mostrar los clientes registrados en la tienda
        tablaClientes = ttk.Treeview(contenedor,height=12)
        tablaClientes.place(x=20,y=20)

        #organizacion espacial y formateo de filas y columnas de la tabla anterior
        verscrlbar = ttk.Scrollbar(contenedor, orient="vertical", command=tablaClientes.yview)
        verscrlbar.place(x=542, y=20, height=265)
        tablaClientes.configure(yscrollcommand=verscrlbar.set)

        tablaClientes["columns"] = ("Nombre","Documento","Email")
        tablaClientes.column("#0",width= 0,stretch=NO)
        tablaClientes.column("Nombre",anchor=W,width=170)
        tablaClientes.column("Documento",anchor=CENTER,width=100)
        tablaClientes.column("Email",anchor=CENTER,width=250)

        tablaClientes.tag_configure('oddrow', background="white")
        tablaClientes.tag_configure('evenrow', background="lightblue")

        tablaClientes.heading("#0", text="", anchor=W)
        tablaClientes.heading("Nombre", text="Nombre", anchor=W)
        tablaClientes.heading("Documento", text="Documento", anchor=CENTER)
        tablaClientes.heading("Email", text="Email", anchor=CENTER)

        #se agregan los clientes registrados en la tabla anterior
        for cliente in self._listaClientes:
            if self._listaClientes.index(cliente) % 2 ==0:
                tablaClientes.insert(parent='', index='end', text="", values=(cliente.getNombre(), cliente.getDocumento(), cliente.getEmail()), tags=('evenrow',))
            else:
                tablaClientes.insert(parent='', index='end', text="", values=(cliente.getNombre(), cliente.getDocumento(), cliente.getEmail()), tags=('oddrow',))
           

    # metodo para poner este frame en la ventana en la cual estará contenido
    def VentanaClientes(self):
        self.configInicial()
        self.OrganizarEspacio()

    @classmethod
    def setTienda(cls, tienda):
        cls._tienda = tienda
