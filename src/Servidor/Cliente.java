
package Servidor;
/**
 *
 * @author Stephanie Katherine Correa Alcántara C.I. 19.598.574
 */
public class Cliente {
    private String IP, MAC, Nombre, id;
    private int Disponible;

    public Cliente() {
        IP = "";
        Disponible = 0;
        MAC = "";
        Nombre = "";
        id = "";
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDisponible() {
        return Disponible;
    }

    public void setDisponible(int Disponible) {
        this.Disponible = Disponible;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    
}
