from tkinter import *
from tkinter import ttk
from tkinter import messagebox
from FieldFrame import FieldFrame
import datetime as dt
from gestorAplicacion.operaciones.Venta import Venta
from gestorAplicacion.errores.ErrorUsuario import ErrorCampoVacio
from gestorAplicacion.errores.ErrorInterno import ErrorObjetoInexistente, ErrorCantidadInsuficiente


class VentaAlPorMayor(Frame):

    #atributo de clase para guardar una instacia de la tienda principal futuramente
    _tienda = None

    #metodo para autodestruir este frame 
    def destruir(self):
        self.destroy()

    def __init__(self, master):
        #Se inicializa la clasae padre
        super().__init__(master)
        self._ventana=master
        self.count = 0
        
    #limpia el _frame1 (destruye el anterior para borrar todo que había ahí y crea uno nuevo para poner todo)
    def configInicial(self):
        self._ventana._frame1.destroy()
        self._ventana._frame1=self
        self.config(bg="light steel blue")
        self.config(width=960, height=540)
        self.pack(expand=True)

        
    # Se organizan los elementos del frame en el espacio de la ventana 
    def OrganizarEspacio(self):

        # atributos para guardar los datos que se mostrarán en pantalla y datos de control
        self._listaClientes = VentaAlPorMayor._tienda.getClientes()
        self._Prodbodega1 = VentaAlPorMayor._tienda.getBodegas()[0].getProductos()
        self._Prodbodega2 = VentaAlPorMayor._tienda.getBodegas()[1].getProductos()
        self._Productosventa = []
        self._cliente = None
        
        #texto con el titulo del nombre de la funcionalidad
        titulo=Label(self,text="Realizar una Venta Al Por Mayor",font=55,width=66,bg="#3B82BF")
        titulo.place(x=110,y=10)

        #texto co la descripción de la funcionalidad
        descripcion=Label(self,text="Usted realizara una venta en la cual el numero de unidades de\n el/los productos seleccionados es ¡ MAYOR A 57 !, debido a\n esta cantidad se ofrece un descuento en el valor total de la venta\n y los productos son despachados directamente desde las bodegas,\n es decir, se debe saber el nombre del producto que se va vender",font=(55,13),width=81,height=5,bg="#3B82BF")
        descripcion.place(x=110,y=50)

        #FieldFrame para solicitar al usuari el ingreso del producto y la cantidad a vender 
        self._SolicitudDatos1 = FieldFrame(self, "Productos", ["Producto", "Unidades"], "Información")
        self._SolicitudDatos1.place(x=100,y=160)
        self._SolicitudDatos1.funAceptar(self.botonAceptarProducto)

        #Tabla para mostrar la lista de los clientes registrados en la tienda
        self._tablaClientes = ttk.Treeview(self,height=8)
        self._tablaClientes.place(x=385,y=160)

        #organizacion espacial y formateo de las filas y columnas de la tabla 
        self._scrlbarcliente = ttk.Scrollbar(self, orient="vertical", command=self._tablaClientes.yview)
        self._scrlbarcliente.place(x=867, y=160, height=185)
        self._tablaClientes.configure(yscrollcommand=self._scrlbarcliente.set)

        self._tablaClientes["columns"] = ("Nombre","Documento","Email")
        self._tablaClientes.column("#0",width= 0,stretch=NO)
        self._tablaClientes.column("Nombre",anchor=W,width=160)
        self._tablaClientes.column("Documento",anchor=CENTER,width=90)
        self._tablaClientes.column("Email",anchor=CENTER,width=230)

        self._tablaClientes.tag_configure('oddrow', background="white")
        self._tablaClientes.tag_configure('evenrow', background="lightblue")

        self._tablaClientes.heading("#0", text="", anchor=W)
        self._tablaClientes.heading("Nombre", text="Nombre", anchor=W)
        self._tablaClientes.heading("Documento", text="Documento", anchor=CENTER)
        self._tablaClientes.heading("Email", text="Email", anchor=CENTER)

        #se agrega la informacion de los clientes a la tabla
        for cliente in self._listaClientes:
            if self._listaClientes.index(cliente) % 2 ==0:
                self._tablaClientes.insert(parent='', index='end', text="", values=(cliente.getNombre(), cliente.getDocumento(), cliente.getEmail()), tags=('evenrow',))
            else:
                self._tablaClientes.insert(parent='', index='end', text="", values=(cliente.getNombre(), cliente.getDocumento(), cliente.getEmail()), tags=('oddrow',))


        #Boton con comando para guardar el cliente seleccionado de la tabla que es al que se le realizará la venta
        self._botoncliente= Button(self,text="Seleccionar Cliente",bg="#519BA8",command=self.botonAceptarCliente)
        self._botoncliente.pack()
        self._botoncliente.place(x=385,y=350)

        #tabla para mostrar en pantalla el producto seleccionado y la cantidad a vender
        self.tablaprod = ttk.Treeview(self,height=1)

        #boton para confirmar la venta y mostrar la factura
        self.botonAceptarFinal = Button(self, text="Generar Venta", font = ("Verdana", 12), fg = "white", bg = "gray",command=self.GenerarFactura)
       
        #organizacion espacial de la tabla en la que se muestra el producto
        self.tablaprod["columns"] = ("Producto","#Venta")
        self.tablaprod.column("#0",width= 0,stretch=NO)
        self.tablaprod.column("Producto",anchor=W,width=150)
        self.tablaprod.column("#Venta",anchor=CENTER,width=100)


        self.tablaprod.tag_configure('evenrow', background="lightblue")

        self.tablaprod.heading("#0", text="", anchor=W)
        self.tablaprod.heading("Producto", text="Producto", anchor=W)
        self.tablaprod.heading("#Venta", text="Un. Venta", anchor=CENTER)


    # metodo para poner este frame en la ventana en la cual estará contenido
    def VentanaVentaAlPorMayor(self):
        self.configInicial()
        self.OrganizarEspacio()

        
    def botonAceptarCliente(self):
        
        try:
            seleccion=self._tablaClientes.focus()
            
            if(seleccion.strip(" ") == ''):
                raise ErrorObjetoInexistente("Debe seleccionar un cliente de la tabla")

            # se obtiene la informacion del cliente seleccionado en la tabla
            info=self._tablaClientes.item(seleccion)
            nombre=info.get("values")[0]
            documento=info.get("values")[1]
            email=info.get("values")[2] 

            #se busca el cliente en los datos de la tienda
            cliente = VentaAlPorMayor._tienda.buscarClientePorNombre(nombre)

            #se muestra un mensaje de confirmacion y se guarda la instancia del cliente
            txt = f"¡ Cliente Al que se le realizará la venta !\n Nombre: {nombre}  Documento: {documento} Email: {email}"
            self._tablaClientes.destroy()
            self._botoncliente.destroy()
            self._scrlbarcliente.destroy()
            resultado=Label(self,text=txt,font=(55,13),width=81,bg="#3B82BF")
            resultado.place(x=110,y=480)

            self._cliente = cliente
        except ErrorObjetoInexistente as e:
            print(e)
            e.mostrarError()

            return


        

    
    def botonAceptarProducto(self):
        try:
            self.tablaprod.place(x=300,y=160)
            self.botonAceptarFinal.place(x=650, y=160)

            #Se obtiene la informacion del producto ingresado en el fieldFrame
            valorProducto = self._SolicitudDatos1.getValue("Producto")
            valorUnidades = self._SolicitudDatos1.getValue("Unidades")

            if(valorProducto.strip(" ") == '' or valorUnidades.strip(" ") == ''):
                raise ErrorCampoVacio("Producto" if valorProducto.strip(" ") == '' else "Unidades")

            self._producto = valorProducto
            self._unidades = int(valorUnidades)

            if(self._unidades < 57):
                raise ErrorCantidadInsuficiente(57)

            # se agrega el producto a la tabla donde será mostrado en pantalla
            self.tablaprod.insert(parent='', index='end', text="", values=(self._producto, self._unidades), tags=('evenrow',))
            self._SolicitudDatos1.destroy()

        except (ErrorCampoVacio, ErrorCantidadInsuficiente) as e:
            print(e)
            e.mostrarError()

            if(type(e) == ErrorCantidadInsuficiente):
                self._SolicitudDatos1.setValue("Unidades", str(e._cantidadMinima))
            return

    def GenerarFactura(self):

        # se obtine una copia de la instancia del producto que se va a vener
        prodb1 = VentaAlPorMayor._tienda.getBodegas()[0].buscarProductoPorNombreCopia(self._producto)
        prodb2 = VentaAlPorMayor._tienda.getBodegas()[1].buscarProductoPorNombreCopia(self._producto)
        prodfinal = prodb1 if prodb1 != None and prodb1.getCantidad()>prodb2.getCantidad() else prodb2
        prodfinal.setCantidad(self._unidades)

        # se despaccha el producto de la bodega que tenga mayor cantidad del mismo
        if VentaAlPorMayor._tienda.getBodegas()[0].buscarProductoPorNombreCopia(self._producto).getCantidad()>VentaAlPorMayor._tienda.getBodegas()[1].buscarProductoPorNombreCopia(self._producto).getCantidad():
            VentaAlPorMayor._tienda.getBodegas()[0].despachoPorMayor(dt.datetime.now(), prodfinal)
        else:
            VentaAlPorMayor._tienda.getBodegas()[1].despachoPorMayor(dt.datetime.now(), prodfinal)

        # se crea la instancia de la venta, se calcula los ingresos y se crea y muestra la factura
        venta = Venta(dt.datetime.now(),[prodfinal],self._cliente)
        VentaAlPorMayor._tienda.agregarVenta(venta)
        venta.calcularIngresos(0.1)
        factura = venta.generarFactura()
        alerta = messagebox.showinfo("FACTURA",factura)






    @classmethod
    def setTienda(cls, tienda):
        cls._tienda = tienda
