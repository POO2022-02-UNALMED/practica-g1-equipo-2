from tkinter import *
from tkinter import ttk

class ResumenProveedores(Frame):

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
        # atributos para guardar los datos que se mostrarán en pantalla
        self._listaProv = ResumenProveedores._tienda.getBodegas()[0].getProveedores()

        #texto con el titulo del nombre de la funcionalidad
        titulo=Label(self,text="Resumen de Proveedores Actuales para las Bodegas",font=55,width=66,bg="#3B82BF")
        titulo.place(x=110,y=50)

        #texto co la descripción de la funcionalidad
        contenedor=Frame(self,bg="light steel blue",width=730,height=310,relief="ridge",bd=1)
        contenedor.pack()
        contenedor.place(x=110,y=80)

        #tabla para mostrar los proveedores actuales de las bodegas
        tablaClientes = ttk.Treeview(contenedor,height=12)
        tablaClientes.place(x=20,y=20)

        #organizacion espacial y formateo de filas y columnas de la tabla anterior
        verscrlbar = ttk.Scrollbar(contenedor, orient="vertical", command=tablaClientes.yview)
        verscrlbar.place(x=693, y=20, height=265)
        tablaClientes.configure(yscrollcommand=verscrlbar.set)

        tablaClientes["columns"] = ("Nombre","Documento","Email", "TipoProducto")
        tablaClientes.column("#0",width= 0,stretch=NO)
        tablaClientes.column("Nombre",anchor=W,width=175)
        tablaClientes.column("Documento",anchor=CENTER,width=100)
        tablaClientes.column("Email",anchor=CENTER,width=250)
        tablaClientes.column("TipoProducto",anchor=CENTER,width=160)

        tablaClientes.tag_configure('oddrow', background="white")
        tablaClientes.tag_configure('evenrow', background="lightblue")

        tablaClientes.heading("#0", text="", anchor=W)
        tablaClientes.heading("Nombre", text="Nombre", anchor=W)
        tablaClientes.heading("Documento", text="Documento", anchor=CENTER)
        tablaClientes.heading("Email", text="Email", anchor=CENTER)
        tablaClientes.heading("TipoProducto", text="Tipo de Producto", anchor=CENTER)

        #se agregan los proveedores actuales de las bodegas a la tabla anterior
        for proveedor in self._listaProv:
            if self._listaProv.index(proveedor) % 2 ==0:
                tablaClientes.insert(parent='', index='end', text="", values=(proveedor.getNombre(), proveedor.getDocumento(), proveedor.getEmail(), proveedor.getProductoVende().name), tags=('evenrow',))
            else:
                tablaClientes.insert(parent='', index='end', text="", values=(proveedor.getNombre(), proveedor.getDocumento(), proveedor.getEmail(), proveedor.getProductoVende().name), tags=('oddrow',))
           
    # metodo para poner este frame en la ventana en la cual estará contenido
    def VentanaProveedores(self):
        self.configInicial()
        self.OrganizarEspacio()

    @classmethod
    def setTienda(cls, tienda):
        cls._tienda = tienda
