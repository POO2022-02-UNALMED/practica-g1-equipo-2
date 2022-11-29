import tkinter as tk

class FieldFrame(tk.Frame):
    
    def __init__(self,frame, tituloCriterios = "", criterios = None, tituloValores = "", valores = None, habilitado = None):
        super().__init__(frame)
        self._tituloCriterios = tituloCriterios
        self._criterios = criterios
        self._tituloValores = tituloValores
        self._valores = valores
        self._habilitado = habilitado

        self._elementos = []

        tituloCrit = tk.Label(self, text = tituloCriterios, font= ("Verdana bold",15), anchor="center")
        tituloCrit.grid(column=0, row=0, padx = (10,10), pady = (10,10))
        
        tituloVal = tk.Label(self, text = tituloValores, font= ("Verdana bold",15), anchor="center")
        tituloVal.grid(column=1, row=0,columnspan=3, padx = (10,10), pady = (10,10))

        for i in range(len(criterios)):
            
            labelCriterio = tk.Label(self, text = criterios[i], font= ("Verdana",12), anchor="center")
            labelCriterio.grid(column=0, row=i+1, padx = (10,10), pady = (10,10))
            
            entryValor = tk.Entry(self)
            entryValor.grid(column=1, row=i+1,columnspan=3, padx = (10,10), pady = (10,10))
           
            if valores is not None:
                entryValor.insert(0, valores[i])
            
            if habilitado is not None and not habilitado[i]:
                entryValor.configure(state = tk.DISABLED)
            
            self._elementos.append(entryValor)

        tk.Button(self, text="Borrar", font = ("Verdana", 12), fg = "white", bg = "gray",command=self.funBorrar, 
        width=12,height=2).grid(pady = (10,10), padx=(10,10), column = 1, row = len(self._criterios)+1, columnspan=3)


    def getValue(self, criterio):
        indice = self._criterios.index(criterio)
        return self._elementos[indice].get()

    def setValue(self, criterio, valor):
        indice = self._criterios.index(criterio)
        self._elementos[indice].delete("0","end")
        self._elementos[indice].insert(0, valor)
        
    # en esta funcion se pasa como argumento un comando en el que se sebe poner lo que se hace cuando se pulsa el boton "aceptar" del fielframe, es decir, el resultado que da la funcionalidad
    def funAceptar(self, comando = None):
        tk.Button(self, text="Aceptar", font = ("Verdana", 12), fg = "white", bg = "gray", width=7,height=2, command=comando).grid(pady = (10,10), 
        padx=(10,10), column = 0, row = len(self._criterios)+1)
        
    def funBorrar(self):
        for entry in self._elementos:
            entry.delete("0","end")