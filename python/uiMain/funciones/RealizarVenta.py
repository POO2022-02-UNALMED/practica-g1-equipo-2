import tkinter as tk
from FieldFrame import FieldFrame
from tkinter import messagebox
import datetime
import traceback
from gestorAplicacion.errores.ErrorUsuario import ErrorCarritoVacio, ErrorCampoVacio, ErrorDatoIncorrecto, ErrorUsuario
from gestorAplicacion.errores.ErrorInterno import ErrorObjetoInexistente

class RealizarVenta(tk.Frame):

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

        #atributos necesarios para generar la venta a un cliente
        self._productosVenta = []
        self._cliente = None
        self._fecha = None

    def organizarEspacio(self):
        self._clientesTienda = RealizarVenta._tienda.getClientes()

        #frame contenedor
        frameContenedor = tk.Frame(self, bg="light steel blue")
        frameContenedor.place(relx=.5, rely=.5,anchor= tk.CENTER)

        #label para formatear el titulo
        tituloFuncionalidad = tk.Label(frameContenedor,text="Realizar una Venta a un Cliente", font=("Helvetica bold", 20), bg="#3B82BF", anchor="center") 
        tituloFuncionalidad.pack(side ="top", pady = 2)

        #frame central que contendrá la lista de productos y los campos para pedir los productos
        frameCentral = tk.Frame(frameContenedor)
        frameCentral.pack(padx=10,pady=5, expand=True)

        #frame anidado para el FildFrame
        frameField = tk.Frame(frameCentral)
        frameField.grid(column=0,row=0,padx=10,pady=10)

        #frame anidado para mostrar la lista de los productos
        frameLista = tk.Frame(frameCentral)
        frameLista.grid(column=0,row=1,padx=10,pady=10, columnspan=2)

        #frame anidado para ingresar los productos necesitados 
        frameField2 = tk.Frame(frameCentral)
        frameField2.grid(column=1,row=0,padx=10,pady=10)

        #instanciacion del fieldFrame para cliente y fecha
        fechaString = datetime.date.today().strftime("%d/%m/%Y")
        self.fieldFrameDatos1 = FieldFrame(frameField, "DATOS", ["Cliente", "Fecha"], "ENTRADAS", ["", fechaString],[True, False])
        self.fieldFrameDatos1.funAceptar(self.aceptarOperacion)
        #cambiamos el entry por un combobox
        self.fieldFrameDatos1._elementos[0] = tk.ttk.Combobox(self.fieldFrameDatos1, values=self.nombresClientesTienda())
        (self.fieldFrameDatos1._elementos[0]).grid(column=1,row=1, columnspan=3, padx = (10,10), pady = (10,10))
        self.fieldFrameDatos1.pack()

        #organizar los productos disponibles en una tabla 
        #frameListaProductos.config(bd=1) #cambiar el grosor del borde
        frameStock = tk.Frame(frameLista)
        frameStock.grid(column=0,row=0,padx=10,pady=10)

        scroll = tk.Scrollbar(frameStock)#Scroll para la tabla
        scroll.pack(side=tk.RIGHT, fill=tk.Y)#posicion del scroll
        
        #instanciamos la tabla con el objeto Treeview
        self.tablaProductos=tk.ttk.Treeview(frameStock, yscrollcommand=scroll.set, selectmode="extended")
        self.tablaProductos.pack()

        #definimos las columnas
        self.tablaProductos["columns"]=("Producto","ID","Precio", "Disponibilidad")  
        self.tablaProductos.column("#0",width= 0,stretch=tk.NO)
        self.tablaProductos.column("Producto",anchor=tk.W,width=120)
        self.tablaProductos.column("ID",anchor=tk.CENTER,width=100)
        self.tablaProductos.column("Precio",anchor=tk.CENTER,width=100)
        self.tablaProductos.column("Disponibilidad", anchor=tk.CENTER, width=100)

        #agregamos tags
        self.tablaProductos.tag_configure('oddrow', background="white")
        self.tablaProductos.tag_configure('evenrow', background="lightblue")

        #creamos los encabezados
        self.tablaProductos.heading("#0", text="", anchor=tk.W)
        self.tablaProductos.heading("Producto", text="Producto", anchor=tk.W)
        self.tablaProductos.heading("ID", text="ID", anchor=tk.CENTER)
        self.tablaProductos.heading("Precio", text="Precio", anchor=tk.CENTER)
        self.tablaProductos.heading("Disponibilidad", text="Disponibilidad", anchor=tk.CENTER)

        scroll.config(command=self.tablaProductos.yview)

        #para insertar los elementos en la tabla, asegurando de que sean de un colo u otro 
        count2=0
        for elemento in RealizarVenta._tienda._productos:
            if count2 % 2 == 0:  
                    self.tablaProductos.insert(parent='', index='end', iid=count2, text="", values=(elemento._nombre, elemento._id,elemento._precioVenta, elemento._cantidad), tags=('evenrow'))
            else:
                    self.tablaProductos.insert(parent='', index='end', iid=count2, text="", values=(elemento._nombre, elemento._id,elemento._precioVenta, elemento._cantidad), tags=('oddrow'))
            count2 += 1


        #creamos los campos para agregar los productos
        self.fieldFrameDatos2 = FieldFrame(frameField2, "DATOS", ["ID producto", "Cantidad"], "ENTRADAS")
        self.fieldFrameDatos2.funAceptar(self.agregarProducto)
        self.fieldFrameDatos2.pack()

        #frame para crear el listbox del carrito de compras
        frameStock2 = tk.Frame(frameLista)
        frameStock2.grid(column=1,row=0,padx=10,pady=10)

        #instanciamos y posicionamos el list box del carrito de compras y el boton para eliminar alguno cuando se necesite
        self.listbox = tk.Listbox(frameStock2)
        btn = tk.Button(frameStock2, text = "delete", command = self.eliminarProductoLista)   
        self.listbox.grid(column=0,row=0,padx=10,pady=10)
        btn.grid(column=0,row=1,padx=10,pady=10)
    

    #método para recuperar los nombres de los clientes de la tienda
    def nombresClientesTienda(self):
        listaNombres = []
        for cliente in self._clientesTienda:
            listaNombres.append(cliente.getNombre())
        return listaNombres


    #método encargado de realizar la venta cuando se oprima el boton aceptar (el de la izquierda)
    def aceptarOperacion(self):

        try: 
            #se obtiene el nombre del cliente seleccionado
            #nombreCliente = (self.fieldFrameDatos1._elementos[0]).get()
            nombreCliente = self.fieldFrameDatos1.getValue("Cliente")

            #condicional para lanzar error si hay un campo vacio en el nombre del cliente
            if(nombreCliente.strip(" ") == ""):
                raise ErrorCampoVacio("Cliente")

            #se valida el formato del nombre del cliente
            if(not ErrorDatoIncorrecto.validarDato(nombreCliente, "nombre")):
                raise ErrorDatoIncorrecto("Cliente")

            #en base al nombre del cliente seleccionado filtramos la referencia al objeto del mismo
            self._cliente = next(filter(lambda cliente: cliente.getNombre() == nombreCliente, self._clientesTienda))
            #generamos la fecha actual
            self._fecha = datetime.date.today()

            #condicional para generar una excepcion si el carrito está vacío
            if len(self._productosVenta) == 0: 
                raise ErrorCarritoVacio()
            
            #se genera la venta
            ventaGenerada = RealizarVenta._tienda.generarVenta(self._fecha, self._cliente, self._productosVenta)
            #se comprueban las ofertas que se puedan aplicar
            RealizarVenta._tienda.ofertaDia(self._productosVenta, self._fecha)
            #calculamos la fidelidad del cliente y aplicamos los descuentos que cumplan las condiciones en base a dicho valor
            fidelidadCliente = self._cliente.calcularFidelidad(len(RealizarVenta._tienda.getVentas()))
            if (fidelidadCliente >= 0.1 and fidelidadCliente < 0.2):
                ventaGenerada.calcularIngresos(0.05)
            elif (fidelidadCliente >= 0.2):
                ventaGenerada.calcularIngresos(0.1)
            
            #generamos la factura de la venta
            tk.messagebox.showinfo("Venta Generada con Exito", ventaGenerada.generarFactura())

            #limpiamos los datos de los fields y la seleccion de la tabla
            self.fieldFrameDatos1.funBorrar()
            self.fieldFrameDatos2.funBorrar()
            self.listbox.delete(0, tk.END)
            self.tablaProductos.selection_remove(*self.tablaProductos.selection())
            
            #volvemos a poner los valores por defecto
            self._productosVenta = []
            self._cliente = None
            self._fecha = None

        except ErrorUsuario as e:
            print(e)
            e.mostrarError()
            return

    def agregarProducto(self):
        try:
            #recuperamos los datos solicitados para agregar el producto al carrito de compras
            valorIdProducto = self.fieldFrameDatos2.getValue(self.fieldFrameDatos2._criterios[0])
            valorCantidadProducto = self.fieldFrameDatos2.getValue(self.fieldFrameDatos2._criterios[1])

            #condicional con excepcion para evitar que los campos estén vacios
            if(valorIdProducto.strip() == "" or valorCantidadProducto.strip() == ""):
                raise ErrorCampoVacio("ID Producto" if valorIdProducto.strip() == "" else "Cantidad")

            #se valida que los datos sean correctos
            if(not ErrorDatoIncorrecto.validarDato(valorIdProducto, "id")):
                raise ErrorDatoIncorrecto("ID Producto")
            if(not ErrorDatoIncorrecto.validarDato(valorCantidadProducto, "cantidad")):
                raise ErrorDatoIncorrecto("Cantidad")

            #datos necesarios para ubicar el producto
            idProducto = int(valorIdProducto)
            cantidadProducto = int(valorCantidadProducto)
            producto = None
        
            #buscamos el producto, si este no existe se lanza excepcion
            producto = RealizarVenta._tienda.buscarProductoPorIDCopia(idProducto)
            if producto == None:
                raise ErrorObjetoInexistente("Ingrese el ID de un producto que esté en el stock")

            #se organiza la cantidad del producto que se compra
            producto.setCantidad(cantidadProducto)

            #se muestra el producto en la listbox del carrito de compras
            self.listbox.insert(tk.END, producto.getNombre())
            #se agrega el producto a la lista de productos de la tienda
            self._productosVenta.append(producto)
            #se llama a la funcion que actualiza los datos de la tienda
            self.actualizarTabla(producto, -1*cantidadProducto)
            
        except ErrorObjetoInexistente as e:
            print(e)
            self.fieldFrameDatos2.funBorrar()
            e.mostrarError()
            
            return
        
        except (ErrorUsuario) as e:
            print(e)
            e.mostrarError()

            return

    def actualizarTabla(self, producto, cantidad):

        #iteramos sobre los productos que se muestran en la tabla
        for child in self.tablaProductos.get_children():
            #si el id del producto esta en la tabla
            if producto._id in self.tablaProductos.item(child)['values']:
                cantidadActual = int(self.tablaProductos.item(child, "values")[3]) 

                #actualizamos el valor de las unidades disponibles y se selecciona el producto sobre el que se trabaja
                self.tablaProductos.item(child, values=(producto.getNombre(), producto.getId(), producto.getPrecioVenta(), cantidadActual+cantidad))
                self.tablaProductos.selection_set(child)


    def eliminarProductoLista(self):
        try:
           #eliminar del listbox
            if(self.listbox.curselection() == ()):
                raise ErrorObjetoInexistente("Debe seleccionar el producto de la lista que desea eliminar")
            productoSeleccionado = self.listbox.get(self.listbox.curselection())
            self.listbox.delete(self.listbox.curselection())

            #eliminar de la lista de productos
            producto = next(filter(lambda producto: producto.getNombre() == productoSeleccionado, self._productosVenta))
            self.actualizarTabla(producto, producto._cantidad)
            self._productosVenta.remove(producto)

        except ErrorObjetoInexistente as e:
            print(e)
            e.mostrarError()
            return

    @classmethod
    def setTienda(cls, tienda):
        cls._tienda = tienda









