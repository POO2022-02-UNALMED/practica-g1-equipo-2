# importar librerías necesarias
import tkinter as tk
import os
import sys
import inspect
pythonProjectRoot = os.path.abspath(os.path.join(os.path.dirname( __file__ ), '..'))
sys.path.insert(0, pythonProjectRoot) 
import VentanaPrincipal

class ventanaInicio(tk.Tk):
    
    # Constructor de la ventana inicio
    def __init__(self):
        super().__init__()       
        
        # Configuracion de la ventana
        self.title("G D I V")
        self.geometry("1400x720")
        self.option_add("*tearOff", False)
        self.resizable(False,False) # No permite agrandar la pestaña
        

                
        # Crear menu
        self._menu_bar = tk.Menu(self)
        self.config(menu=self._menu_bar)

        self._menu_inicio = tk.Menu(self._menu_bar)
        self._menu_bar.add_cascade(label="Inicio", menu = self._menu_inicio)

        # Añadir comandos salir y descripción al menú inicio
        self._menu_inicio.add_command(label="Salir", command= lambda: self.destroy())
        self._menu_inicio.add_command(label="Descripcion", command = lambda: self.mostrarDescripcion())
        
        
        
        # Bienvenida al sistema, imagenes de la app y boton
        self._p1 = P1(self)
        
        
        # Hoja de vida y fotos de los desarrolladores
        self._p2 = P2(self)
        
        
        
        # Poner elementos en pantalla
        self._p1.grid(row = 0, column = 0, padx = (10,10))
        self._p2.grid(row = 0, column = 1, padx = (10,10))

    
        # Mostrar descripcion en el menu
        
    def mostrarDescripcion(self):
        self._p1._descripcion.pack(pady=(10,0))
        self.geometry("1450x940")
    

# Creación clase parte izquierda de la pantalla
class P1(tk.Frame):
    def __init__(self, ventana):
        super().__init__(ventana)


        self._ventana = ventana
        
        # Crear distintos frames
        self._p3 = tk.Frame(self)
        self._p4_ingreso = tk.Frame(self)
        self._p4_imagen = tk.Frame(self)
        
        
        
        # Mensaje de bienvenida
        
        
        saludo = """                
                    Bienvenido a GDIV

        ¡Presione en el boton de ingresar a la aplicacion para continuar!
        """
        
        # Añadir saludo a la pantalla
        self._saludo = tk.Label(self._p3, text=saludo, font=("Verdana", 15))
        self._saludo.pack()
                
        
        # Mostrar descripcion al darle en el boton del menu
        
        descripcion = \
        """
        GDIV es una aplicacion de gestion de inventario y de ventas. El usuario principal es el administrador del establecimiento.
        Este tendrá acceso a un software para llevar control de el inventario y de las ventas tanto de tiendas como de bodegas.

        Entre las funciones de la aplicacion estan:

        • Agregar clientes.
        • Generar balance de tienda.
        • Resumen del stock tanto de la tienda como de la bodega.
        • Realizar compras a proveedores y ventas a clientes.
        • Verificar productos con stock bajo.
        ¡Y muchas funciones mas!
        """

        self._descripcion = tk.Label(self._p3, text=descripcion, width=100, justify="left", font=("Verdana", 9))
        

        # Mostrar imagenes de la aplicacion en p4
        
        self._imgAct = 0
        self._imagenes = []
        
        # Dado que son 5 imágenes, se recorre el ciclo 5 veces
        for i in range(5):
            absolute_folder_path = os.path.dirname(os.path.realpath(__file__))
            archivo = os.path.join(absolute_folder_path, f"imagenes", "aplicacion", f"img_{i+1}.png")  
            image = tk.PhotoImage(file = archivo) # Se lee la imagen en el formato de Tkinter
            self._imagenes.append(image) # Se añade la imagen a la lista de imágenes para ser mostradas
            
        self._imagen = tk.Label(self._p4_imagen, image = self._imagenes[0], height=480, width = 640)   # Añadir imagen a un label 
        self._imagen.bind('<Enter>', self.imgSiguiente) # Cambiar imagen cada vez que se pasa el mouse
        self._imagen.pack() # Mostrar imagen en pantalla
    

        # Boton de acceso a la app
        
        self._boton = tk.Button(self._p4_ingreso, text = "Ingresar a la aplicacion", font=("Verdana", 16), command = lambda:self.ingresarApp())
        self._boton.pack() 
        
        
        # Mostrar elementos en pantalla
        self._p3.grid(row = 0, column = 0, pady=(10,10))
        self._p4_imagen.grid(row = 1, column = 0, pady=(10,10))
        self._p4_ingreso.grid(row = 2, column = 0, pady=(10,10))

    # Pasar imagen siguiente al pasar el mouse
    def imgSiguiente(self, args): 
        if self._imgAct == 4:
            self._imgAct = 0
        else:
            self._imgAct += 1

        self._imagen.configure(image = self._imagenes[self._imgAct])
        self._imagen.image = self._imagenes[self._imgAct]
        
    # Ingresar app al darle al boton
    def ingresarApp(self):
        self._ventana.destroy() 
        ventana_principal = VentanaPrincipal.VentaPrincipal()


# Parte derecha de la pestaña
class P2(tk.Frame):
    _posicion_imagen = [(0, 0), (0, 1), (1, 0), (1, 1)] # Dado que son 4 imágenes se ponen 4 coordenadas

    # Constructor de la clase
    def __init__(self, window):
        super().__init__(window)

    # Creación de los frames donde irán los elementos 
        self._p5 = tk.Frame(self)
        self._p6 = tk.Frame(self)

        self._text = None
        self._next_hv = 0
        self._photos = [None, None, None, None]
        self._labels = []

    # Cargar texto inicial
        self.cargarHVTexto(0)
    # Cargar imagenes iniciales
        for i in range(0, 4):
            label = tk.Label(self._p6, width = 320, height = 240)
            (r, c) = P2._posicion_imagen[i]
            label.grid(row=r, column=c)
            self._labels.append(label)
            # Se cargan las primeras imagenes
            self.cargarHVImagen(0, i)
    
    # Añadir en pantalla ambos frames (texto e imágenes)
        self._p5.grid()
        self._p6.grid()


    # Se usa para mostrar la hoja de vida que sigue
    def proximo(self, _):
        if self._next_hv < 4: 
            self._next_hv = self._next_hv + 1
        else:
            self._next_hv = 0

        self._photos = [None, None, None, None]
        self.cargarHVTexto(self._next_hv)
        for i in range(0, 4):
            self.cargarHVImagen(self._next_hv, i)


    # Carga el component imagen que muestra las fotos
    def cargarHVImagen(self, hv_num, numero):
        absolute_folder_path = os.path.dirname(os.path.realpath(__file__))
        path = os.path.join(absolute_folder_path, f"imagenes", "hv", f"img_{hv_num}{numero}.png") 
        photo = tk.PhotoImage(file = path)
        self._labels[numero].configure(image = photo)
        self._labels[numero].image = photo


    # Carga el texto para la hoja de vida respecto al numero asignado
    def cargarHVTexto(self, numero):
        self._text = tk.Text(self._p5, height = 10, font = ("Verdana",10), width = 80)
        self._text.grid(row = 1, column = 0)
        self._text.bind('<Button-1>', self.proximo)
        absolute_folder_path = os.path.dirname(os.path.realpath(__file__))
        path = os.path.join(absolute_folder_path, f"imagenes", "hv", f"hv{numero}.txt") 

        with open(path, "r+") as hv_text:
            self._text.insert(tk.INSERT, hv_text.read())
        
        
if __name__ == "__main__":
    app = ventanaInicio()
    app.mainloop()