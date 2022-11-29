import tkinter as tk
from tkinter import ttk

class ResumenStockTienda(tk.Frame):

    _tienda = None

    def __init__(self, window):
        #instanciamos el frame nuevo
        super().__init__(window)

        #destruimos el frame original
        window._frame1.destroy()
        window._frame1=self

        #configuramos inicialmente el frame nuevo y lo posicionamos en la ventana
        self.config(bg="light steel blue")
        self.config(width=960, height=540)
        self.pack(expand=True, fill="both")

    def organizarEspacio(self):
        #lista de los productos de la tienda
        self._productosTienda = ResumenStockTienda._tienda.getProductos()

        #frame central que contendrá el titulo y la tabla de los productos
        frameCentral = tk.Frame(self, bg="light steel blue")
        frameCentral.place(relx=.5, rely=.5,anchor= tk.CENTER)

        #mostramos el titulo de la funcionalidad
        tituloFuncionalidad = tk.Label(frameCentral,text="Resumen stock de la Tienda", font=("Helvetica bold", 25), bg="#3B82BF", anchor="center") 
        tituloFuncionalidad.pack(pady = 10)

        #Tabla de la Tienda
        frameTienda=tk.LabelFrame(frameCentral,text="Stock Tienda",padx=5,pady=5,relief="ridge",font=30)#frame con marco para poner la Tabla de la tienda
        frameTienda.pack(pady=10)
        frameTienda.config(bd=1) #cambiar el grosor del borde
        frameTienda.config(bg="light steel blue")#color
        scroll1 = tk.Scrollbar(frameTienda)#Scroll para la tabla
        scroll1.pack(side=tk.RIGHT, fill=tk.Y)#posicion del scroll

        #instanciamos la tabla 
        tablaTienda=ttk.Treeview(frameTienda, yscrollcommand=scroll1.set, height=13, selectmode="extended")#se crea el Treeview para poner los datos de la Bodega 1
        tablaTienda.pack()

        #se configuran las columnas
        tablaTienda["columns"]=("Producto","ID","Tipo","Disponibilidad")  
        tablaTienda.column("#0",width= 0,stretch=tk.NO)
        tablaTienda.column("Producto",anchor=tk.W,width=120)
        tablaTienda.column("ID",anchor=tk.CENTER,width=100)
        tablaTienda.column("Tipo",anchor=tk.CENTER,width=120)
        tablaTienda.column("Disponibilidad",anchor=tk.CENTER,width=60)

        #se agregan tags
        tablaTienda.tag_configure('oddrow', background="white")
        tablaTienda.tag_configure('evenrow', background="lightblue")
        tablaTienda.tag_configure("Bajo", background="#F55A49")#color rojo por si la cantidad del producto es baja o 0

        #se configuran los encabezados
        tablaTienda.heading("#0", text="", anchor=tk.W)
        tablaTienda.heading("Producto", text="Producto", anchor=tk.W)
        tablaTienda.heading("ID", text="ID", anchor=tk.CENTER)
        tablaTienda.heading("Tipo", text="Tipo", anchor=tk.CENTER)
        tablaTienda.heading("Disponibilidad", text="Disponibilidad", anchor=tk.CENTER)
        scroll1.config(command=tablaTienda.yview)

        #para insertar los elementos en la tabla, asegurando de que sean de un colo u otro 
        count=0
        for elemento in self._productosTienda:
            if count % 2 == 0:  #para ponerle un color a los pares y otro a los impares
                if elemento.getCantidad() <= 3:#Esto va a ser por si la cantidad del producto es 0
                    tablaTienda.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(), elemento.getId(), elemento.getTipoProducto().name, elemento.getCantidad()), tags=('Bajo'))
                else:
                    tablaTienda.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(), elemento.getId(), elemento.getTipoProducto().name, elemento.getCantidad()), tags=('evenrow'))
            else:
                if elemento.getCantidad() <= 3:#Esto va a ser por si la cantidad del producto es 0
                    tablaTienda.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(), elemento.getId(), elemento.getTipoProducto().name, elemento.getCantidad()), tags=('Bajo'))
                else:
                    tablaTienda.insert(parent='', index='end', iid=count, text="", values=(elemento.getNombre(), elemento.getId(), elemento.getTipoProducto().name, elemento.getCantidad()), tags=('oddrow'))
            count += 1

        #Texto que indica celdas de color rojo
        frameP= tk.Frame(frameCentral,bg="#F55A49",width=15, height=15)
        frameP.pack(pady=10)
        textoP= tk.Label(frameCentral,text="Indica producto con cantidad menor o igual a 3 unidades",bg="light steel blue")
        textoP.pack()

    @classmethod
    def setTienda(cls, tienda):
        cls._tienda = tienda