from tkinter import *
from tkinter import ttk
from FieldFrame import FieldFrame
import datetime as dt

class HacerTraslado(Frame):

    #atributo de clase para guardar una instacia de la tienda principal futuramente
    _tienda = None

    #metodo para autodestruir este frame 
    def destruir(self):
        self.destroy()

    def __init__(self, master):
        #Se inicializa la clasae padre
        super().__init__(master)
        self._ventana=master
        #atributo con la fecha actual
        self._fecha = f"{dt.datetime.now().day}/{dt.datetime.now().month}/{dt.datetime.now().year}"
        

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
        titulo=Label(self,text="Hacer Traslado de Productos de Bodega a Tienda",font=55,width=66,bg="#3B82BF")
        titulo.place(x=110,y=10)

        #texto co la descripción de la funcionalidad
        descripcion=Label(self,text="Usted trasladará cierta cantidad de uno o varios productos\n desde alguna de las bodegas hacia la Tienda",font=(55,13),width=81,height=5,bg="#3B82BF")
        descripcion.place(x=110,y=50)

        # atributos para guardar los datos que se mostrarán en pantalla
        self._datosBodega1= HacerTraslado._tienda.getBodegas()[0].getProductos()
        self._datosBodega2=HacerTraslado._tienda.getBodegas()[1].getProductos()

        # frame anidado para mostrar los productos disponibles en la bodega 1
        Bodeg1Frame=LabelFrame(self,text="Bodega 1",padx=5,pady=5,relief="ridge",font=30)#frame con marco para poner la Tabla de la bodega 1
        Bodeg1Frame.pack()
        Bodeg1Frame.place(x=100, y=160)
        Bodeg1Frame.config(bd=1) #cambiar el grosor del borde
        Bodeg1Frame.config(bg="light steel blue")#color
        scroll1 = Scrollbar(Bodeg1Frame)#Scroll para la tabla
        scroll1.pack(side=RIGHT, fill=Y)#posicion del scroll

        #tabla dentro de el frame bodeg1Frame para mostrar los productos en la bodega 1
        self.tablaBo1=ttk.Treeview(Bodeg1Frame, yscrollcommand=scroll1.set,height=5, selectmode="extended",)#se crea el Treeview para poner los datos de la Bodega 1
        self.tablaBo1.pack()

        #organizacion espacial y formateo de filas y columnas de la tabla anterior
        self.tablaBo1["columns"]=("Producto","ID","Tipo","Cantidad")  
        self.tablaBo1.column("#0",width= 0,stretch=NO)
        self.tablaBo1.column("Producto",anchor=W,width=100)
        self.tablaBo1.column("ID",anchor=CENTER,width=80)
        self.tablaBo1.column("Tipo",anchor=CENTER,width=100)
        self.tablaBo1.column("Cantidad",anchor=CENTER,width=60)

        self.tablaBo1.tag_configure('oddrow', background="white")
        self.tablaBo1.tag_configure('evenrow', background="lightblue")
        self.tablaBo1.tag_configure("Bajo", background="#F55A49")#color rojo por si la cantidad del producto es baja o 0

        self.tablaBo1.heading("#0", text="", anchor=W)
        self.tablaBo1.heading("Producto", text="Producto", anchor=W)
        self.tablaBo1.heading("ID", text="ID", anchor=CENTER)
        self.tablaBo1.heading("Tipo", text="Tipo", anchor=CENTER)
        self.tablaBo1.heading("Cantidad", text="Cantidad", anchor=CENTER)
        scroll1.config(command=self.tablaBo1.yview)


        # Se agregan los productos de la bodega 1 a la tabla anterior
        global count1
        count1=0
        for prodtemp in self._datosBodega1:
            if count1 %2 == 0:  #para ponerle un color a los pares y otro a los impares
                if prodtemp.getCantidad()<=3:#Esto va a ser por si la cantidad del producto es 0
                    self.tablaBo1.insert(parent='', index='end', iid=count1, text="", values=(prodtemp.getNombre(), prodtemp.getId(), prodtemp.getTipoProducto().name,prodtemp.getCantidad()), tags=('Bajo',))
                else:
                    self.tablaBo1.insert(parent='', index='end', iid=count1, text="", values=(prodtemp.getNombre(), prodtemp.getId(), prodtemp.getTipoProducto().name,prodtemp.getCantidad()), tags=('evenrow',))
            else:
                if prodtemp.getCantidad()<=3:#Esto va a ser por si la cantidad del producto es 0
                    self.tablaBo1.insert(parent='', index='end', iid=count1, text="", values=(prodtemp.getNombre(), prodtemp.getId(), prodtemp.getTipoProducto().name,prodtemp.getCantidad()), tags=('Bajo',))
                else:
                    self.tablaBo1.insert(parent='', index='end', iid=count1, text="", values=(prodtemp.getNombre(), prodtemp.getId(), prodtemp.getTipoProducto().name,prodtemp.getCantidad()), tags=('oddrow',))
                
            count1 += 1

      
        # SE REPITE EL MISMO PROCESO PARA MOSTRAR LOS PRODUCTOS DISPONIBLES AHORA EN LA BODEGA 2
        #Tabla de la bodega 2
        Bodeg2Frame=LabelFrame(self,text="Bodega 2",padx=5,pady=5,relief="ridge",font=30)#frame con marco para poner la Tabla
        Bodeg2Frame.pack()
        Bodeg2Frame.place(x=100, y=320)
        Bodeg2Frame.config(bd=1) #cambiar el grosor del borde
        Bodeg2Frame.config(bg="light steel blue")#color
        scroll2 = Scrollbar(Bodeg2Frame)#Scroll para la tabla
        scroll2.pack(side=RIGHT, fill=Y)#posicion del scroll

        self.tablaBo2=ttk.Treeview(Bodeg2Frame, yscrollcommand=scroll2.set,height=5, selectmode="extended",)#se crea el Treeview para poner los datos
        self.tablaBo2.pack()

        self.tablaBo2["columns"]=("Producto","ID","Tipo","Cantidad")  
        self.tablaBo2.column("#0",width= 0,stretch=NO)
        self.tablaBo2.column("Producto",anchor=W,width=100)
        self.tablaBo2.column("ID",anchor=CENTER,width=80)
        self.tablaBo2.column("Tipo",anchor=CENTER,width=100)
        self.tablaBo2.column("Cantidad",anchor=CENTER,width=60)

        self.tablaBo2.tag_configure('oddrow', background="white")
        self.tablaBo2.tag_configure('evenrow', background="lightblue")
        self.tablaBo2.tag_configure("Bajo", background="#F55A49")#color rojo por si la cantidad del producto es baja o 0

        self.tablaBo2.heading("#0", text="", anchor=W)
        self.tablaBo2.heading("Producto", text="Producto", anchor=W)
        self.tablaBo2.heading("ID", text="ID", anchor=CENTER)
        self.tablaBo2.heading("Tipo", text="Tipo", anchor=CENTER)
        self.tablaBo2.heading("Cantidad", text="Cantidad", anchor=CENTER)
        scroll2.config(command=self.tablaBo2.yview)

        global count2
        count2=0
        for prodtemp in self._datosBodega2:
            if count2 %2 == 0:  #para ponerle un color a los pares y otro a los impares
                if prodtemp.getCantidad()<=3:#Esto va a ser por si la cantidad del producto es 0
                    self.tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(prodtemp.getNombre(), prodtemp.getId(), prodtemp.getTipoProducto().name,prodtemp.getCantidad()), tags=('Bajo',))
                else:
                    self.tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(prodtemp.getNombre(), prodtemp.getId(),prodtemp.getTipoProducto().name,prodtemp.getCantidad()), tags=('evenrow',))
            else:
                if prodtemp.getCantidad()<=3:#Esto va a ser por si la cantidad del producto es 0
                    self.tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(prodtemp.getNombre(), prodtemp.getId(), prodtemp.getTipoProducto().name,prodtemp.getCantidad()), tags=('Bajo',))
                else:
                    self.tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(prodtemp.getNombre(), prodtemp.getId(),prodtemp.getTipoProducto().name,prodtemp.getCantidad()), tags=('oddrow',))
                
            count2 += 1

        #FieldFrame para solicitar al usuario que ingrese los datos del producto que va a trasladar
        self._SolicitudDatos = FieldFrame(self, "Productos", ["Fecha","ID de Producto", "Unidades"], "Información",habilitado=[False,True,True], valores=[self._fecha,"",""])
        self._SolicitudDatos.place(x=500,y=160)
        self._SolicitudDatos.funAceptar(self.botonAceptarProducto)


    # metodo para poner este frame en la ventana en la cual estará contenido
    def VentanaHacerTraslado(self):
        self.configInicial()
        self.OrganizarEspacio()

    #metodo para guardar la informacion del producto a trasladar y hacer el traslado
    def botonAceptarProducto(self):

        # se obtiene la informacion del producto del fieldframe 
        idProducto = int(self._SolicitudDatos.getValue("ID de Producto"))
        unidades = int(self._SolicitudDatos.getValue("Unidades"))


        bodegaIndicada = None
        # se busca la bodega en la cual hay mayor cantidad de este producto
        for bodg in HacerTraslado._tienda._bodegas:
            if bodegaIndicada == None:
                bodegaIndicada = bodg
            elif bodg.buscarProductoPorIDOriginal(idProducto) != None and bodegaIndicada.buscarProductoPorIDOriginal(idProducto) != None:
                if bodg.buscarProductoPorIDOriginal(idProducto).getCantidad() > bodegaIndicada.buscarProductoPorIDOriginal(idProducto).getCantidad():
                    bodegaIndicada = bodg
            else:
                bodegaIndicada = bodg if bodg.buscarProductoPorIDOriginal(idProducto) != None else bodegaIndicada

        # Se hace el traslado desde la bodega seleccionada anteriormente (actualiza cantidad en bodega)
        bodegaIndicada.trasladoTienda(dt.datetime.now(), idProducto, unidades)

        #se actualiza la cantidad del producto trasladado en la tienda
        productoTienda = HacerTraslado._tienda.buscarProductoPorIDOriginal(idProducto)
        if productoTienda != None:
            productoTienda.setCantidad(productoTienda.getCantidad() + unidades)
        else: 
            HacerTraslado._tienda.agregarProducto(bodegaIndicada.buscarProductoPorIDCopia(idProducto))
            prodAgregado = HacerTraslado._tienda.buscarProductoPorIDOriginal(idProducto)
            prodAgregado.setCantidad(unidades)
        
        #se actualiza la tabla que muestra los productos de la bodega de la cual se hizo el traslado 
        self.actualizarTablas(bodegaIndicada, productoTienda)
        txt = f"¡ Producto Trasladado con Exito !\n Producto: {productoTienda.getNombre()}  |  Und. Trasladadas: {unidades}"
        resultado=Label(self,text=txt,font=(55,13),width=81,bg="#3B82BF")
        resultado.place(x=110,y=490)

    #metodo para actualiza la tabla que muestra los productos de la bodega de la cual se hizo el traslado 
    def actualizarTablas(self, bodega, producto):
        tabla = self.tablaBo1 if bodega.getId() == 1 else self.tablaBo2
        for child in tabla.get_children():
            if producto._id in tabla.item(child)['values']: 
                tabla.item(child, values=(producto.getNombre(), producto.getId(), producto.getTipoProducto().name, bodega.buscarProductoPorIDCopia(producto._id).getCantidad()))
                tabla.selection_add(child)        

    @classmethod
    def setTienda(cls, tienda):
        cls._tienda = tienda