from tkinter import *
from tkinter import ttk

class ResumenStockBodegas(Frame):

    def destruir(self):
        self.destroy()
    
    def __init__(self, master):
        super().__init__(master)
        self._ventana=master
        self._tienda=self._ventana._tiendaPrincipal
        self._datosBodega1=self._tienda.getBodegas()[0].getProductos()
        self._datosBodega2=self._tienda.getBodegas()[1].getProductos()
        
    #setters and getters
    def get_tienda(self):
        return self._tienda

    def set_tienda(self,tienda):
        self._tienda=tienda

    def get_datosBodega1(self):
        return self._datosBodega1

    def set_datosBodega1(self,datosproductos):
        self._datosBodega1=datosproductos

    def get_datosBodega2(self):
        return self._datosBodega2

    def set_datosBodega2(self,datosproductos):
        self._datosBodega2=datosproductos

 

    def configInicial(self):#limpia el _frame1 (destruye el anterior para borrar todo que había ahí y crea uno nuevo para poner todo)
        self._ventana._frame1.destroy()
        self._ventana._frame1=self
        self.config(bg="light steel blue")
        self.config(width=960, height=540)
        self.pack(expand=True)
     
    def OrganizarEspacio(self):#Organiza todo el Frame 
        
        titulo=Label(self,text="Resumen de Stock en Bodegas",font=65,width=40,bg="#3B82BF")#Titulo 
        titulo.pack()
        titulo.place(x=290,y=20)

        #Tabla de la Bodega 1
        Bodeg1Frame=LabelFrame(self,text="Bodega 1",padx=5,pady=5,relief="ridge",font=30)#frame con marco para poner la Tabla de la bodega 1
        Bodeg1Frame.pack()
        Bodeg1Frame.place(x=30, y=80)
        Bodeg1Frame.config(bd=1) #cambiar el grosor del borde
        Bodeg1Frame.config(bg="light steel blue")#color
        scroll1 = Scrollbar(Bodeg1Frame)#Scroll para la tabla
        scroll1.pack(side=RIGHT, fill=Y)#posicion del scroll

        tablaBo1=ttk.Treeview(Bodeg1Frame, yscrollcommand=scroll1.set,height=13, selectmode="extended",)#se crea el Treeview para poner los datos de la Bodega 1
        tablaBo1.pack()
        #Asignacion de colomunas en la tabla 
        tablaBo1["columns"]=("Producto","ID","Tipo","Cantidad")  
        tablaBo1.column("#0",width= 0,stretch=NO)
        tablaBo1.column("Producto",anchor=W,width=120)
        tablaBo1.column("ID",anchor=CENTER,width=100)
        tablaBo1.column("Tipo",anchor=CENTER,width=120)
        tablaBo1.column("Cantidad",anchor=CENTER,width=60)

        tablaBo1.tag_configure('oddrow', background="white")
        tablaBo1.tag_configure('evenrow', background="lightblue")
        tablaBo1.tag_configure("Bajo", background="#F55A49")#color rojo por si la cantidad del producto es baja o 0

        tablaBo1.heading("#0", text="", anchor=W)
        tablaBo1.heading("Producto", text="Producto", anchor=W)
        tablaBo1.heading("ID", text="ID", anchor=CENTER)
        tablaBo1.heading("Tipo", text="Tipo", anchor=CENTER)
        tablaBo1.heading("Cantidad", text="Cantidad", anchor=CENTER)
        scroll1.config(command=tablaBo1.yview)

        #Se le asignan los valores a la tabla
        global count1
        count1=0
        for elemento in self._datosBodega1:
            if count1 %2 == 0:  #para ponerle un color a los pares y otro a los impares
                if elemento.getCantidad()<=3:#Esto va a ser por si la cantidad del producto  es menor o igual a 3
                    tablaBo1.insert(parent='', index='end', iid=count1, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getTipoProducto().name,elemento.getCantidad()), tags=('Bajo',))
                else:
                    tablaBo1.insert(parent='', index='end', iid=count1, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getTipoProducto().name,elemento.getCantidad()), tags=('evenrow',))
            else:
                if elemento.getCantidad()<=3:#Esto va a ser por si la cantidad del producto es menor o igual a 3
                    tablaBo1.insert(parent='', index='end', iid=count1, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getTipoProducto().name,elemento.getCantidad()), tags=('Bajo',))
                else:
                    tablaBo1.insert(parent='', index='end', iid=count1, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getTipoProducto().name,elemento.getCantidad()), tags=('oddrow',))
                
            count1 += 1

      
        #Tabla de la bodega 2
        Bodeg2Frame=LabelFrame(self,text="Bodega 2",padx=5,pady=5,relief="ridge",font=30)#frame con marco para poner la Tabla
        Bodeg2Frame.pack()
        Bodeg2Frame.place(x=480, y=80)
        Bodeg2Frame.config(bd=1) #cambiar el grosor del borde
        Bodeg2Frame.config(bg="light steel blue")#color

        scroll2 = Scrollbar(Bodeg2Frame)#Scroll para la tabla 2 
        scroll2.pack(side=RIGHT, fill=Y)#posicion del scroll

        tablaBo2=ttk.Treeview(Bodeg2Frame, yscrollcommand=scroll2.set,height=13, selectmode="extended",)#se crea el Treeview para poner los datos
        tablaBo2.pack()
        #Asignacion de colomunas en la tabla 
        tablaBo2["columns"]=("Producto","ID","Tipo","Cantidad")  
        tablaBo2.column("#0",width= 0,stretch=NO)
        tablaBo2.column("Producto",anchor=W,width=120)
        tablaBo2.column("ID",anchor=CENTER,width=100)
        tablaBo2.column("Tipo",anchor=CENTER,width=120)
        tablaBo2.column("Cantidad",anchor=CENTER,width=60)

        tablaBo2.tag_configure('oddrow', background="white")
        tablaBo2.tag_configure('evenrow', background="lightblue")
        tablaBo2.tag_configure("Bajo", background="#F55A49")#color rojo por si la cantidad del producto es baja o 0

        tablaBo2.heading("#0", text="", anchor=W)
        tablaBo2.heading("Producto", text="Producto", anchor=W)
        tablaBo2.heading("ID", text="ID", anchor=CENTER)
        tablaBo2.heading("Tipo", text="Tipo", anchor=CENTER)
        tablaBo2.heading("Cantidad", text="Cantidad", anchor=CENTER)
        scroll2.config(command=tablaBo2.yview)

        #Se le asignan los valores a la tabla
        global count2
        count2=0
        for elemento in self._datosBodega2:
            if count2 %2 == 0:  #para ponerle un color a los pares y otro a los impares
                if elemento.getCantidad()<=3:#Esto va a ser por si la cantidad del producto  es menor o igual a 3
                    tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getTipoProducto().name,elemento.getCantidad()), tags=('Bajo',))
                else:
                    tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getTipoProducto().name,elemento.getCantidad()), tags=('evenrow',))
            else:
                if elemento.getCantidad()<=3:#Esto va a ser por si la cantidad del producto  es menor o igual a 3
                    tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getTipoProducto().name,elemento.getCantidad()), tags=('Bajo',))
                else:
                    tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getTipoProducto().name,elemento.getCantidad()), tags=('oddrow',))
                
            count2 += 1

        #Texto que indica celdas de color rojo
        frameP= Frame(self,bg="#F55A49",width=15, height=15)
        frameP.pack()
        frameP.place(x=300,y=460)
        textoP= Label(self,text="Indica producto con cantidad menor o igual a 3 unidades",bg="light steel blue")
        textoP.pack()
        textoP.place(x=320,y=458)

    def VentanResumenBodegas(self):
        self.configInicial()
        self.OrganizarEspacio()