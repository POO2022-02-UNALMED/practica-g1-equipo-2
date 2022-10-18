package uiMain.Opciones;
import uiMain.OpcionesMenu;
import gestorAplicacion.modelos.Cliente;
import gestorAplicacion.modelos.Tienda;

public class AgregarCliente implements OpcionesMenu {

    Tienda tienda;

    @Override
    public void ejecutar() {
        System.out.println("Ingrese los datos correspondientes al nuevo cliente a agregar: \n");
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("\nIngrese el documento: ");
        String documento = scanner.nextLine();
        System.out.print("\nIngrese el email: ");
        String email = scanner.nextLine();
        tienda.agregarCliente(new Cliente(nombre, documento, email));
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}
