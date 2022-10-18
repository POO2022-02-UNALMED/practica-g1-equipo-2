package uiMain.Opciones;
import gestorAplicacion.modelos.Tienda;
import uiMain.OpcionesMenu;

public class HacerTraslado implements OpcionesMenu {
    Tienda tienda;
    @Override
    public void ejecutar() {
        

    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}

