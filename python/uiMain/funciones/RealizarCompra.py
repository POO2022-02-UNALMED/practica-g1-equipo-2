from tkinter import *
from tkinter import ttk
from datetime import datetime
from gestorAplicacion.modelos.Producto import Producto
from gestorAplicacion.modelos.TipoProducto import TipoProducto
from gestorAplicacion.errores.ErrorInterno import ErrorObjetoInexistente, ErrorCantidadInsuficiente
from gestorAplicacion.errores.ErrorUsuario import ErrorUsuario, ErrorCampoVacio, ErrorDatoIncorrecto


class RealizarCompra(Frame):

    def destruir(self):
        self.destroy()
    
    def __init__(self, master):
        super().__init__(master)
        self._ventana=master
        self._tienda=self._ventana._tiendaPrincipal
        self._datosProductos=self._tienda.getProductos()

    def get_tienda(self):
        return self._tienda

    def set_tienda(self,tienda):
        self._tienda=tienda

    def get_datosProductos(self):
        return self._datosProductos

    def set_datosProductos(self,productos):
        self._datosProductos=productos

        
    def configInicial(self):#limpia el _frame1 (destruye el anterior para borrar todo que había ahí y crea uno nuevo para poner todo)
        self._ventana._frame1.destroy()
        self._ventana._frame1=self
        self.config(bg="light steel blue")
        self.config(width=960, height=540)
        self.pack(expand=True)

   
    def OrganizarEspacio(self):#Organiza los datos en el Frame de acuerdo con las necesidades de la funcionalidad

        titulo=Label(self,text="Realizar Compra a proveedores",font=65,width=40,bg="#3B82BF")#Titulo 
        titulo.pack()
        titulo.place(x=290,y=20)

        minFrame=Frame(self,padx=5,pady=5,relief="ridge")#Frame para poner la tabla en la que van los datos de los productos
        minFrame.pack()
        minFrame.place(x=160, y=70)
        minFrame.config(bd=1) #cambiar el grosor del borde
        minFrame.config(bg="light steel blue")#color
        scroll = Scrollbar(minFrame)#Scroll para la tabla
        scroll.pack(side=RIGHT, fill=Y)#posicion del scroll
        tablaBo2=ttk.Treeview(minFrame, yscrollcommand=scroll.set,height=13, selectmode="extended",)#se crea el Treeview para poner los datos
        tablaBo2.pack()
        
        #Asignacion de colomunas en la tabla 
        tablaBo2["columns"]=("Producto","ID","Precio")  
        tablaBo2.column("#0",width= 0,stretch=NO)
        tablaBo2.column("Producto",anchor=W,width=120)
        tablaBo2.column("ID",anchor=CENTER,width=100)
        tablaBo2.column("Precio",anchor=CENTER,width=100)

        tablaBo2.tag_configure('oddrow', background="white")
        tablaBo2.tag_configure('evenrow', background="lightblue")

        tablaBo2.heading("#0", text="", anchor=W)
        tablaBo2.heading("Producto", text="Producto", anchor=W)
        tablaBo2.heading("ID", text="ID", anchor=CENTER)
        tablaBo2.heading("Precio", text="Precio", anchor=CENTER)
        scroll.config(command=tablaBo2.yview)
        
        #Se le asignan los datos a la tabla y se le asigna un color dependiendo si es par o impar
        global count2
        count2=0
        for elemento in self._datosProductos:
            if count2 %2 == 0:  #para ponerle un color a los pares y otro a los impares
                    tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getCostoCompra()), tags=('evenrow',))
            else:
                    tablaBo2.insert(parent='', index='end', iid=count2, text="", values=(elemento.getNombre(),elemento.getId(),elemento.getCostoCompra()), tags=('oddrow',))
                
            count2 += 1


        #Frame para poner los datos del producto que se quiere comprar 
        FrameCompra=Frame(self,width=300, height=270,bg="lightblue")
        FrameCompra.pack()
        FrameCompra.place(x=530, y=80)

        textoPr=Label(FrameCompra, text="Producto",bg="lightblue",font=2)
        textoID=Label(FrameCompra, text="ID",bg="lightblue",font=2)
        textoBg=Label(FrameCompra, text="Bodega",bg="lightblue",font=2)
        textoCan=Label(FrameCompra, text="Cantidad",bg="lightblue",font=2)

        textoPr.pack()
        textoPr.place(x=10,y=10)
        textoID.pack()
        textoID.place(x=10,y=70)
        textoBg.pack()
        textoBg.place(x=10,y=130)
        textoCan.pack()
        textoCan.place(x=10,y=190)

        #Comboox para elegir la bodega
        comboBodega=ttk.Combobox(FrameCompra,state="readonly")
        comboBodega["values"]=("Bodega 1","Bodega 2")
        comboBodega.pack()
        comboBodega.current(0)
        comboBodega.place(x=10, y=160)

        #Entrada para colocar la cantidad de productos que se quiere comprar
        CantidadEn=Entry(FrameCompra)
        CantidadEn.pack()
        CantidadEn.place(x=10,y=220)
        #Label para colocar Nombre, ID del producto
        NomProduc=Label(FrameCompra,width=20,anchor=W)
        NomProduc.pack()
        NomProduc.place(x=10,y=40)
        IDProduc=Label(FrameCompra,width=20,anchor=W)
        IDProduc.pack()
        IDProduc.place(x=10,y=100)

        textoNuevoProducto=Label(self,text="¿Desea agregar un producto nuevo al stock?",bg="light steel blue")
        textoNuevoProducto.pack()
        textoNuevoProducto.place(x=350,y=420)
        boton1= Button(self,text="Nuevo producto",bg="#519BA8")#Boton para agragar un nuevo producto
        boton1.pack()
        boton1.place(x=420,y=460)

        def agragarNuevo():#Se crea una ventana secudaria para pedir los datos y se agraga el producto nuevo

            NuevoP=Toplevel()# se crea una ventana para mostrar los datos de la factura
            NuevoP.focus()
            NuevoP.title("Nuevo Producto")
            NuevoP.geometry("300x450")
            frameNuevoP=Frame(NuevoP,width=400, height=500)# Frame para poner todos los datos 
            frameNuevoP.pack()
            #Se crean los Frame y los Label para poner la información de la compra

            textoNuevo=Label(frameNuevoP, text="Agregar nuevo producto",bg="#3B82BF",font=2,width=20)
            textoNuevo.pack()
            textoNuevo.place(x=60,y=10)
            textoNombre=Label(frameNuevoP, text="Nombre :",font=1,bg="#BDBDBB")
            textoIDpro=Label(frameNuevoP, text="ID :",font=1,bg="#BDBDBB")
            textoPreVenta=Label(frameNuevoP, text="Precio de Venta :",font=1,bg="#BDBDBB")
            textoPrenCom=Label(frameNuevoP, text="Costo de Compra :",font=1,bg="#BDBDBB")
            textoCantidad=Label(frameNuevoP, text="Cantidad :",font=1,bg="#BDBDBB")
            textoTipoPro=Label(frameNuevoP, text="Tipo Producto :",font=1,bg="#BDBDBB")
            textoBodega=Label(frameNuevoP, text="Bodega :",font=1,bg="#BDBDBB")

            #Frame gris para decorar 
            framedec=Frame(frameNuevoP,bg="#BDBDBB",width=300,height=28)
            framedec.pack()
            framedec.lower()
            framedec.place(x=10,y=46)
            #Frame gris para decorar 
            framedec1=Frame(frameNuevoP,bg="#BDBDBB",width=300,height=28)
            framedec1.pack()
            framedec1.lower()
            framedec1.place(x=10,y=98)
            #Frame gris para decorar 
            framedec2=Frame(frameNuevoP,bg="#BDBDBB",width=300,height=28)
            framedec2.pack()
            framedec2.lower()
            framedec2.place(x=10,y=148)
            #Frame gris para decorar      
            framedec3=Frame(frameNuevoP,bg="#BDBDBB",width=300,height=28)
            framedec3.pack()
            framedec3.lower()
            framedec3.place(x=10,y=208)
            #Frame gris para decorar 
            framedec4=Frame(frameNuevoP,bg="#BDBDBB",width=300,height=28)
            framedec4.pack()
            framedec4.lower()
            framedec4.place(x=10,y=268)
            #Frame gris para decorar
            framedec5=Frame(frameNuevoP,bg="#BDBDBB",width=300,height=28)
            framedec5.pack()
            framedec5.lower()
            framedec5.place(x=10,y=328)
            #Frame gris para decorar
            framedec6=Frame(frameNuevoP,bg="#BDBDBB",width=300,height=28)
            framedec6.pack()
            framedec6.lower()
            framedec6.place(x=10,y=388)

            #Se posicionan los Frame 
            textoNombre.pack()
            textoNombre.place(x=10,y=50)
            textoIDpro.pack()
            textoIDpro.place(x=10,y=100)
            textoPreVenta.pack()
            textoPreVenta.place(x=10,y=150)
            textoPrenCom.pack()
            textoPrenCom.place(x=10,y=210)
            textoCantidad.pack()
            textoCantidad.place(x=10,y=270)
            textoTipoPro.pack()
            textoTipoPro.place(x=10,y=330)
            textoBodega.pack()
            textoBodega.place(x=10,y=390)
            #Comobox para elegir la bodega 
            comboBodega1=ttk.Combobox(frameNuevoP,state="readonly")
            comboBodega1["values"]=("Bodega 1","Bodega 2")
            comboBodega1.pack()
            comboBodega1.current(0)
            comboBodega1.place(x=155, y=392)

            global IDnuevo
            IDnuevo=0
            for producto in self._tienda.getProductos():#Buscar el ultimo Id 
                if producto.getId()>IDnuevo:
                    IDnuevo=producto.getId()
            #se crean los Entry y Label para los datos 
            IDnuevo+=1#ID para el nuevo producto
            labelproN=Entry(frameNuevoP,font=1,width=10,bg="#BDBDBB")#Entry para poner el nombre del producto nuevo 
            labelidN=Label(frameNuevoP,text=IDnuevo,font=1,anchor=E,width=10,bg="#BDBDBB")
            labelPReVen=Entry(frameNuevoP,font=1,width=10,bg="#BDBDBB")#Entry para pedir el precio de venta del nuevo producto
            labelPreCom=Entry(frameNuevoP,font=1,width=10,bg="#BDBDBB")#Entry para pedir el costo de compra del nuevo producto
            labelCanNue=Entry(frameNuevoP,font=1,width=10,bg="#BDBDBB")#Entry para pedir cantidad del nuevo producto
            ComboTipo=ttk.Combobox(frameNuevoP,state="readonly")# Combobox para poner el tipo del  producto nuevo
            ComboTipo["values"]=( "Aseo","Alimentos","Hogar","Licores","Confiteria")# Tipos de producto

            #Se posicionan todos los entry y los label
            ComboTipo.pack()
            ComboTipo.current(1)
            ComboTipo.place(x=155,y=332)
            labelproN.pack()
            labelidN.pack()
            labelPReVen.pack()
            labelPreCom.pack()
            labelCanNue.pack()
            labelproN.place(x=200,y=50)
            labelidN.place(x=200,y=100)
            labelPReVen.place(x=200,y=150)
            labelPreCom.place(x=200,y=210)
            labelCanNue.place(x=200,y=270)
            

            #Para OBtener el tipo de producto segun se haya seleccionado en el combobox
            Tipo=None#Tipo de producto del nuevo producto
            if ComboTipo.get()=="Aseo":
                Tipo=TipoProducto.Aseo
            if ComboTipo.get()=="Alimentos":
                Tipo=TipoProducto.Alimentos
            if ComboTipo.get()=="Hogar":
                Tipo=TipoProducto.Hogar
            if ComboTipo.get()=="Licores":
                Tipo=TipoProducto.Licores
            if ComboTipo.get()=="Confiteria":
                Tipo=TipoProducto.Confiteria   


            def confirmarAgragar():#Toma los valores ingresados y crea el producto nuevo

                try:
                    #Se obtienen todos los datos ingresados 
                    global bodeganum1
                    bodeganum1=0
                    if comboBodega1.get()=="Bodega 2":#Asignar numero a la bodega
                        bodeganum1=1
                    bodegaN=self._tienda.getBodegas()[bodeganum1]#Bodega a la cual se le va a asignar la cantidad 

                    valorPrecioVenta = labelPReVen.get()
                    valorCostoCompra = labelPreCom.get()
                    valorCantidad = labelCanNue.get()
                    Nombre=labelproN.get()#Nomnbre del producto nuevo

                    if(Nombre.strip(" ") == ""):
                        raise ErrorCampoVacio("Nombre")
                    if(valorPrecioVenta.strip(" ") == ""):
                        raise ErrorCampoVacio("Precio de venta")
                    if(valorCostoCompra.strip(" ") == ""):
                        raise ErrorCampoVacio("Costo de compra")
                    if(valorCantidad.strip(" ") == ""):
                        raise ErrorCampoVacio("Cantidad")

                    if(not ErrorDatoIncorrecto.validarDato(Nombre, "nombre")):
                        raise ErrorDatoIncorrecto("Nombre")
                    if(not ErrorDatoIncorrecto.validarDato(valorPrecioVenta, "cantidad")):
                        raise ErrorDatoIncorrecto("Precio de venta")
                    if(not ErrorDatoIncorrecto.validarDato(valorCostoCompra, "cantidad")):
                        raise ErrorDatoIncorrecto("Costo de compra")
                    if(not ErrorDatoIncorrecto.validarDato(valorCantidad, "cantidad")):
                        raise ErrorDatoIncorrecto("Cantidad")
                    
                    

                    PrecioVenta=int(valorPrecioVenta)#Precio de Venta
                    PrecioCompra=int(valorCostoCompra)#Costo de compra
                    fecha=datetime.now()#fecha actual
                    CantidadNuevo=int(valorCantidad)#cantidad del producto nuevo
                    NuevoPoducto = Producto(Nombre,IDnuevo,PrecioVenta,PrecioCompra,CantidadNuevo,Tipo)# se crea la instancia del nuevo producto
                    listaNuevo=[NuevoPoducto]
                    bodegaN.generarCompra(fecha,listaNuevo)
                    NuevoPoductoT = Producto(Nombre,IDnuevo,PrecioVenta,PrecioCompra,0,Tipo)#se crea el producto con cantidad 0 para asignarlo a la tienda y a la otra bodega
                    self._tienda.agregarProducto(NuevoPoductoT)
                    #Dependiendo de la bodega a la cual se le asigno la cantidad, a la otra se le manda el producto con cantidad 0
                    if bodeganum1==0:
                        self._tienda.getBodegas()[1].agregarProducto(NuevoPoductoT)
                    elif bodeganum1==1:
                        self._tienda.getBodegas()[0].agregarProducto(NuevoPoductoT)

                    #Se crea un mensaje temporal para confirmar
                    labecon1=Label(self,text="¡Se agragó el Nuevo Producto!",bg="#3B82BF")
                    labecon1.pack()
                    labecon1.place(x=390,y=495)
                    def borrar():
                        labecon1.destroy()
                    labecon1.after(2000,borrar)
                    self.OrganizarEspacio()# se reinicia el Frame para que los datos carguen correctamente
                    #Se cierra la ventana secundaria
                    NuevoP.destroy()
              
                except ErrorUsuario as e:
                    print(e)
                    e.mostrarError()

                    return

            botonAgregar=Button(frameNuevoP,text="Agregar",command=confirmarAgragar,bg="#BDBDBB")#boton para agragar el porducto nuevo y cerrar la ventana
            botonAgregar.pack()
            botonAgregar.place(x=240,y=425)

           

        boton1.config(command=agragarNuevo)

        #Funcion para obtener los datos del elemnto seleccionado en la tabla 
        def obtenerdatos(): 
            seleccion=tablaBo2.focus()
            info=tablaBo2.item(seleccion)
            producto=info.get("values")[0]
            id=info.get("values")[1]
            NomProduc["text"]=producto
            IDProduc["text"]=id

        #Se realiza la compra al proveedor y se genera la factura de compra
        def generarCompra():
          try:
            seleccion=tablaBo2.focus()
            info=tablaBo2.item(seleccion)
            
            try:
                info.get("values")[0]
            except IndexError:
                raise ErrorObjetoInexistente("Debe seleccionar un producto de la tabla")

            producto=info.get("values")[0]
            id=info.get("values")[1]
            precio=info.get("values")[2]

            cantidadValue = CantidadEn.get()
            if(cantidadValue.strip() == ""):
                raise ErrorCantidadInsuficiente(1)

            factura=Toplevel()# se crea una ventana para mostrar los datos de la factura
            factura.focus()
            factura.title("Factura")
            factura.geometry("300x330")
            frame4=Frame(factura,width=400, height=500)# Frame para poner todos los datos 
            frame4.pack()

            #Se crean los Frame y los Label para poner la información de la compra
            texto6=Label(frame4, text="Factura de Compra",bg="#3B82BF",font=2,width=20)
            texto6.pack()
            texto6.place(x=60,y=10)
            textoPr=Label(frame4, text="Producto :",font=1,bg="#BDBDBB")
            textoID=Label(frame4, text="ID :",font=1,bg="#BDBDBB")
            textoBg=Label(frame4, text="Bodega :",font=1,bg="#BDBDBB")
            textoCan=Label(frame4, text="Cantidad :",font=1,bg="#BDBDBB")
            textoto=Label(frame4, text="Total a pagar :",font=1,bg="#BDBDBB")

            #Frame gris para decorar
            framed=Frame(frame4,bg="#BDBDBB",width=300,height=28)
            framed.pack()
            framed.lower()
            framed.place(x=10,y=46)

            #Frame gris para decorar
            framed1=Frame(frame4,bg="#BDBDBB",width=300,height=28)
            framed1.pack()
            framed1.lower()
            framed1.place(x=10,y=98)

            #Frame gris para decorar
            framed2=Frame(frame4,bg="#BDBDBB",width=300,height=28)
            framed2.pack()
            framed2.lower()
            framed2.place(x=10,y=148)

            #Frame gris para decorar     
            framed3=Frame(frame4,bg="#BDBDBB",width=300,height=28)
            framed3.pack()
            framed3.lower()
            framed3.place(x=10,y=208)

            #Frame gris para decorar
            framed4=Frame(frame4,bg="#BDBDBB",width=300,height=28)
            framed4.pack()
            framed4.lower()
            framed4.place(x=10,y=268)

            #Se posicionan todos los label
            textoPr.pack()
            textoPr.place(x=10,y=50)
            textoID.pack()
            textoID.place(x=10,y=100)
            textoBg.pack()
            textoBg.place(x=10,y=150)
            textoCan.pack()
            textoCan.place(x=10,y=210)
            textoto.pack()
            textoto.place(x=10,y=270)

            #Se crean los Label donde se colocarán los datos y se posicionan en el Frame
            labelpro=Label(frame4,font=1,anchor=E,width=10,bg="#BDBDBB")
            labelid=Label(frame4,font=1,anchor=E,width=10,bg="#BDBDBB")
            labelbog=Label(frame4,font=1,anchor=E,width=10,bg="#BDBDBB")
            labelcan=Label(frame4,font=1,anchor=E,width=10,bg="#BDBDBB")
            labelto=Label(frame4,font=1,anchor=E,width=10,bg="#BDBDBB")
            labelpro.pack()
            labelid.pack()
            labelbog.pack()
            labelcan.pack()
            labelto.pack()
            labelpro.place(x=200,y=50)
            labelid.place(x=200,y=100)
            labelbog.place(x=200,y=150)
            labelcan.place(x=200,y=210)
            labelto.place(x=200,y=270)

            #Se obtienen los respectivos datos y se le asginan a los Label
            labelpro["text"]=producto
            labelid["text"]=id
            labelbog["text"]=comboBodega.get()
            labelcan["text"]=CantidadEn.get()
            labelto["text"]=str(int(cantidadValue)*int(precio))+" $"

            #Realizar la compra segun los datos obtenidos
            bodeganum=0
            if comboBodega.get()=="Bodega 2":#Asignar numero a la bodega
                bodeganum=1
            nombreproducto=info.get("values")[0]
            id=int(info.get("values")[1])
            cantidad2=int(CantidadEn.get())#Cantidad ingresada 
            bodega=self._tienda.getBodegas()[bodeganum] #Bodega a en la que se realiza la compra
            fecha=datetime.now() #fecha actual para hacer la compra
            producto=bodega.buscarProductoPorIDCopia(id) #producto del cual se va hacer la compra
            producto.setCantidad(cantidad2)# Cantidad que se va comprar
            listaproducto=[producto]# Se agrega en una lista 
            bodega.generarCompra(fecha,listaproducto)# se genera la compra

            #Función para cerrar la ventana secundaria y reestablecer los datos
            def aceptar():
                factura.destroy()
                NomProduc["text"]=""
                IDProduc["text"]=""
                CantidadEn.delete(0,END)
                labecon2=Label(self,text="¡La compra se realizó con éxito!",bg="#3B82BF")#Mensaje para cofirmar que se realizó la compra
                labecon2.pack()
                labecon2.place(x=390,y=495)
                #Para borrar el Label pasado cierto tiempo para que no quede en pantalla
                def borrar2():
                    labecon2.destroy()
                labecon2.after(2000,borrar2)

            botonAceptar=Button(frame4,text="Aceptar",command=aceptar,bg="#BDBDBB")#boton para cerrar la ventana
            botonAceptar.pack()
            botonAceptar.place(x=240,y=300)

          except (ErrorCantidadInsuficiente, ErrorObjetoInexistente) as e:
            print(e)
            e.mostrarError()

            if(type(e) == ErrorCantidadInsuficiente):
                CantidadEn.delete(0,END)
                CantidadEn.insert(0, str(e._cantidadMinima))
            return

        boton1= Button(self,text="Seleccionar producto",bg="#519BA8",command=obtenerdatos)#Boton para obtener los datos del producto seleccionado
        boton1.pack()
        boton1.place(x=158,y=370)

        boton2= Button(FrameCompra,text="Confirmar Compra",bg="#519BA8",command=generarCompra)#Boton para generar la compra según los datos
        boton2.pack()
        boton2.place(x=190,y=240)
    

    def VentanaRealizarCompra(self):
        self.configInicial()
        self.OrganizarEspacio()