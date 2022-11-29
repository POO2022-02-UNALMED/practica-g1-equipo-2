from tkinter import *
from tkinter import ttk
from datetime import datetime


class ResumenStockBajo(Frame):

    
    def __init__(self, master):
        super().__init__(master)
        self._ventana=master
        self._tienda=self._ventana._tiendaPrincipal
        self._datosTienda=None

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

    def OrganizarEspacio(self):

        titulo=Label(self,text="Productos con Stock Bajo en la Tienda",font=65,width=40,bg="#3B82BF")#Titulo 
        titulo.pack()
        titulo.place(x=290,y=20)
        #se crea el frame que va llevar la tabla
        minFrame=Frame(self,padx=5,pady=5,relief="ridge")
        minFrame.pack()
        minFrame.place(x=160, y=70)
        minFrame.config(bd=1) #cambiar el grosor del borde
        minFrame.config(bg="light steel blue")#color
        scroll = Scrollbar(minFrame)#Scroll para la tabla
        scroll.pack(side=RIGHT, fill=Y)#posicion del scroll

        tablaBo2=ttk.Treeview(minFrame, yscrollcommand=scroll.set,height=13, selectmode="extended",)#se crea el Treeview para poner los datos
        tablaBo2.pack()

        #Asignacion de colomunas en la tabla 
        tablaBo2["columns"]=("Producto","Cantidad","ID","Tipo","CantidadE")  
        tablaBo2.column("#0",width= 0,stretch=NO)
        tablaBo2.column("Producto",anchor=W,width=120)
        tablaBo2.column("Cantidad",anchor=CENTER,width=160)
        tablaBo2.column("ID",anchor=CENTER,width=100)
        tablaBo2.column("Tipo",anchor=CENTER,width=120)
        tablaBo2.column("CantidadE",anchor=CENTER,width=160)

        tablaBo2.tag_configure('oddrow', background="white")
        tablaBo2.tag_configure('evenrow', background="lightblue")
        tablaBo2.tag_configure("Bajo", background="#F55A49")#color rojo por si la cantidad del producto es baja o 0

        tablaBo2.heading("#0", text="", anchor=W)
        tablaBo2.heading("Producto", text="Producto", anchor=W)
        tablaBo2.heading("Cantidad", text="Cantidad", anchor=W)
        tablaBo2.heading("ID", text="ID", anchor=CENTER)
        tablaBo2.heading("Tipo", text="Tipo", anchor=CENTER)
        tablaBo2.heading("CantidadE", text="Cantidad Total en Bodegas", anchor=CENTER)
        scroll.config(command=tablaBo2.yview)
        
        productosStockBajo=[]#lista que va a contener los productos con stock bajo
        for producto in self._tienda.getProductos():#se busca cuales productos tienen Stock bajo y se gregan a la lista
            if self._tienda.productoStockBajo(producto):
                productosStockBajo.append(producto)
        self._datosTienda=productosStockBajo#A el atributo _datosTienda se le asigna la lista de productos con stock bajo

        #Se le agragan los valores a la tabla
        global count2
        count2=0
        for producto1 in self._datosTienda:
            if count2 %2 == 0:  #para ponerle un color a los pares y otro a los impares

                tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(producto1.getNombre(),producto1.getCantidad(), producto1.getId(),producto1.getTipoProducto().name,self._tienda.cantidadEnBodegas(producto1)), tags=('evenrow',))
            else:
               
                tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(producto1.getNombre(),producto1.getCantidad(), producto1.getId(),producto1.getTipoProducto().name,self._tienda.cantidadEnBodegas(producto1)), tags=('oddrow',))
                
            count2 += 1
        #Se crea el mensaje y el botón para realizar el traslado 
        textoProductos=Label(self,text="¿Desea surtir todos los productos para que cumplan con el Stock minimo?",bg="light steel blue")
        textoProductos.pack()
        textoProductos.place(x=255,y=400)
        boton1= Button(self,text="Realizar traslado",bg="#519BA8")
        boton1.pack()
        boton1.place(x=420,y=440)

        def surtirProductos():#Se surten todos los productos con Stock bajo para que cumplan con el requerimiento de Stock
            for producto in productosStockBajo:
                cantidadFaltante= abs(producto.getCantidad()- self._tienda.getMinProducto())#Se calcula la cantidad necesaria para que cumplan 
                fecha=datetime.now()#Fecha actual
                self._tienda.pedirTraslado(fecha,producto,cantidadFaltante)#se pide el traslado 
            for dato in tablaBo2.get_children():#Se limpia la tabla 
                tablaBo2.delete(dato)

            #Mensaje temporal para confirmar que si se hizo el traslado
            labecon=Label(self,text="¡El traslado se realizó con éxito!",bg="#3B82BF")
            labecon.pack()
            labecon.place(x=390,y=500)
            def borrar():#funcion para borrar el Label
                    labecon.destroy()
            labecon.after(2000,borrar)
        boton1.config(command=surtirProductos)

    def VentanaResumenStockBajo(self):
        self.configInicial()
        self.OrganizarEspacio()