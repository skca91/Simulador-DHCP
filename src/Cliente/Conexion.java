
package Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Stephanie Katherine Correa Alcántara C.I. 19.598.574
 */

public class Conexion {

    DatagramSocket socketSalida;
    DatagramPacket salida;
    int puertoSalida;
    int cantBytes = 256;
    byte buffer[];
    ClienteUDP clienteUDP;
    Cliente cliente;
    InetAddress IPservidor;
    String IPasignada;
    InetAddress IP;

    public Conexion(ClienteUDP frame) throws UnknownHostException {
        buffer = new byte[cantBytes];
        puertoSalida = 3000;
        IP = InetAddress.getByName("localhost");
        this.clienteUDP = frame;
        this.IPasignada = "";
    }


    public int getPuertoSalida() {
        return puertoSalida;
    }
    
    public InetAddress getIPservidor() {
        return IPservidor;
    }

    public void setIPservidor(InetAddress IPservidor) {
        this.IPservidor = IPservidor;
    }

    public String getIPasignada() {
        return IPasignada;
    }

    public void setIPasignada(String IPasignada) {
        this.IPasignada = IPasignada;
    }
    
    
    public void Conectar() throws IOException {
        try {
            socketSalida = new DatagramSocket();

            System.out.println("cliente conectado");
            System.out.println("Buscando Servidor(es)");
        } catch (SocketException ex) {
            System.out.println("Error - " + ex.toString());
            System.exit(-1);
        }
        Enviar(IP);
    }

    public void Enviar( InetAddress ip) throws IOException {
        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    String num = Integer.toString(clienteUDP.getId());
                    String mensaje = "Solicitar-"+clienteUDP.getNombre()+"-"+clienteUDP.getMAC()+"-"+num+"-hola";
                    mensaje.getBytes(0, mensaje.length(), buffer, 0);
                    salida = new DatagramPacket(buffer, mensaje.length(), IP, puertoSalida);
                    try {
                        socketSalida.send(salida);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Thread.sleep(200000000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    System.out.println("Solicitud enviada "+mensaje);
                }
            }
        }).start();
    }
    
    public void Enviar(String mensaje, InetAddress ip) throws IOException {
        mensaje.getBytes(0, mensaje.length(), buffer, 0);
        salida = new DatagramPacket(buffer, mensaje.length(), ip, puertoSalida);
        socketSalida.send(salida);
    }
}
