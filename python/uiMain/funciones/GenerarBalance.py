from tkinter import *
from tkinter import ttk
from heapq import nlargest

class GenerarBalance(Frame):

    def destruir(self):
        self.destroy()
    
    def __init__(self, master):
        super().__init__(master)
        self._ventana=master
        self._tienda=self._ventana._tiendaPrincipal
        self._datosTienda=None

     #setters and getters
    def get_tienda(self):
        return self._tienda

    def set_tienda(self,tienda):
        self._tienda=tienda

    def get_datosTienda(self):
        return self._datosTienda

    def set_datosTienda(self,datosproductos):
        self._datosTienda=datosproductos

    def configInicial(self):#limpia el _frame1 (destruye el anterior para borrar todo que había ahí y crea uno nuevo para poner todo)
        self._ventana._frame1.destroy()
        self._ventana._frame1=self
        self.config(bg="light steel blue")
        self.config(width=960, height=540)
        self.pack(expand=True)


    def OrganizarEspacio(self):#Organiza todo el _frame1 

        minFrame=LabelFrame(self,text="Cantidad disponible",padx=5,pady=5,relief="ridge",font=30)#frame con marco para poner la Tabla
        minFrame.pack()
        minFrame.place(x=30, y=20)
        minFrame.config(bd=1) #cambiar el grosor del borde
        minFrame.config(bg="light steel blue")#color
        scroll = Scrollbar(minFrame)#Scroll para la tabla
        scroll.pack(side=RIGHT, fill=Y)#posicion del scroll
        
        tabla=ttk.Treeview(minFrame, yscrollcommand=scroll.set,height=10, selectmode="extended",)#se crea el Treeview para poner los datos
        tabla.pack()

        #Combobox para elegir bodegas o tienda de la que se quiere mirar el inventario
        comboBodega=ttk.Combobox(self,state="readonly")
        comboBodega["values"]=("Tienda","Bodega 1","Bodega 2")#se le asignan los valores al Combobox
        comboBodega.pack()
        comboBodega.current(0)#se le asigna el valor por defecto
        comboBodega.place(x=160, y=280)

        scroll.config(command=tabla.yview)

        #Asignacion de colomunas en la tabla 
        tabla["columns"]=("Producto","Cantidad")  
        tabla.column("#0",width= 0,stretch=NO)
        tabla.column("Producto",anchor=W,width=140)
        tabla.column("Cantidad",anchor=CENTER,width=100)

        tabla.tag_configure('oddrow', background="white")
        tabla.tag_configure('evenrow', background="lightblue")
        tabla.tag_configure("Bajo", background="#F55A49")#color rojo por si la cantidad del producto es baja o 0

        tabla.heading("#0", text="", anchor=W)
        tabla.heading("Producto", text="Producto", anchor=W)
        tabla.heading("Cantidad", text="Cantidad", anchor=CENTER)

        self._datosTienda=self._tienda.getProductos()#se le asigna el valor a _datosTienda para que muestre los datos por defecto
        #se agregan los datos a la tabla 
        global count
        count=0
        for elemento in self._datosTienda:
                    if count %2 == 0:  #para ponerle un color a los pares y otro a los impares
                        if elemento.getCantidad()<=3:#Esto va a ser por si la cantidad del producto es 0
                            tabla.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(),elemento.getCantidad()), tags=('Bajo',))
                        else:
                            tabla.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(), elemento.getCantidad()), tags=('evenrow',))
                    else:
                        if elemento.getCantidad()<=3:#Esto va a ser por si la cantidad del producto es 0
                            tabla.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(),elemento.getCantidad()), tags=('Bajo',))
                        else:
                            tabla.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(), elemento.getCantidad()), tags=('oddrow',))
                    count += 1

        #Asigna los valores en la tabla segun sea la seleccion del combobox, (comand para boton "Mostrar Datos" )
        def mostrarDatos():
            for dato in tabla.get_children():
                    tabla.delete(dato)
            if comboBodega.get()=="Bodega 1":
                self._datosTienda=self._tienda.getBodegas()[0].getProductos()
            if comboBodega.get()=="Bodega 2":
                self._datosTienda=self._tienda.getBodegas()[1].getProductos()
            if comboBodega.get()=="Tienda":
                self._datosTienda=self._tienda.getProductos()
            global count
            count=0
            for elemento in self._datosTienda:
                    if count %2 == 0:  #para ponerle un color a los pares y otro a los impares
                        if elemento.getCantidad()<=3:#Esto va a ser por si la cantidad del producto es 0
                            tabla.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(),elemento.getCantidad()), tags=('Bajo',))
                        else:
                            tabla.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(), elemento.getCantidad()), tags=('evenrow',))
                    else:
                        if elemento.getCantidad()<=3:#Esto va a ser por si la cantidad del producto es 0
                            tabla.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(),elemento.getCantidad()), tags=('Bajo',))
                        else:
                            tabla.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(), elemento.getCantidad()), tags=('oddrow',))
                    count += 1
        

        botonMostrar=Button(self,text="Mostrar Datos",command=mostrarDatos)#Boton para actualizar los valores de la tabla
        botonMostrar.pack()
        botonMostrar.place(x=160, y=310)
        

        #Total de productos en bodegas y en tienda 
        TotalFrame=LabelFrame(self,text="Cantidad total de productos",padx=30,pady=7,relief="ridge",font=40)#frame para poner las cantidades
        TotalFrame.config(bg="light steel blue")
        TotalFrame.pack()
        
        cantidadproductos=0
        cantidadprodBog1=0
        cantidadprodBog2=0

        #se Calcula la cantidad de productos de la Tienda y de cada una de las bodegas
        for producto in self._tienda.getProductos():
            cantidadproductos+=producto.getCantidad()
        for producto in self._tienda.getBodegas()[0].getProductos():
            cantidadprodBog1+=producto.getCantidad()
        for producto in self._tienda.getBodegas()[1].getProductos():
            cantidadprodBog2+=producto.getCantidad()

        #Se crean los Label en los que se van a mostrar la cantidad de productos de cada uno
        texto1=Label(TotalFrame,text=f"Cantidad de Productos en Bodega 1 :   {cantidadprodBog1}")
        texto2=Label(TotalFrame,text=f"Cantidad de Productos en Bodega 2 :  {cantidadprodBog2}")
        texto3=Label(TotalFrame,text=f"Cantidad de Productos en tienda   :  {cantidadproductos}")
        #Se posicionan los Label
        texto3.config(bg="light steel blue")
        texto3.pack()
        texto1.config(bg="light steel blue")
        texto1.pack()
        texto2.config(bg="light steel blue")
        texto2.pack()
        TotalFrame.place(x=30, y=350)



        #Ranking (Prductos mas vendidos,Clienstes con mas compras,Proveedores con mas compras)

        titulo=Label(self,text="Ranking",font=56,width=40,bg="#3B82BF")#Titulo 
        titulo.place(x=350,y=10)
        frame2=Frame(self,bg="light steel blue",width=370,height=400,relief="ridge",bd=1)#Frame para poner los rankings 
        frame2.pack()
        frame2.place(x=350,y=40)

        #Ranking Prductos mas vendidos
        ProdMV=Label(frame2,text="Productos mas vendidos",width=38,bg="lightblue",font=2)
        ProdMV.pack()
        ProdMV.place(x=10,y=10)


        #tabla para poner los productos mas vendidos
        tablaProd = ttk.Treeview(frame2,height=3)
        tablaProd["columns"]=("Producto","Cantidad")#Elementos que va tener la tabla
        tablaProd.pack()
        tablaProd.place(x=60,y=40) 
        #Asignacion de colomunas en la tabla     
        tablaProd.column("#0",width= 0,stretch=NO)
        tablaProd.column("Producto",anchor=W,width=140)
        tablaProd.column("Cantidad",anchor=CENTER,width=100)
        tablaProd.tag_configure('evenrow', background="lightblue")#Cambiar el color
        tablaProd.heading("#0", text="", anchor=W)
        tablaProd.heading("Producto", text="Producto", anchor=W)
        tablaProd.heading("Cantidad", text="Cantidad", anchor=W)

        #Se obtienen los 3 productos mas vendidos y se le asignan a la tabla de los productos mas vendidos
        productosV={}
        for venta in self._tienda.getVentas():
            for producto in venta.getProductos():
                if productosV.__contains__(producto.getNombre()):
                    productosV[producto.getNombre()]+=1
                else :
                    productosV[producto.getNombre()]=1

        prmasVen=nlargest(3,productosV, key=productosV.get)
        for producto in prmasVen:
            tablaProd.insert('', index='end', text="", values=(producto, productosV[producto]))




        #Ranking Clienstes con mas compras

        ClienteMs=Label(frame2,text="Clientes con mas compras",width=38,bg="lightblue",font=2)
        ClienteMs.pack()
        ClienteMs.place(x=10,y=140)



        #Tabla para los 3 clientes con mas compras
        TablaClientes = ttk.Treeview(frame2,height=3)
        TablaClientes["columns"]=("Cliente","Numero")#Elementos que va tener la tabla
        TablaClientes.pack()
        TablaClientes.place(x=60,y=170)  
        #Asignacion de colomunas en la tabla      
        TablaClientes.column("#0",width= 0,stretch=NO)
        TablaClientes.column("Cliente",anchor=W,width=140)
        TablaClientes.column("Numero",anchor=CENTER,width=100)
        TablaClientes.tag_configure('evenrow', background="lightblue")#Cambiar el color
        TablaClientes.heading("#0", text="", anchor=W)
        TablaClientes.heading("Cliente", text="Cliente", anchor=W)
        TablaClientes.heading("Numero", text="Compras", anchor=W)

        #Se obtienen los 3 clientes con mas compras y se le asignan a la tabla de los clientes con mas ventas
        clienteVentas={}
        for cliente in self._tienda.getClientes():
            clienteVentas[cliente.getNombre()]=len(cliente.getHistorico())
        masVentas=nlargest(3,clienteVentas, key=clienteVentas.get)
        for cliente in masVentas:
            TablaClientes.insert('', index='end', text="", values=(cliente, clienteVentas[cliente]))



        #Ranking Proveedores

        ProveeMc=Label(frame2,text="Proveedores a los que mas se le ha comprado",width=38,bg="lightblue",font=2)
        ProveeMc.pack()
        ProveeMc.place(x=10,y=265)
        #Tabla para los 3 proveedores a los que mas se les ha comprado 

        TablaProveedores = ttk.Treeview(frame2,height=3)
        TablaProveedores["columns"]=("Proveedor","Numero")#Elementos que va tener la tabla
        TablaProveedores.pack()
        TablaProveedores.place(x=60,y=300)
        #Asignacion de colomunas en la tabla
        TablaProveedores.column("#0",width= 0,stretch=NO)
        TablaProveedores.column("Proveedor",anchor=W,width=140)
        TablaProveedores.column("Numero",anchor=CENTER,width=100)
        TablaProveedores.tag_configure('evenrow', background="lightblue")#Cambiar el color
        TablaProveedores.heading("#0", text="", anchor=W)
        TablaProveedores.heading("Proveedor", text="Proveedor", anchor=W)
        TablaProveedores.heading("Numero", text="Compras", anchor=W)


        #Se obtienen los 3 proveedores a los cuales se les ha comprado mas y se asignan a la tabla de proveedores a los que mas se le ha comprado
        provedores={}
        for bodega_a in self._tienda.getBodegas(): 
            for proveedor in bodega_a.getProveedores():
                provedores[proveedor.getNombre()]=len(proveedor.getHistoricoProveedor())
        promasCom=nlargest(3,provedores, key=provedores.get)
        for proveedor1 in promasCom:
            TablaProveedores.insert('', index='end', text="", values=(proveedor1, provedores[proveedor1]))



        #Ingresesos Y Egresos 

        #Se calcula el total de Egresos según todo lo que se ha comprado para surtir la tienda
        totalEgresos=0
        for bodega in self._tienda.getBodegas():
            for compra in bodega.getCompras():
                totalEgresos+= compra.getTotal()

        #Se calcula el total de Ingresos según todo lo que se ha vendido en la tienda
        totalIngresos=0
        for venta in self._tienda.getVentas():
            totalIngresos+=venta.getTotal()

        #Se crean los Label para mostrar los datos que se calcularon anteriormente y se posicionan
        LabelIngr=LabelFrame(self,text="Ingresos y Egresos",bg="light steel blue",font=1,width=190,height=120)
        LabelIngr.pack()
        LabelIngr.place(x=760,y=10)
        Ingresos=Label(LabelIngr,text="Los Ingresos son : ${:,.2f}".format(totalIngresos),bg="light steel blue",)
        Ingresos.pack()
        Ingresos.place(x=2,y=15)
        Egresos=Label(LabelIngr,text="Los Egresos son : ${:,.2f}".format(totalEgresos),bg="light steel blue",)
        Egresos.pack()
        Egresos.place(x=2,y=50)
       
    def VentanaBalance(self):
        self.configInicial()
        self.OrganizarEspacio()

    
