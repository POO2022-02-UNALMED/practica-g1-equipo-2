# Importar librerias necesarias
import tkinter as tk
from tkinter import messagebox
from funciones.GenerarBalance import GenerarBalance
from funciones.AgregarCliente import AgregarCliente
from funciones.ResumenClientes import ResumenClientes 
from funciones.ResumenProveedores import ResumenProveedores
from funciones.ResumenStockBajo import ResumenStockBajo
from funciones.ResumenStockBodegas import ResumenStockBodegas
from funciones.VentaAlPorMayor import VentaAlPorMayor
from funciones.RealizarCompra import RealizarCompra
from funciones.RealizarVenta import RealizarVenta 
from funciones.HacerTraslado import HacerTraslado
from funciones.ResumenStockTienda import ResumenStockTienda
from funciones.ResumenClientes import ResumenClientes
import ventanaInicio

import os
import sys
import inspect
currentdir = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
parentdir = os.path.dirname(currentdir)
sys.path.insert(0, parentdir) 
from baseDatos.instanciadorBD import instanciadorBD


class VentaPrincipal(tk.Tk):

    # Variable de clase de la tienda principal para hacer todas las operaciones
    _tiendaPrincipal = None

    # Constructor de clase
    def __init__(self):

        # deserializar la tienda
        self._tiendaPrincipal = instanciadorBD.getTienda()

        super().__init__()

        # Organizar la ventana
        self.geometry("960x540")
        self.title("G D I V")
        self.option_add("*tearOff", False)
        self.resizable(False,False)
            
        # Creacion del menu
        self._menubar = tk.Menu(self)

        # Añadir opciones del menu, parte de archivo
        archivo =tk.Menu(self._menubar)
        self._menubar.add_cascade(label="Archivo", menu= archivo)
        archivo.add_command(label="Aplicación", command=self.funAplicacion)
        archivo.add_command(label="Salir", command = lambda: self.botonSalir())
        
        # Añadir opciones parte procesos y consultas
        procYcon = tk.Menu(self._menubar)
        self._menubar.add_cascade(label="Procesos y consultas", menu= procYcon)
        operaciones = tk.Menu(procYcon)
        
        # Añadir submenu operaciones
        procYcon.add_cascade(label="Operaciones", menu=operaciones)
        
        operaciones.add_command(label="Realizar Compra",command=self.botonRC)
        operaciones.add_command(label="Realizar Venta", command=self.botonRV)
        operaciones.add_command(label="Hacer Traslado", command=self.botonHT)
        operaciones.add_command(label="Realizar Venta Al Por Mayor", command=self.botonVPM)
        consultas = tk.Menu(procYcon)
        
        # Añadir submenu consultas
        procYcon.add_cascade(label="Consultas", menu=consultas)
       
        consultas.add_command(label="Resumen Stock Tienda", command=self.botonRST)
        consultas.add_command(label="Resumen Stock Bodegas",command=self.botonRSB)
        consultas.add_command(label="Resumen Proveedores", command=self.botonRP)
        consultas.add_command(label="Resumen Productos Stock Bajo",command=self.botonRSBT)
        consultas.add_command(label="Generar Balance",command=self.botonGB)
        
        # Añadir submenu clientes
        clientes = tk.Menu(procYcon)
        procYcon.add_cascade(label="Clientes", menu=clientes)
        clientes.add_command(label="Agregar Cliente", command=self.botonAC)
        clientes.add_command(label="Resumen Clientes", command=self.botonRCS)

        # Añadir menu ayuda
        ayuda = tk.Menu(self._menubar)
        self._menubar.add_cascade(label="Ayuda", menu=ayuda)
        ayuda.add_command(label="Acerca de", command=self.funAcerdaDe)

        
        # Creacion frame inicial 
        self._frame1 = tk.Frame(self,bg="light steel blue", width=960, height=540)
        mensaje1 = tk.Label(self._frame1, text = "¡¡Bienvenido!!", font= ("Verdana",15), background="light steel blue")
        mensaje2 = tk.Label(self._frame1, text = "Seleccione una opción del menú de 'Procesos y Consultas' para visualizar\n su contenido.", font= ("Verdana",15), background="light steel blue")
        self._frame1.pack(expand=True)
        mensaje1.place(relx=0.43, rely=0.3)
        mensaje2.place(relx=0.12, rely=0.5)


        self.config(menu=self._menubar)

    # Metodos para invocar cada funcionalidad del menu
    
    
    def botonGB(self):#funcion que crea la instancia de Generar balance para cuando se de click 
        frame1 = GenerarBalance(self)
        frame1.VentanaBalance()
        
    # Funcion para llamar el resumen de clientes
    def botonRC(self):
        frame1= ResumenClientes(self)
        frame1.VentanaClientes()
    
    # Funcion para llamar el resumen de proveedores
    def botonRP(self):
        frame1= ResumenProveedores(self)
        frame1.setTienda(self._tiendaPrincipal)
        frame1.VentanaProveedores()

    # Funcion para llamar la funcionalidad de agregar cliente
    def botonAC(self):
        frame1 = AgregarCliente(self)
        frame1.setTienda(self._tiendaPrincipal)
        frame1.VentanaAgregarCliente()

    # Funcion para llamar el resumen del stock de las bodegas
    def botonRSB(self):
        frame1= ResumenStockBodegas(self)
        frame1.VentanResumenBodegas()
    
    # Funcion para llamar la venta al por mayor
    def botonVPM(self):
        frame1= VentaAlPorMayor(self)
        frame1.setTienda(self._tiendaPrincipal)
        frame1.VentanaVentaAlPorMayor()

    # Funcion para llamar la funcionalidad para productos con stock bajo
    def botonRSBT(self):
        frame1= ResumenStockBajo(self)
        frame1.VentanaResumenStockBajo()

    # Funcion para llamar la funcionalidad de realizar compra
    def botonRC(self):
        frame1=RealizarCompra(self)
        frame1.VentanaRealizarCompra()

    # Funcion para mostrar al presionar en archivo - descripcion 
    def funAplicacion(self):
        alerta = messagebox.showinfo("Información: G D I V", "GDIV es una aplicación diseñada para la gestión de inventario de una tienda junto a sus bodegas. El rol principal es el de gerente de una tienda, el cual se encargará de realizar venta de productos a clientes, solicitar rellenar stock de productos desde las bodegas, comprar productos a proveedores, generar un balance general tanto de la tienda como de las bodegas, entre otras funciones.")
    
    # Funcion para mostrar acerca de al entrar por ayuda - acerca de
    def funAcerdaDe(self):
        alerta = messagebox.showinfo("AUTORES", " Juan Jose Nanclares Cardenas\n\n David Castrillon Vallejo\n\n Juan David Restrepo Montoya\n\n Juan Esteban Cadavid Arango\n\n Andres Esteban Monsalve Vasquez")

    # Funcion para salir de la pestaña, volver a la ventana de inicio y serializar la tienda
    def botonSalir(self):
        instanciadorBD.setTienda(self._tiendaPrincipal)
        self.destroy()
        ventana_inicio = ventanaInicio.ventanaInicio()

    # Funcion para llamar la funcionalidad de realizar venta
    def botonRV(self):
        frame1 = RealizarVenta(self)
        frame1.setTienda(self._tiendaPrincipal)
        frame1.organizarEspacio()

    # Funcion para llamar la funcionalidad de hacer traslado
    def botonHT(self):
        frame1 = HacerTraslado(self)
        frame1.setTienda(self._tiendaPrincipal)
        frame1.VentanaHacerTraslado()
        
    # Funcion para llamar el resumend el stock de la tienda
    def botonRST(self):
        frame1 = ResumenStockTienda(self)
        frame1.setTienda(self._tiendaPrincipal)
        frame1.organizarEspacio()        

    # Funcion para llamar el resumend de clientes
    def botonRCS(self):
        frame1 = ResumenClientes(self)
        frame1.setTienda(self._tiendaPrincipal)
        frame1.VentanaClientes()   